package com.graphAnalysis.frequency;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Vector;
import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.log.UtilLog;

public class FrequencyTable {
	private JTable jtable;

	public FrequencyTable(Connection con, String sql) {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("服务器日期");
			columnName.add("形态类型");
			columnName.add("出现次数");
			Vector<?> tableValue = FrequencyDao.getInstance().getVector(con, sql);
			int[] cellEditableColumn = null;
			int[] columnWidth = { 70, 65, 60 };
			int[] columnHide = null;
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);

			STable table = new STable(bean);
			jtable = table.getJtable();
			this.jtable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					// mouseDoubleClick();
				}
			});
		} catch (Exception e) {
			UtilLog.logError("数据源配置列表构造错误：", e);
		} finally {
		}
	}

	public JTable getJtable() {
		return jtable;
	}
}
