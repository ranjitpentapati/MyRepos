package com.gogoair.ps.edge.session.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

/**
 * This stores session information by session token. This allows session persistence between instances. Each user gets
 * its own row in the db with the required meta data (Cassandra could track usage and self delete).
 * 
 * This will also serve as a singleton for all session info. This will leverage DynamoDb for now open to other
 * solutions. Persistence
 * 
 * @author rboxall
 *
 */
public enum SessionManager {
	INSTANCE;

	private static AmazonDynamoDBClient dynamoDB;
	private static final String tableName = "UserSessionPersistence";
	private final static String hashName = "SessionToken";
	// Need to think about this one.
	private final static String rangeName = "Service";

	// TODO: need a thread to pull out all data that is due to be expired.

	static {
		dynamoDB = new AmazonDynamoDBClient(new ClasspathPropertiesFileCredentialsProvider());
		Region region = Region.getRegion(Regions.US_EAST_1);
		dynamoDB.setRegion(region);
	}

	// Return Json... this will include user account info...
	public String getAccountInfo(String serviceName, String sessionId) {

		if (sessionId == null)
			return null;

		return getSession(sessionId, serviceName);

	}

	/**
	 * time to keep ensures we can clean out old data.
	 */
	public void deleteSession(String sessionId, String service) {

		HashMap<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put(hashName, new AttributeValue().withS(sessionId));
		item.put(rangeName, new AttributeValue().withS(service));

		DeleteItemRequest deleteItemRequest = new DeleteItemRequest()
				.withTableName(tableName)
				.withKey(item);

		dynamoDB.deleteItem(deleteItemRequest);

	}

	/**
	 * time to keep ensures we can clean out old data.
	 */
	public void setSession(String sessionId, String service, String json, Date expirationTime) {

		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put(hashName, new AttributeValue().withS(sessionId));
		item.put(rangeName, new AttributeValue().withS(service));
		// item.put("ExpireTime", new AttributeValue().withS(expirationTime.toString()));
		item.put("Json", new AttributeValue().withS(json));

		PutItemRequest putItemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
		dynamoDB.putItem(putItemRequest);

		return;
	}

	public String getSession(String sessionId, String service) {

		Map<String, AttributeValue> lastEvaluatedKey = null;
		do {

			Condition hashKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString()).withAttributeValueList(
					new AttributeValue().withS(sessionId));

			Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString()).withAttributeValueList(
					new AttributeValue().withS(service));

			Map<String, Condition> keyConditions = new HashMap<String, Condition>();
			keyConditions.put(hashName, hashKeyCondition);
			keyConditions.put(rangeName, rangeKeyCondition);

			QueryRequest queryRequest = new QueryRequest().withTableName(tableName).withKeyConditions(keyConditions).withLimit(1)
					.withExclusiveStartKey(lastEvaluatedKey);

			QueryResult result = dynamoDB.query(queryRequest);

			for (Map<String, AttributeValue> item : result.getItems()) {

				String dbSessionId = null;
				String dbService = null;
				String dbJson = null;

				if (item.get(hashName) != null) {
					dbSessionId = item.get(hashName).getS();
				}

				if (item.get(rangeName) != null) {
					dbService = item.get(rangeName).getS();
				}

				if (item.get("Json") != null) {
					dbJson = item.get("Json").getS();
				}

				if (dbService != null && dbService.equals(service) && dbSessionId != null && dbSessionId.equals(sessionId))
					return dbJson;

			}

			lastEvaluatedKey = result.getLastEvaluatedKey();
		} while (lastEvaluatedKey != null);

		return null;
	}

	public Map<String, String> getSessions(String sessionId) {

		Map<String, String> services = new TreeMap<>();

		Map<String, AttributeValue> lastEvaluatedKey = null;
		do {

			Condition hashKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ.toString()).withAttributeValueList(
					new AttributeValue().withS(sessionId));

			Map<String, Condition> keyConditions = new HashMap<String, Condition>();
			keyConditions.put(hashName, hashKeyCondition);

			QueryRequest queryRequest = new QueryRequest().withTableName(tableName).withKeyConditions(keyConditions).withLimit(1)
					.withExclusiveStartKey(lastEvaluatedKey);

			QueryResult result = dynamoDB.query(queryRequest);

			for (Map<String, AttributeValue> item : result.getItems()) {

				// String dbSessionId = null;
				String dbService = null;
				String dbJson = null;

				// if (item.get(hashName) != null) {
				// dbSessionId = item.get(hashName).getS();
				// }

				if (item.get(rangeName) != null) {
					dbService = item.get(rangeName).getS();
				}

				if (item.get("Json") != null) {
					dbJson = item.get("Json").getS();
				}

				services.put(dbService, dbJson);

			}

			lastEvaluatedKey = result.getLastEvaluatedKey();
		} while (lastEvaluatedKey != null);

		return services;
	}

}
