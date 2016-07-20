package com.graphAnalysis.amplitude;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;

public class AmplitudeDao {
	private static AmplitudeDao dao = null;

	public static AmplitudeDao getInstance() {
		if (dao == null)
			dao = new AmplitudeDao();
		return dao;
	}

	public Vector<Vector<String>> getVector(Connection con, String sql) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> dnConnVector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sm.executeQuery(sql);
			// 先循环一次记录下最大值的数据
			HashMap<String, String> mapMaxValue = new HashMap<String, String>();
			while (rs.next()) {
				if ("最大".equals(rs.getString("GROUPTYPE"))) {
					mapMaxValue.put(rs.getString("ID60"), rs.getString("VALUE"));
				}
			}
			rs.beforeFirst();
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				if ("最小".equals(rs.getString("GROUPTYPE"))) {
					rowValue.add(rs.getString("ID60"));
					rowValue.add(mapMaxValue.get(rs.getString("ID60")));
					rowValue.add(rs.getString("VALUE"));
					dnConnVector.add(rowValue);
				}
			}
		} catch (Exception e) {
			UtilLog.logWarn(sql);
			UtilLog.logError(" 获取表格信息错误:", e);
		} finally {
			UtilSql.close(rs, sm, con);
		}
		return dnConnVector;
	}
}
