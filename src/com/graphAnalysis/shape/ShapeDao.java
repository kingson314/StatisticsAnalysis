package com.graphAnalysis.shape;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import consts.Const;

public class ShapeDao {
	private static ShapeDao dao = null;

	public static ShapeDao getInstance() {
		if (dao == null)
			dao = new ShapeDao();
		return dao;
	}

	public Vector<Vector<String>> getVector(Connection con, String sql) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> dnConnVector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				rowValue.add(rs.getString(Const.DateType));
				rowValue.add(rs.getString(Const.TimeType));
				rowValue.add(rs.getString("SHAPETYPE"));
				rowValue.add(rs.getString("ID"));
				dnConnVector.add(rowValue);
			}

		} catch (Exception e) {
			UtilLog.logError(" 获取表格信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return dnConnVector;
	}
}
