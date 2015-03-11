	package com.gogoair.platform.app;

import javax.inject.Singleton;

import com.codahale.metrics.MetricRegistry;
import com.netflix.hystrix.contrib.codahalemetricspublisher.HystrixCodaHaleMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;

/**
 * Collection of constants - move to leverage netflix oss archaius for anything not truely fixed in the future.
 * 
 * @author rboxall
 *
 */
@Singleton
public class Metrics {

	// I notice that I have codehale here not yammer... need to review this.
	public static final MetricRegistry instance = new MetricRegistry();

	public Metrics() {
		HystrixCodaHaleMetricsPublisher publisher = new HystrixCodaHaleMetricsPublisher(instance);
		HystrixPlugins.getInstance().registerMetricsPublisher(publisher);
	}

}
