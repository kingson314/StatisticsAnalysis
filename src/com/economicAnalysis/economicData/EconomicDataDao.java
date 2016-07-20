package com.economicAnalysis.economicData;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import com.economic.indicator.EconomicIndicatorDao;

import common.util.date.UtilDate;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;

public class EconomicDataDao {
	private static EconomicDataDao dao = null;
	private Connection con;

	public static EconomicDataDao getInstance() {
		if (dao == null)
			dao = new EconomicDataDao();
		return dao;
	}

	// 构造
	private EconomicDataDao() {
		this.con = UtilJDBCManager.getConnection(Const.DbName);
	}

	// 获取信息表格
	public Vector<Vector<String>> getVector(boolean isAuto, String sql) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			boolean hasUnPublishIndicator = false;
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				rowValue.add(UtilString.isNil(rs.getString("id")));
				rowValue.add(UtilString.isNil(rs.getString("indicatorId")));
				rowValue.add(UtilString.isNil(rs.getString("source")));
				rowValue.add(UtilString.isNil(rs.getString("indicatorName")));
				rowValue.add("["+UtilDate.getDayOfWeek(UtilString.isNil(rs.getString("publishDate")))+"] "+UtilString.isNil(rs.getString("publishDate")));
				rowValue.add(UtilString.isNil(rs.getString("publishTime")));
				rowValue.add(UtilString.isNil(rs.getString("modifyTime")));
				rowValue.add(UtilString.isNil(rs.getString("country")));
				rowValue.add(UtilString.isNil(rs.getString("indicator")));
				rowValue.add(UtilString.isNil(rs.getString("importance")));
				rowValue.add(UtilString.isNil(rs.getString("previousValue")));
				rowValue.add(UtilString.isNil(rs.getString("predictedvalue")));
				rowValue.add(UtilString.isNil(rs.getString("predictedResult")));
				rowValue.add(UtilString.isNil(rs.getString("publishedValue")));
				rowValue.add(" " + UtilString.isNil(rs.getString("compareResult")));
				rowValue.add(UtilString.isNil(rs.getString("indicatoreffect")));
				rowValue.add(UtilString.isNil(rs.getString("effect")));
				rowValue.add(UtilString.isNil(rs.getString("matchrate")));
				rowValue.add(UtilString.isNil(rs.getString("analysisreport")));
//				rowValue.add(UtilString.isNil(rs.getString("isfitexpecteffect")));
//				rowValue.add(UtilString.isNil(rs.getString("amplitude")));
//				rowValue.add(UtilString.isNil(rs.getString("sensitivity")));
//				rowValue.add(UtilString.isNil(rs.getString("advice")));
//				rowValue.add(UtilString.isNil(rs.getString("memo")));
				vector.add(rowValue);
				if (isAuto) {
					// 记录未发布的时间
					if ("侦查中".equals(rs.getString("publishedValue").trim())) {
						if (!"".equals(UtilString.isNil(rs.getString("publishTime")))) {
							EconomicDataTab.getInstance().getListUnPublisTimeFh().add(rs.getString("publishTime"));
							hasUnPublishIndicator = true;
						}
					}
				}
			}
			if (!hasUnPublishIndicator) {
				if (EconomicDataTab.getInstance().timeSchedule.isRunning) {
					EconomicDataTab.getInstance().timeSchedule.stop();
					EconomicDataTab.getInstance().btnAuto.setText("自动刷新");
					EconomicDataTab.getInstance().btnAuto.setForeground(Color.black);
				}
			}
		} catch (Exception e) {
			UtilLog.logError("获取信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return vector;
	}

	public void save(String sql) throws Exception {
		UtilSql.executeUpdate(con, sql, new Object[] {});
	}

	// 根据economic_data中的指标名称匹配economic_indicator里的指标id和国家
	public void match(String economic_data_id, String economic_data_indicator, int source) throws Exception {
		if (source == -1)
			return;
		List<HashMap<String, Object>> list = EconomicIndicatorDao.getInstance().getCountryAndIndicatorId(economic_data_indicator, source);
		Map<String, Object> map = null;
		if (list == null || list.size() == 0) {
			return;
		} else if (list.size() > 1) {
			// ShowMsg.showWarn("出现重复匹配指标，请查证：source[" + source + "];indicator["
			// + economic_data_indicator+"]");
			System.out.println("出现重复匹配指标，请查证：source[" + source + "];indicator[" + economic_data_indicator + "]");
			// 当出现重复匹配时，取字数最多的优先匹配
			int len = 0;
			for (int i = 0; i < list.size(); i++) {
				if (UtilString.isNil(list.get(i).get("INDICATOR")).length() > len) {
					map = list.get(i);
					len = UtilString.isNil(list.get(i).get("INDICATOR")).length();
				}
			}
		} else if (list.size() == 1) {
			map = list.get(0);
		}
		if (map != null) {
			String indicatorId = map.get("ID").toString();
			String country = map.get("COUNTRY").toString();
			if (!"".equals(indicatorId)) {
				String sql = "update economic_data set indicatorId='" + indicatorId + "' , country='" + country + "' where id='" + economic_data_id + "'";
				System.out.println(sql);
				UtilSql.executeUpdate(con, sql, new Object[] {});
			}
		}
	}

	public void syncResult(String sql) throws Exception {
		UtilSql.executeUpdate(con, sql, new Object[] {});
	}
}
