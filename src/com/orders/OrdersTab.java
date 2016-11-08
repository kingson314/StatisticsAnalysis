package com.orders;

/**
 * @msg:订单分析
 * @date:2013-01-26
 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;


import com.config.account.AccountDao;

import common.component.SButton;
import common.component.SCalendar;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.component.STabbedPane;
import common.component.STextField;
import common.util.array.UtilArray;
import common.util.bean.KeyValue;
import common.util.conver.UtilConver;
import common.util.string.UtilString;
import config.dictionary.DictionaryDao;
import consts.Const;
import consts.ImageContext;

public class OrdersTab {
	private static OrdersTab frequencySplt;
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
	private JLabel lType;
	private SComboBox cmbQueryTime;
	private SLabel lQueryTime;
	private SLabel lAccountId;
	private SComboBox cmbAccountName;

	public static OrdersTab getInstance() {
		if (frequencySplt == null)
			frequencySplt = new OrdersTab();
		return frequencySplt;
	}

	public OrdersTab() {
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
				lType = new SLabel("订单类型");
				lType.setSize(60, 17);
				lType.setMinimumSize(new java.awt.Dimension(60, 17));
				lType.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lType);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbType = new SComboBox(DictionaryDao.getInstance().getDicionary("OrderType"));
				cmbType.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbType.setMaximumSize(new java.awt.Dimension(120, 24));
				tbQuery.add(cmbType);
			}
			{
				tbQuery.addSeparator(new Dimension(10, 30));
			}
			{
				lAccountId = new SLabel("账户ID");
				lAccountId.setSize(60, 17);
				lAccountId.setMinimumSize(new java.awt.Dimension(60, 17));
				lAccountId.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lAccountId);
			}
			{
				tbQuery.addSeparator(new Dimension(2, 30));
			}
			{
				cmbAccountName = new SComboBox(AccountDao.getInstance().getAccountArr());
				cmbAccountName.setMinimumSize(new java.awt.Dimension(120, 24));
				cmbAccountName.setMaximumSize(new java.awt.Dimension(120, 24));
				tbQuery.add(cmbAccountName);
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
			}
		}
		return tbQuery;
	}

	private void query() {
		String orderType = ((KeyValue) cmbType.getSelectedItem()).getName().toString();
		String accountName = cmbAccountName.getSelectedItem().toString();
		String begDate = UtilString.isNil(txtBegDate.getText());
		String endDate = UtilString.isNil(txtEndDate.getText());
		String sql = "";
		String[][] queryTimes = UtilArray.getArray2(cmbQueryTime.getSelectedItem().toString());
		String beginSql = " select  1 ord1,accountName, orderNo, opendate, opentime, symbol, ordertype, lots, profit, openprice, closeprice, stoploss,takeprofit, closedate, closetime, memo, tax, cost, fee  from t_orders  a";
		String conSql = " where  " + "openDate >='" + begDate + "' and openDate <='" + endDate + "'";
		if ("".equals(orderType)) {
		} else {
			conSql = conSql + " and ordertype  ='" + orderType + "' ";
		}
		if ("全部".equals(accountName)) {
		} else {
			conSql += " and name =" + accountName + "' ";
		}
		if (queryTimes != null) {
			conSql += " and (  ";
			for (int i = 0; i < queryTimes.length; i++) {
				if (queryTimes[i].length != 2)
					continue;// 要求查询时段必须成对出现
				if (i == queryTimes.length - 1)
					conSql += "(opentime>='" + queryTimes[i][0] + ":00" + "' and opentime<='" + queryTimes[i][1] + ":00" + "')";
				else {
					conSql += "(opentime>='" + queryTimes[i][0] + ":00" + "' and opentime<='" + queryTimes[i][1] + ":00" + "') or ";
				}
			}
			conSql += ")";
		}
		String orderSql = " order by ord1,accountName,openDate desc,openTime desc";

		String sumSql = "select 2,'合计', '', '', '', '', '', sum(lots), sum(profit), 0, 0, 0,0,'', '', '', sum(tax), sum(cost),sum( fee)  from t_orders ";
		sql = "select * from (" + beginSql + conSql + " union all " + sumSql + conSql + ")" + orderSql;
		System.out.println(sql);
		tabPanel.removeAll();
		{
			JScrollPane scrlTabel = new JScrollPane();
			scrlTabel.setViewportView(new OrdersTable(Const.DbName, sql).getJtable());
			tabPanel.addTab("历史订单", scrlTabel);
		}
	}
}
