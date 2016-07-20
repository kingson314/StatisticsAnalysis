package com.economicAnalysis.timingValue;

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
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import com.app.AppTableView;
import com.economicAnalysis.timingMaximinRangeRate.TimingMaximinRangeRateTab;
import com.economicAnalysis.timingmaximin.TimingMaximinTab;
import com.graphAnalysis.klineSimulate.KLineSimulate;
import com.graphAnalysis.klineSimulate.KLineSimulateDialog;
import common.component.SMenuItem;
import common.component.SSplitPane;
import common.component.STable;
import common.component.STableBean;
import common.jfreechart.kline.KLine;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;
import consts.Const;
import consts.ImageContext;

public class TimingValueTable {
	private JTable jtable;
	private Vector<String> columnName;
	private String[] arr = new String[] { "指标Id", "经济数据ID", "发布日期", "发布时间", "国家", "指标名称", "比较结果", "来源" };
	private int row = 0;
	private String symbol;

	// 构造
	public TimingValueTable(String sql, final String symbol) {
		try {
			this.symbol = symbol;
			columnName = UtilConver.arrayToVector(arr);
			Vector<?> tableValue = TimingValueDao.getInstance().getVector(sql);
			int[] cellEditableColumn = null;
			int[] columnWidth = new int[] { 0, 0, 85, 55, 60, 200, 120 };
			int[] columnHide = new int[] { 0, 1, 7 };
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader,
					isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			table.setPmenu(this.getPmenu());
			jtable = table.getJtable();
			jtable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					viewChart(symbol);
					viewNews();
					viewRelationEconomicData();
					setRowRender(getPublishDate(), getPublishTime(), 2, 3);
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
						jtable.setToolTipText(value);
					}
				}
			});
		} catch (Exception e) {
			UtilLog.logError("列表构造错误:", e);
		} finally {
		}
	}

	private void setRowRender(String publishDate, String publishTime, int colIndexPublishDate, int colIndexPublishTime) {
		final List<Integer> listEquals = new ArrayList<Integer>();
		for (int i = 0; i < this.jtable.getRowCount(); i++) {
			String tmpPublishDate = this.jtable.getValueAt(i, colIndexPublishDate).toString().substring(4);
			String tmpPublishTime = this.jtable.getValueAt(i, colIndexPublishTime).toString();
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
				return cell;
			}
		};
		jtable.selectAll();
		for (int j = 0; j < this.jtable.getColumnCount(); j++) {
			jtable.getColumn(columnName.get(j)).setCellRenderer(rowRender);
		}
		jtable.clearSelection();
	}

	private JPopupMenu getPmenu() {
		JPopupMenu menu = new JPopupMenu();
		try {
			final SMenuItem miTImingMaximin = new SMenuItem("时点价差极值分析", ImageContext.LogView);
			miTImingMaximin.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							AppTableView.getInstance().addTab("时点价差极值分析");
							String indicatorId = jtable.getValueAt(row, 0).toString();
							Map<String, String> map = new HashMap<String, String>();
							map.put("symbol", symbol);
							map.put("indicatorId", indicatorId);
							TimingMaximinTab.getInstance().query(map);
						}
					});
			menu.add(miTImingMaximin);

			final SMenuItem miTImingMaximinRangeRate = new SMenuItem("时点价差极值波动频率分析", ImageContext.LogView);
			miTImingMaximinRangeRate.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							AppTableView.getInstance().addTab("时点价差极值波动频率分析");
							String indicatorId = jtable.getValueAt(row, 0).toString();
							 Map<String,String>map=new HashMap<String,String>();
							    map.put("symbol",symbol);
							    map.put("indicatorId",indicatorId);
							TimingMaximinRangeRateTab.getInstance().query(map);
						}
					});
			menu.add(miTImingMaximinRangeRate);
			menu.addSeparator();

			final SMenuItem miSimulate = new SMenuItem("行情模拟", ImageContext.NowDate);
			miSimulate.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							showSimulateKLine();
						}
					});
			menu.add(miSimulate);
		} catch (Exception e) {
			UtilLog.logError("错误:", e);
		} finally {
		}
		return menu;
	}

	private void showSimulateKLine() {
		AppTableView.getInstance().addTab("行情模拟", KLineSimulate.getInstance().getSimulateTab());
		KLineSimulateDialog.getInstance().setDateTime(getPublishDate(), getPublishTime());
		KLineSimulateDialog.getInstance().setVisible(true);
	}

	private void viewChart(String symbol) {
		int i = this.row;
		String economicDataId = this.jtable.getValueAt(i, 1).toString();
		TimingValueTab.getInstance().setChart(economicDataId, symbol, getPublishDate(), getPublishTime());

		SSplitPane kLine = KLine.getInstance().getCombinationKline(Const.DbName, symbol, getPublishDate(), getPublishTime() + "00");
		TimingValueTab.getInstance().setKline(kLine);
	}

	private void viewRelationEconomicData() {
		String id = this.jtable.getValueAt(this.row, 0).toString();
		String source = this.jtable.getValueAt(this.row, 7).toString();
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("id", id);
		mapParam.put("publishDate", getPublishDate());
		mapParam.put("publishTime", getPublishTime());
		mapParam.put("source", source);
		TimingValueTab.getInstance().queryEconomicDataDateTimeTablel(mapParam);
	}

	private void viewNews() {
		String id = this.jtable.getValueAt(this.row, 0).toString();
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("id", id);
		mapParam.put("publishDate", getPublishDate());
		mapParam.put("publishTime", getPublishTime());
		TimingValueTab.getInstance().queryEconomicNewsTablel(mapParam);
	}

	// 获取信息表格
	public JTable getJtable() {
		return jtable;
	}

	private String getPublishDate() {
		return this.jtable.getValueAt(this.row, 2).toString().substring(4);
	}

	private String getPublishTime() {
		return this.jtable.getValueAt(this.row, 3).toString();
	}
}
