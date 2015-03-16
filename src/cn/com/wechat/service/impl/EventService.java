package cn.com.wechat.service.impl;

import java.util.Date;
import java.util.Map;

import cn.com.wechat.entity.resp.TextMessage;
import cn.com.wechat.service.BaseService;
import cn.com.wechat.service.MessageService;
import cn.com.wechat.util.MenuUtil;
import cn.com.wechat.util.MessageUtil;

public class EventService implements BaseService {

    @Override
    public String handle(Map<String, String> requestMap) {
	// 返回值
	String respMessage = "";

	// 发送方帐号（open_id）
	String fromUserName = requestMap.get("FromUserName");
	// 公众帐号
	String toUserName = requestMap.get("ToUserName");

	// 回复文本消息
	TextMessage textMessage = new TextMessage();
	textMessage.setToUserName(fromUserName);
	textMessage.setFromUserName(toUserName);
	textMessage.setCreateTime(new Date().getTime() / 1000);
	textMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);

	// 事件类型
	String eventType = requestMap.get("Event");
	String respContent = "";
	if (eventType.equals(MessageService.EVENT_TYPE_SUBSCRIBE)) { // 订阅
	    respContent = MenuUtil.getMainMenu();
	} else if (eventType.equals(MessageService.EVENT_TYPE_UNSUBSCRIBE)) { // 取消订阅
	    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
	} else if (eventType.equals(MessageService.EVENT_TYPE_CLICK)) { // 自定义菜单点击事件
	    // TODO 自定义菜单权没有开放，暂不处理该类消息
	}
	textMessage.setContent(respContent);
	respMessage = MessageUtil.textMessageToXml(textMessage);

	return respMessage;
    }

}
