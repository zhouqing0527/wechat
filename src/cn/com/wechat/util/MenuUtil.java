package cn.com.wechat.util;

public class MenuUtil {
    public static String getMainMenu() {
	StringBuffer buffer = new StringBuffer();
	buffer.append("您好，我是小q，请回复数字选择服务：").append("\n\n");
	buffer.append("1  历史上的今天").append("\n");
	buffer.append("2  公交查询").append("\n");
	buffer.append("3  周边搜索").append("\n");
	buffer.append("4  歌曲点播").append("\n");
	buffer.append("5  经典游戏").append("\n");
	buffer.append("6  自动翻译").append("\n");
	buffer.append("7  人脸识别").append("\n");
	buffer.append("8  聊天唠嗑").append("\n");
	buffer.append("9  <a href=\"http://www.baidu.com\">百度</a>").append("\n\n");
	buffer.append("回复“?”显示此帮助菜单");
	return buffer.toString();
    }
}
