package cn.com.wechat.service;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.wechat.entity.resp.Music;
import cn.com.wechat.util.HttpUtil;
import cn.com.wechat.util.StringUtil;

public class BaiduMusicService {

    /**
     * 解析音乐参数
     * 
     * @param inputStream 百度音乐搜索API返回的输入流
     * @return Music
     */
    @SuppressWarnings("unchecked")
    private static Music parseMusic(InputStream inputStream) {
	Music music = null;
	try {
	    // 使用dom4j解析xml字符串
	    SAXReader reader = new SAXReader();
	    Document document = reader.read(inputStream);
	    // 得到xml根元素
	    Element root = document.getRootElement();
	    // count表示搜索到的歌曲数
	    String count = root.element("count").getText();
	    // 当搜索到的歌曲数大于0时
	    if (!"0".equals(count)) {
		// 普通品质
		List<Element> urlList = root.elements("url");
		// 高品质
		List<Element> durlList = root.elements("durl");

		// 普通品质的encode、decode
		String urlEncode = urlList.get(0).element("encode").getText();
		String urlDecode = urlList.get(0).element("decode").getText();
		// 普通品质音乐的URL
		String url = urlEncode.substring(0, urlEncode.lastIndexOf("/") + 1) + urlDecode;
		if (-1 != urlDecode.lastIndexOf("&"))
		    url = urlEncode.substring(0, urlEncode.lastIndexOf("/") + 1)
			            + urlDecode.substring(0, urlDecode.lastIndexOf("&"));

		// 默认情况下，高音质音乐的URL 等于 普通品质音乐的URL
		String durl = url;

		// 判断高品质节点是否存在
		Element durlElement = durlList.get(0).element("encode");
		if (null != durlElement) {
		    // 高品质的encode、decode
		    String durlEncode = durlList.get(0).element("encode").getText();
		    String durlDecode = durlList.get(0).element("decode").getText();
		    // 高品质音乐的URL
		    durl = durlEncode.substring(0, durlEncode.lastIndexOf("/") + 1) + durlDecode;
		    if (-1 != durlDecode.lastIndexOf("&"))
			durl = durlEncode.substring(0, durlEncode.lastIndexOf("/") + 1)
			                + durlDecode.substring(0, durlDecode.lastIndexOf("&"));
		}
		music = new Music();
		// 设置普通品质音乐链接
		music.setMusicUrl(url);
		// 设置高品质音乐链接
		music.setHQMusicUrl(durl);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return music;
    }

    /**
     * 根据名称和作者搜索音乐
     * 
     * @param musicTitle 音乐名称
     * @param musicAuthor 音乐作者
     * @return Music
     */
    public static Music searchMusic(String musicTitle, String musicAuthor) {
	// 百度音乐搜索地址
	String requestUrl = "http://box.zhangmen.baidu.com/x?op=12&count=1&title={TITLE}$${AUTHOR}$$$$";
	// 对音乐名称、作者进URL编码
	requestUrl = requestUrl.replace("{TITLE}", StringUtil.urlEncodeUTF8(musicTitle));
	requestUrl = requestUrl.replace("{AUTHOR}", StringUtil.urlEncodeUTF8(musicAuthor));
	// 处理名称、作者中间的空格
	requestUrl = requestUrl.replaceAll("\\+", "%20");

	// 查询并获取返回结果
	InputStream inputStream = HttpUtil.doGetStream(requestUrl);
	// 从返回结果中解析出Music
	Music music = parseMusic(inputStream);

	// 如果music不为null，设置标题和描述
	if (null != music) {
	    music.setTitle(musicTitle);
	    // 如果作者不为""，将描述设置为作者
	    if (!"".equals(musicAuthor))
		music.setDescription(musicAuthor);
	    else
		music.setDescription("来自百度音乐");
	}
	return music;
    }

    /**
     * 歌曲点播使用指南
     * 
     * @return
     */
    public static String getUsage() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("歌曲点播操作指南").append("\n\n");
	buffer.append("回复：歌曲+歌名").append("\n");
	buffer.append("例如：歌曲存在").append("\n");
	buffer.append("或者：歌曲存在@汪峰").append("\n\n");
	buffer.append("回复“?”显示主菜单");
	return buffer.toString();
    }

    // 测试方法
    public static void main(String[] args) {
	Music music = searchMusic("相信自己", "零点乐队");
	System.out.println("音乐名称：" + music.getTitle());
	System.out.println("音乐描述：" + music.getDescription());
	System.out.println("普通品质链接：" + music.getMusicUrl());
	System.out.println("高品质链接：" + music.getHQMusicUrl());
    }
}