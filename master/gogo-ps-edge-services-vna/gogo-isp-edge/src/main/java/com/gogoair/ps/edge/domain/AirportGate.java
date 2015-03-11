package com.gogoair.ps.edge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rboxall on 2/6/15.
 */
@JsonInclude(value = Include.NON_NULL)
public class AirportGate {

	private final String name;
	private final String details;

	public AirportGate(@JsonProperty("name") String name, @JsonProperty("details") String details) {
		this.name = name;
		this.details = details;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("details")
	public String getDetails() {
		return details;
	}

	@Override
	public String toString() {
		return "AirportGate{" + "name='" + name + '\'' + ", details='" + details + '\'' + '}';
	}
}
