package cn.com.wechat.service.impl;

import java.util.Date;
import java.util.Map;

import cn.com.wechat.entity.resp.TextMessage;
import cn.com.wechat.service.BaseService;
import cn.com.wechat.service.FaceService;
import cn.com.wechat.service.MessageService;
import cn.com.wechat.util.MessageUtil;

public class ImageService implements BaseService {

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

	// 取得图片地址
	String picUrl = requestMap.get("PicUrl");
	// 人脸检测
	String detectResult = FaceService.detect(picUrl);
	textMessage.setContent(detectResult);
	respMessage = MessageUtil.textMessageToXml(textMessage);

	return respMessage;
    }

}
