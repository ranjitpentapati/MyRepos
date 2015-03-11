package com.gogoair.ps.container;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * Run the code as an embedded Jetty container. Enables code to be run directly via gradle without need of Tomcat. The
 * project is also wired to leverage Tomcat and both approaches share a common JerseyConfig.
 * 
 * @author rboxall
 */
public class JettyEmbeddedRunner {

	public void startServer() {

		try {

			Server server = new Server();
			ServerConnector c = new ServerConnector(server);
			c.setIdleTimeout(1000);
			c.setAcceptQueueSize(10);
			c.setPort(8080);
			c.setHost("localhost");
			ServletContextHandler handler = new ServletContextHandler(server, "/rest", true, false);

			// adds Jersey Servlet with a customized ResourceConfig
			handler.addServlet(new ServletHolder(new ServletContainer(new JerseyResourceConfig())), "/*");

			// add Hystrix
			final HystrixMetricsStreamServlet servlet = new HystrixMetricsStreamServlet();
			final ServletHolder holder = new ServletHolder(servlet);
			handler.addServlet(holder, "/hystrix.stream");

			server.addConnector(c);
			server.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
