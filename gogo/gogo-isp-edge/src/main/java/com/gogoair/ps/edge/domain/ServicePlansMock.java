package com.gogoair.ps.edge.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gogoair.ps.edge.session.domain.ServicePlan;

public class ServicePlansMock {

	public List<ServicePlan> getServicePlans(Map<String, Object> attribs) {

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

		return plans;
	}

}
