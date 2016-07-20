package com.economicAnalysis.timingValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import org.jfree.data.category.DefaultCategoryDataset;


import com.economicAnalysis.timingmaximin.TimingMaximinDao;

import common.util.date.UtilDate;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;

public class TimingValueDao {
	private static TimingValueDao dao = null;
	private Connection con;

	public static TimingValueDao getInstance() {
		if (dao == null)
			dao = new TimingValueDao();
		return dao;
	}

	// 构造
	private TimingValueDao() {
		this.con = UtilJDBCManager.getConnection(Const.DbName);
	}

	// 获取信息表格
	public Vector<Vector<String>> getVector(String sql) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				rowValue.add(UtilString.isNil(rs.getString("indicatorId")));
				rowValue.add(UtilString.isNil(rs.getString("economicDataId")));
				rowValue.add("["+UtilDate.getDayOfWeek(UtilString.isNil(rs.getString("publishDate")))+"] "+UtilString.isNil(rs.getString("publishDate")));
				rowValue.add(UtilString.isNil(rs.getString("publishtime")));
				rowValue.add(UtilString.isNil(rs.getString("country")));
				rowValue.add(UtilString.isNil(rs.getString("indicatorName")));
				rowValue.add(UtilString.isNil(rs.getString("compareresult")));
				rowValue.add(UtilString.isNil(rs.getString("source")));
				vector.add(rowValue);
			}
		} catch (Exception e) {
			UtilLog.logError("获取信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return vector;
	}

	public DefaultCategoryDataset getDataSert(String symbol, String economicDataId, String publishDate, String publishTime) {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		Statement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select  a.*  from sa_timingvalue a  where  " + "   a.symbol='" + symbol + "'  and economicDataId='" + economicDataId  +"'";
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				String priceType = UtilString.isNil(rs.getString("priceType"));

				String lineName = "";// rs.getString("dateLocal");
				if ("2".equals(priceType)) {
					lineName += "高";
				} else if ("3".equals(priceType)) {
					lineName += "低";
				}
				dataSet.addValue((rs.getDouble("valueMinuteBefore5")), lineName, "-5");
				dataSet.addValue((rs.getDouble("valueMinuteBefore4")), lineName, "-4");
				dataSet.addValue((rs.getDouble("valueMinuteBefore3")), lineName, "-3");
				dataSet.addValue((rs.getDouble("valueMinuteBefore2")), lineName, "-2");
				dataSet.addValue((rs.getDouble("valueMinuteBefore1")), lineName, "-1");
				dataSet.addValue(0, lineName, "0");
				dataSet.addValue((rs.getDouble("valueMinuteAfter1")), lineName, "1");
				dataSet.addValue((rs.getDouble("valueMinuteAfter2")), lineName, "2");
				dataSet.addValue((rs.getDouble("valueMinuteAfter3")), lineName, "3");
				dataSet.addValue((rs.getDouble("valueMinuteAfter4")), lineName, "4");
				dataSet.addValue((rs.getDouble("valueMinuteAfter5")), lineName, "5");
				dataSet.addValue((rs.getDouble("valueMinuteAfter6")), lineName, "6");
				dataSet.addValue((rs.getDouble("valueMinuteAfter7")), lineName, "7");
				dataSet.addValue((rs.getDouble("valueMinuteAfter8")), lineName, "8");
				dataSet.addValue((rs.getDouble("valueMinuteAfter9")), lineName, "9");
				dataSet.addValue((rs.getDouble("valueMinuteAfter10")), lineName, "10");
				dataSet.addValue((rs.getDouble("valueMinuteAfter11")), lineName, "11");
				dataSet.addValue((rs.getDouble("valueMinuteAfter12")), lineName, "12");
				dataSet.addValue((rs.getDouble("valueMinuteAfter13")), lineName, "13");
				dataSet.addValue((rs.getDouble("valueMinuteAfter14")), lineName, "14");
				dataSet.addValue((rs.getDouble("valueMinuteAfter15")), lineName, "15");
				dataSet.addValue((rs.getDouble("valueMinuteAfter16")), lineName, "16");
				dataSet.addValue((rs.getDouble("valueMinuteAfter17")), lineName, "17");
				dataSet.addValue((rs.getDouble("valueMinuteAfter18")), lineName, "18");
				dataSet.addValue((rs.getDouble("valueMinuteAfter19")), lineName, "19");
				dataSet.addValue((rs.getDouble("valueMinuteAfter20")), lineName, "20");
				dataSet.addValue((rs.getDouble("valueMinuteAfter21")), lineName, "21");
				dataSet.addValue((rs.getDouble("valueMinuteAfter22")), lineName, "22");
				dataSet.addValue((rs.getDouble("valueMinuteAfter23")), lineName, "23");
				dataSet.addValue((rs.getDouble("valueMinuteAfter24")), lineName, "24");
				dataSet.addValue((rs.getDouble("valueMinuteAfter25")), lineName, "25");
				dataSet.addValue((rs.getDouble("valueMinuteAfter26")), lineName, "26");
				dataSet.addValue((rs.getDouble("valueMinuteAfter27")), lineName, "27");
				dataSet.addValue((rs.getDouble("valueMinuteAfter28")), lineName, "28");
				dataSet.addValue((rs.getDouble("valueMinuteAfter29")), lineName, "29");
				dataSet.addValue((rs.getDouble("valueMinuteAfter30")), lineName, "30");
				dataSet.addValue((rs.getDouble("valueMinuteAfter35")), lineName, "35");
				dataSet.addValue((rs.getDouble("valueMinuteAfter40")), lineName, "40");
				dataSet.addValue((rs.getDouble("valueMinuteAfter45")), lineName, "45");
				dataSet.addValue((rs.getDouble("valueMinuteAfter50")), lineName, "50");
				dataSet.addValue((rs.getDouble("valueMinuteAfter55")), lineName, "55");
				dataSet.addValue((rs.getDouble("valueMinuteAfter60")), lineName, "60");
				dataSet.addValue((rs.getDouble("valueMinuteAfter70")), lineName, "70");
				dataSet.addValue((rs.getDouble("valueMinuteAfter80")), lineName, "80");
				dataSet.addValue((rs.getDouble("valueMinuteAfter90")), lineName, "90");
				dataSet.addValue((rs.getDouble("valueMinuteAfter100")), lineName, "1b");
				dataSet.addValue((rs.getDouble("valueMinuteAfter110")), lineName, "b1");
				dataSet.addValue((rs.getDouble("valueMinuteAfter120")), lineName, "b2");
			}
			TimingMaximinDao.getInstance().getLineDataSet(dataSet, symbol, economicDataId);
		} catch (Exception e) {
			UtilLog.logError("获取DefaultCategoryDataset错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return dataSet;
	}
}
