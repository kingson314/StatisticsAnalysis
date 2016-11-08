package com.economic.indicator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;


import common.component.SButton;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.util.string.UtilString;
import config.dictionary.DictionaryDao;
import consts.ImageContext;

/**
 * @Description:经济指标
 * @date May 20, 2014
 * @author:fgq
 */
public class EconomicIndicatorTab {
	private static EconomicIndicatorTab tab;
	private JSplitPane splMain;
	private JButton btnQuery;
	private JToolBar tbQuery;
	private SComboBox cmbCountry;
	private SLabel lCountry;
	private JScrollPane scrlTabel;
	private JTable tblIndicator;
	private SLabel lIndicator;
	private SComboBox cmbIndicator;
	private SLabel lImportance;
	private SComboBox cmbImportance;
	private SButton btnUpdateImportanceByBatch;

	public static EconomicIndicatorTab getInstance() {
		if (tab == null)
			tab = new EconomicIndicatorTab();
		return tab;
	}

	public EconomicIndicatorTab() {
		splMain = new SSplitPane(0, 35, false);
		scrlTabel = new JScrollPane();
		splMain.add(getPnlTop(), JSplitPane.TOP);
		splMain.add(scrlTabel, JSplitPane.BOTTOM);
	}

	public JSplitPane getSplMain() {
		return splMain;
	}

	private JToolBar getPnlTop() {
		if (tbQuery == null) {
			tbQuery = new JToolBar();
			{
				tbQuery.addSeparator(new Dimension(10, 30));
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
				cmbIndicator.setSize(200, 24);
				cmbIndicator.setMinimumSize(new java.awt.Dimension(200, 24));
				cmbIndicator.setMaximumSize(new java.awt.Dimension(200, 24));
				tbQuery.add(cmbIndicator);
			}
			{
				tbQuery.addSeparator(new Dimension(30, 30));
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

			{
				btnUpdateImportanceByBatch = new SButton("批量更新指标重要性", ImageContext.Refresh);
				btnUpdateImportanceByBatch.setVisible(false);
				btnUpdateImportanceByBatch.setSize(150, 24);
				btnUpdateImportanceByBatch.setMinimumSize(new java.awt.Dimension(120, 24));
				btnUpdateImportanceByBatch.setMaximumSize(new java.awt.Dimension(120, 24));
				btnUpdateImportanceByBatch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						updateImportance();
					}
				});
				tbQuery.add(btnUpdateImportanceByBatch);
			}
		}
		return tbQuery;
	}

	private void updateImportance() {
		String sql = "update economic_indicator a set a.importance=(select decode(max(decode(importance,'高',3,'中',2,'低',1,0)),3,'高',2,'中',1,'低') "
				+ "from economic_data b  where source=1 and indicatorid is not null  and a.id=b.indicatorid group by indicatorid)  ";
		EconomicIndicatorDao.getInstance().executeUpdate(sql);
	}

	private void query() {
		String country = cmbCountry.getSelectedItem().toString();
		String sql = "";
		String beginSql = "";
		if ("全部".equals(country)) {
			beginSql = " select  * from economic_Indicator a where 1=1 ";
		} else {
			beginSql = " select  * from economic_Indicator a where country like'%" + country + "%'";
		}

		String indicator = cmbIndicator.getSelectedItem().toString();
		if (!"".equals(UtilString.isNil(indicator))) {
			beginSql += " and a.indicator like '%" + indicator + "%' ";
		}

		String importance = cmbImportance.getSelectedItem().toString();
		if (!"".equals(UtilString.isNil(importance)) && !"全部".equals(UtilString.isNil(importance))) {
			beginSql += " and a.importance like '%" + importance + "%' ";
		}
		String orderSql = " order by importance,ord,country,indicator";
		sql = beginSql + orderSql;
		{
			this.tblIndicator = new EconomicIndicatorTable(sql).getJtable();
			scrlTabel.setViewportView(this.tblIndicator);
		}
	}
}
