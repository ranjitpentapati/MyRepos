package com.gogoair.ps.edge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Created by rboxall on 2/6/15.
 */
@JsonInclude(value = Include.NON_NULL)
public class AirportMeta {

	private final String faa;
	private final String name;
	private final String location;

	public AirportMeta(String faa, String name, String location) {
		this.faa = faa;
		this.name = name;
		this.location = location;
	}

	public String getFaa() {
		return faa;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "AirportMeta{" +
				"faa='" + faa + '\'' +
				", name='" + name + '\'' +
				", location='" + location + '\'' +
				'}';
	}
}
