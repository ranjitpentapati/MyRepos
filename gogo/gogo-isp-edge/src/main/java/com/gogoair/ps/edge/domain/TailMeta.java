package com.gogoair.ps.edge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class TailMeta {

	private final String tailNumber;
	private final String airlineCode;
	private final String airlineCodeIata;
	private final String aircraftType;
	private final String flightNumber;
	private final String flightNumberAlpha;
	private final String flightNumberNumberic;

	public TailMeta(
			@JsonProperty("tail_number") String tailNumber,
			@JsonProperty("airline_code") String airlineCode,
			@JsonProperty("airline_code_iata") String airlineCodeIata,
			@JsonProperty("aircraft_type") String aircraftType,
			@JsonProperty("flight_number") String flightNumber,
			@JsonProperty("flight_number_alpha") String flightNumberAlpha,
			@JsonProperty("flight_number_numberic") String flightNumberNumberic) {
		super();
		this.tailNumber = tailNumber;
		this.airlineCode = airlineCode;
		this.airlineCodeIata = airlineCodeIata;
		this.aircraftType = aircraftType;
		this.flightNumber = flightNumber;
		this.flightNumberAlpha = flightNumberAlpha;
		this.flightNumberNumberic = flightNumberNumberic;
	}

	@JsonProperty("tail_number")
	public String getTailNumber() {
		return tailNumber;
	}

	@JsonProperty("airline_code")
	public String getAirlineCode() {
		return airlineCode;
	}

	@JsonProperty("airline_code_iata")
	public String getAirlineCodeIata() {
		return airlineCodeIata;
	}

	@JsonProperty("aircraft_type")
	public String getAircraftType() {
		return aircraftType;
	}

	@JsonProperty("flight_number")
	public String getFlightNumber() {
		return flightNumber;
	}

	@JsonProperty("flight_number_alpha")
	public String getFlightNumberAlpha() {
		return flightNumberAlpha;
	}

	@JsonProperty("flight_number_numberic")
	public String getFlightNumberNumberic() {
		return flightNumberNumberic;
	}

	@Override
	public String toString() {
		return "TailMeta [tailNumber=" + tailNumber + ", airlineCode=" + airlineCode + ", airlineCodeIata=" + airlineCodeIata
				+ ", aircraftType=" + aircraftType + ", flightNumber=" + flightNumber + ", flightNumberAlpha=" + flightNumberAlpha
				+ ", flightNumberNumberic=" + flightNumberNumberic + "]";
	}

}
