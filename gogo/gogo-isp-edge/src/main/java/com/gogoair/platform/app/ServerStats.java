package com.gogoair.platform.app;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.annotations.MonitorTags;
import com.netflix.servo.tag.BasicTag;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.TagList;

public class ServerStats {

	@MonitorTags
	private final TagList tags;

	@Monitor(name = "Status", type = DataSourceType.INFORMATIONAL )
	private AtomicReference<String> status = new AtomicReference<String>("UP");

	@Monitor(name = "CurrentConnections", type = DataSourceType.GAUGE)
	private AtomicInteger currentConnections = new AtomicInteger(0);

	@Monitor(name = "TotalConnections", type = DataSourceType.COUNTER)
	private AtomicInteger totalConnections = new AtomicInteger(0);

	@Monitor(name = "BytesIn", type = DataSourceType.COUNTER)
	private AtomicLong bytesIn = new AtomicLong(0L);

	@Monitor(name = "BytesOut", type = DataSourceType.COUNTER)
	private AtomicLong bytesOut = new AtomicLong(0L);

	public ServerStats(String id) {

		tags = BasicTagList.of(new BasicTag("ID", id));
		totalConnections.addAndGet(5);

	}

}