package com.graphAnalysis.amlitudeRate;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
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
import common.component.ShowMsg;
import common.jfreechart.bar.Bar;
import common.jfreechart.pie.Pie;
import common.util.array.UtilArray;
import common.util.bean.KeyValue;
import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.string.UtilString;
import config.dictionary.DictionaryDao;
import consts.Const;
import consts.ImageContext;

/**
 * @fun :振幅在一定范围内的出现频率比率图(饼图或柱图)
 * @date :2013-01-06
 */
public class AmplitudeRate {
	private JSplitPane splMain;
	private STabbedPane tabPanel;
	private STextField txtBegDate;
	private JButton btnQuery;
	private JButton btnEndDate;
	private JButton btnBegDate;
	private STextField txtEndDate;
	private SLabel lEndDate;
	private SLabel lBegDate;
	private SComboBox cmbType;
	private Component lType;
	private SComboBox cmbQueryTime;
	private SLabel lQueryTime;
	private SLabel lRange;
	private SComboBox cmbRange;
	private JToolBar tbQuery;
	private SLabel lSymbol;
	private SComboBox cmbSymbol;
	private static AmplitudeRate amplitudeRate;

	public static AmplitudeRate getInstance() {
		if (amplitudeRate == null)
			amplitudeRate = new AmplitudeRate();
		return amplitudeRate;
	}

	public AmplitudeRate() {
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
				lEndDate.setSize(50, 17);
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
				lQueryTime.setSize(50, 17);
				lQueryTime.setMinimumSize(new java.awt.Dimension(60, 17));
				lQueryTime.setMaximumSize(new java.awt.Dimension(60, 17));
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbQueryTime = new SComboBox(DictionaryDao.getInstance().getDicionary("查询时段"));
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
				lType.setSize(50, 17);
				lType.setMinimumSize(new java.awt.Dimension(60, 17));
				lType.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lType);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbType = new SComboBox(DictionaryDao.getInstance().getDicionary("形态分类"));
				cmbType.setSize(120, 24);
				cmbType.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbType.setMaximumSize(new java.awt.Dimension(120, 24));
				tbQuery.add(cmbType);
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lRange = new SLabel("振幅范围");
				lRange.setSize(50, 17);
				lRange.setMinimumSize(new java.awt.Dimension(60, 17));
				lRange.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lRange);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbRange = new SComboBox(DictionaryDao.getInstance().getDicionary("区间数组"));
				cmbRange.setSize(50, 17);
				cmbRange.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbRange.setMaximumSize(new java.awt.Dimension(120, 24));
				tbQuery.add(cmbRange);
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
		String sql = "";
		String[][] queryTimes = UtilArray.getArray2(cmbQueryTime.getSelectedItem().toString());
		String beginSql = " select a.* from(select valueMax*100 value,'最大' groupType, id60 from sa_feature_" + symbol
				+ " union all select valueMin*100 value,'最小' groupType, id60 from  sa_feature_" + symbol + " ) a,   "  + "price_"+ symbol+"60  b  " + " where a.id60=b.id   and "
				+ Const.DateType + ">='" + begDate + "' and " + Const.DateType + " <='" + endDate + "'";
		String typeSql = "";
		if ("0".equals(type)) {
			typeSql = "";
		} else {
			typeSql = " and b.type in(" + type + ") ";
		}
		String queryTimesSql = "";
		if (queryTimes != null) {
			queryTimesSql = " and (  ";
			for (int i = 0; i < queryTimes.length; i++) {
				if (queryTimes[i].length != 2)
					continue;// 要求查询时段必须成对出现
				if (i == queryTimes.length - 1)
					queryTimesSql = queryTimesSql + "(" + Const.TimeType + ">='" + queryTimes[i][0] + "00" + "' and " + Const.TimeType + "<='" + queryTimes[i][1] + "00"
							+ "')";
				else {
					queryTimesSql = queryTimesSql + "(" + Const.TimeType + ">='" + queryTimes[i][0] + "00" + "' and " + Const.TimeType + "<='" + queryTimes[i][1] + "00"
							+ "') or ";
				}
			}
			queryTimesSql = queryTimesSql + ")";
		}
		String groupSql = " order by b.id";
		sql = beginSql + typeSql + queryTimesSql + groupSql;
		// System.out.println(sql);
		Connection con = UtilJDBCManager.getConnection(Const.DbName);
		tabPanel.removeAll();

		String[][] arr = UtilArray.getArray2(((KeyValue) cmbRange.getSelectedItem()).getName().toString());
		int len = arr.length;
		List<HashMap<String, String>> list = UtilSql.queryM(con, sql);
		// 创建一个存放出现在每种数组区间 频率数
		int[] countMaxValue = new int[len];
		int[] countMinValue = new int[len];
		if (list == null) {
			ShowMsg.showWarn("没有数据，请检查查询条件!");
			return;
		}
		for (Map<String, String> map : list) {
			for (int i = 0; i < len; i++) {
				Double value = Double.valueOf(map.get("VALUE"));
				Double beginValue = Double.valueOf(arr[i][0]);
				Double endValue = Double.valueOf(arr[i][1]);
				if (value > 0) {
					if (value > beginValue && value <= endValue) {
						countMaxValue[i] = countMaxValue[i] + 1;
						break;
					}
				} else {
					value = Math.abs(value);
					if (value > beginValue && value <= endValue) {
						countMinValue[i] = countMinValue[i] + 1;
						break;
					}
				}
			}
		}
		// 柱形图表数据集
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		// 数据部数据集
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		for (int i = 0; i < len; i++) {
			Vector<String> rowValue = new Vector<String>();
			String range = arr[i][0] + "-" + arr[i][1];
			rowValue.add(range);
			rowValue.add(String.valueOf(countMaxValue[i]));
			rowValue.add(String.valueOf(countMinValue[i]));
			vector.add(rowValue);

			if (countMaxValue[i] > 0) {
				dataset.addValue(countMaxValue[i], "最大值", range);
			}
			if (countMinValue[i] > 0) {
				dataset.addValue(countMinValue[i], "最小值", range);
			}
		}
		// 振幅范围频率柱形图表
		{
			ChartPanel barPanel = new Bar("振幅范围频率柱形图", "频率值", "类型", dataset).getChart();
			tabPanel.addTab("振幅范围频率图表", barPanel);
		}
		// 振幅范围频率饼形图表
		{
			ChartPanel piePanel = new Pie("振幅范围频率饼装图", dataset).getChart();
			tabPanel.addTab("振幅范围频率图表", piePanel);
		}
		// 振幅范围频率数据
		{
			JScrollPane scrlTabel = new JScrollPane();
			scrlTabel.setViewportView(new AmplitudeRateTable(vector).getJtable());
			tabPanel.addTab("振幅范围频率数据", scrlTabel);
		}
	}

	public static void main(String[] args) {
		String[][] arr = UtilArray
				.getArray2("[0,5];[5,10];[10,15];[15,20];[20,25];[25,30];[30,35];[35,40];[40,45];[45,50];[50,55];[55,60];[60,65];[65,70];[70,75];[75,80];[80,85];[85,90];[90,95];[95,200]");
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.println(arr[i][j]);
			}
			System.out.println("------------");
		}
	}
}
