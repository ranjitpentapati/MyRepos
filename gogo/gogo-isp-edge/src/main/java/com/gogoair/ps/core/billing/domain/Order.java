package com.gogoair.ps.core.billing.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for purchase order.
 * 
 */
@JsonInclude(value = Include.NON_NULL)
public class Order {

	private final String orderId;
	private final UserAccount user;

	public Order(
			@JsonProperty("order_id") final String orderId,
			@JsonProperty("user_account") UserAccount user) {
		this.orderId = orderId;
		this.user = user;
	}

	@JsonProperty("order_id")
	public UserAccount getUser() {
		return user;
	}

	@JsonProperty("user_account")
	public String getOrderId() {
		return orderId;
	}

}
