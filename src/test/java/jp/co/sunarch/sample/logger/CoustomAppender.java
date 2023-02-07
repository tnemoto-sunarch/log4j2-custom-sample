package jp.co.sunarch.sample.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.layout.PatternLayout;

import lombok.Getter;

// プラグイン化したい場合は必要だけど、
// 今回はテスト時の一時切り替えなのでそのまま使用する
//@Plugin(
//		  name = "CoustomAppender", 
//		  category = Core.CATEGORY_NAME, 
//		  elementType = Appender.ELEMENT_TYPE)
@Getter
public class CoustomAppender extends AbstractAppender {
	
	private List<LogEvent> capturedAppEvents = new ArrayList<LogEvent>();
	private List<LogEvent> capturedOutReqEvents = new ArrayList<LogEvent>();
	private List<LogEvent> capturedOutResEvents = new ArrayList<LogEvent>();
	
	private Pattern regex = null;

	public CoustomAppender(Pattern regex) {
        super("CoustomAppender", null, PatternLayout.createDefaultLayout(), false, Property.EMPTY_ARRAY);
        this.regex = regex;
    }

// プラグイン化したい場合は必要だけど、
// 今回はテスト時の一時切り替えなのでそのまま使用する
//    @PluginFactory
//    public static CoustomAppender createAppender(
//      @PluginAttribute("name") String name,
//      @PluginElement("Filter") Filter filter,
//      @PluginElement("Layout") Layout<? extends Serializable> layout) {
//        return new CoustomAppender(name, filter, layout);
//    }

	@Override
	public void append(LogEvent event) {
		/*
		 * 拾いたいログだけ拾う処理
		 */
		if(event.getMarker() == null && regex.matcher(event.getLoggerName()).find()) {
			capturedAppEvents.add(event);
		} else {
//			if(event.getMarker() != null) {
//				switch(event.getMarker().getName()) {
//				case "out-request":
//					capturedOutReqEvents.add(event);
//					break;
//				case "out-response":
//					capturedOutResEvents.add(event);
//					break;
//				}
//			}
		}
	}

	public void clear() {
		this.capturedAppEvents.clear();
		this.capturedOutReqEvents.clear();
		this.capturedOutResEvents.clear();
	}
}
