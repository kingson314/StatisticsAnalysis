package com.economicAnalysis.timingMaximinRangeRate;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import common.component.STable;
import common.component.STableBean;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;

import consts.Const;

/**
 * @Description:
 * @date Jul 7, 2014
 * @author:fgq
 */
public class TimingMaximinRangeRateTable {
    private JTable jtable;

    // 构造
    public TimingMaximinRangeRateTable(Map<String, String> mapParams) {
	try {
	    String[] arr = new String[] { "指标Id", "国家", "指标名称", "重要性", "极值范围", "出现次数", "总次数", "比率", "平均波动值", "最大波动值", "最小波动值" };
	    Vector<String> columnName = UtilConver.arrayToVector(arr);
	    Vector<?> tableValue = TimingMaximinRangeRateDao.getInstance().getVector(mapParams);
	    int[] cellEditableColumn = null;
	    int[] columnWidth = new int[] { 0, 70, 200, 50, 120, 100, 100, 120, 120, 100, 100 };
	    int[] columnHide = new int[] { 0 };
	    boolean isChenckHeader = false;
	    boolean isReorderingAllowed = false;
	    boolean isResizingAllowed = true;
	    STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
	    STable table = new STable(bean);
	    jtable = table.getJtable();
	    jtable.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent evt) {
		}
	    });
	    jtable.getColumn(columnName.get(3)).setCellRenderer(this.getImportanceRender());
	    jtable.getColumn(columnName.get(4)).setCellRenderer(this.getValueRender());
	    jtable.getColumn(columnName.get(5)).setCellRenderer(this.getValueRender());
	    jtable.getColumn(columnName.get(6)).setCellRenderer(this.getValueRender());
	    jtable.getColumn(columnName.get(7)).setCellRenderer(this.getValueRender());
	    jtable.getColumn(columnName.get(8)).setCellRenderer(this.getValueRender());
	    jtable.getColumn(columnName.get(9)).setCellRenderer(this.getValueRender());
	    jtable.getColumn(columnName.get(10)).setCellRenderer(this.getValueRender());
	} catch (Exception e) {
	    UtilLog.logError("列表构造错误:", e);
	} finally {
	}
    }

    // 获取信息表格
    public JTable getJtable() {
	return jtable;
    }

    private DefaultTableCellRenderer getValueRender() {
	DefaultTableCellRenderer renderValue = new DefaultTableCellRenderer() {
	    private static final long serialVersionUID = 1L;

	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (column == 4 || column == 5 || column == 6 || column == 7 || column == 8 || column == 9 || column == 10) {
		    ((JLabel) cell).setHorizontalAlignment(SwingConstants.RIGHT);
		    return cell;
		} else
		    return cell;
	    }
	};
	return renderValue;
    }

    private DefaultTableCellRenderer getImportanceRender() {
	DefaultTableCellRenderer renderImportance = new DefaultTableCellRenderer() {
	    private static final long serialVersionUID = 1L;

	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (column == 3) {
		    if (table.getValueAt(row, 3) != null) {
			if (table.getValueAt(row, 3).equals("高")) {
			    cell.setForeground(Const.ColorHigh);
			} else if (table.getValueAt(row, 3).equals("中")) {
			    cell.setForeground(Const.ColorMiddle);
			} else {
			    cell.setForeground(Const.ColorLow);
			}
		    }
		    cell.setFont(Const.tTablefont);
		    ((JLabel) cell).setHorizontalAlignment(SwingConstants.CENTER);
		    return cell;
		} else
		    return cell;
	    }
	};
	return renderImportance;
    }

}
