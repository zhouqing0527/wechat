package cn.com.wechat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.wechat.entity.resp.Article;
import cn.com.wechat.entity.resp.Music;
import cn.com.wechat.entity.resp.MusicMessage;
import cn.com.wechat.entity.resp.NewsMessage;
import cn.com.wechat.entity.resp.TextMessage;
import cn.com.wechat.service.BaiduMusicService;
import cn.com.wechat.service.BaiduTranslateService;
import cn.com.wechat.service.BaseService;
import cn.com.wechat.service.FaceService;
import cn.com.wechat.service.MessageService;
import cn.com.wechat.service.TodayInHistoryService;
import cn.com.wechat.util.MenuUtil;
import cn.com.wechat.util.MessageUtil;

public class TextService implements BaseService {
    private static Logger logger = Logger.getLogger(TextService.class);

    @Override
    public String handle(Map<String, String> requestMap) {
	// 返回值
	String respMessage = "";

	// 发送方帐号（open_id）
	String fromUserName = requestMap.get("FromUserName");
	// 公众帐号
	String toUserName = requestMap.get("ToUserName");
	// 文本消息内容
	String content = requestMap.get("Content").trim();

	logger.info("FromUserName = " + fromUserName + "----ToUserName = " + toUserName + "-----content = " + content);

	// 图文消息集合
	List<Article> articles = new ArrayList<Article>();
	// 图片地址
	String imgPath = "http://23.226.230.24/WeChat/images/";
	// 回复内容
	String respContent = "";

	// 回复文本消息
	TextMessage textMessage = new TextMessage();
	textMessage.setToUserName(fromUserName);
	textMessage.setFromUserName(toUserName);
	textMessage.setCreateTime(new Date().getTime() / 1000);
	textMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);

	if (content.equals("?") || content.equals("？")) {
	    respContent = MenuUtil.getMainMenu();
	    textMessage.setContent(respContent);
	    respMessage = MessageUtil.textMessageToXml(textMessage);
	} else if (MessageUtil.isQqFace(content)) { // 判断用户发送的是否是单个QQ表情
	    // 用户发什么QQ表情，就返回什么QQ表情
	    textMessage.setContent(content);
	    respMessage = MessageUtil.textMessageToXml(textMessage);
	} else if (content.equals("news1")) { // 单图文消息
	    Article article = new Article();
	    article.setTitle("新手开发者接入指南");
	    article.setDescription("登录微信公众平台官网后，在公众平台后台管理页面 - 开发者中心页，点击“修改配置”按钮，填写服务器地址（URL）、Token和EncodingAESKey。");
	    article.setPicUrl(imgPath + "640.jpg");
	    article.setUrl("http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html");
	    articles.add(article);

	    NewsMessage newsMessage = new NewsMessage();
	    newsMessage.setCreateTime(new Date().getTime() / 1000);
	    newsMessage.setFromUserName(toUserName);
	    newsMessage.setToUserName(fromUserName);
	    newsMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_NEWS);
	    newsMessage.setArticleCount(articles.size());
	    newsMessage.setArticles(articles);

	    respMessage = MessageUtil.newsMessageToXml(newsMessage);
	} else if (content.equals("news2")) { // 单图文消息，不含图片
	    Article article = new Article();
	    article.setTitle("新手开发者接入指南");
	    article.setDescription("接入微信公众平台开发，开发者需要按照如下步骤完成：" + "\n\n 1、填写服务器配置；" + "\n\n 2、验证服务器地址的有效性；"
		            + "\n\n 3、依据接口文档实现业务逻辑。");
	    article.setPicUrl("");
	    article.setUrl("http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html");
	    articles.add(article);

	    NewsMessage newsMessage = new NewsMessage();
	    newsMessage.setCreateTime(new Date().getTime() / 1000);
	    newsMessage.setFromUserName(toUserName);
	    newsMessage.setToUserName(fromUserName);
	    newsMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_NEWS);
	    newsMessage.setArticleCount(articles.size());
	    newsMessage.setArticles(articles);

	    respMessage = MessageUtil.newsMessageToXml(newsMessage);
	} else if (content.equals("news3")) { // 多图文消息
	    Article article1 = new Article();
	    article1.setTitle("第一步：填写服务器配置");
	    article1.setDescription("");
	    article1.setPicUrl(imgPath + "640.jpg");
	    article1.setUrl("http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html#.E7.AC.AC.E4.B8.80.E6.AD.A5.EF.BC.9A.E5.A1.AB.E5.86.99.E6.9C.8D.E5.8A.A1.E5.99.A8.E9.85.8D.E7.BD.AE");

	    Article article2 = new Article();
	    article2.setTitle("第二步：验证服务器地址的有效性");
	    article2.setDescription("");
	    article2.setPicUrl(imgPath + "200.jpg");
	    article2.setUrl("http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html#.E7.AC.AC.E4.BA.8C.E6.AD.A5.EF.BC.9A.E9.AA.8C.E8.AF.81.E6.9C.8D.E5.8A.A1.E5.99.A8.E5.9C.B0.E5.9D.80.E7.9A.84.E6.9C.89.E6.95.88.E6.80.A7");

	    Article article3 = new Article();
	    article3.setTitle("第三步：依据接口文档实现业务逻辑");
	    article3.setDescription("");
	    article3.setPicUrl(imgPath + "200.jpg");
	    article3.setUrl("http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html#.E7.AC.AC.E4.B8.89.E6.AD.A5.EF.BC.9A.E4.BE.9D.E6.8D.AE.E6.8E.A5.E5.8F.A3.E6.96.87.E6.A1.A3.E5.AE.9E.E7.8E.B0.E4.B8.9A.E5.8A.A1.E9.80.BB.E8.BE.91");

	    articles.add(article1);
	    articles.add(article2);
	    articles.add(article3);

	    NewsMessage newsMessage = new NewsMessage();
	    newsMessage.setCreateTime(new Date().getTime() / 1000);
	    newsMessage.setFromUserName(toUserName);
	    newsMessage.setToUserName(fromUserName);
	    newsMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_NEWS);
	    newsMessage.setArticleCount(articles.size());
	    newsMessage.setArticles(articles);

	    respMessage = MessageUtil.newsMessageToXml(newsMessage);
	} else if (content.equals("news4")) { // 多图文消息，最后一条消息不含图片
	    Article article1 = new Article();
	    article1.setTitle("公众平台开发者问答系统");
	    article1.setDescription("");
	    article1.setPicUrl(imgPath + "200.jpg");
	    article1.setUrl("http://mp.weixin.qq.com/qa/home/index.html");

	    Article article2 = new Article();
	    article2.setTitle("微信公众平台接口调试工具");
	    article2.setDescription("");
	    article2.setPicUrl(imgPath + "640.jpg");
	    article2.setUrl("http://mp.weixin.qq.com/debug/");

	    Article article3 = new Article();
	    article3.setTitle("如有其他问题，请访问微信公众平台开发者文档官方页面！");
	    article3.setDescription("");
	    article3.setPicUrl("");
	    article3.setUrl("http://mp.weixin.qq.com/wiki/home/index.html");

	    articles.add(article1);
	    articles.add(article2);
	    articles.add(article3);

	    NewsMessage newsMessage = new NewsMessage();
	    newsMessage.setCreateTime(new Date().getTime() / 1000);
	    newsMessage.setFromUserName(toUserName);
	    newsMessage.setToUserName(fromUserName);
	    newsMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_NEWS);
	    newsMessage.setArticleCount(articles.size());
	    newsMessage.setArticles(articles);

	    respMessage = MessageUtil.newsMessageToXml(newsMessage);
	} else if (content.equals("1")) { // 历史上的今天
	    respContent = TodayInHistoryService.getTodayHistoryInfo();
	    textMessage.setContent(respContent);
	    respMessage = MessageUtil.textMessageToXml(textMessage);
	} else if (content.equals("4")) { // 歌曲点播指南
	    respContent = BaiduMusicService.getUsage();
	    textMessage.setContent(respContent);
	    respMessage = MessageUtil.textMessageToXml(textMessage);
	} else if (content.equals("7")) { // 人脸识别指南
	    respContent = FaceService.getUsage();
	    textMessage.setContent(respContent);
	    respMessage = MessageUtil.textMessageToXml(textMessage);
	} else if (content.equals("6")) { // 自动翻译指南
	    textMessage.setContent(BaiduTranslateService.getTranslateUsage());
	    respMessage = MessageUtil.textMessageToXml(textMessage);
	} else if (content.startsWith("翻译")) { // 自动翻译
	    String keyWord = content.replaceAll("^翻译", "").trim();
	    if ("".equals(keyWord)) {
		textMessage.setContent(BaiduTranslateService.getTranslateUsage());
	    } else {
		textMessage.setContent(BaiduTranslateService.translate(keyWord));
	    }
	    respMessage = MessageUtil.textMessageToXml(textMessage);
	} else if (content.startsWith("歌曲")) {
	    // 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
	    String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");
	    // 如果歌曲名称为空
	    if ("".equals(keyWord)) {
		respContent = BaiduMusicService.getUsage();
	    } else {
		String[] kwArr = keyWord.split("@");
		// 歌曲名称
		String musicTitle = kwArr[0];
		// 演唱者默认为空
		String musicAuthor = "";
		if (2 == kwArr.length)
		    musicAuthor = kwArr[1];

		// 搜索音乐
		Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);
		// 未搜索到音乐
		if (null == music) {
		    respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
		} else {
		    // 音乐消息
		    MusicMessage musicMessage = new MusicMessage();
		    musicMessage.setToUserName(fromUserName);
		    musicMessage.setFromUserName(toUserName);
		    musicMessage.setCreateTime(new Date().getTime());
		    musicMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_MUSIC);
		    musicMessage.setMusic(music);
		    respMessage = MessageUtil.musicMessageToXml(musicMessage);
		}
	    }
	    // 未搜索到音乐时返回使用指南
	    if (null == respMessage) {
		if (null == respContent)
		    respContent = BaiduMusicService.getUsage();
		textMessage.setContent(respContent);
		respMessage = MessageUtil.textMessageToXml(textMessage);
	    }
	} else {
	    respContent = "您发送的是文本消息！";
	    textMessage.setContent(respContent);
	    respMessage = MessageUtil.textMessageToXml(textMessage);
	}

	return respMessage;
    }
}
