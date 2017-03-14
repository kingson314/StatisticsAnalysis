package com.economicAnalysis.timingmaximin;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import org.jfree.chart.ChartPanel;

import com.economic.indicator.EconomicIndicatorDao;
import common.component.SButton;
import common.component.SCalendar;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.component.STabbedPane;
import common.component.STextArea;
import common.component.STextField;
import common.component.ShowMsg;
import common.jfreechart.bar.Bar;
import common.jfreechart.pie.Pie;
import common.util.UtilFun;
import common.util.conver.UtilConver;
import common.util.string.UtilString;

import config.dictionary.DictionaryDao;
import config.symbol.SymbolDao;
import consts.Const;
import consts.ImageContext;

/**
 * @Description:
 * @date Jul 7, 2014
 * @author:fgq
 */
public class TimingMaximinTab {
	private SSplitPane splMain;
	private SSplitPane spltMainDataAndChar;
	private JScrollPane scrlDataTabel;
	private STabbedPane tabChart;
	private JToolBar tbQuery;
	private SLabel lBegDate;
	private STextField txtBegDate;
	private SButton btnBegDate;
	private SLabel lEndDate;
	private STextField txtEndDate;
	private SButton btnEndDate;
	private SLabel lQueryTime;
	private SLabel lCountry;
	private SComboBox cmbCountry;
	private SLabel lIndicator;
	private SComboBox cmbIndicator;
	private SLabel lSymbol;
	private SComboBox cmbSymbol;
	private SButton btnQuery;
	private STextField txtQueryTime;
	private SSplitPane splPie;
	private JScrollPane scrlReport;
	private SLabel lRange;
	private SComboBox cmbRange;
	private static TimingMaximinTab tab = null;
	private static String[] indicatorIdArr;
	private static String[] indicatorNameArr;

	public static TimingMaximinTab getInstance() {
		if (tab == null)
			tab = new TimingMaximinTab();
		return tab;
	}

	public TimingMaximinTab() {
		splMain = new SSplitPane(0, 35, false);
		spltMainDataAndChar = new SSplitPane(0, 0.35, false);
		splMain.add(getPnlTop(), JSplitPane.TOP);// 按钮工具栏
		splMain.add(spltMainDataAndChar, JSplitPane.BOTTOM);// 数据与图表
		scrlDataTabel = new JScrollPane();
		spltMainDataAndChar.add(scrlDataTabel, JSplitPane.TOP);// 

		tabChart = new STabbedPane(new String[] { "分析报告", "比率分布图" });
		tabChart.setVisible(false);
		scrlReport = new JScrollPane();
		tabChart.addTab("分析报告", ImageContext.TabTask, scrlReport, "分析报告", false);
		splPie = new SSplitPane(1, 0.5, false);
		tabChart.addTab("比率分布图", ImageContext.TabTask, splPie, "比率分布图", false);
		spltMainDataAndChar.add(tabChart, JSplitPane.BOTTOM);
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
				txtBegDate = new STextField("20000101");
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
				lQueryTime = new SLabel("查询时点(分)");
				tbQuery.add(lQueryTime);
				lQueryTime.setSize(75, 17);
				lQueryTime.setMinimumSize(new java.awt.Dimension(60, 17));
				lQueryTime.setMaximumSize(new java.awt.Dimension(60, 17));
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				txtQueryTime = new STextField();
				txtQueryTime.setText("30");
				txtQueryTime.setSize(70, 24);
				txtQueryTime.setMinimumSize(new java.awt.Dimension(70, 24));
				txtQueryTime.setMaximumSize(new java.awt.Dimension(70, 24));
				tbQuery.add(txtQueryTime);
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lCountry = new SLabel("国家");
				lCountry.setSize(60, 17);
				lCountry.setMinimumSize(new java.awt.Dimension(60, 17));
				lCountry.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lCountry);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbCountry = new SComboBox(DictionaryDao.getInstance().getDicionary("COUNTRY"));
				cmbCountry.setEditable(true);
				cmbCountry.setSize(120, 24);
				cmbCountry.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbCountry.setMaximumSize(new java.awt.Dimension(120, 24));
				cmbCountry.addItemListener(new ItemListener() {
					@SuppressWarnings("unchecked")
					public void itemStateChanged(ItemEvent e) {
						indicatorNameArr = EconomicIndicatorDao.getInstance().getIndicatorName(e.getItem().toString());
						indicatorIdArr = EconomicIndicatorDao.getInstance().getIndicatorId(e.getItem().toString());
						ComboBoxModel comboBoxModel = new DefaultComboBoxModel(indicatorNameArr);
						cmbIndicator.setModel(comboBoxModel);
						try{
						cmbSymbol.setSelectedItem(UtilFun.getSymbol(cmbCountry.getSelectedItem().toString()));
						}catch(Exception e1){}
					}
				});
				tbQuery.add(cmbCountry);
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lIndicator = new SLabel("指标名称");
				lIndicator.setSize(60, 17);
				lIndicator.setMinimumSize(new java.awt.Dimension(60, 17));
				lIndicator.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lIndicator);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				indicatorNameArr = EconomicIndicatorDao.getInstance().getIndicatorName("美国");
				indicatorIdArr = EconomicIndicatorDao.getInstance().getIndicatorId("美国");
				cmbIndicator = new SComboBox(indicatorNameArr);
				cmbIndicator.setSize(120, 24);
				cmbIndicator.setMinimumSize(new java.awt.Dimension(200, 24));
				cmbIndicator.setMaximumSize(new java.awt.Dimension(200, 24));
				tbQuery.add(cmbIndicator);
			}
			{
				tbQuery.addSeparator(new Dimension(30, 30));
			}
			{
				lSymbol = new SLabel("货币名称");
				lSymbol.setSize(60, 17);
				lSymbol.setMinimumSize(new java.awt.Dimension(60, 17));
				lSymbol.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lSymbol);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbSymbol = new SComboBox(SymbolDao.getInstance().getModel());
				cmbSymbol.setEditable(true);
				cmbSymbol.setSize(120, 24);
				cmbSymbol.setMinimumSize(new java.awt.Dimension(200, 24));
				cmbSymbol.setMaximumSize(new java.awt.Dimension(200, 24));
				tbQuery.add(cmbSymbol);
			}
			{
				tbQuery.addSeparator(new Dimension(30, 30));
			}
			{
				lRange = new SLabel("极值范围");
				lRange.setSize(50, 17);
				lRange.setMinimumSize(new java.awt.Dimension(60, 17));
				lRange.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lRange);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbRange = new SComboBox(DictionaryDao.getInstance().getDicionary("ArrayRange"));
				cmbRange.setEditable(true);
				cmbRange.setSize(50, 17);
				cmbRange.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbRange.setMaximumSize(new java.awt.Dimension(120, 24));
				tbQuery.add(cmbRange);
			}
			{
				btnQuery = new SButton("查  询", ImageContext.Query);
				btnQuery.setSize(120, 24);
				btnQuery.setMinimumSize(new java.awt.Dimension(120, 24));
				btnQuery.setMaximumSize(new java.awt.Dimension(120, 24));
				btnQuery.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						query();
					}
				});
				tbQuery.add(btnQuery);
				{
					tbQuery.addSeparator(new Dimension(30, 30));
				}
			}
		}
		return tbQuery;
	}

	public void query(Map<String, String> mapParams) {
		if ("".equals(txtBegDate.getText())) {
			ShowMsg.showWarn("请选择开始日期!");
			return;
		}
		if ("".equals(txtEndDate.getText())) {
			ShowMsg.showWarn("请选择结束日期!");
			return;
		}
		if ("".equals(txtQueryTime.getText())) {
			ShowMsg.showWarn("请选择时点!");
			return;
		}
		mapParams.put("begDate", txtBegDate.getText());
		mapParams.put("endDate", txtEndDate.getText());
		mapParams.put("minute", txtQueryTime.getText());
		mapParams.put("range", cmbRange.getSelectedItem().toString());
		doQuery(mapParams);
		String country = mapParams.get("country");
		String indicator = mapParams.get("indicator");
		cmbCountry.setSelectedItem(country);
		cmbIndicator.setSelectedItem(indicator);
	}

	private void query() {
		if ("".equals(txtBegDate.getText())) {
			ShowMsg.showWarn("请选择开始日期!");
			return;
		}
		if ("".equals(txtEndDate.getText())) {
			ShowMsg.showWarn("请选择结束日期!");
			return;
		}
		if ("".equals(txtQueryTime.getText())) {
			ShowMsg.showWarn("请选择时点!");
			return;
		}
		if ("".equals(cmbSymbol.getSelectedItem().toString())) {
			ShowMsg.showWarn("请选择货币!");
			return;
		}

		if ("".equals(UtilString.isNil(cmbIndicator.getSelectedItem()))) {
			ShowMsg.showWarn("请选择指标!");
			return;
		}
		if ("".equals(UtilString.isNil(cmbRange.getSelectedItem()))) {
			ShowMsg.showWarn("请选择极值范围!");
			return;
		}
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("begDate", txtBegDate.getText());
		mapParams.put("endDate", txtEndDate.getText());
		mapParams.put("minute", txtQueryTime.getText());
		mapParams.put("symbol", cmbSymbol.getSelectedItem().toString());
		mapParams.put("indicatorId", indicatorIdArr[cmbIndicator.getSelectedIndex()]);
		mapParams.put("range", cmbRange.getSelectedItem().toString());
		doQuery(mapParams);
	}

	private void doQuery(Map<String, String> mapParams) {
		// Table
		scrlDataTabel.setViewportView(new TimingMaximinTable(mapParams).getJtable());
		// 分析报告
		STextArea txtaReport = new STextArea();
		StringBuilder sbReportBefore = TimingMaximinDao.getInstance().getReport(mapParams, "before");
		StringBuilder sbReportAfter = TimingMaximinDao.getInstance().getReport(mapParams, "after");
		txtaReport.setText(sbReportBefore.append(sbReportAfter).toString());
		txtaReport.setEditable(false);
		scrlReport.setViewportView(txtaReport);
		// Pie图
		String title = txtQueryTime.getText() + "分钟内极值比率图";
		Pie pie1 = new Pie(title, TimingMaximinDao.getInstance().getPieDataSet(mapParams, "after"));
		ChartPanel chartPanel1 = pie1.getChart();
		splPie.add(chartPanel1, JSplitPane.LEFT);

		title = "前5分钟极值比率图";
		Pie pie2 = new Pie(title, TimingMaximinDao.getInstance().getPieDataSet(mapParams, "before"));
		ChartPanel chartPanel2 = pie2.getChart();
		splPie.add(chartPanel2, JSplitPane.RIGHT);
		splPie.setDividerLocation(0.5);
		tabChart.setVisible(true);
		spltMainDataAndChar.setDividerLocation(0.35);
		// Bar图
		title = txtQueryTime.getText() + "分钟内极值图";
		tabChart.remove(title);
		ChartPanel chartPanel3 = new Bar(title, "价差", "日期", TimingMaximinDao.getInstance().getBarDataSet(mapParams, "after")).getChart();
		tabChart.addTab(title, ImageContext.TabTask, chartPanel3, title, false);

		title = "前5分钟极值图";
		tabChart.remove(title);
		ChartPanel chartPanel4 = new Bar(title, "价差", "日期", TimingMaximinDao.getInstance().getBarDataSet(mapParams, "before")).getChart();
		tabChart.addTab(title, ImageContext.TabTask, chartPanel4, title, false);
	}
}
