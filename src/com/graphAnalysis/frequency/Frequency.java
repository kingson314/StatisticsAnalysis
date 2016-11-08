package com.graphAnalysis.frequency;

/**
 * @msg:频率分析
 * @date:2013-01-01
 */
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;


import common.component.SButton;
import common.component.SCalendar;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.component.STabbedPane;
import common.component.STextField;
import common.jfreechart.bar.Bar;
import common.jfreechart.dataset.Dataset;
import common.util.array.UtilArray;
import common.util.bean.KeyValue;
import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.string.UtilString;
import config.dictionary.DictionaryDao;
import consts.Const;
import consts.ImageContext;

public class Frequency {
	private static Frequency frequencySplt;
	private JSplitPane splMain;
	private STabbedPane tabPanel;
	private STextField txtBegDate;
	private JButton btnQuery;
	private JButton btnEndDate;
	private JButton btnBegDate;
	private STextField txtEndDate;
	private JLabel lEndDate;
	private JLabel lBegDate;
	private JToolBar tbQuery;
	private SComboBox cmbType;
	private Component lType;
	private SComboBox cmbQueryTime;
	private SLabel lQueryTime;
	private SLabel lSymbol;
	private SComboBox cmbSymbol;

	public static Frequency getInstance() {
		if (frequencySplt == null)
			frequencySplt = new Frequency();
		return frequencySplt;
	}

	public Frequency() {
		splMain = new SSplitPane(0, 35, false);
		tabPanel = new STabbedPane();
		splMain.add(getPnlTop(), JSplitPane.TOP);
		splMain.add(tabPanel, JSplitPane.BOTTOM);
	}

	public JSplitPane getSplMain() {
		return splMain;
	}

	private JToolBar getPnlTop() {
		if (tbQuery == null) {
			tbQuery = new JToolBar();
			{
				lBegDate = new SLabel("开始日期");
				lBegDate.setMinimumSize(new java.awt.Dimension(60, 17));
				lBegDate.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lBegDate);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				txtBegDate = new STextField();
				txtBegDate.setText(UtilConver.dateToStr(Const.fm_yyyyMMdd));
				txtBegDate.setSize(70, 24);
				txtBegDate.setMinimumSize(new java.awt.Dimension(70, 24));
				txtBegDate.setMaximumSize(new java.awt.Dimension(70, 24));
				tbQuery.add(txtBegDate);
			}
			{
				btnBegDate = new SButton("..");
				btnBegDate.setSize(26, 24);
				btnBegDate.setMinimumSize(new java.awt.Dimension(26, 24));
				btnBegDate.setMaximumSize(new java.awt.Dimension(26, 24));
				btnBegDate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
					new SCalendar(txtBegDate);
					}
				});
				tbQuery.add(btnBegDate);
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lEndDate = new SLabel("结束日期");
				lEndDate.setSize(60, 17);
				lEndDate.setMinimumSize(new java.awt.Dimension(60, 17));
				lEndDate.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lEndDate);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				txtEndDate = new STextField();
				txtEndDate.setText(UtilConver.dateToStr(Const.fm_yyyyMMdd));
				txtEndDate.setSize(70, 24);
				txtEndDate.setMinimumSize(new java.awt.Dimension(70, 24));
				txtEndDate.setMaximumSize(new java.awt.Dimension(70, 24));
				tbQuery.add(txtEndDate);
			}
			{
				btnEndDate = new SButton("..");
				btnEndDate.setSize(26, 24);
				btnEndDate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						new SCalendar(txtEndDate);
					}
				});
				tbQuery.add(btnEndDate);
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lSymbol = new SLabel("货币名称");
				tbQuery.add(lSymbol);
				lSymbol.setMinimumSize(new java.awt.Dimension(60, 17));
				lSymbol.setMaximumSize(new java.awt.Dimension(60, 17));
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbSymbol = new SComboBox(Const.SymbolArr);
				tbQuery.add(cmbSymbol);
				cmbSymbol.setSelectedIndex(0);
				cmbSymbol.setMinimumSize(new java.awt.Dimension(80, 24));
				cmbSymbol.setMaximumSize(new java.awt.Dimension(80, 24));
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lQueryTime = new SLabel("查询时段");
				tbQuery.add(lQueryTime);
				lQueryTime.setSize(60, 17);
				lQueryTime.setMinimumSize(new java.awt.Dimension(60, 17));
				lQueryTime.setMaximumSize(new java.awt.Dimension(60, 17));
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbQueryTime = new SComboBox(DictionaryDao.getInstance().getDicionary("QueryTime"));
				tbQuery.add(cmbQueryTime);
				cmbQueryTime.setSelectedIndex(0);
				cmbQueryTime.setSize(250, 24);
				cmbQueryTime.setMinimumSize(new java.awt.Dimension(250, 24));
				cmbQueryTime.setMaximumSize(new java.awt.Dimension(250, 24));
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lType = new SLabel("形态类型");
				lType.setSize(60, 17);
				lType.setMinimumSize(new java.awt.Dimension(60, 17));
				lType.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lType);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbType = new SComboBox(DictionaryDao.getInstance().getDicionary("ModalClassification"));
				cmbType.setSize(120, 24);
				cmbType.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbType.setMaximumSize(new java.awt.Dimension(120, 24));
				tbQuery.add(cmbType);
			}
			{
				tbQuery.addSeparator(new Dimension(30, 30));
			}
			{
				btnQuery = new SButton("查  询", ImageContext.Query);
				btnQuery.setSize(120, 24);
				btnQuery.setMinimumSize(new java.awt.Dimension(120, 24));
				btnQuery.setMaximumSize(new java.awt.Dimension(120, 24));
				btnQuery.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						query(cmbSymbol.getSelectedItem().toString().trim());
					}
				});
				tbQuery.add(btnQuery);
			}
		}
		return tbQuery;
	}

	private void query(String symbol) {
		String type = ((KeyValue) cmbType.getSelectedItem()).getName().toString();
		String begDate = UtilString.isNil(txtBegDate.getText());
		String endDate = UtilString.isNil(txtEndDate.getText());
		;
		String sql = "";
		String[][] queryTimes = UtilArray.getArray2(cmbQueryTime.getSelectedItem().toString());
		String beginSql = "select count(1) cnt ,b.name type," + Const.DateType + " from    price_"+symbol+"60   a,config_dictionary b " + " where a.type=b.name and b.groupName='形态分类'  and "
				+ Const.DateType + ">='" + begDate + "' and " + Const.DateType + " <='" + endDate + "'";
		String typeSql = "";
		if ("0".equals(type)) {
			typeSql = "";
		} else {
			typeSql = " and type in(" + type + ") ";
		}
		String queryTimesSql = "";
		if (queryTimes != null) {
			queryTimesSql = " and (  ";
			for (int i = 0; i < queryTimes.length; i++) {
				if (queryTimes[i].length != 2)
					continue;// 要求查询时段必须成对出现
				if (i == queryTimes.length - 1)
					queryTimesSql = queryTimesSql + "(" + Const.TimeType + ">='" + queryTimes[i][0] + "00" + "' and " + Const.TimeType + "<='" + queryTimes[i][1] + "00" + "')";
				else {
					queryTimesSql = queryTimesSql + "(" + Const.TimeType + ">='" + queryTimes[i][0] + "00" + "' and " + Const.TimeType + "<='" + queryTimes[i][1] + "00" + "') or ";
				}
			}
			queryTimesSql = queryTimesSql + ")";
		}
		String groupSql = " group by " + Const.DateType + ",b.name";
		sql = beginSql + typeSql + queryTimesSql + groupSql;
		System.out.println(sql);
		Connection con = UtilJDBCManager.getConnection(Const.DbName);
		tabPanel.removeAll();
		// 频率图表
		{
			String[] fields = new String[] { "CNT", "TYPE", Const.DateType.toUpperCase() };// 大写
			DefaultCategoryDataset dataset = new Dataset().getBarDataSet(con, sql, fields);
			ChartPanel barPanel = new Bar("频率分析", "频率值", "类型", dataset).getChart();
			tabPanel.addTab("频率图表", barPanel);
		}
		// 频率数据
		{
			JScrollPane scrlTabel = new JScrollPane();
			scrlTabel.setViewportView(new FrequencyTable(con, sql).getJtable());
			tabPanel.addTab("频率数据", scrlTabel);
		}
	}
}
