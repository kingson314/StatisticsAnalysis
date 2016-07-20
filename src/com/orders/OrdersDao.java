package com.orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;

public class OrdersDao {
	private static OrdersDao dao = null;

	public static OrdersDao getInstance() {
		if (dao == null)
			dao = new OrdersDao();
		return dao;
	}

	public Vector<Vector<String>> getVector(String dbName, String sql, String[] fieldsArr) {
		Connection con = UtilJDBCManager.getConnection(dbName);
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				for (String field : fieldsArr) {
					rowValue.add(UtilString.isNil(rs.getString(field)));
				}
				vector.add(rowValue);
			}
		} catch (Exception e) {
			UtilLog.logError(" 获取表格信息错误:", e);
		} finally {
			UtilSql.close(rs, sm, con);
		}
		return vector;
	}
}
