package com.gogoair.ps.edge.rest;

import java.time.OffsetDateTime;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gogoair.ps.edge.domain.AirportGate;
import com.gogoair.ps.edge.domain.AirportMeta;
import com.gogoair.ps.edge.domain.FlightEvent;
import com.gogoair.ps.edge.domain.FlightStatus;
import com.gogoair.ps.edge.domain.FlightTripData;
import com.gogoair.ps.edge.domain.TailMeta;

/**
 * Flight information. Totally mocked up for now.
 * 
 * @author rboxall
 *
 */
@Singleton
@Path("v1/flight")
public class EdgeFlightStatus {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFlightStatus() {

		OffsetDateTime estimatedTime = OffsetDateTime.now().minusHours(3);
		OffsetDateTime actualTime = OffsetDateTime.now().minusHours(3).plusMinutes(25);
		AirportMeta airportMeta = new AirportMeta("ATL", "Hartsfield - Jackson Atlanta International Airport", "Atlanta, Georgia, USA");
		AirportGate gate = new AirportGate("A21", "Domestic South");
		FlightEvent departure = new FlightEvent(estimatedTime, actualTime, airportMeta, gate);

		OffsetDateTime estimatedArrival = actualTime.plusMinutes(269);
		OffsetDateTime actualArrival = null;
		AirportMeta destAirportMeta = new AirportMeta("JFK", "John F Kennedy International Airport", "New York, New York, USA");
		AirportGate destGate = new AirportGate("46", "Terminal 1");
		FlightEvent arrival = new FlightEvent(estimatedArrival, actualArrival, destAirportMeta, destGate);

		FlightTripData flightData = new FlightTripData(269, 194, 28, "ON TIME", 2133, "miles");
		TailMeta tail = new TailMeta("3724T", "DL", "006", "PA1", "DL2260", "DL", "2260");
		FlightStatus status = new FlightStatus("ATL -> JFK", "DL2260 Operated by Delta Air Lines", tail, departure, arrival, flightData);

		return Response.ok(status).build();
	}
}