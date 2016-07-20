package com.economic.nationaldept;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import common.component.STable;
import common.component.STableBean;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;

public class EconomicNationalDebtTable {
	private JTable jtable;

	// 构造
	public EconomicNationalDebtTable(String sql) {
		try {
			String[] arr = new String[] { "发生日期", "发生时间", "国家", "地区", "重要性", "事件" };
			Vector<String> columnName = UtilConver.arrayToVector(arr);
			Vector<?> tableValue = EconomicNationalDebtDao.getInstance().getVector(sql);
			int[] cellEditableColumn = null;
			int[] columnWidth = new int[] { 100, 60, 60, 100, 50, 500 };
			int[] columnHide = null;
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			jtable = table.getJtable();

			DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if (table.getValueAt(row, column) != null) {
						if (table.getValueAt(row, column).equals("高")) {
							cell.setBackground(Color.green);
						} else if (table.getValueAt(row, column).equals("中")) {
							cell.setBackground(Color.yellow);
						} else {
							cell.setBackground(Color.WHITE);
						}
					}
					return cell;
				}
			};
			jtable.getColumn(columnName.get(4)).setCellRenderer(dcr);
		} catch (Exception e) {
			UtilLog.logError("经济数据列表构造错误:", e);
		} finally {
		}
	}

	// 获取信息表格
	public JTable getJtable() {
		return jtable;
	}
}
