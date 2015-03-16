package cn.com.wechat.entity.req;

/**
 * 文本消息
 * 
 * @author zhqing
 *
 */
public class TextMessage extends BaseMessage {
    // 消息内容
    private String Content;

    public String getContent() {
	return Content;
    }

    public void setContent(String content) {
	Content = content;
    }
}
