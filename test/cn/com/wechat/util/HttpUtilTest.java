package cn.com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.junit.Test;

public class HttpUtilTest extends HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtilTest.class);

    @Test
    public void testSendGetRequest() throws IOException {
	String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=vedsR8MMrPF1XRDq9hk9Iisv&q=today&from=auto&to=auto";
	// String resp = HttpUtil.sendGetRequest(url);

	// String resp = HttpUtil.sendProxyRequest(url);
	// logger.info(resp);

	InputStream is = HttpUtil.doGetStream(url);
	InputStreamReader inputStreamReader = new InputStreamReader(is, "utf-8");
	BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

	// 读取返回结果
	StringBuffer buffer = new StringBuffer();
	String str = null;
	while ((str = bufferedReader.readLine()) != null) {
	    buffer.append(str);
	}
	logger.info(buffer.toString());
    }
    
    @Test
    public void testSendProxyRequest() throws IOException {
	String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=vedsR8MMrPF1XRDq9hk9Iisv&q=today&from=auto&to=auto";

	String str = HttpUtil.sendProxyRequest(url);
	logger.info(str);
    }

}
