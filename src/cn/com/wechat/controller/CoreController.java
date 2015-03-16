package cn.com.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.com.wechat.service.BaseService;
import cn.com.wechat.service.IStudentService;
import cn.com.wechat.service.MessageService;
import cn.com.wechat.util.MessageUtil;
import cn.com.wechat.util.SignUtil;

@Controller
public class CoreController {

    @Resource
    IStudentService studentService;

    @RequestMapping(value = "coreServlet", method = RequestMethod.GET)
    public void check(HttpServletRequest request, HttpServletResponse response) {
	// 微信加密签名
	String signature = request.getParameter("signature");
	// 时间戳
	String timestamp = request.getParameter("timestamp");
	// 随机数
	String nonce = request.getParameter("nonce");
	// 随机字符串
	String echostr = request.getParameter("echostr");

	PrintWriter out = null;
	try {
	    out = response.getWriter();
	    // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
	    if (SignUtil.checkSignature(signature, timestamp, nonce)) {
		out.print(echostr);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (out != null) {
		out.close();
		out = null;
	    }
	}
    }

    @RequestMapping(value = "coreServlet", method = RequestMethod.POST)
    public void handle(HttpServletRequest request, HttpServletResponse response) {
	// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
	try {
	    request.setCharacterEncoding("UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    e1.printStackTrace();
	}
	response.setCharacterEncoding("UTF-8");

	// 调用核心业务类接收消息、处理消息
	String respMessage = "";
	PrintWriter out = null;
	try {
	    out = response.getWriter();
	    // xml请求解析
	    Map<String, String> requestMap = MessageUtil.parseXml(request);
	    String msgType = requestMap.get("MsgType");

	    // 判断请求消息类型
	    BaseService service = MessageService.checkMessageType(msgType);

	    // 消息处理
	    respMessage = service.handle(requestMap);

	    // respMessage = CoreService.processRequest(request);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (out != null) {
		out.print(respMessage);
		out.close();
		out = null;
	    }
	}
    }

}
