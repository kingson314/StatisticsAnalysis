package com.graphAnalysis.amplitude;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Vector;
import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.log.UtilLog;

public class AmplitudeTable {
	private JTable jtable;

	public AmplitudeTable(Connection con, String sql) {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("ID60");
			columnName.add("最大值");
			columnName.add("最小值");
			Vector<?> tableValue = AmplitudeDao.getInstance().getVector(con, sql);
			int[] cellEditableColumn = null;
			int[] columnWidth = { 70, 200, 200 };
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
