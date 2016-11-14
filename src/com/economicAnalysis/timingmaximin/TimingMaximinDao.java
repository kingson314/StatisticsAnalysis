package com.economicAnalysis.timingmaximin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Vector;

import org.jfree.data.category.DefaultCategoryDataset;


import common.jfreechart.dataset.Dataset;
import common.util.Math.UtilMath;
import common.util.array.UtilArray;
import common.util.date.UtilDate;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;

public class TimingMaximinDao {
	private static TimingMaximinDao dao = null;
	private Connection con;

	public static TimingMaximinDao getInstance() {
		if (dao == null)
			dao = new TimingMaximinDao();
		return dao;
	}

	// 构造
	private TimingMaximinDao() {
		this.con = UtilJDBCManager.getConnection(Const.DbName);
	}

	// 获取信息表格
	public Vector<Vector<String>> getBarVector(Map<String, String> mapParams) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		try {
			String begDate = mapParams.get("begDate");
			String endDate = mapParams.get("endDate");
			String minute = mapParams.get("minute");
			String symbol = mapParams.get("symbol");
			String indicatorId = mapParams.get("indicatorId");
			String fieldAfter = "MAXIMINMINUTEAFTER" + minute;
			String sql = " select max(h1) h1," + "max(hn) hn, " + "min(l1) l1, " + "min(ln) ln, " + "publishDate, " + "publishTime, " + "country, " + "indicatorid,"
					+ "max(indicator) indicator, " + "max(importance) importance, " + "max(compareResult) compareResult " + "from (select 0 h1, " + "        0 hn, "
					+ "        a.maximinminutebefore1 l1, " + "        a."
					+ fieldAfter
					+ " ln, "
					+ "        b.indicatorid,"
					+ "        b.publishDate, "
					+ "        b.publishTime, "
					+ "        b.country, "
					+ "        b.indicator, "
					+ "        b.importance, "
					+ "        b.compareResult "
					+ "   from sa_timingMaximin a, economic_data b "
					+ "  where a.economicdataid = b.id "
					+ "    and a.symbol = '"
					+ symbol
					+ "'  "
					+ "    and b.indicatorId ='"
					+ indicatorId
					+ "'  "
					+ "    and b.publishDate between '"
					+ begDate
					+ "' and  '"
					+ endDate
					+ "'  "
					+ "    and maximintype = 1 "// --'最低价'
					+ " union all "
					+ " select a.maximinminutebefore1, "
					+ "        a."
					+ fieldAfter
					+ ", "
					+ "        999999999, "
					+ "        999999999, "
					+ "        b.indicatorid,"
					+ "        b.publishDate, "
					+ "        b.publishTime, "
					+ "        b.country, "
					+ "        b.indicator, "
					+ "        b.importance, "
					+ "        b.compareResult "
					+ "   from sa_timingMaximin a, economic_data b "
					+ "  where a.economicdataid = b.id "
					+ "   and a.symbol ='"
					+ symbol
					+ "'  "
					+ "    and b.indicatorId ='"
					+ indicatorId + "' " + "    and b.publishDate between '" + begDate + "' and  '" + endDate + "'  " + "    and maximintype = 0  "// --'最高价'
					+ " )a " + "group by indicatorid,publishDate, publishTime, country, importance " + "order by publishDate desc, publishTime asc";
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				rowValue.add(UtilString.isNil(rs.getString("indicatorid")));
				rowValue.add("["+UtilDate.getDayOfWeek(UtilString.isNil(rs.getString("publishDate")))+"] "+UtilString.isNil(rs.getString("publishDate")));
				rowValue.add(UtilString.isNil(rs.getString("publishTime")));
				rowValue.add(UtilString.isNil(rs.getString("country")));
				rowValue.add(UtilString.isNil(rs.getString("indicator")));
				rowValue.add(UtilString.isNil(rs.getString("importance")));
				rowValue.add(UtilString.isNil(rs.getString("compareResult")));
				rowValue.add(String.valueOf(UtilMath.round(Double.valueOf(UtilString.isNil(rs.getString("hn"), "0")), 2)));
				rowValue.add(String.valueOf(UtilMath.round(Double.valueOf(UtilString.isNil(rs.getString("ln"), "0")), 2)));
				rowValue.add(String.valueOf(UtilMath.round(Double.valueOf(UtilString.isNil(rs.getString("h1"), "0")), 2)));
				rowValue.add(String.valueOf(UtilMath.round(Double.valueOf(UtilString.isNil(rs.getString("l1"), "0")), 2)));
				vector.add(rowValue);
			}
		} catch (Exception e) {
			UtilLog.logError("获取信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return vector;
	}

	/**
	 * @Description:
	 * @param symbol
	 * @param indicatorId
	 * @param begDate
	 * @param endDate
	 * @param beforeAfter
	 *            :时点前，或者时点后[before,after,both]
	 * @param minute
	 *            :时点前后的第N分钟 void
	 * @date Jul 7, 2014
	 * @author:fgq
	 */
	public DefaultCategoryDataset getBarDataSet(Map<String, String> mapParams, String beforeAfter) {
		DefaultCategoryDataset dataset = null;
		try {
			String begDate = mapParams.get("begDate");
			String endDate = mapParams.get("endDate");
			String minute = mapParams.get("minute");
			String symbol = mapParams.get("symbol");
			String indicatorId = mapParams.get("indicatorId");
			// 最大最小值
			String sql = "select  case a.maximinType when  0 then  '最大值'  when 1 then  '最小值' end TYPE,a.*,b.*  from sa_timingMaximin a,economic_data b where  a.economicDataId=b.id and " + "   a.symbol='" + symbol + "'  and b.indicatorId='"
					+ indicatorId + "'   and b.publishDate between '" + begDate + "' and '" + endDate + "'   order by b.publishDate desc,b.publishTime asc, a.maximinType";
			String field = "";
			if ("before".equalsIgnoreCase(beforeAfter)) {
				field = "MAXIMINMINUTEBEFORE" + 1;
			} else if ("after".equalsIgnoreCase(beforeAfter)) {
				field = "MAXIMINMINUTEAFTER" + minute;
			} else if ("both".equalsIgnoreCase(beforeAfter)) {
			}
			String[] fields = new String[] { field, "TYPE", "PUBLISHDATE" };// 大写
			dataset = new Dataset().getBarDataSet(con, sql, fields);
		} catch (Exception e) {
			UtilLog.logError("错误:", e);
		} finally {
		}
		return dataset;
	}

	/**
	 * @Description:
	 * @param symbol
	 * @param indicatorId
	 * @param begDate
	 * @param endDate
	 * @param maximin
	 *            最大最小 0表示最大，1表示最小
	 * @param beforeAfter
	 *            :时点前，或者时点后[before,after,both]
	 * @param minute
	 *            :时点前后的第N分钟
	 * @param range
	 *            :统计大于或者等该点数的概率 [0,30];[30,50];[50,100] void
	 * @date Jul 7, 2014
	 * @author:fgq
	 */
	public DefaultCategoryDataset getPieDataSet(Map<String, String> mapParams, String beforeAfter) {
		DefaultCategoryDataset dataset = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			String begDate = mapParams.get("begDate");
			String endDate = mapParams.get("endDate");
			String minute = mapParams.get("minute");
			String symbol = mapParams.get("symbol");
			String indicatorId = mapParams.get("indicatorId");
			String range = mapParams.get("range");
			String field = "";
			if ("before".equalsIgnoreCase(beforeAfter)) {
				field = "MAXIMINMINUTEBEFORE" + 1;
			} else if ("after".equalsIgnoreCase(beforeAfter)) {
				field = "MAXIMINMINUTEAFTER" + minute;
			} else if ("both".equalsIgnoreCase(beforeAfter)) {
			}
			// 最大最小值
			String sql = "select b.publishDate,max(abs(" + field + "))" + field + " from sa_timingmaximin a,economic_data b  where  a.economicDataId=b.id and " + "   a.symbol='" + symbol + "'  and b.indicatorId='"
					+ indicatorId + "'   and b.publishDate between '" + begDate + "' and '" + endDate + "' group by publishDate  order by publishDate ";
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			double value = 0.0;
			String[][] pointArr = UtilArray.getArray2(range);
			int[] count = new int[pointArr.length];
			while (rs.next()) {
				value = Math.abs(Double.valueOf(UtilString.isNil(rs.getString(field))));
				for (int i = 0; i < pointArr.length; i++) {
					if (value > Double.valueOf(pointArr[i][0]) && value <= Double.valueOf(pointArr[i][1])) {
						count[i] = count[i] + 1;
					}
				}
			}
			dataset = new DefaultCategoryDataset();
			for (int i = 0; i < count.length; i++) {
				if (count[i] == 0)
					continue;
				dataset.addValue(count[i], "极值范围", pointArr[i][0] + "-" + pointArr[i][1]);
			}
		} catch (Exception e) {
			UtilLog.logError("错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return dataset;
	}

	/**
	 * @Description:获取统计报表
	 * @param mapParams
	 * @param beforeAfter
	 * @return StringBuilder
	 * @date Jul 14, 2014
	 * @author:fgq
	 */
	public StringBuilder getReport(Map<String, String> mapParams, String beforeAfter) {
		StringBuilder sbReport = new StringBuilder();
		Statement sm = null;
		ResultSet rs = null;
		try {
			String begDate = mapParams.get("begDate");
			String endDate = mapParams.get("endDate");
			String minute = mapParams.get("minute");
			String symbol = mapParams.get("symbol");
			String indicatorId = mapParams.get("indicatorId");
			String range = mapParams.get("range");
			String field = "";
			String title = "";
			if ("before".equalsIgnoreCase(beforeAfter)) {
				field = "MAXIMINMINUTEBEFORE" + 1;
				title = "前[5]分钟极值频率统计";
			} else if ("after".equalsIgnoreCase(beforeAfter)) {
				field = "MAXIMINMINUTEAFTER" + minute;
				title = "第[" + minute + "]分钟极值频率统计";
			} else if ("both".equalsIgnoreCase(beforeAfter)) {
			}
			// 最大最小值
			String sql = "select b.publishDate,max(abs(" + field + "))" + field + " from sa_timingmaximin a,economic_data b  where  a.economicDataId=b.id and " + "   a.symbol='" + symbol + "'  and b.indicatorId='"
					+ indicatorId + "'   and b.publishDate between '" + begDate + "' and '" + endDate + "' group by publishDate  order by publishDate ";
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			double value = 0.0;
			String[][] pointArr = UtilArray.getArray2(range);
			int[] count = new int[pointArr.length];
			int totalCount = 0;
			while (rs.next()) {
				value = Math.abs(Double.valueOf(UtilString.isNil(rs.getString(field))));
				for (int i = 0; i < pointArr.length; i++) {
					if (value > Double.valueOf(pointArr[i][0]) && value <= Double.valueOf(pointArr[i][1])) {
						count[i] = count[i] + 1;
					}
				}
				totalCount += 1;
			}
			sbReport.append(title).append("\n");
			sbReport.append("总计次数：" + totalCount).append("\n");
			for (int i = 0; i < count.length; i++) {
				if (count[i] == 0)
					continue;	
				double rate = Double.valueOf(count[i]) / totalCount;
				sbReport.append("["+pointArr[i][0] + "-" + pointArr[i][1] + "]出现次数:" + count[i] + ",占比：" + UtilMath.round(rate * 100, 2)).append("%\n");
			}
			sbReport.append("\n");
		} catch (Exception e) {
			UtilLog.logError("错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return sbReport;
	}

	public void getLineDataSet(DefaultCategoryDataset dataSet, String symbol, String economicDataId) {
		Statement sm = null;
		ResultSet rs = null;
		try {
			// 最大最小值
			String sql = "select  a.*  from sa_timingMaximin a   where   " + "   a.symbol='" + symbol + "'  and a.economicDataId='" + economicDataId + "'";
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				String maximinType = UtilString.isNil(rs.getString("maximinType"));
				String lineName = "";// rs.getString("dateLocal");
				if ("0".equals(maximinType)) {
					lineName += "最大值";
				} else if ("1".equals(maximinType)) {
					lineName += "最小值";
				}
				dataSet.addValue((rs.getDouble("maximinMinuteBefore5")), lineName, "-5");
				dataSet.addValue((rs.getDouble("maximinMinuteBefore4")), lineName, "-4");
				dataSet.addValue((rs.getDouble("maximinMinuteBefore3")), lineName, "-3");
				dataSet.addValue((rs.getDouble("maximinMinuteBefore2")), lineName, "-2");
				dataSet.addValue((rs.getDouble("maximinMinuteBefore1")), lineName, "-1");

				dataSet.addValue((rs.getDouble("maximinMinuteAfter1")), lineName, "1");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter2")), lineName, "2");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter3")), lineName, "3");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter4")), lineName, "4");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter5")), lineName, "5");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter6")), lineName, "6");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter7")), lineName, "7");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter8")), lineName, "8");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter9")), lineName, "9");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter10")), lineName, "10");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter11")), lineName, "11");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter12")), lineName, "12");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter13")), lineName, "13");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter14")), lineName, "14");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter15")), lineName, "15");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter16")), lineName, "16");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter17")), lineName, "17");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter18")), lineName, "18");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter19")), lineName, "19");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter20")), lineName, "20");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter21")), lineName, "21");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter22")), lineName, "22");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter23")), lineName, "23");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter24")), lineName, "24");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter25")), lineName, "25");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter26")), lineName, "26");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter27")), lineName, "27");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter28")), lineName, "28");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter29")), lineName, "29");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter30")), lineName, "30");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter35")), lineName, "35");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter40")), lineName, "40");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter45")), lineName, "45");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter50")), lineName, "50");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter55")), lineName, "55");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter60")), lineName, "60");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter70")), lineName, "70");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter80")), lineName, "80");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter90")), lineName, "90");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter100")), lineName, "1b");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter110")), lineName, "b1");
				dataSet.addValue((rs.getDouble("maximinMinuteAfter120")), lineName, "b2");
			}

		} catch (Exception e) {
			UtilLog.logError("获取DefaultCategoryDataset错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
	}
}
