package jp.co.sunarch.sample.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.sunarch.sample.logger.CoustomAppender;

@SpringBootTest
public class SampleServiceTest {

	private CoustomAppender coustomAppender = null;

	@Autowired
	private SampleService service;

	@BeforeEach
	void beforeEach() {
		// めんどいので、BeforeEachに記載する
		// 真面目にやるなら初期化は一度だけして、
		// BeforeEachでclearするのが良いと思う
		final LoggerContext context = LoggerContext.getContext(false);
		final Configuration config = context.getConfiguration();

		coustomAppender = new CoustomAppender(Pattern.compile("jp\\.co\\.sunarch\\.sample.*"));
		coustomAppender.start();
		config.addAppender(coustomAppender);

		// log4j2.xmlに定義されているLogger名
		LoggerConfig loggerConfig = config.getLoggerConfig("jp.co.sunarch");
		// 強制的にログレベル変更
		loggerConfig.setLevel(Level.DEBUG);
		loggerConfig.addAppender(coustomAppender, Level.DEBUG, null);
		// 設定を反映
		context.updateLoggers(config);
	}

	/**
	 * ログが何行取得したかのアサート
	 * @param n
	 */
	protected void assertAppLogPutCnt(int n) {
		assertEquals(n, this.coustomAppender.getCapturedAppEvents().size());
	}

	/**
	 * ログ内容をチェックするアサート
	 * @param level
	 * @param message
	 * @param throwableFlg
	 * @param n
	 */
	protected void assertAppLog(Level level, String message, boolean throwableFlg, int n) {
		List<LogEvent> eventList = this.coustomAppender.getCapturedAppEvents();
		assertFalse(eventList.isEmpty(), "アプリケーションログ出力無し");
		assertThat(eventList.size(), is(greaterThanOrEqualTo(n)));

		LogEvent event = this.coustomAppender.getCapturedAppEvents().get(n - 1);
		assertEquals(level, event.getLevel());
		assertEquals(message, event.getMessage().getFormattedMessage().replace(StringUtils.CR, StringUtils.EMPTY));
		assertTrue(event.getThrownProxy() != null == throwableFlg, "スタックトレース出力" + (throwableFlg ? "無" : "有"));
	}

	@Test
	void test1() {
		service.methodA();

		assertAppLogPutCnt(7);
		assertAppLog(Level.INFO, "SampleService.methodA start", false, 1);
		assertAppLog(Level.DEBUG, "loop count:0", false, 2);
		assertAppLog(Level.DEBUG, "loop count:1", false, 3);
		assertAppLog(Level.DEBUG, "loop count:2", false, 4);
		assertAppLog(Level.DEBUG, "loop count:3", false, 5);
		assertAppLog(Level.DEBUG, "loop count:4", false, 6);
		assertAppLog(Level.INFO, "SampleService.methodA end", false, 7);
	}
}
