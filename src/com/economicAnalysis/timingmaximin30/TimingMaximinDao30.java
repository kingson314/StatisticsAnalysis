package com.economicAnalysis.timingmaximin30;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import common.util.Math.UtilMath;
import common.util.date.UtilDate;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;

import consts.Const;

public class TimingMaximinDao30 {
	private static TimingMaximinDao30 dao = null;
	private Connection con;

	public static TimingMaximinDao30 getInstance() {
		if (dao == null)
			dao = new TimingMaximinDao30();
		return dao;
	}

	// 构造
	private TimingMaximinDao30() {
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
				rowValue.add("[" + UtilDate.getDayOfWeek(UtilString.isNil(rs.getString("publishDate"))) + "] " + UtilString.isNil(rs.getString("publishDate")));
				rowValue.add(UtilString.isNil(rs.getString("publishtime")));
				rowValue.add(UtilString.isNil(rs.getString("country")));
				rowValue.add(UtilString.isNil(rs.getString("indicatorName")));
				rowValue.add(UtilString.isNil(rs.getString("predictedresult")));
				rowValue.add(UtilString.isNil(UtilMath.round(rs.getDouble("extremumbefore"), 2)));
//				rowValue.add(UtilString.isNil(rs.getString("trendbefore")));
				rowValue.add(UtilString.isNil(rs.getString("statisticalResultBefore")));
				rowValue.add(UtilString.isNil(rs.getString("compareresult")));

				rowValue.add(UtilString.isNil(UtilMath.round(rs.getDouble("maxafter"), 2)));
				rowValue.add(UtilString.isNil(UtilMath.round(rs.getDouble("minafter"), 2)));
				rowValue.add(UtilString.isNil(UtilMath.round(rs.getDouble("extremumafter"), 2)));
//				rowValue.add(UtilString.isNil(rs.getString("trendmaxafter")));
//				rowValue.add(UtilString.isNil(rs.getString("trendminafter")));
//				rowValue.add(UtilString.isNil(rs.getString("trendextremumafter")));
				rowValue.add(UtilString.isNil(rs.getString("statisticalresultmaxafter")));
				rowValue.add(UtilString.isNil(rs.getString("statisticalresultminafter")));
				rowValue.add(UtilString.isNil(rs.getString("statisticalresultextremumafter")));
				String matchAtLeast = "";// 最低匹配 ，只要有一个符合则为符合
				if ("符合".equals(rs.getString("statisticalresultmaxafter")) || "符合".equals(rs.getString("statisticalresultminafter"))
						|| "符合".equals(rs.getString("statisticalresultextremumafter"))) {
					matchAtLeast = "符合";
				}
				rowValue.add(UtilString.isNil(matchAtLeast));
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

}
