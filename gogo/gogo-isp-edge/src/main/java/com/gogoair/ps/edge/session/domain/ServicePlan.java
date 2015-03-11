package com.gogoair.ps.edge.session.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ServicePlan {

	@JsonProperty("id")
	private final String planId;
	@JsonProperty("title")
	private final String title;
	@JsonProperty("price")
	private final String price;
	@JsonProperty("description")
	private final String description;
	@JsonProperty("details")
	private final String details;

	public ServicePlan(
			@JsonProperty("id") String planId,
			@JsonProperty("title") final String title,
			@JsonProperty("price") final String price,
			@JsonProperty("description") final String description,
			@JsonProperty("details") final String details) {
		this.planId = planId;
		this.title = title;
		this.price = price;
		this.description = description;
		this.details = details;
	}

	public String getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public String getDetails() {
		return details;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "ServicePlan{" +
				"title='" + title + '\'' +
				", price='" + price + '\'' +
				", description='" + description + '\'' +
				", details='" + details + '\'' +
				'}';
	}
}
