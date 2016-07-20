package com.config.account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


import common.util.collection.UtilCollection;
import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;

/**
 * 
 * @msg :账户信息据库操作
 * @date :2013-01-13
 */
public class AccountDao {
	public static AccountDao dao = null;
	private Connection con;

	public static AccountDao getInstance() {
		if (dao == null)
			dao = new AccountDao();
		return dao;
	}

	// 构造
	public AccountDao() {
		this.con = UtilJDBCManager.getConnection(Const.DbName);
	}

	// 获取账户信息Bean
	public Account getBean(String accountId) {
		Account bean = null;
		try {
			String sql = "select * from config_account where accountId ='" + accountId + "'";
			Map<String, Object> map = UtilCollection.getObjectMap(UtilSql.QueryA(con, sql));
			bean = (Account) UtilConver.convertMap(map, Account.class);
		} catch (Exception e) {
			UtilLog.logError(" 获取账户信息错误:", e);
		}
		return bean;
	}

	// 获取账户Id数组
	public String[] getAccountArr() {
		String[] idArr = null;
		try {
			String sql = "select '全部'name,0 ord from dual union select distinct name,1 ord from config_account order by ord";
			ArrayList<HashMap<String, String>> list = UtilSql.executeSql(con, sql, new Object[0]);
			idArr = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				idArr[i] = list.get(i).get("NAME");
			}
		} catch (Exception e) {
			UtilLog.logError(" 获取账户错误:", e);
		}
		return idArr;
	}

	// 获取账户信息表格
	public Vector<Vector<String>> getAccountVector(String accountId) {
		Statement sm = null;
		ResultSet rs = null;
		Vector<Vector<String>> accountVector = new Vector<Vector<String>>();
		try {
			sm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (accountId.length() > 1)
				rs = sm.executeQuery("select * from config_account where account like ('%" + accountId + "%')");
			else
				rs = sm.executeQuery("select * from config_account");
			while (rs.next()) {
				Vector<String> rowValue = new Vector<String>();
				rowValue.add(UtilString.isNil(rs.getString("id")));
				rowValue.add(UtilString.isNil(rs.getString("name")));
				rowValue.add(UtilString.isNil(rs.getString("password")));
				rowValue.add(UtilString.isNil(rs.getString("type")));
				rowValue.add(UtilString.isNil(rs.getString("company")));
				rowValue.add(UtilString.isNil(rs.getString("equity")));
				rowValue.add(UtilString.isNil(rs.getString("balance")));
				rowValue.add(UtilString.isNil(rs.getString("profit")));
				rowValue.add(UtilString.isNil(rs.getString("leverage")));
				rowValue.add(UtilString.isNil(rs.getString("margin")));
				rowValue.add(UtilString.isNil(rs.getString("server")));
				rowValue.add(UtilString.isNil(rs.getString("opendate")));
				rowValue.add(UtilString.isNil(rs.getString("agent")));
				rowValue.add(UtilString.isNil(rs.getString("agentMsg")));
				rowValue.add(UtilString.isNil(rs.getString("software")));
				rowValue.add(UtilString.isNil(rs.getString("softwareIp")));
				rowValue.add(UtilString.isNil(rs.getString("softwareDir")));
				rowValue.add(UtilString.isNil(rs.getString("maxLot")));
				rowValue.add(UtilString.isNil(rs.getString("minLot")));
				rowValue.add(UtilString.isNil(rs.getString("state")));
				rowValue.add(UtilString.isNil(rs.getString("ord")));
				rowValue.add(UtilString.isNil(rs.getString("memo")));
				accountVector.add(rowValue);
			}
		} catch (Exception e) {
			UtilLog.logError(" 获取账户信息错误:", e);
		} finally {
			UtilSql.close(rs, sm);
		}
		return accountVector;
	}

	// 新增账户信息
	public void addaccount(Account account) {
		try {
			String sql = "insert into config_account(name,password,type,company,equity,balance,profit,leverage,"
					+ "margin,server,opendate,agent,agentmsg,software,softwareIp,softwareDir,maxLot,minLot,state,ord,memo)" + " values (?,?,?,?,?,   " + "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?,"
					+ "?)";
			UtilSql.executeUpdate(con, sql, new Object[] { account.getName(), account.getPassword(), account.getType(), account.getCompany(), account.getEquity(), account.getBalance(),
					account.getProfit(), account.getLeverage(), account.getMargin(), account.getServer(), account.getOpendate(), account.getAgent(), account.getAgentMsg(), account.getServer(),
					account.getSoftwareIp(), account.getSoftwareDir(), account.getMaxLot(), account.getMinLot(), account.getState(), account.getOpendate(), account.getMemo() });
		} catch (Exception e) {
			UtilLog.logError(" 新增账户信息错误:", e);
		}
	}

	// 修改账户信息
	public void modaccount(Account account) {
		try {
			String sql = "update config_account set name=?,password=?,type=?,company=?,equity=?,balance=?,profit=?,leverage=?,"
					+ "margin=?,server=?,opendate=?,agent=?,agentmsg=?,software=?,softwareIp=?,softwareDir=?,maxLot=?,minLot=? ," + "state=?,ord=?,memo=?  " + "  where  id='" + account.getId() + "'";
			UtilSql.executeUpdate(con, sql, new Object[] { account.getName(), account.getPassword(), account.getType(), account.getCompany(), account.getEquity(), account.getBalance(),
					account.getProfit(), account.getLeverage(), account.getMargin(), account.getServer(), account.getOpendate(), account.getAgent(), account.getAgentMsg(), account.getServer(),
					account.getSoftwareIp(), account.getSoftwareDir(), account.getMaxLot(), account.getMinLot(), account.getState(), account.getOpendate(), account.getMemo() });
		} catch (Exception e) {
			UtilLog.logError(" 修改账户信息错误:", e);
		}
	}

	// 删除账户信息
	public void delaccount(String id) {
		try {
			String sql = "delete from config_account where id='" + id + "'";
			UtilSql.executeUpdate(con, sql, new Object[0]);
		} catch (Exception e) {
			UtilLog.logError(" 删除账户信息错误:", e);
		}
	}

}
