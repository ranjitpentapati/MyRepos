package com.gogoair.ps.container;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.DeflateEncoder;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;

import com.codahale.metrics.JmxReporter;
import com.gogoair.platform.app.Metrics;
import com.gogoair.platform.app.ServerStats;
import com.netflix.servo.monitor.Monitors;

/**
 * Alternate method to configure ServletContext instead of 'web.xml' seems to be more flexible and better supported in
 * Jersey 2.
 */
public class JerseyResourceConfig extends ResourceConfig {

	/**
	 * Register JAX-RS application components.
	 */
	public JerseyResourceConfig() {

		// enable CORS.
		register(CorsFilter.class);

		// register features - jackson for json
		register(JacksonFeature.class);
		register(MultiPartFeature.class);

		// enable gzip
		register(GZipEncoder.class);
		register(EncodingFilter.class);
		register(DeflateEncoder.class);

		// register(GzipWriterInterceptor.class);
		// RB: This didn't seem to work so I created ObjectMapperResolver instead.
		// register(JSR310Module.class);

		// The location of the API's.
		packages("com.gogoair.ps.edge.rest");

		// configure servo
		ServerStats s1 = new ServerStats("s1");
		Monitors.registerObject(s1);

		new Metrics();
		// yammer metrics.
		final JmxReporter reporter = JmxReporter.forRegistry(Metrics.instance).build();
		reporter.start();

	}

}