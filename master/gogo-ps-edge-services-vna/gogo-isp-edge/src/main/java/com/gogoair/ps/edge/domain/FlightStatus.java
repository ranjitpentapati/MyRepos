package com.gogoair.ps.edge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value=Include.NON_NULL)
public class FlightStatus {

	private final String title;
	private final String description;
	private final TailMeta tail;
	private final FlightEvent departure;
	private final FlightEvent arrival;
	private final FlightTripData flightData;

	public FlightStatus(
			@JsonProperty("title") String title,
			@JsonProperty("description") String description,
			@JsonProperty("tail") TailMeta tail,
			@JsonProperty("departure") FlightEvent departure,
			@JsonProperty("arrival") FlightEvent arrival,
			@JsonProperty("flight_data") FlightTripData flightData) {
		super();
		this.title = title;
		this.description = description;
		this.tail = tail;
		this.departure = departure;
		this.arrival = arrival;
		this.flightData = flightData;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("departure")
	public FlightEvent getDeparture() {
		return departure;
	}

	@JsonProperty("arrival")
	public FlightEvent getArrival() {
		return arrival;
	}

	@JsonProperty("flight_data")
	public FlightTripData getFlightData() {
		return flightData;
	}

	@JsonProperty("tail")
	public TailMeta getTail() {
		return tail;
	}

	@Override
	public String toString() {
		return "FlightStatus [title=" + title + ", description=" + description + ", departure=" + departure + ", arrival=" + arrival
				+ ", flightData=" + flightData + "]";
	}

}
