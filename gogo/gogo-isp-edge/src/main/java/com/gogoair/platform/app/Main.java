package com.gogoair.platform.app;

import java.io.IOException;

import com.gogoair.ps.container.JettyEmbeddedRunner;


/**
 * Run as an embedded Jetty container. You can run directly from IDE or gradle.
 * 
 * @author rboxall
 *
 */
public class Main {

	public static void main(final String[] args) throws IOException {

		new JettyEmbeddedRunner().startServer();

	}
}