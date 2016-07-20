package com.graphAnalysis.shape;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTable;

import com.app.AppTableView;

import common.component.STable;
import common.component.STableBean;
import common.jfreechart.kline.KLine;
import common.util.log.UtilLog;

import consts.Const;

public class ShapeTable {
	private JTable jtable;

	public ShapeTable(Connection con, String sql, final String symbol) {
		try {
			Vector<String> columnName = new Vector<String>();
			columnName.add("日期");
			columnName.add("时间");
			columnName.add("类型");
			columnName.add("ID");
			Vector<?> tableValue = ShapeDao.getInstance().getVector(con, sql);
			int[] cellEditableColumn = null;
			int[] columnWidth = { 70, 60, 60, 0 };
			int[] columnHide = new int[]{3};
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader,
					isReorderingAllowed, isResizingAllowed);

			STable table = new STable(bean);
			jtable = table.getJtable();
			this.jtable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					AppTableView.getInstance().addTab("形态对比图",getShapeMatch(symbol));
				}
			});

			this.jtable.addKeyListener(new KeyListener(){

				public void keyPressed(KeyEvent e) {
					/** 
					* @Description: 
					* @date 2014-9-13
					* @author:fgq 
					*/ 
				}

				public void keyReleased(KeyEvent e) {
					AppTableView.getInstance().addTab("形态对比图",getShapeMatch(symbol));
				}

				public void keyTyped(KeyEvent e) {
					/** 
					* @Description: 
					* @date 2014-9-13
					* @author:fgq 
					*/ 
				}});
		} catch (Exception e) {
			UtilLog.logError("数据源配置列表构造错误：", e);
		} finally {
		}
	}

//	private void mouseDoubleClick(String symbol) {
//		AppMain.appMain.getSpltCenter().removeAll();
//		final String[] tabName = new String[] { "形态对比图", "振幅分析图", "时间分析图" };
//		final STabbedPane tb = new STabbedPane(tabName);
//		AppMain.appMain.getSpltCenter().add(tb);
//		tb.addMouseListener(new MouseListener() {
//			public void mouseClicked(MouseEvent e) {
//				if (tb.getTitleAt(tb.getSelectedIndex()).equals(tabName[0])) {
//					// tb.remove(tb.getSelectedIndex());
//					// tb.addTab(tabName[0],
//					// ImageContext.TabTask,getShapeMatch(), tabName[0], false);
//					// tb.addTab(tabName[1],
//					// ImageContext.TabTask,getAmplitude(), tabName[1], false);
//					// tb.setSelectedIndex(tb.getSelectedIndex());
//				}
//			}
//
//			public void mouseEntered(MouseEvent e) {
//
//			}
//
//			public void mouseExited(MouseEvent e) {
//
//			}
//
//			public void mousePressed(MouseEvent e) {
//
//			}
//
//			public void mouseReleased(MouseEvent e) {
//
//			}
//		});
//		// tb.addTab(tabName[1], ImageContext.TabTask,getAmplitude(),
//		// tabName[1], false);
//		// tb.addTab(tabName[0], ImageContext.TabTask,null, tabName[0], false);
//		tb.addTab(tabName[0], ImageContext.TabTask, getShapeMatch(symbol), tabName[0], false);
//		if (this.jtable.getSelectedRow() >= 0) {
//			String type = UtilString.isNil(this.jtable.getValueAt(this.jtable.getSelectedRow(), 0));
//			if (!"".equals(UtilString.isNil(type)))
//				tb.addTab(tabName[1], ImageContext.TabTask, getAmplitude(symbol), tabName[1], false);
//		}
//
//	}

	// 获取60分钟K线与5分钟K线的比对图
	private JComponent getShapeMatch(String symbol) {
		String date = this.jtable.getValueAt(this.jtable.getSelectedRow(), 0).toString();
		String time = this.jtable.getValueAt(this.jtable.getSelectedRow(), 1).toString();
		return KLine.getInstance().getCombinationKline(Const.DbName, symbol, date, time);
	}
//
//	// 获取该id60的振幅图
//	private JComponent getAmplitude(String symbol) {
//		String id60 = this.jtable.getValueAt(this.jtable.getSelectedRow(), 3).toString();
//		String[] tabName = new String[] { "振幅图表", "振幅数据" };
//		STabbedPane tabPanel = new STabbedPane(tabName);
//		String sql = " select*from(select valueMax*100 value,'最大' groupType, id60 from  " + "sa_feature where symbol='" + symbol
//				+ "' union all select  valueMin*100 value,'最小' groupType,id60 from  " + "sa_feature where symbol='" + symbol + "') a  where id60='"
//				+ id60 + "'";
//		System.out.println(sql);
//		Connection con = UtilJDBCManager.getConnection(Const.DbName);
//		tabPanel.removeAll();
//		// 频率图表
//		{
//			String[] fields = new String[] { "VALUE", "GROUPTYPE", "ID60" };// 大写
//			DefaultCategoryDataset dataset = new Dataset().getBarDataSet(con, sql, fields);
//			ChartPanel barPanel = new Bar("频率分析", "频率值", "类型", dataset).getChart();
//			tabPanel.addTab("振幅图表", barPanel);
//		}
//		// 频率数据
//		{
//			JScrollPane scrlTabel = new JScrollPane();
//			scrlTabel.setViewportView(new AmplitudeTable(con, sql).getJtable());
//			tabPanel.addTab("振幅数据", scrlTabel);
//		}
//		return tabPanel;
//	}

	public JTable getJtable() {
		return jtable;
	}

}
