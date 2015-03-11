package com.gogoair.ps.edge.rest;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.gogoair.platform.app.Metrics;
import com.gogoair.ps.edge.uxd.RedirectEngine;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.annotations.MonitorTags;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.tag.BasicTag;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.TagList;

/**
 * Gets a UI request from a user. Decided what UI to load based on what data is provided.
 * 
 * This is still a work in progress - we need to better understand what the SCE and Partner provides.
 * 
 * @author rboxall
 *
 */

@Singleton
@Path("v1/uxd/")
public class EdgeUxDirector {

	private static final Logger logger = LoggerFactory.getLogger(EdgeUxDirector.class.getName());

	private static Counter requestCounter = new BasicCounter(MonitorConfig.builder("EdgeResource_redirect_request_counter").build());

	private static UxdStats uxdStats = new UxdStats("UxDirector");

	// Metrics Counter
	private final com.codahale.metrics.Counter requests = Metrics.instance.counter(MetricRegistry.name(EdgeUxDirector.class,
			"redirect_request_counter"));

	// Meter
	private final com.codahale.metrics.Meter rxMessages = Metrics.instance.meter(MetricRegistry.name(EdgeUxDirector.class,
			"Rx-Messages",
			"Send", TimeUnit.SECONDS.toString()));
	// Timer
	private final com.codahale.metrics.Timer responseTime = Metrics.instance.timer(MetricRegistry.name(EdgeUxDirector.class,
			"Response-Time"));

	final Histogram resultCounts = Metrics.instance.histogram(MetricRegistry.name(EdgeUxDirector.class, "result-counts"));

	@Context
	HttpServletRequest req;

	static {
		DefaultMonitorRegistry.getInstance().register(requestCounter);
		Monitors.registerObject(uxdStats);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response registerDevice(
			@Context UriInfo ui,
			@HeaderParam("User-Agent") String userAgent,
			// @CookieParam("USERSESSIONID") String sessionCookie,
			@PathParam("deviceid") String deviceid,
			@QueryParam("testmode") String testmode) {

		MetricRegistry.name(EdgeUxDirector.class, "requests", "size");

		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		final Timer.Context tContext = responseTime.time();

		try
		{

			requests.inc();

			uxdStats.inc();
			requestCounter.increment();

			rxMessages.mark();

			Thread.sleep((long) (Math.random() * 1000));

			resultCounts.update((int) (Math.random() * 100));

			String requesting = req.getRequestURI();
			String local = req.getLocalName();
			String server = req.getServerName();

			String ipAddress = req.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = req.getRemoteAddr();
			}

			logger.info("UIX Request: [IP]" + req.getRemoteAddr() + " [user agent]: " + userAgent + " req: " + requesting + local
					+ server);

			Cookie cncookie = new Cookie("USERSESSIONID", deviceid, "/", ".gogoair.com");
			NewCookie cookie = new NewCookie(cncookie, "one hour session cookie", 3600, false);

			String url = new RedirectEngine(userAgent, ipAddress).execute();

			final URI redirectUri = URI.create(url);
			ResponseBuilder builder = Response.temporaryRedirect(redirectUri);
			return builder.cookie(cookie).build();

		} catch (Exception ex)
		{
			// TODO: add web exception with correct error code.
			throw new RuntimeException("add the error message", ex);
		} finally {
			tContext.stop();
			context.shutdown();
		}

	}

}

/**
 * Quick test on different ways to collect and report statistics. Will compare to yammer metrics.
 * 
 * @author rboxall
 *
 */
class UxdStats {

	@MonitorTags
	private final TagList tags;

	@Monitor(name = "redirects", type = DataSourceType.GAUGE)
	private AtomicInteger redirectCount = new AtomicInteger(0);

	UxdStats(String name) {
		tags = BasicTagList.of(new BasicTag("ID", name));
	}

	public void inc() {
		this.redirectCount.incrementAndGet();
	}
}
