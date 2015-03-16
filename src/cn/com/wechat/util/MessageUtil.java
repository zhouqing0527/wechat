package cn.com.wechat.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.wechat.entity.resp.Article;
import cn.com.wechat.entity.resp.MusicMessage;
import cn.com.wechat.entity.resp.NewsMessage;
import cn.com.wechat.entity.resp.TextMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {

    /**
     * 解析微信发来的请求（XML）
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
	// 将解析结果存储在HashMap中
	Map<String, String> map = new HashMap<String, String>();

	// 从request中取得输入流
	InputStream inputStream = request.getInputStream();
	// 读取输入流
	SAXReader reader = new SAXReader();
	Document document = reader.read(inputStream);
	// 得到xml根元素
	Element root = document.getRootElement();
	// 得到根元素的所有子节点
	List<Element> elementList = root.elements();

	// 遍历所有子节点
	for (Element e: elementList)
	    map.put(e.getName(), e.getText());

	// 释放资源
	inputStream.close();
	inputStream = null;

	return map;
    }

    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
	public HierarchicalStreamWriter createWriter(Writer out) {
	    return new PrettyPrintWriter(out) {
		// 对所有xml节点的转换都增加CDATA标记
		boolean cdata = true;

		@SuppressWarnings("rawtypes")
		public void startNode(String name, Class clazz) {
		    super.startNode(name, clazz);
		}

		protected void writeText(QuickWriter writer, String text) {
		    if (cdata) {
			writer.write("<![CDATA[");
			writer.write(text);
			writer.write("]]>");
		    } else {
			writer.write(text);
		    }
		}
	    };
	}
    });

    /**
     * 文本消息对象转换成xml
     * 
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
	xstream.alias("xml", textMessage.getClass());
	return xstream.toXML(textMessage);
    }

    /**
     * 音乐消息对象转换成xml
     * 
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
	xstream.alias("xml", musicMessage.getClass());
	return xstream.toXML(musicMessage);
    }

    /**
     * 图文消息对象转换成xml
     * 
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
	xstream.alias("xml", newsMessage.getClass());
	xstream.alias("item", new Article().getClass());
	return xstream.toXML(newsMessage);
    }

    /**
     * 判断是否是QQ表情
     * 
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
	boolean result = false;

	// 判断QQ表情的正则表达式
	String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
	Pattern p = Pattern.compile(qqfaceRegex);
	Matcher m = p.matcher(content);
	if (m.matches()) {
	    result = true;
	}
	return result;
    }

    /**
     * emoji表情转换(hex -> utf-16)
     * 
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
	return String.valueOf(Character.toChars(hexEmoji));
    }
}
