package com.economic.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


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
				rowValue.add("["+UtilDate.getDayOfWeek(UtilString.isNil(rs.getString("publishDate")))+"] "+UtilString.isNil(rs.getString("publishDate")));
				rowValue.add(UtilString.isNil(rs.getString("publishTime")));
				rowValue.add(UtilString.isNil(rs.getString("country")));
				rowValue.add(UtilString.isNil(rs.getString("indicator")));
				rowValue.add(UtilString.isNil(rs.getString("importance")));
				rowValue.add(UtilString.isNil(rs.getString("previousValue")));
				rowValue.add(UtilString.isNil(rs.getString("previousValue")));
				rowValue.add(UtilString.isNil(rs.getString("publishedValue")));
				rowValue.add(UtilString.isNil(rs.getString("memo")));
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
