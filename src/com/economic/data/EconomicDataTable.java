package com.economic.data;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import common.component.STable;
import common.component.STableBean;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;

public class EconomicDataTable {
	private JTable jtable;

	// 构造
	public EconomicDataTable(String sql) {
		try {
			String[] arr = new String[] { "发布日期", "发布时间", "国家", "指标名称", "重要性", "前值", "预测值", "公布值", "操作建议" };
			Vector<String> columnName = UtilConver.arrayToVector(arr);
			Vector<?> tableValue = EconomicDataDao.getInstance().getVector(sql);
			int[] cellEditableColumn = null;
			int[] columnWidth = new int[] { 85, 60, 60, 400, 50, 100, 100, 100, 500 };
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
					if (column == 8) {
						if (table.getValueAt(row, 4) != null) {
							if (table.getValueAt(row, 4).equals("高")) {
								cell.setBackground(Color.green);
							} else if (table.getValueAt(row, 4).equals("中")) {
								cell.setBackground(Color.yellow);
							} else {
								cell.setBackground(Color.WHITE);
							}
						}
						return cell;
					}
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
			jtable.getColumn(columnName.get(8)).setCellRenderer(dcr);
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
