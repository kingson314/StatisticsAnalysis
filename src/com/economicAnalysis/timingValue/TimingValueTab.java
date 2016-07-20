package com.economicAnalysis.timingValue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.economic.indicator.EconomicIndicatorDao;
import com.economic.news.EconomicNewsTable;
import com.economicAnalysis.economicData.EconomicDataTable;
import common.component.SButton;
import common.component.SCalendar;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.component.STabbedPane;
import common.component.STextField;
import common.util.UtilFun;
import common.util.collection.UtilCollection;
import common.util.conver.UtilConver;
import common.util.string.UtilString;

import config.dictionary.DictionaryDao;
import config.symbol.SymbolDao;
import consts.Const;
import consts.ImageContext;

/**
 * @Description: 经济数据
 * @date Feb 17, 2014
 * @author:fgq
 */
public class TimingValueTab {
	private static TimingValueTab tab;
	private JSplitPane splMain;
	private STextField txtBegDate;
	private JButton btnQuery;
	private JButton btnEndDate;
	private JButton btnBegDate;
	private STextField txtEndDate;
	private JLabel lEndDate;
	private JLabel lBegDate;
	private JToolBar tbQuery;
	private SComboBox cmbCountry;
	private JLabel lCountry;
	private SComboBox cmbQueryTime;
	private SLabel lQueryTime;
	private JScrollPane scrlEconominDataMainTabel;// 主经济数据表
	private JScrollPane scrlEconominDataRelationTabel;// 相关经济数据表
	private JScrollPane scrlNewsTabel;// 即时新闻表
	private STabbedPane tabBottom;// 相关经济数据表与K线图
	private SLabel lIndicator;
	private SComboBox cmbIndicator;
	private SLabel lSymbol;
	private SComboBox cmbSymbol;
	private SSplitPane spltTop;
	private SSplitPane spltMainEconimicData;
	private SSplitPane spltMainEconimicDataAndNews;
	private JScrollPane scrlChart;
	private Map<String, String> mapQueryParam;

	public static TimingValueTab getInstance() {
		if (tab == null)
			tab = new TimingValueTab();
		return tab;
	}

	public TimingValueTab() {
		splMain = new SSplitPane(0, 0.45, false);
		spltTop = new SSplitPane(0, 35, false);
		spltMainEconimicData = new SSplitPane(1, 535, false);
		spltMainEconimicDataAndNews = new SSplitPane(0, 0.5, false);
		scrlEconominDataMainTabel = new JScrollPane();
		scrlEconominDataRelationTabel = new JScrollPane();
		scrlNewsTabel = new JScrollPane();

		spltTop.add(getPnlTop(), JSplitPane.TOP);// 按钮工具栏
		spltTop.add(spltMainEconimicData, JSplitPane.BOTTOM);// 经济主数据，相关经济数据表、新闻
		spltMainEconimicData.add(scrlEconominDataMainTabel, JSplitPane.LEFT);// 经济主数据
		spltMainEconimicData.add(spltMainEconimicDataAndNews, JSplitPane.RIGHT);// 相关经济数据表、新闻
		spltMainEconimicDataAndNews.add(scrlEconominDataRelationTabel, JSplitPane.TOP);// 相关经济数据表
		spltMainEconimicDataAndNews.add(scrlNewsTabel, JSplitPane.BOTTOM);// 新闻
		splMain.add(spltTop, JSplitPane.TOP);

		scrlChart = new JScrollPane();
		tabBottom = new STabbedPane(new String[] { "时点价差图", "K线图" });
		tabBottom.addTab("时点价差图", ImageContext.TabTask, scrlChart, "时点价差图", false);
		splMain.add(tabBottom, JSplitPane.BOTTOM);
	}

	public JSplitPane getSplMain() {
		return splMain;
	}

	public JSplitPane getSplMain(Map<String, String> mapQueryParam) {
		this.mapQueryParam = mapQueryParam;
		this.queryByParams();
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
				lQueryTime = new SLabel("查询时间");
				tbQuery.add(lQueryTime);
				lQueryTime.setSize(60, 17);
				lQueryTime.setMinimumSize(new java.awt.Dimension(60, 17));
				lQueryTime.setMaximumSize(new java.awt.Dimension(60, 17));
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbQueryTime = new SComboBox(DictionaryDao.getInstance().getDicionary("查询时间"));
				cmbQueryTime.setEditable(true);
				tbQuery.add(cmbQueryTime);
				cmbQueryTime.setSelectedIndex(0);
				cmbQueryTime.setSize(250, 24);
				cmbQueryTime.setMinimumSize(new java.awt.Dimension(80, 24));
				cmbQueryTime.setMaximumSize(new java.awt.Dimension(80, 24));
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
				cmbCountry = new SComboBox(DictionaryDao.getInstance().getDicionary("国家"));
				cmbCountry.setEditable(true);
				cmbCountry.setSize(120, 24);
				cmbCountry.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbCountry.setMaximumSize(new java.awt.Dimension(120, 24));
				cmbCountry.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EconomicIndicatorDao.getInstance().getIndicatorName(e.getItem().toString()));
						cmbIndicator.setModel(comboBoxModel);
						cmbSymbol.setSelectedItem(UtilFun.getSymbol(cmbCountry.getSelectedItem().toString()));
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
				cmbIndicator = new SComboBox(EconomicIndicatorDao.getInstance().getIndicatorName("美国"));
				cmbIndicator.setEditable(true);
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

	public void queryByParams() {
		String tableName = "sa_timingvalue";
		String symbol = this.mapQueryParam.get("symbol");
		String country=this.mapQueryParam.get("country");
		String indicator=this.mapQueryParam.get("indicator");
		cmbCountry.setSelectedItem(country);
		cmbIndicator.setSelectedItem(indicator);
		StringBuilder sbSql = new StringBuilder(
				"select  distinct a.economicDataId,a.symbol,b.publishDate,b.publishTime,b.indicatorid, b.country, b.indicator indicatorName, b.compareresult,c.indicatorfh,c.matchrate,c.analysisreport,b.source  from ")
				.append(tableName)
				.append(
						" a,economic_data b,economic_indicator c  where  a.economicDataId=b.id  and b.indicatorid=c.id   and symbol='"
								+ symbol + "'");
		String indicatorId = this.mapQueryParam.get("indicatorId");
		if (!"".equals(indicatorId)) {
			sbSql.append(" and b.indicatorId='").append(indicatorId).append("'");
		}
		sbSql.append(" order by country,b.publishDate desc ,b.publishTime asc");
		JTable table = new TimingValueTable(sbSql.toString(), symbol).getJtable();
		{
			scrlEconominDataMainTabel.setViewportView(table);
		}
	}

	private void query() {
		// String country = cmbCountry.getSelectedItem().toString();
		String begDate = UtilString.isNil(txtBegDate.getText());
		String endDate = UtilString.isNil(txtEndDate.getText());
		String symbol = UtilString.isNil(cmbSymbol.getSelectedItem().toString());
		String tableName = "sa_timingvalue";
		if (!"".equals(cmbSymbol.getSelectedItem().toString())) {
			symbol = cmbSymbol.getSelectedItem().toString();
		}
		StringBuilder sbSql = new StringBuilder(
				"select   distinct a.economicDataId,a.symbol,b.publishDate,b.publishTime,b.indicatorid, b.country, b.indicator indicatorName, b.compareresult,c.indicatorfh,c.matchrate,c.analysisreport,b.source from ")
				.append(tableName)
				.append(
						" a,economic_data b,economic_indicator c where  a.economicDataId=b.id  and b.indicatorid=c.id  and  b.publishDate >='")
				.append(begDate).append("' and b.publishDate <='").append(endDate).append("' and a.symbol='").append(symbol).append("'");
		String queryTime = cmbQueryTime.getSelectedItem().toString();
		if (!"".equals(queryTime)) {
			sbSql.append(" and  b.publishTime='").append(queryTime).append("' ");
		}
		String indicatorName = cmbIndicator.getSelectedItem().toString();
		if (!"".equals(indicatorName)) {
			sbSql.append(" and b.indicator like'%").append(indicatorName).append("%'");
		}
		sbSql.append(" order by country,b.publishDate desc ,b.publishTime asc");
		JTable table = new TimingValueTable(sbSql.toString(), symbol).getJtable();
		{
			scrlEconominDataMainTabel.setViewportView(table);
		}
	}

	public void setChart(String indicatorId, String symbol, String date, String time) {
		scrlChart.setViewportView(new TimingValueLineChart().getChartPanel(indicatorId, symbol, date, time));
	}

	public void setKline(JComponent kLine) {
		tabBottom.remove("K线图");
		tabBottom.addTab("K线图", ImageContext.TabTask, kLine, "K线图", false);
	}

	public void queryEconomicNewsTablel(Map<String, String> mapParam) {
		EconomicNewsTable economicNewsTable = new EconomicNewsTable(mapParam);
		economicNewsTable.setJtableWithSelected(scrlNewsTabel, mapParam);
	}

	public void queryEconomicDataDateTimeTablel(Map<String, String> mapParam) {
		String publishDate = UtilCollection.isNilMap(mapParam, "publishDate");
		String publishTime = UtilCollection.isNilMap(mapParam, "publishTime");
		String source = UtilCollection.isNilMap(mapParam, "source");
		StringBuilder sbSql = new StringBuilder(
				"select  a.*,to_char(a.ModifyDate,'hh24miss')modifyTime,b.indicator as indicatorName,b.indicatoreffect,b.matchrate,b.analysisreport,a.source from economic_data a,economic_indicator b where a.indicatorId=b.id(+) ");
		if (!"".equals(publishDate)) {
			sbSql.append(" and a.publishDate='" + publishDate + "'");
		}
		if (!"".equals(publishTime)) {
			sbSql.append(" and a.publishTime>='" + publishTime + "'");
		}
		sbSql.append("  and a.source = " + source);
		sbSql.append(" order by publishdate desc ,publishTime asc,a.country");
		{
			EconomicDataTable economicDataTable = new EconomicDataTable(true, false, sbSql.toString());
			economicDataTable.setRowRender(publishDate, publishTime, 4, 5);
			scrlEconominDataRelationTabel.setViewportView(economicDataTable.getJtable());
		}
	}
}
