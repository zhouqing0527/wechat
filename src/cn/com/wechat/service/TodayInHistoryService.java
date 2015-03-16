package cn.com.wechat.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cn.com.wechat.util.HttpUtil;

public class TodayInHistoryService {

    public static String getTodayHistoryInfo() {
	String source = "";
	String url = "http://www.rijiben.com/";
	source = HttpUtil.doGet(url);
	
//	source = StringUtil.readTxtFile("C:\\Users\\z003bc3j\\Desktop\\history.txt");
	
	return extract(source);
    }

    private static String extract(String source) {
	StringBuffer buffer = null;
	String dataTag = getMonthDay(0); // 今天

	Pattern pattern = Pattern.compile("(.*)(<div class=\"listren\">)(.*?)(</div>)(.*)");
	Matcher matcher = pattern.matcher(source);
	if (matcher.matches()) {
	    buffer = new StringBuffer();

	    if (matcher.group(3).contains(getMonthDay(-1))) {
		dataTag = getMonthDay(-1);
	    }

	    // 拼装标题
	    buffer.append("||  ").append("历史上的").append(dataTag).append("   ||").append("\n\n");

	    // 抽取数据
	    for (String info: matcher.group(3).split("  ")) {
		info = info.replace(dataTag, "").replace("（图）", "").replaceAll("</?[^>]+>", "").replace("&nbsp;", "").trim();
		// 在每行末尾追加2个换行符
		if (!"".equals(info)) {
		    buffer.append(info).append("\n\n");
		}
	    }
	}
	// 将buffer最后两个换行符移除并返回
	return (null == buffer) ? null: buffer.substring(0, buffer.lastIndexOf("\n\n"));
    }

    /**
     * 获取前/后n天的日期(M月d日)
     * 
     * @param diff ： 0表示当天，1表示明天，-1表示昨天
     * @return
     */
    private static String getMonthDay(int diff) {
	DateFormat df = new SimpleDateFormat("M月d日");
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DAY_OF_YEAR, diff);
	return df.format(c.getTime());
    }

    public static void main(String[] args) {
	// System.out.println(getMonthDay(-1));
	System.out.println(getTodayHistoryInfo());
    }

}
