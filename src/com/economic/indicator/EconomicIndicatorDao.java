package com.economic.indicator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


import common.util.UtilFun;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import config.dictionary.DictionaryDao;
import consts.Const;

public class EconomicIndicatorDao {
	private static EconomicIndicatorDao dao = null;
	private Connection con;

	public static EconomicIndicatorDao getInstance() {
		if (dao == null)
			dao = new EconomicIndicatorDao();
		return dao;
	}

	// 构造
	private EconomicIndicatorDao() {
		this.con = UtilJDBCManager.getConnection(Const.DbName);
	}

	// 获取信息表格
	@SuppressWarnings("unchecked")
	public Vector<Vector<String>> getVector(String sql) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(sql);
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Vector rowValue = new Vector();
				rowValue.add(false);
				rowValue.add(UtilString.isNil(rs.getString("id")));
				rowValue.add(UtilString.isNil(rs.getString("country")));
				rowValue.add(UtilString.isNil(rs.getString("indicator")));
				rowValue.add(UtilString.isNil(rs.getString("indicatorfh")));
				rowValue.add(UtilString.isNil(rs.getString("importance")));
				rowValue.add(UtilString.isNil(rs.getString("publishfrequency")));
				rowValue.add(UtilString.isNil(rs.getString("statisticalmethod")));
				rowValue.add(UtilString.isNil(rs.getString("indicatorexplain")));
				rowValue.add(UtilString.isNil(rs.getString("indicatoreffect")));
				rowValue.add(UtilString.isNil(rs.getString("attentionreason")));
				rowValue.add(UtilString.isNil(rs.getString("publishorganization")));
				rowValue.add(UtilString.isNil(rs.getString("state")));
				rowValue.add(UtilString.isNil(rs.getString("memo")));
				rowValue.add(UtilString.isNil(rs.getString("ord")));
				vector.add(rowValue);
			}
		} catch (Exception e) {
			UtilLog.logError("获取信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return vector;
	}

	public void executeUpdate(String sql) {
		UtilSql.exectueSQL(con, sql);
	}

	// public Vector<?> getModel(String country) {
	// String sql =
	// " select '' id,''indicator,-1 ord  from dual union select id,indicator ,ord  from economic_indicator  where country='"
	// + country
	// + "' and state=0 order by ord";
	// List<HashMap<String, Object>> list = UtilSql.QueryM(con, sql);
	// Vector<KeyValue> vector = new Vector<KeyValue>();
	// for (HashMap<String, Object> map : list) {
	// vector.addElement(new KeyValue(map.get("ID").toString(),
	// map.get("INDICATOR").toString()));
	// }
	// return vector;
	// }

	public void updateImportance(String indicatorid, String importance) {
		String sql = "update economic_indicator set importance='" + importance + "' where id='" + indicatorid + "'";
		UtilSql.exectueSQL(con, sql);
	}

	public String[] getIndicatorId(String country) {
		return getIndicator(country, 0);
	}

	public String[] getIndicatorName(String country) {
		return getIndicator(country, 1);
	}

	/**
	 * @Description:默认只显示重要性为 中 高 的指标
	 * @param country
	 * @param type
	 *            :0 表示Id,1表示名字
	 * @return String[]
	 * @date 2014-7-8
	 * @author:fgq
	 */
	public String[] getIndicator(String country, int type) {
		String sql = " select '' id,''indicator,-1 ord ,'高' importance from dual union select id,indicator ,ord,importance  from economic_indicator  where country='" + country
				+ "' and state=0 and importance in ('中','高') order by importance,ord,indicator";
		List<HashMap<String, Object>> list = UtilSql.QueryM(con, sql);
		if (list == null)
			return null;
		int size = list.size();
		String[] arr = new String[size];
		String field = "";
		if (type == 0)
			field = "ID";
		else if (type == 1)
			field = "INDICATOR";

		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i).get(field).toString();
		}
		return arr;
	}

	public List<HashMap<String, Object>> getCountryAndIndicatorId(String economic_data_indicator, int source) {
		String sql = "";
		String[] arrCountry = DictionaryDao.getInstance().getDicionaryArr("国家");
		String country = UtilFun.getCountry(economic_data_indicator, arrCountry);
		if (source == 0) {// 汇通
			sql = "select id,country,indicatorHt as indicator from economic_indicator where instr('" + economic_data_indicator + "',trim(indicatorHt))>0";
		} else if (source == 1) {// 福汇
			sql = "select id,country,indicatorFh as indicator from economic_indicator where instr('" + economic_data_indicator + "',trim(indicatorFh))>0";
		}
		if (!"".equals(country)) {
			sql += "  and country='" + country + "'";
		}
		System.out.println(sql);
		return UtilSql.QueryM(con, sql);
	}
}
