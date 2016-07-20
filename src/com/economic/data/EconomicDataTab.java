package com.economic.data;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;


import common.component.SButton;
import common.component.SCalendar;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.component.STextField;
import common.timeSchedule.TimeSchedule;
import common.timeSchedule.TimeScheduleTask;
import common.util.array.UtilArray;
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
	private static EconomicDataTab tab;
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
	private JScrollPane scrlTabel;
	private SButton btnAuto;
	private TimeSchedule timeSchedule;

	class MyTimerTask extends TimeScheduleTask {// 继承TimerTask类
		public void run() {
			if (this.isRunning)
				query(true);
		}
	}

	public static EconomicDataTab getInstance() {
		if (tab == null)
			tab = new EconomicDataTab();
		return tab;
	}

	public EconomicDataTab() {
		splMain = new SSplitPane(0, 35, false);
		scrlTabel = new JScrollPane();
		splMain.add(getPnlTop(), JSplitPane.TOP);
		splMain.add(scrlTabel, JSplitPane.BOTTOM);
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
				cmbCountry.setSize(120, 24);
				cmbCountry.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbCountry.setMaximumSize(new java.awt.Dimension(120, 24));
				tbQuery.add(cmbCountry);
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
						query(false);
					}
				});
				tbQuery.add(btnQuery);
				{
					tbQuery.addSeparator(new Dimension(30, 30));
				}

				btnAuto = new SButton("自动刷新", ImageContext.Query);
				btnAuto.setSize(120, 24);
				btnAuto.setMinimumSize(new java.awt.Dimension(120, 24));
				btnAuto.setMaximumSize(new java.awt.Dimension(120, 24));
				btnAuto.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (timeSchedule.isRunning) {
							timeSchedule.stop();
							btnAuto.setText("自动刷新");
						} else {
							timeSchedule.start();
							btnAuto.setText("停止刷新");
						}
					}
				});
				tbQuery.add(btnAuto);
			}
		}
		return tbQuery;
	}

	private void query(boolean isAuto) {
		String country = cmbCountry.getSelectedItem().toString();
		String begDate = UtilString.isNil(txtBegDate.getText());
		String endDate = UtilString.isNil(txtEndDate.getText());
		String sql = "";
		String queryTimesSql = "";
		String beginSql = "";
		if (isAuto) {
			beginSql = "select  * from economic_data where publishdate='" + UtilConver.dateToStr("yyyy-MM-dd") + "'";
		} else {
			String[][] queryTimes = UtilArray.getArray2(cmbQueryTime.getSelectedItem().toString());
			if ("全部".equals(country)) {
				beginSql = " select  * from economic_data where  publishdate >='" + begDate + "' and publishdate <='" + endDate + "'";
			} else {
				beginSql = " select  * from economic_data where country like'%" + country + "%' and publishdate >='" + begDate + "' and publishdate <='" + endDate + "'";
			}
			if (queryTimes != null) {
				queryTimesSql = " and (  ";
				for (int i = 0; i < queryTimes.length; i++) {
					if (queryTimes[i].length != 2)
						continue;// 要求查询时段必须成对出现
					if (i == queryTimes.length - 1)
						queryTimesSql = queryTimesSql + "(publishTime>='" + queryTimes[i][0] + ":00" + "' and publishTime<='" + queryTimes[i][1] + ":00" + "')";
					else {
						queryTimesSql = queryTimesSql + "(publishTime>='" + queryTimes[i][0] + ":00" + "' and publishTime<='" + queryTimes[i][1] + ":00" + "') or ";
					}
				}
				queryTimesSql = queryTimesSql + ")";
			}
		}
		String groupSql = " order by publishdate desc ,publishTime asc,country";
		sql = beginSql + queryTimesSql + groupSql;
		{
			scrlTabel.setViewportView(new EconomicDataTable(sql).getJtable());
		}
	}
}
