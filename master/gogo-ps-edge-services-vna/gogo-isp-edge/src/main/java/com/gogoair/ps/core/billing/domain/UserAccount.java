package com.gogoair.ps.core.billing.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple POJO to represent a user and their meta-data.
 */
@JsonInclude(value = Include.NON_NULL)
public class UserAccount {

	private final int userId;
	private final String name;
	private final int accountType;

	public UserAccount(
			@JsonProperty("user_id") int userId, 
			@JsonProperty("user_name") String name, 
			@JsonProperty("account_type") int accountType
			) {
		this.userId = userId;
		this.name = name;
		this.accountType = accountType;
	}

	@JsonProperty("user_id")
	public int getUserId() {
		return userId;
	}

	@JsonProperty("user_name")
	public String getName() {
		return name;
	}

	@JsonProperty("account_type")
	public int getAccountType() {
		return accountType;
	}

	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", name=" + name + ", accountType=" + accountType + "]";
	}
	
}
