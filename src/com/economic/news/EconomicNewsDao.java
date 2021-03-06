package com.economic.news;

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

public class EconomicNewsDao {
	private static EconomicNewsDao dao = null;
	private Connection con;

	public static EconomicNewsDao getInstance() {
		if (dao == null)
			dao = new EconomicNewsDao();
		return dao;
	}

	// 构造
	private EconomicNewsDao() {
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
				rowValue.add("["+UtilDate.getDayOfWeek(UtilString.isNil(rs.getString("occurDate")))+"] "+UtilString.isNil(rs.getString("occurDate")));
				rowValue.add(UtilString.isNil(rs.getString("occurTime")));
				rowValue.add(UtilString.isNil(rs.getString("news")));
				rowValue.add(UtilString.isNil(rs.getString("newsDetail")));
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
