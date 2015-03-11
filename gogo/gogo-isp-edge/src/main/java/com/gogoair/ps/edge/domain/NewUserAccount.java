package com.gogoair.ps.edge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple structure for the account create page.
 * 
 * @author rboxall
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class NewUserAccount {

	private final String firstName;
	private final String lastName;
	private final String emailAddress;
	private final String country;
	private final String password;
	private final String reminderQuestion;
	private final String reminderAnswer;
	private final Boolean allowMarketingEmail;

	public NewUserAccount(
			@JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") String lastName,
			@JsonProperty("email_address") String emailAddress,
			@JsonProperty("country") String country,
			@JsonProperty("password") String password,
			@JsonProperty("reminder_question") String reminderQuestion,
			@JsonProperty("reminder_answer") String reminderAnswer,
			@JsonProperty("allow_marketing_email") Boolean allowMarketingEmail) {

		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.country = country;
		this.password = password;
		this.reminderQuestion = reminderQuestion;
		this.reminderAnswer = reminderAnswer;
		this.allowMarketingEmail = allowMarketingEmail;
	}

	@JsonProperty("first_name")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("last_name")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("email_address")
	public String getEmailAddress() {
		return emailAddress;
	}

	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	@JsonProperty("reminder_question")
	public String getReminderQuestion() {
		return reminderQuestion;
	}

	@JsonProperty("reminder_answer")
	public String getReminderAnswer() {
		return reminderAnswer;
	}

	@JsonProperty("allow_marketing_email")
	public Boolean getAllowMarketingEmail() {
		return allowMarketingEmail;
	}

	@Override
	public String toString() {
		return "UserMeta [firstName=" + firstName + ", lastName=" + lastName + ", emailAddress=" + emailAddress + ", country=" + country
				+ ", password=" + password + ", reminderQuestion=" + reminderQuestion + ", reminderAnswer=" + reminderAnswer
				+ ", allowMarketingEmail=" + allowMarketingEmail + "]";
	}

}
