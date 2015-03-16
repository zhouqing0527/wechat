package cn.com.wechat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class StringUtil {

    public static String readTxtFile(String filePath) {
	StringBuffer buffer = new StringBuffer();
	try {
	    String encoding = "UTF-8";
	    File file = new File(filePath);
	    if (file.isFile() && file.exists()) { // 判断文件是否存在
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = "";
		while ((lineTxt = bufferedReader.readLine()) != null) {
		    if (lineTxt.equals("window.onload=showDate;")) {
			System.out.println(lineTxt);
		    }
		    buffer.append(lineTxt + "\n");
		}
		read.close();
	    } else {
		System.out.println("找不到指定的文件");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return buffer.toString();
    }

    /**
     * utf编码
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
	String result = source;
	try {
	    result = java.net.URLEncoder.encode(source, "utf-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return result;
    }

    public static void writeToFile(String content, String path, String fileName, String encoding) {
	File file = new File(path);
	if (!file.exists()) {
	    file.mkdirs();
	}
	OutputStreamWriter out = null;
	try {
	    out = new OutputStreamWriter(new FileOutputStream(path + File.separator + fileName), encoding);
	    out.write(content);
	    out.flush();
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
        } finally {
            if(out != null) {
        	try {
	            out.close();
                } catch (IOException e) {
	            e.printStackTrace();
                }
            }
        }
    }
}
