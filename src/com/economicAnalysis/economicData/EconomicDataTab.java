package com.economicAnalysis.economicData;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import com.economic.indicator.EconomicIndicatorDao;
import com.economic.news.EconomicNewsTable;
import common.component.SButton;
import common.component.SCalendar;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.component.STextField;
import common.timeSchedule.TimeSchedule;
import common.timeSchedule.TimeScheduleTask;
import common.util.collection.UtilCollection;
import common.util.conver.UtilConver;
import common.util.string.UtilString;
import config.dictionary.DictionaryDao;
import consts.Const;
import consts.ImageContext;

/**
 * @Description: 经济数据
 * @date Feb 17, 2014
 * @author:fgq
 */
public class EconomicDataTab {
	private static final long serialVersionUID = 1L;
	private JSplitPane splMain;
	private JSplitPane splEconomicData;
	// private JSplitPane splEconomicDataRelation;
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
	private JScrollPane scrlEconomicDataTablel;
	// private JScrollPane scrlEconomicDataDateTimeTablel;// 相关时点经济数据
	private JScrollPane scrlEconomicNewsTable;// 相关时点即时新闻
	private JLabel lIndicator;
	private SComboBox cmbIndicator;
	private static EconomicDataTab tab;
	private JTable economicDataTable;
	// private JTable economicDataDateTimeTablel;

	private SLabel lScource;
	private SComboBox cmbSource;
	// 福汇最近未发布时间
	public List<String> listUnPublisTimeFh = new ArrayList<String>();
	public SButton btnAuto;
	public TimeSchedule timeSchedule;
	private SLabel lImportance;
	private SComboBox cmbImportance;

	class MyTimerTask extends TimeScheduleTask {// 继承TimerTask类
		public void run() {
			if (this.isRunning) {
				query(true);
			}
		}
	}

	public static EconomicDataTab getInstance() {
		if (tab == null)
			tab = new EconomicDataTab();
		return tab;
	}

	public List<String> getListUnPublisTimeFh() {
		if (listUnPublisTimeFh == null)
			listUnPublisTimeFh = new ArrayList<String>();
		return listUnPublisTimeFh;
	}

	public EconomicDataTab() {
		splMain = new SSplitPane(0, 35, false);
		splEconomicData = new SSplitPane(0, 0.5, false);
		// splEconomicDataRelation = new SSplitPane(0, 0.5, false);
		scrlEconomicDataTablel = new JScrollPane();
		splEconomicData.add(scrlEconomicDataTablel, JSplitPane.TOP);
		scrlEconomicNewsTable = new JScrollPane();
		// splEconomicData.add(splEconomicDataRelation, JSplitPane.BOTTOM);
		splEconomicData.add(scrlEconomicNewsTable, JSplitPane.BOTTOM);
		// scrlEconomicDataDateTimeTablel = new JScrollPane();

		// splEconomicDataRelation.add(scrlEconomicDataDateTimeTablel,
		// JSplitPane.TOP);
		// splEconomicDataRelation.add(scrlEconomicNewsTable,
		// JSplitPane.BOTTOM);
		splMain.add(getPnlTop(), JSplitPane.TOP);
		splMain.add(splEconomicData, JSplitPane.BOTTOM);
		timeSchedule = new TimeSchedule(new MyTimerTask(), 1000);
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
				tbQuery.addSeparator(new Dimension(5, 30));
			}
			{
				txtBegDate = new STextField(UtilConver.dateToStr(Const.fm_yyyyMMdd));
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
				tbQuery.addSeparator(new Dimension(5, 30));
			}
			{
				lEndDate = new SLabel("结束日期");
				lEndDate.setSize(60, 17);
				lEndDate.setMinimumSize(new java.awt.Dimension(60, 17));
				lEndDate.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lEndDate);
			}
			{
				tbQuery.addSeparator(new Dimension(5, 30));
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
				tbQuery.addSeparator(new Dimension(5, 30));
			}
			{
				lQueryTime = new SLabel("查询时间");
				tbQuery.add(lQueryTime);
				lQueryTime.setSize(60, 17);
				lQueryTime.setMinimumSize(new java.awt.Dimension(60, 17));
				lQueryTime.setMaximumSize(new java.awt.Dimension(60, 17));
			}
			{
				cmbQueryTime = new SComboBox(DictionaryDao.getInstance().getDicionary("QueryTime"));
				cmbQueryTime.setEditable(true);
				tbQuery.add(cmbQueryTime);
				cmbQueryTime.setSelectedIndex(0);
				cmbQueryTime.setSize(250, 24);
				cmbQueryTime.setMinimumSize(new java.awt.Dimension(80, 24));
				cmbQueryTime.setMaximumSize(new java.awt.Dimension(80, 24));
			}
			{
				tbQuery.addSeparator(new Dimension(5, 30));
			}
			{
				lScource = new SLabel("来源");
				lScource.setSize(40, 17);
				lScource.setMinimumSize(new java.awt.Dimension(40, 17));
				lScource.setMaximumSize(new java.awt.Dimension(40, 17));
				tbQuery.add(lScource);
			}
			{
				cmbSource = new SComboBox(Const.EconomicDataSource);
				cmbSource.setSize(120, 24);
				cmbSource.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbSource.setMaximumSize(new java.awt.Dimension(120, 24));
				tbQuery.add(cmbSource);
			}
			{
				tbQuery.addSeparator(new Dimension(5, 30));
			}
			{
				lCountry = new SLabel("国家");
				lCountry.setSize(40, 17);
				lCountry.setMinimumSize(new java.awt.Dimension(40, 17));
				lCountry.setMaximumSize(new java.awt.Dimension(40, 17));
				tbQuery.add(lCountry);
			}
			{
				cmbCountry = new SComboBox(DictionaryDao.getInstance().getDicionary("COUNTRY"));
				cmbCountry.setSize(120, 24);
				cmbCountry.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbCountry.setMaximumSize(new java.awt.Dimension(120, 24));
				cmbCountry.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						ComboBoxModel comboBoxModel = new DefaultComboBoxModel(EconomicIndicatorDao.getInstance().getIndicatorName(e.getItem().toString()));
						cmbIndicator.setModel(comboBoxModel);
					}
				});
				tbQuery.add(cmbCountry);
			}

			{
				tbQuery.addSeparator(new Dimension(5, 30));
			}
			{
				lIndicator = new SLabel("指标名称");
				lIndicator.setSize(60, 17);
				lIndicator.setMinimumSize(new java.awt.Dimension(60, 17));
				lIndicator.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lIndicator);
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
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lImportance = new SLabel("重要性");
				lImportance.setSize(60, 17);
				lImportance.setMinimumSize(new java.awt.Dimension(60, 17));
				lImportance.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lImportance);
			}
			{
				cmbImportance = new SComboBox(DictionaryDao.getInstance().getDicionaryArr("Importance"));
				cmbImportance.setEditable(true);
				cmbImportance.setSize(80, 24);
				cmbImportance.setMinimumSize(new java.awt.Dimension(80, 24));
				cmbImportance.setMaximumSize(new java.awt.Dimension(80, 24));
				tbQuery.add(cmbImportance);
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				btnQuery = new SButton("查  询", ImageContext.Query);
				btnQuery.setSize(90, 24);
				btnQuery.setMinimumSize(new java.awt.Dimension(90, 24));
				btnQuery.setMaximumSize(new java.awt.Dimension(90, 24));
				btnQuery.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						query(false);
					}
				});
				tbQuery.add(btnQuery);
				{
					tbQuery.addSeparator(new Dimension(10, 30));
				}
				btnAuto = new SButton("自动刷新", ImageContext.NowDate);
				btnAuto.setSize(90, 24);
				btnAuto.setMinimumSize(new java.awt.Dimension(90, 24));
				btnAuto.setMaximumSize(new java.awt.Dimension(90, 24));
				btnAuto.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (timeSchedule.isRunning) {
							timeSchedule.stop();
							btnAuto.setText("自动刷新");
							btnAuto.setForeground(Color.black);
						} else {
							timeSchedule.start();
							btnAuto.setText("停止刷新");
							btnAuto.setForeground(Color.red);
						}
					}
				});
				tbQuery.add(btnAuto);
			}
		}
		return tbQuery;
	}

	public void queryEconomicNewsTablel(Map<String, String> mapParam) {
		EconomicNewsTable economicNewsTable = new EconomicNewsTable(mapParam);
		economicNewsTable.setJtableWithSelected(scrlEconomicNewsTable, mapParam);
	}

//	public void queryEconomicDataDateTimeTablel(Map<String, String> mapParam) {
//		String id = UtilCollection.isNilMap(mapParam, "id");
//		String publishDate = UtilCollection.isNilMap(mapParam, "publishDate");
//		String publishTime = UtilCollection.isNilMap(mapParam, "publishTime");
//		String importance = UtilCollection.isNilMap(mapParam, "importance");
//		StringBuilder sbSql = new StringBuilder(
//				"select  a.*,to_char(a.ModifyDate,'hh24miss')modifyTime,b.indicator as indicatorName,b.indicatoreffect from economic_data a,economic_indicator b where a.indicatorId=b.id(+) ");
//		if (!"".equals(id)) {
//			sbSql.append(" and a.id!='" + id + "'");
//		}
//		if (!"".equals(publishDate)) {
//			sbSql.append(" and a.publishDate='" + publishDate + "'");
//		}
//		if (!"".equals(publishTime)) {
//			sbSql.append(" and a.publishTime>='" + publishTime + "'");
//		}
//		if (!"".equals(importance)) {
//			sbSql.append(" and a.importance>='" + importance + "'");
//		}
//		sbSql.append("  and a.source = " + Const.EconomicDataSourceKey[cmbSource.getSelectedIndex()]);
//		sbSql.append(" order by publishdate desc ,publishTime asc,a.country,decode(a.importance,'高',0,'中',1,'低',2,100),a.indicatorId,b.indicator");
//		{
//			// economicDataDateTimeTablel = new EconomicDataTable(true, false,
//			// sbSql.toString()).getJtable();
//			// scrlEconomicDataDateTimeTablel.setViewportView(economicDataDateTimeTablel);
//		}
//	}

	public void query(Map<String, String> mapParam) {
		String indicatorId = UtilCollection.isNilMap(mapParam, "indicatorId");
		String publishDate = UtilCollection.isNilMap(mapParam, "publishDate");
		String publishTime = UtilCollection.isNilMap(mapParam, "publishTime");
		String importance = UtilCollection.isNilMap(mapParam, "importance");
		StringBuilder sbSql = new StringBuilder(
				"select  a.*,to_char(a.ModifyDate,'hh24miss')modifyTime,b.indicator as indicatorName,b.indicatoreffect,b.matchrate,b.analysisreport from economic_data a,economic_indicator b where a.indicatorId=b.id(+) ");
		if (!"".equals(indicatorId)) {
			sbSql.append(" and a.indicatorId='" + indicatorId + "'");
		}
		if (!"".equals(publishDate)) {
			sbSql.append(" and a.publishDate='" + publishDate + "'");
		}
		if (!"".equals(publishTime)) {
			sbSql.append(" and a.publishTime='" + publishTime + "'");
		}
		if (!"".equals(importance)) {
			sbSql.append(" and a.importance>='" + importance + "'");
		}
		sbSql.append("  and a.source = " + Const.EconomicDataSourceKey[cmbSource.getSelectedIndex()]);
		sbSql.append(" order by publishdate desc ,publishTime asc,a.country,decode(a.importance,'高',0,'中',1,'低',2,100),a.indicatorId,b.indicator");
		{
			economicDataTable = new EconomicDataTable(false, false, sbSql.toString()).getJtable();
			scrlEconomicDataTablel.setViewportView(economicDataTable);
		}
	}

	public void query(boolean isAuto) {
		String beginSql = "";
		if (isAuto) {
			// 调度保护：当指标中侦查中的指标未到达时点时，函数自动跑空，只要是防止过度访问数据库。
			if (listUnPublisTimeFh != null && listUnPublisTimeFh.size() > 0) {
				// 取list中的第一个，即最近的未发布时间
				if (UtilConver.dateToStr("HHmm").compareTo(listUnPublisTimeFh.get(0)) < 0) {
					return;
				} else {
					System.out.println(UtilConver.dateToStr(Const.fm_HHmmss) + "到达最近的发布时间：" + listUnPublisTimeFh.get(0) + ",执行刷新");
				}
			}
			beginSql = "select  a.*,to_char(a.ModifyDate,'hh24miss')modifyTime,b.indicator as indicatorName,b.indicatoreffect,b.matchrate,b.analysisreport from economic_data a,economic_indicator b where a.indicatorId=b.id(+) and  publishdate='"
					+ UtilConver.dateToStr("yyyy-MM-dd") + "'";
		} else {
			String country = cmbCountry.getSelectedItem().toString();
			String begDate = UtilString.isNil(txtBegDate.getText());
			String endDate = UtilString.isNil(txtEndDate.getText());
			if ("全部".equals(country)) {
				beginSql = "select   a.*,to_char(a.ModifyDate,'hh24miss')modifyTime,b.indicator as indicatorName,b.indicatoreffect,b.matchrate,b.analysisreport from economic_data a,economic_indicator b where a.indicatorId=b.id(+) and   publishdate >='"
						+ begDate + "' and publishdate <='" + endDate + "'";
			} else {
				beginSql = "select   a.*,to_char(a.ModifyDate,'hh24miss')modifyTime,b.indicator as indicatorName,b.indicatoreffect,b.matchrate,b.analysisreport from economic_data a,economic_indicator b where a.indicatorId=b.id(+) and  a.country like'%"
						+ country + "%' and publishdate >='" + begDate + "' and publishdate <='" + endDate + "'";
			}
			String queryTimes = cmbQueryTime.getSelectedItem().toString();
			if (!"".equals(UtilString.isNil(queryTimes))) {
				beginSql += " and publishTime='" + queryTimes + "' ";
			}
			String importance = cmbImportance.getSelectedItem().toString();
			if (!"".equals(UtilString.isNil(importance)) && !"全部".equals(UtilString.isNil(importance))) {
				beginSql += " and a.importance='" + importance + "' ";
			}
			String indicator = cmbIndicator.getSelectedItem().toString();
			if (!"".equals(UtilString.isNil(indicator))) {
				beginSql += " and a.indicator like '%" + indicator + "%' ";
			}
			beginSql += "  and a.source = " + Const.EconomicDataSourceKey[cmbSource.getSelectedIndex()];

		}
		String groupSql = " order by publishdate desc ,publishTime asc,a.country,decode(a.importance,'高',0,'中',1,'低',2,100),a.indicatorId,b.indicator";
		String sql = beginSql + groupSql;
		{
			economicDataTable = new EconomicDataTable(false, isAuto, sql).getJtable();
			scrlEconomicDataTablel.setViewportView(economicDataTable);
		}
	}

	public JTable getTable() {
		return economicDataTable;
	}
}
