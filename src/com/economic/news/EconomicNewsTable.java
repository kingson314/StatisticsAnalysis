package com.economic.news;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import common.component.STable;
import common.component.STableBean;
import common.util.collection.UtilCollection;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;

import consts.Const;

public class EconomicNewsTable {
    private JTable jtable;
    private Vector<String> columnName;

    // 构造
    public EconomicNewsTable(String sql) {
	try {
	    createTbale(sql);
	} catch (Exception e) {
	    UtilLog.logError("经济新闻列表构造错误:", e);
	} finally {
	}
    }

    private void createTbale(String sql) {
	String[] arr = new String[] { "发生日期", "发生时间", "新闻", "详细内容" };
	columnName = UtilConver.arrayToVector(arr);
	Vector<?> tableValue = EconomicNewsDao.getInstance().getVector(sql);
	int[] cellEditableColumn = null;
	int[] columnWidth = new int[] { 100, 60, 600, 800 };
	int[] columnHide = null;
	boolean isChenckHeader = false;
	boolean isReorderingAllowed = false;
	boolean isResizingAllowed = true;
	STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
	STable table = new STable(bean);
	jtable = table.getJtable();
    }

    public EconomicNewsTable(Map<String, String> mapParam) {
	String publishDate = UtilCollection.isNilMap(mapParam, "publishDate");
	String publishTime = UtilCollection.isNilMap(mapParam, "publishTime");
	StringBuilder sbSql = new StringBuilder("select  * from economic_news a where 1=1 ");

	if (!"".equals(publishDate)) {
	    sbSql.append(" and a.occurDate='" + publishDate + "'");
	}
	if (!"".equals(publishTime)) {
	    // sbSql.append(" and a.occurTime<='" + publishTime + "'");
	}
	sbSql.append(" order by occurdate desc ,occurTime desc");
	createTbale(sbSql.toString());
    }

    public void setJtableWithSelected(JScrollPane scrlNewsTabel, Map<String, String> mapParam) {
	String publishDate = UtilCollection.isNilMap(mapParam, "publishDate");
	String publishTime = UtilCollection.isNilMap(mapParam, "publishTime");
	scrlNewsTabel.setViewportView(this.jtable);
	int row = this.setRowRender(publishDate, publishTime, 0, 1);
	this.jtable.getSelectionModel().setSelectionInterval(row - 1, row - 1);
	Rectangle curPageRect = this.jtable.getVisibleRect();
	int curPageRow=(int) Math.ceil(curPageRect.getHeight()/this.jtable.getRowHeight());
	Rectangle rect = this.jtable.getCellRect(row + curPageRow, 0, true);
	this.jtable.updateUI();
	this.jtable.scrollRectToVisible(rect);
    }

    public int setRowRender(String publishDate, String publishTime, int colIndexPublishDate, int colIndexPublishTime) {
	int row = -1;// 第一次相同日期以及时间的记录
	final List<Integer> listEquals = new ArrayList<Integer>();
	for (int i = 0  ; i <this.jtable.getRowCount(); i++) {
	    String tmpPublishDate = this.jtable.getValueAt(i, colIndexPublishDate).toString().substring(4);// 4
	    String tmpPublishTime = this.jtable.getValueAt(i, colIndexPublishTime).toString();// 5
	    if (publishDate.equals(tmpPublishDate) && publishTime.equals(tmpPublishTime)) {
		listEquals.add(i);
		row = i;
	    }
	}
	if (row == -1) {// 没有匹配的时间点，再循环一次，获取最接近的比该时间小的时间行号
	    for (int i = this.jtable.getRowCount() - 1; i >= 0; i--) {
		String tmpPublishDate = this.jtable.getValueAt(i, colIndexPublishDate).toString().substring(4);// 4
		String tmpPublishTime = this.jtable.getValueAt(i, colIndexPublishTime).toString();// 5
		if (publishDate.equals(tmpPublishDate) && publishTime.compareTo(tmpPublishTime) <= 0) {
		    listEquals.add(i);
		    row = i;
		    break;
		}
	    }
	}
	if(row==-1){//如果还是没有匹配，则赋值0
	    row=0;
	}
	DefaultTableCellRenderer rowRender = new DefaultTableCellRenderer() {
	    private static final long serialVersionUID = 1L;

	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (listEquals.indexOf(row) >= 0) {
		    cell.setBackground(Const.ColorSelection);
		} else {
		    cell.setBackground(Color.WHITE);
		}
		return cell;
	    }

	};
	jtable.selectAll();
	for (int j = 0; j < this.jtable.getColumnCount(); j++) {
	    jtable.getColumn(columnName.get(j)).setCellRenderer(rowRender);
	}
	jtable.clearSelection();
	return row;
    }

    // 获取信息表格
    public JTable getJtable() {
	return jtable;
    }
}
