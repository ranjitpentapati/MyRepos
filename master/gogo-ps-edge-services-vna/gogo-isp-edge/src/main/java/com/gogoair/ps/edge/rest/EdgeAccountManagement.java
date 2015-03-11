package com.gogoair.ps.edge.rest;

import java.math.BigDecimal;
import java.net.HttpCookie;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gogoair.ps.core.billing.CreditCardCommand;
import com.gogoair.ps.core.billing.GetUserAccountCommand;
import com.gogoair.ps.core.billing.domain.CreditCardAuthorizationResult;
import com.gogoair.ps.core.billing.domain.Order;
import com.gogoair.ps.core.billing.domain.PaymentInformation;
import com.gogoair.ps.core.billing.domain.UserAccount;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicCounter;
import com.netflix.servo.monitor.Counter;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.StatsTimer;
import com.netflix.servo.monitor.Stopwatch;
import com.netflix.servo.stats.StatsConfig;

/**
 * Account Management Gateway - can we name it something else?
 * 
 * Credit: Leveraged Flux Capacitor and Hystrix samples to get started.
 * 
 * @author rboxall
 *
 */
@Singleton
@Path("v1/amg/")
public class EdgeAccountManagement {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// JMX: com.netflix.servo.COUNTER.EdgeResource_purchase_request_counter
	private static Counter requestCounter = new BasicCounter(MonitorConfig.builder("EdgeResource_purchase_request_counter").build());

	// JMX: com.netflix.servo.COUNTER.EdgeResource__purchase_request_fallback_counter
	private static Counter fallbackCounter = new BasicCounter(MonitorConfig.builder("EdgeResource__purchase_request_fallback_counter")
			.build());

	// JMX: com.netflix.servo.EdgeResource_purchase_request_stats_timer (95th and 99th percentile)
	private static StatsTimer statsTimer = new StatsTimer(MonitorConfig.builder("EdgeResource_purchase_request_stats_timer").build(),
			new StatsConfig.Builder().build());

	// JMX: com.netflix.servo.COUNTER.EdgeResource_purchase_request_timeout_counter
	private static Counter timeoutCounter = new BasicCounter(MonitorConfig
			.builder("EdgeResource_purchase_request_timeout_counter").build());

	// JMX: com.netflix.servo.COUNTER.EdgeResource_purchase_request_error_counter
	private static Counter errorCounter = new BasicCounter(MonitorConfig
			.builder("EdgeResource_purchase_request_error_counter").build());

	static {
		DefaultMonitorRegistry.getInstance().register(requestCounter);
		DefaultMonitorRegistry.getInstance().register(errorCounter);
		DefaultMonitorRegistry.getInstance().register(fallbackCounter);
		DefaultMonitorRegistry.getInstance().register(timeoutCounter);
		DefaultMonitorRegistry.getInstance().register(statsTimer);
	}

	/**
	 * basic html post can't do json so this one can wait. This approach with Map means we can have VERY flexible edge
	 * API not tightly coupled to a POJO.
	 * 
	 * @return
	 */
	@POST
	@Path("signup-json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signupJson(Map<String, Object> attribs) {
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).entity("created").build();
	}

	/**
	 * 
	 * @param account
	 * @return
	 */
	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response signup(
			@HeaderParam("User-Agent") String useragent,
			@CookieParam("USERSESSIONID") String sessionId,
			@FormParam("title") String title) {

		logger.info("We have a user account of :" + title);

		// check the cookie..

		return Response.status(Status.CREATED).type(MediaType.TEXT_PLAIN).entity("welcome to the internet...").build();
	}

	/**
	 * purchase path using hystrix for fault tolerance and servo for metrics
	 * 
	 * @param useragent
	 * @param sessionId
	 * @param card
	 * @return
	 */
	@POST
	@Path("purchase")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response purchasePath(
			@HeaderParam("User-Agent") String useragent,
			@CookieParam("USERSESSIONID") String sessionId,
			@FormParam("orderGuid") String orderGuid,
			@FormParam("userName") String userName,
			@FormParam("card") String creditCardNumber,
			@FormParam("expirationMonth") Integer expirationMonth,
			@FormParam("expirationYear") Integer expirationYear
			) {

		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try
		{
			// See commented out code below for bigger example thanks to Netflix sample.
			// TODO: Add code to detect the user from the cookie? Or should user be submitted here?
			UserAccount user = new GetUserAccountCommand(new HttpCookie("mockKey", "mockValueFromHttpRequest")).execute();

			Order order = new Order(orderGuid, user);

			PaymentInformation payment = new PaymentInformation(user, creditCardNumber, expirationMonth, expirationYear);

			BigDecimal amount = BigDecimal.valueOf(10.00);

			return validatePurchase(order, payment, amount);

		} catch (Exception exc)
		{
			// TODO: add web exception with correct error code.
			throw new RuntimeException("add the error message", exc);
		} finally {
			   context.shutdown();
		}
	}

	/*-
	public void executeSimulatedUserRequestForOrderConfirmationAndCreditCardPayment() throws InterruptedException, ExecutionException {
		
		// fetch user object with http cookies 
		UserAccount user = new GetUserAccountCommand(new HttpCookie("mockKey", "mockValueFromHttpRequest")).execute();

		// fetch the payment information (asynchronously) for the user so the credit card payment can proceed 
		Future<PaymentInformation> paymentInformation = new GetPaymentInformationCommand(user).queue();

		// fetch the order we're processing for the user
		int orderIdFromRequestArgument = 13579;
		Order previouslySavedOrder = new GetOrderCommand(orderIdFromRequestArgument).execute();

		CreditCardCommand credit = new CreditCardCommand(previouslySavedOrder, paymentInformation.get(), new BigDecimal(123.45));
		credit.execute();
	}
	 */

	private Response validatePurchase(final Order order, final PaymentInformation payment, final BigDecimal amount) {

		Stopwatch stopwatch = statsTimer.start();

		try {
			// increment request counter
			requestCounter.increment();

			// invoke service through Hystrix

			HystrixCommand<CreditCardAuthorizationResult> getCommand = new CreditCardCommand(order, payment, amount);

			Future<CreditCardAuthorizationResult> future = getCommand.queue();

			CreditCardAuthorizationResult responseString = future.get();

			// increment the fall back counter if fall-back was used.
			// TODO: this isn't needed as the hystrix framework exports its own metrics on a per-command basis.
			if (getCommand.isResponseFromFallback()) {
				fallbackCounter.increment();
			}

			// increment the timeout counter is timeout was used.
			// TODO: this isn't needed as the hystrix framework exports its own metrics on a per-command basis.
			if (getCommand.isResponseTimedOut()) {
				timeoutCounter.increment();
			}

			// return response
			return Response.ok(responseString).build();
		} catch (Exception ex) {
			// add error counter
			errorCounter.increment();

			logger.error("Error processing the add request.", ex);

			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
		} finally {
			stopwatch.stop();

			statsTimer.record(stopwatch.getDuration(TimeUnit.MILLISECONDS),
					TimeUnit.MILLISECONDS);
		}
	}
}
