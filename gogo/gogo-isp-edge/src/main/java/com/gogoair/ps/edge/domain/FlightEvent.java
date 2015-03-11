package com.gogoair.ps.edge.domain;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rboxall on 2/6/15.
 */
@JsonInclude(value = Include.NON_NULL)
public class FlightEvent {

	@JsonProperty("estimated_time")
	private final OffsetDateTime estimatedTime;

	@JsonProperty("actual_time")
	private final OffsetDateTime actualTime;

	@JsonProperty("airport")
	private final AirportMeta airportMeta;

	@JsonProperty("gate")
	private final AirportGate gate;

	public FlightEvent(
			OffsetDateTime estimatedTime,
			OffsetDateTime actualTime,
			AirportMeta airportMeta,
			AirportGate gate) {

		this.estimatedTime = estimatedTime;
		this.actualTime = actualTime;
		this.airportMeta = airportMeta;
		this.gate = gate;
	}

	public OffsetDateTime getEstimatedTime() {
		return estimatedTime;
	}

	public OffsetDateTime getActualTime() {
		return actualTime;
	}

	public AirportMeta getAirportMeta() {
		return airportMeta;
	}

	public AirportGate getGate() {
		return gate;
	}

	@Override
	public String toString() {
		return "FlightEvent{" +
				"estimatedTime=" + estimatedTime +
				", actualTime=" + actualTime +
				", airportMeta=" + airportMeta +
				", gate=" + gate +
				'}';
	}
}
