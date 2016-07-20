package com.economicAnalysis.timingmaximin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import com.app.AppTableView;
import com.economicAnalysis.timingMaximinRangeRate.TimingMaximinRangeRateTab;
import common.component.SMenuItem;
import common.component.STable;
import common.component.STableBean;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;
import consts.Const;
import consts.ImageContext;

/**
 * @Description:
 * @date Jul 7, 2014
 * @author:fgq
 */
public class TimingMaximinTable {
    private JTable jtable;
    private Vector<String> columnName;
    private int row = 0;
    private String symbol;
    // 构造
    public TimingMaximinTable(Map<String, String> mapParams) {
	try {
	    this.symbol=mapParams.get("symbol");
	    String minute = mapParams.get("minute");
	    String[] arr = new String[] { "指标Id", "发布日期", "发布时间", "国家", "指标名称", "重要性", "比较结果", "第" + minute + "分钟最高极值", "第" + minute + "分钟最低极值", "前5分钟最高极值", "前5分钟最低极值" };
	    columnName = UtilConver.arrayToVector(arr);
	    Vector<?> tableValue = TimingMaximinDao.getInstance().getBarVector(mapParams);
	    int[] cellEditableColumn = null;
	    int[] columnWidth = new int[] { 0, 85, 55, 60, 200, 50, 120, 120, 120, 120, 120 };
	    int[] columnHide = new int[] { 0 };
	    boolean isChenckHeader = false;
	    boolean isReorderingAllowed = false;
	    boolean isResizingAllowed = true;
	    STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
	    STable table = new STable(bean);
	    table.setPmenu(this.getPmenu());
	    jtable = table.getJtable();
	    jtable.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent evt) {
		    String publishDate = jtable.getValueAt(jtable.getSelectedRow(), 1).toString().substring(4);
		    String publishTime = jtable.getValueAt(jtable.getSelectedRow(), 2).toString();
		    setRowRender(publishDate, publishTime, 1, 2);
		}
	    });

	    this.jtable.addMouseMotionListener(new MouseMotionListener() {
		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		    Point point = e.getPoint();
		    row = jtable.rowAtPoint(point);
		    int col = jtable.columnAtPoint(point);
		    String value = jtable.getValueAt(row, col).toString();
		    if (value != null) {
//			if (col == 1) {
//			    try {
//				jtable.setToolTipText("星期" + UtilDate.getDayOfWeek(value));
//			    } catch (ParseException e1) {
//				e1.printStackTrace();
//			    }
//			} else {
			    jtable.setToolTipText(value);
//			}
		    }
		}
	    });
	    jtable.getColumn(columnName.get(5)).setCellRenderer(this.getImportanceRender());
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
		if (column == 7 || column == 8 || column == 9 || column == 10) {
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
		if (column == 5) {
		    if (table.getValueAt(row, 5) != null) {
			if (table.getValueAt(row, 5).equals("高")) {
			    cell.setForeground(Const.ColorHigh);
			} else if (table.getValueAt(row, 5).equals("中")) {
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
    
    private JPopupMenu getPmenu() {
	JPopupMenu menu = new JPopupMenu();
	try {
	    final SMenuItem miTImingMaximinRangeRate = new SMenuItem("时点价差极值波动频率分析", ImageContext.LogView);
	    miTImingMaximinRangeRate.addActionListener(new ActionListener() {// 浮动菜单
			public void actionPerformed(ActionEvent arg0) {
				AppTableView.getInstance().addTab("时点价差极值波动频率分析");
			    String indicatorId = jtable.getValueAt(row, 0).toString();
							Map<String, String> map = new HashMap<String, String>();
							map.put("symbol", symbol);
							map.put("indicatorId", indicatorId);
			    TimingMaximinRangeRateTab.getInstance().query(map);
			}
		    });
	    menu.add(miTImingMaximinRangeRate);
	    
	} catch (Exception e) {
	    UtilLog.logError("错误:", e);
	} finally {
	}
	return menu;
    }

    private void setRowRender(String publishDate, String publishTime, int colIndexPublishDate, int colIndexPublishTime) {
	final List<Integer> listEquals = new ArrayList<Integer>();
	for (int i = 0; i < this.jtable.getRowCount(); i++) {
	    String tmpPublishDate = this.jtable.getValueAt(i, colIndexPublishDate).toString().substring(4);
	    String tmpPublishTime = this.jtable.getValueAt(i, colIndexPublishTime).toString();// 5
	    if (publishDate.equals(tmpPublishDate) && publishTime.equals(tmpPublishTime)) {
		listEquals.add(i);
	    }
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
		if (column == 5) {
		    if (table.getValueAt(row, 5) != null) {
			if (table.getValueAt(row, 5).equals("高")) {
			    cell.setForeground(Color.red);
			} else if (table.getValueAt(row, 5).equals("中")) {
			    cell.setForeground(Color.blue);
			} else {
			    cell.setForeground(Color.green);
			}
		    }
		    cell.setFont(Const.tTablefont);
		    ((JLabel) cell).setHorizontalAlignment(SwingConstants.CENTER);
		} else if (column == 7 || column == 8 || column == 9 || column == 10) {
		    ((JLabel) cell).setHorizontalAlignment(SwingConstants.RIGHT);
		    cell.setForeground(Color.black);
		} else {
		    cell.setForeground(Color.black);
		    ((JLabel) cell).setHorizontalAlignment(SwingConstants.LEFT);
		}
		return cell;
	    }

	};
	jtable.selectAll();
	for (int j = 0; j < this.jtable.getColumnCount(); j++) {
	    jtable.getColumn(columnName.get(j)).setCellRenderer(rowRender);
	}
	jtable.clearSelection();
    }
}
