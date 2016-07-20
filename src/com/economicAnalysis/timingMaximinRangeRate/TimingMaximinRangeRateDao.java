package com.economicAnalysis.timingMaximinRangeRate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Vector;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;

public class TimingMaximinRangeRateDao {
	private static TimingMaximinRangeRateDao dao = null;
	private Connection con;

	public static TimingMaximinRangeRateDao getInstance() {
		if (dao == null)
			dao = new TimingMaximinRangeRateDao();
		return dao;
	}

	// 构造
	private TimingMaximinRangeRateDao() {
		this.con = UtilJDBCManager.getConnection(Const.DbName);
	}

	// 获取信息表格
	public Vector<Vector<Object>> getVector(Map<String, String> mapParams) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
		try {
			String range = mapParams.get("range");
			String symbol = mapParams.get("symbol");
			String indicatorId = mapParams.get("indicatorId");
			String country = mapParams.get("country");
			String minute = mapParams.get("minute");
			String importance = mapParams.get("importance");
			String sql = "select a.*,b.indicator,b.importance,b.country from Sa_TimingMaximin_RangeRate a,economic_indicator b " + " where a.indicatorid=b.id ";
			if (!"".equals(UtilString.isNil(range))) {
				sql += " and range='" + range + "' ";
			}
			if (!"".equals(UtilString.isNil(symbol)) && !"全部".equals(UtilString.isNil(symbol))) {
				sql += " and symbol='" + symbol + "' ";
			}
			if (!"".equals(UtilString.isNil(indicatorId))) {
				sql += " and indicatorId='" + indicatorId + "' ";
			}
			if (!"".equals(UtilString.isNil(country)) && !"全部".equals(UtilString.isNil(country))) {
				sql += " and country='" + country + "' ";
			}
			if (!"".equals(UtilString.isNil(importance)) && !"全部".equals(UtilString.isNil(importance))) {
				sql += " and importance='" + importance + "' ";
			}
			if (!"".equals(UtilString.isNil(minute))) {
				sql += " and minute='" + minute + "' ";
			}
			sql += " order by country,indicatorid,range";
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> rowValue = new Vector<Object>();
				rowValue.add(UtilString.isNil(rs.getString("indicatorid")));
				rowValue.add(UtilString.isNil(rs.getString("country")));
				rowValue.add(UtilString.isNil(rs.getString("indicator")));
				rowValue.add(UtilString.isNil(rs.getString("importance")));
				rowValue.add(UtilString.isNil(rs.getString("range")));
				rowValue.add(rs.getInt("count"));
				rowValue.add(rs.getInt("totalCount"));
				rowValue.add(rs.getDouble("rate"));
				rowValue.add(rs.getDouble("average"));
				rowValue.add(rs.getDouble("maxValue"));
				rowValue.add(rs.getDouble("minValue"));
				vector.add(rowValue);
			}
		} catch (Exception e) {
			UtilLog.logError("获取信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return vector;
	}

}
