package jp.co.sunarch.sample.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringWriter;
import java.io.Writer;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SampleServiceTest2 {

	@Autowired
	private SampleService service;

	@BeforeEach
	void beforeEach() {
	}

	void setLogger(Writer writer) {
		final LoggerContext context = LoggerContext.getContext(false);
		final Configuration config = context.getConfiguration();
		final PatternLayout layout = PatternLayout.createDefaultLayout();

		Appender appender = WriterAppender.createAppender(layout, null, writer, "jp.co.sunarch", false, true);
		appender.start();
		config.addAppender(appender);

		for (final LoggerConfig loggerConfig : config.getLoggers().values()) {
			loggerConfig.addAppender(appender, null, null);
		}
		config.getRootLogger().addAppender(appender, null, null);
	}

	@Test
	void test1() {
		StringWriter writer = new StringWriter();
		setLogger(writer);
		
		service.methodA();

		assertEquals("SampleService.methodA start\r\nSampleService.methodA end\r\n", writer.toString());
	}
}
