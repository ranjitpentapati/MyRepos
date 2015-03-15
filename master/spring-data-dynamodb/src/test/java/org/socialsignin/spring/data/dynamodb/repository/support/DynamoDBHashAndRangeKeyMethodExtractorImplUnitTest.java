package org.socialsignin.spring.data.dynamodb.repository.support;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.socialsignin.spring.data.dynamodb.domain.sample.PlaylistId;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unused")
public class DynamoDBHashAndRangeKeyMethodExtractorImplUnitTest {
	
	private DynamoDBHashAndRangeKeyMethodExtractor<PlaylistId> playlistIdMetadata;	
	private DynamoDBHashAndRangeKeyMethodExtractor<IdClassWithNoAnnotatedMethods> idClassWithNoHashOrRangeKeyMethodMetadata;
	private DynamoDBHashAndRangeKeyMethodExtractor<IdClassWithOnlyAnnotatedHashKeyMethod> idClassWithOnlyHashKeyMethodMetadata;
	private DynamoDBHashAndRangeKeyMethodExtractor<IdClassWithOnlyAnnotatedRangeKeyMethod> idClassWithOnlyRangeKeyMethodMetadata;
	private DynamoDBHashAndRangeKeyMethodExtractor<IdClassWithMulitpleAnnotatedHashKeyMethods> idClassWitMultipleAnnotatedHashKeysMetadata;
	private DynamoDBHashAndRangeKeyMethodExtractor<IdClassWithMulitpleAnnotatedRangeKeyMethods> idClassWitMultipleAnnotatedRangeKeysMetadata;

	
	@Test
	public void testConstruct_WhenHashKeyMethodExists_WhenRangeKeyMethodExists()
	{
		playlistIdMetadata = new DynamoDBHashAndRangeKeyMethodExtractorImpl<PlaylistId>(PlaylistId.class);
		Method hashKeyMethod = playlistIdMetadata.getHashKeyMethod();
		Assert.assertNotNull(hashKeyMethod);
		Assert.assertEquals("getUserName", hashKeyMethod.getName());
		Method rangeKeyMethod = playlistIdMetadata.getRangeKeyMethod();
		Assert.assertNotNull(rangeKeyMethod);
		Assert.assertEquals("getPlaylistName", rangeKeyMethod.getName());
		
		Assert.assertEquals(PlaylistId.class,playlistIdMetadata.getJavaType());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstruct_WhenHashKeyMethodExists_WhenRangeKeyMethodDoesNotExist()
	{
		idClassWithOnlyHashKeyMethodMetadata = new DynamoDBHashAndRangeKeyMethodExtractorImpl<IdClassWithOnlyAnnotatedHashKeyMethod>(IdClassWithOnlyAnnotatedHashKeyMethod.class);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstruct_WhenHashKeyMethodDoesNotExist_WhenRangeKeyMethodExists()
	{
		idClassWithOnlyRangeKeyMethodMetadata = new DynamoDBHashAndRangeKeyMethodExtractorImpl<IdClassWithOnlyAnnotatedRangeKeyMethod>(IdClassWithOnlyAnnotatedRangeKeyMethod.class);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstruct_WhenMultipleHashKeyMethodsExist()
	{
		idClassWitMultipleAnnotatedHashKeysMetadata = new DynamoDBHashAndRangeKeyMethodExtractorImpl<IdClassWithMulitpleAnnotatedHashKeyMethods>(IdClassWithMulitpleAnnotatedHashKeyMethods.class);
		
	}
	

	
	@Test(expected=IllegalArgumentException.class)
	public void testGetConstruct_WhenMultipleRangeKeyMethodsExist()
	{
		idClassWitMultipleAnnotatedRangeKeysMetadata = new DynamoDBHashAndRangeKeyMethodExtractorImpl<IdClassWithMulitpleAnnotatedRangeKeyMethods>(IdClassWithMulitpleAnnotatedRangeKeyMethods.class);

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstruct_WhenNeitherHashKeyOrRangeKeyMethodExist()
	{
		idClassWithNoHashOrRangeKeyMethodMetadata = new DynamoDBHashAndRangeKeyMethodExtractorImpl<IdClassWithNoAnnotatedMethods>(IdClassWithNoAnnotatedMethods.class);
		
	}
	

	
	private class IdClassWithNoAnnotatedMethods {
		
		public String getHashKey(){  return null;}
		public String getRangeKey(){  return null;}

	}
	
	private class IdClassWithOnlyAnnotatedHashKeyMethod {
		
		@DynamoDBHashKey
		public String getHashKey(){  return null;}
		public String getRangeKey(){  return null;}

	}
	
	private class IdClassWithOnlyAnnotatedRangeKeyMethod {
		
		public String getHashKey(){  return null;}
		
		@DynamoDBRangeKey
		public String getRangeKey(){  return null;}

	}
	
	private class IdClassWithMulitpleAnnotatedHashKeyMethods {
		
		@DynamoDBHashKey
		public String getHashKey(){  return null;}
		
		@DynamoDBHashKey
		public String getOtherHashKey(){  return null;}
		
		@DynamoDBRangeKey
		public String getRangeKey(){  return null;}

	}
	
	private class IdClassWithMulitpleAnnotatedRangeKeyMethods {
		
		@DynamoDBHashKey
		public String getHashKey(){  return null;}
		
		@DynamoDBRangeKey
		public String getOtherRangeKey(){  return null;}
		
		@DynamoDBRangeKey
		public String getRangeKey(){  return null;}

	}
	

}
