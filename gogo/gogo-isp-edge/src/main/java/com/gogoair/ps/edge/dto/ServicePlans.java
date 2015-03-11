package com.gogoair.ps.edge.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gogoair.ps.edge.session.domain.ServicePlan;

/**
 * This could be simply handled by Map for serialization. Probably should create a struture.
 * 
 * Created by rboxall on 2/6/15.
 */
@JsonInclude(value = Include.NON_NULL)
public class ServicePlans {

	private final List<ServicePlan> accessPlans;

	public ServicePlans(@JsonProperty("plans") final List<ServicePlan> accessPlans) {
		this.accessPlans = accessPlans;
	}

	@JsonProperty("plans")
	public List<ServicePlan> getAccessPlans() {
		return accessPlans;
	}

	@Override
	public String toString() {
		return "AccessPlans{" + "accessPlans=" + accessPlans + '}';
	}
}
