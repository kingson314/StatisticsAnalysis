package com.economicAnalysis.timingMaximinRangeRate;

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

import com.economic.indicator.EconomicIndicatorDao;

import common.component.SButton;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.util.UtilFun;
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
public class TimingMaximinRangeRateTab {
	private SSplitPane splMain;
	private JScrollPane scrlDataTabel;
	private JToolBar tbQuery;
	private SLabel lIndicator;
	private SComboBox cmbIndicator;
	private SLabel lSymbol;
	private SComboBox cmbSymbol;
	private SButton btnQuery;
	private SLabel lRange;
	private SComboBox cmbRange;
	private SLabel lCountry;
	private SComboBox cmbCountry;
	private SLabel lMinute;
	private SComboBox cmbMinute;
	private SLabel lImportance;
	private SComboBox cmbImportance;
	private static TimingMaximinRangeRateTab tab = null;
	private static String[] indicatorIdArr;
	private static String[] indicatorNameArr;

	public static TimingMaximinRangeRateTab getInstance() {
		if (tab == null)
			tab = new TimingMaximinRangeRateTab();
		return tab;
	}

	public TimingMaximinRangeRateTab() {
		splMain = new SSplitPane(0, 35, false);
		splMain.add(getPnlTop(), JSplitPane.TOP);// 按钮工具栏
		scrlDataTabel = new JScrollPane();
		splMain.add(scrlDataTabel, JSplitPane.BOTTOM);// 数据
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
				cmbCountry.setSize(80, 24);
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
				cmbIndicator.setSize(200, 24);
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
				cmbSymbol.setSize(80, 24);
				cmbSymbol.setMinimumSize(new java.awt.Dimension(80, 24));
				cmbSymbol.setMaximumSize(new java.awt.Dimension(80, 24));
				tbQuery.add(cmbSymbol);
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
				cmbImportance.setSize(60, 24);
				cmbImportance.setMinimumSize(new java.awt.Dimension(60, 24));
				cmbImportance.setMaximumSize(new java.awt.Dimension(60, 24));
				tbQuery.add(cmbImportance);
			}
			{
				tbQuery.addSeparator(new Dimension(30, 30));
			}
			{
				lMinute = new SLabel("第N分钟");
				lMinute.setSize(50, 17);
				lMinute.setMinimumSize(new java.awt.Dimension(60, 17));
				lMinute.setMaximumSize(new java.awt.Dimension(60, 17));
				tbQuery.add(lMinute);
			}
			{
				cmbMinute = new SComboBox(Const.minutePointArr);
				cmbMinute.setSelectedItem("30");
				cmbMinute.setEditable(true);
				cmbMinute.setSize(60, 17);
				cmbMinute.setMinimumSize(new java.awt.Dimension(60, 24));
				cmbMinute.setMaximumSize(new java.awt.Dimension(60, 24));
				tbQuery.add(cmbMinute);
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

	private void query() {
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("symbol", UtilString.isNil(cmbSymbol.getSelectedItem()));
		mapParams.put("indicatorId", indicatorIdArr[cmbIndicator.getSelectedIndex()]);
		mapParams.put("range", UtilString.isNil(cmbRange.getSelectedItem()));
		mapParams.put("country", UtilString.isNil(cmbCountry.getSelectedItem()));
		mapParams.put("minute", UtilString.isNil(cmbMinute.getSelectedItem()));
		mapParams.put("importance", UtilString.isNil(cmbImportance.getSelectedItem()));
		// Table
		scrlDataTabel.setViewportView(new TimingMaximinRangeRateTable(mapParams).getJtable());
	}
	
	public void query(Map<String, String> mapParams) {
		String country=mapParams.get("country");
		String indicator=mapParams.get("indicator");
		cmbCountry.setSelectedItem(country);
		cmbIndicator.setSelectedItem(indicator);
		
		mapParams.put("range", UtilString.isNil(cmbRange.getSelectedItem()));
		mapParams.put("minute", UtilString.isNil(cmbMinute.getSelectedItem()));
		// Table
		scrlDataTabel.setViewportView(new TimingMaximinRangeRateTable(mapParams).getJtable());
	}
}
