package com.economic.holiday;

import java.util.Vector;
import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;

public class EconomicHolidayTable {
	private JTable jtable;

	// 构造
	public EconomicHolidayTable(String sql) {
		try {
			String[] arr = new String[] { "发生日期", "发生时间", "国家", "地区", "事件" };
			Vector<String> columnName = UtilConver.arrayToVector(arr);
			Vector<?> tableValue = EconomicHolidayDao.getInstance().getVector(sql);
			int[] cellEditableColumn = null;
			int[] columnWidth = new int[] { 100, 60, 60, 100, 500 };
			int[] columnHide = null;
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			jtable = table.getJtable();

		} catch (Exception e) {
			UtilLog.logError("假期发布列表构造错误:", e);
		} finally {
		}
	}

	// 获取信息表格
	public JTable getJtable() {
		return jtable;
	}
}
