package com.gogoair.ps.edge.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gogoair.ps.edge.dto.ServicePlans;
import com.gogoair.ps.edge.session.domain.ServicePlan;

@Singleton
@Path("v1/plans/")
public class EdgeServicePlans {

	public EdgeServicePlans() {
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentPlans() {
		
		//TODO: Wire to interface and also add support for localization. 

		ServicePlan plan1 = new ServicePlan("plan1", "All-Day Pass", "$20.95",
				"Im-air internet access, all day long, on Delta Air Lines",
				"details about this plan.");

		ServicePlan plan2 = new ServicePlan("plan2", "GOGO 30-MINUTE PASS", "$7.00",
				"30 minutes of Internet access on this flight.",
				"details about this plan.");

		ServicePlan plan3 = new ServicePlan("plan3", "Gogo 1-Hour Pass", "$10.50",
				"One hour of internet access on this flight.",
				"details about this plan.");

		ServicePlan plan4 = new ServicePlan("plan4", "Buy 2 Hours, Get One Free!",
				"$21.00",
				"3 hours for the price of 2, valid this flight only.",
				"details about this plan.");

		ServicePlan plan5 = new ServicePlan("plan5", "Delta Air Lines Unlimited",
				"$249.90/Mo",
				"3 hours for the price of 2, valid this flight only.",
				"details about this plan.");

		List<ServicePlan> plans = new ArrayList<>();
		plans.add(plan1);
		plans.add(plan2);
		plans.add(plan3);
		plans.add(plan4);
		plans.add(plan5);

		ServicePlans accessPlans = new ServicePlans(plans);

		return Response.ok(accessPlans).build();
	}
}