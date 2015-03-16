package cn.com.wechat.service;

import com.google.gson.Gson;

import cn.com.wechat.entity.TranslateResult;
import cn.com.wechat.util.HttpUtil;
import cn.com.wechat.util.MessageUtil;
import cn.com.wechat.util.StringUtil;

public class BaiduTranslateService {
    /**
     * @param source
     * @return
     */
    public static String translate(String source) {
	String dst = null;

	// 组装查询地址
	String requestUrl = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=vedsR8MMrPF1XRDq9hk9Iisv&q={keyWord}&from=auto&to=auto";
	// 对参数q的值进行urlEncode utf-8编码
	requestUrl = requestUrl.replace("{keyWord}", StringUtil.urlEncodeUTF8(source));

	// 查询并解析结果
	try {
	    // 查询并获取返回结果
	    String json = HttpUtil.doGet(requestUrl);
	    // 通过Gson工具将json转换成TranslateResult对象
	    TranslateResult translateResult = new Gson().fromJson(json, TranslateResult.class);
	    // 取出translateResult中的译文
	    dst = translateResult.getTrans_result().get(0).getDst();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	if (null == dst)
	    dst = "翻译系统异常，请稍候尝试！";
	return dst;
    }

    /**
     * 翻译指南
     * @return
     */
    public static String getTranslateUsage() {
	StringBuffer buffer = new StringBuffer();
	buffer.append(MessageUtil.emoji(0xe148)).append("自动翻译使用指南").append("\n\n");
	buffer.append("自动翻译为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
	buffer.append("    中 -> 英").append("\n");
	buffer.append("    英 -> 中").append("\n");
	buffer.append("    日 -> 中").append("\n\n");
	buffer.append("使用示例：").append("\n");
	buffer.append("    翻译我是中国人").append("\n");
	buffer.append("    翻译dream").append("\n");
	buffer.append("    翻译さようなら").append("\n\n");
	buffer.append("回复“?”显示主菜单");
	return buffer.toString();
    }
}
