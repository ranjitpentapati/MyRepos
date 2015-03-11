package com.gogoair.ps.container;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CORS filter - seems to fail to handle 404 etc.
 * 
 */
public class CorsFilter implements ContainerResponseFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

		String origin = requestContext.getHeaderString("Origin");

		if (origin == null)
			return;

		logger.debug("CORS response filtering for origin: " + origin);

		MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		headers.add("Access-Control-Allow-Origin", origin);

		if (null == headers.get("Access-Control-Allow-Methods"))
			headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");

		String reqHead = requestContext.getHeaderString("Access-Control-Request-Headers");

		if (null != reqHead) {
			headers.add("Access-Control-Allow-Headers", reqHead);
		}

		headers.add("Access-Control-Allow-Credentials", "true");
		headers.add("Cors-Provider", "Jersey2");

	}
}
