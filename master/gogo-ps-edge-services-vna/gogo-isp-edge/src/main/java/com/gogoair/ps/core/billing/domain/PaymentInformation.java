package com.gogoair.ps.core.billing.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Payment Information POJO
 * 
 * @author rboxall
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class PaymentInformation {

	private final UserAccount user;
	private final String creditCardNumber;
	private final Integer expirationMonth;
	private final Integer expirationYear;

	public PaymentInformation(UserAccount user, String creditCardNumber, Integer expirationMonth, Integer expirationYear) {
		this.user = user;
		this.creditCardNumber = creditCardNumber;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public Integer getExpirationMonth() {
		return expirationMonth;
	}

	public Integer getExpirationYear() {
		return expirationYear;
	}

	public UserAccount getUser() {
		return user;
	}

}
