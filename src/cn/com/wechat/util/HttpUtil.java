package cn.com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
    private static String ENCODING = "UTF-8";
    private static String PROXY_HOST = "139.24.205.16";
    private static int PROXY_PORT = 8080;
    private static String PROXY_SCHEME = "http";
    private static String REQUEST_USERNAME = "z003bc3j";
    private static String REQUEST_PWD = "Password0527";
    private static String REQUEST_WORKSTATION = "AAECNNKG00056L";
    private static String REQUEST_DOMAIN = "cn001";
    
    /**
     * 发送Get请求
     * @param url
     * @return
     */
    public static String doGet(String url) {
	if (StringUtils.isBlank(url)) {
	    return null;
	}
	DefaultHttpClient httpclient = new DefaultHttpClient();
	String result = "";
	try {
	    HttpGet httpGet = new HttpGet(url);
	    HttpResponse response = httpclient.execute(httpGet);
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode == 200) {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    result = EntityUtils.toString(entity, ENCODING);
		}
		EntityUtils.consume(entity);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    httpclient.getConnectionManager().shutdown();
	}
	return result;
    }

    /**
     * Get方式获取InputStream
     * @param url
     * @return
     */
    public static InputStream doGetStream(String url) {
	DefaultHttpClient httpclient = new DefaultHttpClient();
	InputStream is = null;
	try {
	    HttpGet httpGet = new HttpGet(url);
	    HttpResponse response = httpclient.execute(httpGet);
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode == 200) {
		is = response.getEntity().getContent();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    httpclient.getConnectionManager().shutdown();
	}
	return is;
    }

    public static String doPost(String url, Map<String, String> params, String charset) {
	if (StringUtils.isBlank(url)) {
	    return null;
	}
	DefaultHttpClient httpclient = new DefaultHttpClient();
	String result = "";
	try {
	    List<NameValuePair> pairs = null;
	    if (params != null && !params.isEmpty()) {
		pairs = new ArrayList<NameValuePair>(params.size());
		for (Map.Entry<String, String> entry: params.entrySet()) {
		    String value = entry.getValue();
		    if (value != null) {
			pairs.add(new BasicNameValuePair(entry.getKey(), value));
		    }
		}
	    }
	    HttpPost httpPost = new HttpPost(url);
	    if (pairs != null && pairs.size() > 0) {
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, ENCODING));
	    }
	    HttpResponse response = httpclient.execute(httpPost);
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode == 200) {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
		    result = EntityUtils.toString(entity, ENCODING);
		}
		EntityUtils.consume(entity);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    httpclient.getConnectionManager().shutdown();
	}
	return result;
    }

    /**
     * 通过代理发送请求
     * 
     * @param requestUrl
     * @return
     */
    public static String sendProxyRequest(String requestUrl) {
	if (StringUtils.isBlank(requestUrl)) {
	    return null;
	}
	DefaultHttpClient httpclient = new DefaultHttpClient();
	StringBuffer sb = new StringBuffer("");
	BufferedReader in = null;
	try {
	    // 授权用户
	    NTCredentials creds = new NTCredentials(REQUEST_USERNAME, REQUEST_PWD, REQUEST_WORKSTATION,
		            REQUEST_DOMAIN);
	    httpclient.getCredentialsProvider().setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT), creds);
	    // 设置代理
	    HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT, PROXY_SCHEME);
	    httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

	    // 发送请求
	    HttpGet httpget = new HttpGet(requestUrl);
	    HttpResponse response = httpclient.execute(proxy, httpget);

	    // 处理结果
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (200 == statusCode) {
		in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line = "";
		String NL = System.getProperty("line.separator");
		while ((line = in.readLine()) != null) {
		    sb.append(line + NL);
		}
	    }
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    httpclient.getConnectionManager().shutdown();
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return sb.toString();
    }

}
