package com.config.account;

import java.util.Vector;

import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.log.UtilLog;

public class AccountTable {
	private JTable jtable;

	// 构造
	public AccountTable(String sName) {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("序列号");
			columnName.add("账户名称");
			columnName.add("账户密码");
			columnName.add("账户类型");
			columnName.add("账户公司名");
			columnName.add("账户资产净值");
			columnName.add("账户余额");
			columnName.add("账户利润");
			columnName.add("账户杠杆比率");
			columnName.add("帐户保证金");
			columnName.add("服务器");
			columnName.add("开户日期");
			columnName.add("开户代理人(IB)");
			columnName.add("开户代理人基本信息");
			columnName.add("操作软件");
			columnName.add("作软件所在计算机Ip地址");
			columnName.add("操作软件所在目录");
			columnName.add("最大单量");
			columnName.add("最小单量");
			columnName.add("状态");
			columnName.add("顺序号");
			columnName.add("备注说明");
			Vector<?> tableValue = AccountDao.getInstance().getAccountVector(sName);
			int[] cellEditableColumn = null;
			int[] columnWidth = new int[] { 50, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 };
			int[] columnHide = null;
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			jtable = table.getJtable();
		} catch (Exception e) {
			UtilLog.logError("账户信息列表构造错误:", e);
		} finally {
		}
	}

	// 获取信息表格
	public JTable getJtable() {
		return jtable;
	}

}
