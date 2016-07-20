package com.config.symbol;

import java.util.Vector;
import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.log.UtilLog;
import config.symbol.SymbolDao;

public class SymbolTable {
	private JTable jtable;

	// 构造
	public SymbolTable(String sName) {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("序列号");
			columnName.add("服务器/公司");
			columnName.add("货币名称");
			columnName.add("货币点差");
			columnName.add("小数位数");
			columnName.add("状态");
			columnName.add("顺序号");
			columnName.add("备注说明");

			Vector<?> tableValue = SymbolDao.getInstance().getSymbolVector(sName);
			int[] cellEditableColumn = null;
			int[] columnWidth = new int[] { 50, 100, 100, 80, 80, 50, 50, 200 };
			int[] columnHide = new int[] { 0 };
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			jtable = table.getJtable();
		} catch (Exception e) {
			UtilLog.logError("货币信息列表构造错误:", e);
		} finally {
		}
	}

	// 获取货币信息表格
	public JTable getJtable() {
		return jtable;
	}

}
