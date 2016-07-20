package com.news;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import common.util.conver.UtilChinese;
import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.string.UtilString;
import consts.Const;

public class Mt4 {
	private static Mt4 mt4;
	private Timer timer = null;

	public static Mt4 getInstance() {
		if (mt4 == null)
			mt4 = new Mt4();
		return mt4;
	}

	private List<Map<String, Object>> getNews(String filePath) {
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
		try {
			BufferedReader fReader = new BufferedReader(new FileReader(filePath));
			String tmpline = "";
			String dateTime = "";
			String content = "";
			while ((tmpline = fReader.readLine()) != null) {
				tmpline = UtilString.getMessyCode(tmpline).replaceAll("DJNewswires", "");
				tmpline = UtilChinese.getInstance().conver(tmpline, 0);
				while (tmpline.indexOf("20") > 0) {
					dateTime = tmpline.substring(tmpline.indexOf("20"), tmpline.indexOf("DJ") - 2);
					if (tmpline.indexOf("Chinese") > 0)
						content = tmpline.substring(tmpline.indexOf("DJ") + 2, tmpline.indexOf("Chinese"));
					else
						content = tmpline.substring(tmpline.indexOf("DJ") + 2);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("SDATE", UtilConver.dateToStr(UtilConver.strToDate(dateTime.substring(0, 8), "yyyyMMdd"), "yyyyMMdd"));
					map.put("STIME", UtilConver.dateToStr(UtilConver.strToDate(dateTime.substring(8), "HHmm"), "HHmm"));
					map.put("NEWS", content);
					tmpline = tmpline.substring(tmpline.indexOf("Chinese") + "Chinese".length());
					rsList.add(map);
				}
			}
			fReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return rsList;
	}

	public void update() {
		String filePath = "E:\\Program Files (x86)\\MetaTrader - EXNESS\\history\\news.dat";
		List<Map<String, Object>> list = Mt4.getInstance().getNews(filePath);
		Connection con = UtilJDBCManager.getConnection(Const.DbName);
		for (Map<String, Object> map : list) {
			try {
				UtilSql.insert(con, "t_news", map, "seq_base", "id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void start(long delay) {
		if (timer == null) {
			timer = new Timer();// 生成一个Timer对象
			Task realKlineTask = new Task();// 初始化任务
			timer.schedule(realKlineTask, 0, delay);// 还有其他重载方法...
		}
	}

	public void stop() {
		timer.cancel();
		timer.purge();
		timer = null;
	}

	class Task extends TimerTask {// 继承TimerTask类
		public void run() {
			Mt4.getInstance().update();
		}
	}

	public static void main(String[] args) {
		String tmpline = "市場快訊週五亞洲金融市場綜述";
		String simple = UtilChinese.getInstance().conver(tmpline, 0);
		System.out.println(simple);
	}
}
