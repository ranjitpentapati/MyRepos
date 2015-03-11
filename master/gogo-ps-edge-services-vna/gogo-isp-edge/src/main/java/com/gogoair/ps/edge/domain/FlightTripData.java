package com.gogoair.ps.edge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class FlightTripData {

	private final Integer totalTime;
	private final Integer remainingTime;
	private final Integer progress;
	private final String status;
	private final Integer distance;
	private final String unit;

	public FlightTripData(
			@JsonProperty("total_time") Integer totalTime,
			@JsonProperty("remaining_time") Integer remainingTime,
			@JsonProperty("progress") Integer progress, String status,
			@JsonProperty("distance") Integer distance,
			@JsonProperty("unit") String unit) {
		super();
		this.totalTime = totalTime;
		this.remainingTime = remainingTime;
		this.progress = progress;
		this.status = status;
		this.distance = distance;
		this.unit = unit;
	}

	@JsonProperty("total_time")
	public Integer getTotalTime() {
		return totalTime;
	}

	@JsonProperty("remaining_time")
	public Integer getRemainingTime() {
		return remainingTime;
	}

	@JsonProperty("progress")
	public Integer getProgress() {
		return progress;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("distance")
	public Integer getDistance() {
		return distance;
	}

	@JsonProperty("unit")
	public String getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return "FlightData [totalTime=" + totalTime + ", remainingTime=" + remainingTime + ", progress=" + progress + ", status=" + status
				+ ", distance=" + distance + ", unit=" + unit + "]";
	}

}
