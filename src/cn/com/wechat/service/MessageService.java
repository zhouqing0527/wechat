package cn.com.wechat.service;

import cn.com.wechat.service.impl.EventService;
import cn.com.wechat.service.impl.ImageService;
import cn.com.wechat.service.impl.LinkService;
import cn.com.wechat.service.impl.LocationService;
import cn.com.wechat.service.impl.TextService;
import cn.com.wechat.service.impl.VideoService;
import cn.com.wechat.service.impl.VoiceService;

public class MessageService {
    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：图片
     */
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 返回消息类型：语音
     */
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 返回消息类型：视频
     */
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：视频
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 请求消息类型：事件推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：LOCATION(上报地理位置事件)
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 判断请求的消息类型
     * @param msgType
     * @return
     * @throws Exception
     */
    public static BaseService checkMessageType(String msgType) throws Exception {
	BaseService message = null;

	if (msgType.equals(REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
	    message = new TextService();
	} else if (msgType.equals(REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
	    message = new ImageService();
	} else if (msgType.equals(REQ_MESSAGE_TYPE_LOCATION)) { // 地理位置消息
	    message = new LocationService();
	} else if (msgType.equals(REQ_MESSAGE_TYPE_LINK)) { // 链接消息
	    message = new LinkService();
	} else if (msgType.equals(REQ_MESSAGE_TYPE_VOICE)) { // 音频消息
	    message = new VoiceService();
	} else if (msgType.equals(REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
	    message = new VideoService();
	} else if (msgType.equals(REQ_MESSAGE_TYPE_EVENT)) { // 事件推送
	    message = new EventService();
	} else {
	    message = new TextService();
	}
	return message;
    }
}
