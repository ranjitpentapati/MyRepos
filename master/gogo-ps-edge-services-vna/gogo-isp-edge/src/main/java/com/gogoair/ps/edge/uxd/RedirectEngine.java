package com.gogoair.ps.edge.uxd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gogoair.platform.app.GogoEdgeConstants;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * Place holder for hystrix based redirect logic.
 * 
 * TODO: For flexibility best to send all values in future probably as a Map.
 * 
 * @author rboxall
 *
 */
public class RedirectEngine extends HystrixCommand<String> {

	private static final Logger logger = LoggerFactory.getLogger(RedirectEngine.class);

	private final String userAgent;
	private final String ipAddress;

	public RedirectEngine(String userAgent, String ipAddress) {
		super(
				Setter.withGroupKey(
						HystrixCommandGroupKey.Factory
								.asKey(GogoEdgeConstants.EDGE_TIER_HYSTRIX_GROUP))
						.andCommandKey(
								HystrixCommandKey.Factory
										.asKey(GogoEdgeConstants.EDGE_TIER_HYSTRIX_REDIRECT_ENGINE_KEY))
						.andThreadPoolKey(
								HystrixThreadPoolKey.Factory
										.asKey(GogoEdgeConstants.EDGE_TIER_HYSTRIX_THREAD_POOL)));

		this.userAgent = userAgent;
		this.ipAddress = ipAddress;
	}

	@Override
	protected String run() {
		try {

			logger.info("UIX Request: [IP]" + ipAddress + " with [user-agent]: " + userAgent);

			return "/servicemock.html";

		} catch (Exception exc) {
			throw new RuntimeException("Exception", exc);
		}

	}

	@Override
	protected String getFallback() {
		return GogoEdgeConstants.EDGE_DEFAULT_REDIRECT;
	}
}
