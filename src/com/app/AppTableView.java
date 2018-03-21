package com.app;

import java.awt.Color;
import javax.swing.JComponent;
import com.economic.data.EconomicDataTab;
import com.economic.event.EconomicEventTab;
import com.economic.holiday.EconomicHolidayTab;
import com.economic.indicator.EconomicIndicatorTab;
import com.economic.nationaldept.EconomicNationalDebtTab;
import com.economicAnalysis.timingMaximinRangeRate.TimingMaximinRangeRateTab;
import com.economicAnalysis.timingValue.TimingValueTab;
import com.economicAnalysis.timingmaximin.TimingMaximinTab;
import com.economicAnalysis.timingmaximin30.TimingMaximinTab30;
import com.graphAnalysis.amlitudeRate.AmplitudeRate;
import com.graphAnalysis.amplitude.Amplitude;
import com.graphAnalysis.frequency.Frequency;
import com.graphAnalysis.klineSimulate.KLineSimulate;
import com.orders.OrdersTab;
import common.component.SLabel;
import common.component.STabbedPane;
import consts.Const;
import consts.ImageContext;

/**
 * @info 程序表格视图
 * 
 * @author fgq 20120831
 * 
 */
public class AppTableView {
	private SLabel lImage;
	private static STabbedPane tab = new STabbedPane(null);
	private static AppTableView appTableView = null;

	public static AppTableView getInstance() {
		if (appTableView == null)
			appTableView = new AppTableView();
		return appTableView;
	}

	// 构造，添加默认的3个列表
	private AppTableView() {
		tab.setFont(Const.tfont);
	}

	// 添加列表
	public void addTab(String title) {
		boolean has = tab.hasTab(title);
		if (!has) {
			if (title.equals("经济数据分析"))
				tab.addTab(title, ImageContext.TabTask, com.economicAnalysis.economicData.EconomicDataTab.getInstance().getSplMain(), title, false);
			else if (title.equals("时点价差分析"))
				tab.addTab(title, ImageContext.TabTask, TimingValueTab.getInstance().getSplMain(), title, false);
			else if (title.equals("时点价差极值30"))
				tab.addTab(title, ImageContext.TabTask, TimingMaximinTab30.getInstance().getSplMain(), title, false);
			else if (title.equals("时点价差极值分析"))
				tab.addTab(title, ImageContext.TabTask, TimingMaximinTab.getInstance().getSplMain(), title, false);
			else if (title.equals("时点价差极值波动频率分析"))
				tab.addTab(title, ImageContext.TabTask, TimingMaximinRangeRateTab.getInstance().getSplMain(), title, false);
			else if (title.equals("经济指标"))
				tab.addTab(title, ImageContext.TabTask, EconomicIndicatorTab.getInstance().getSplMain(), title, false);
			else if (title.equals("经济数据"))
				tab.addTab(title, ImageContext.TabTask, EconomicDataTab.getInstance().getSplMain(), title, false);
			else if (title.equals("财经事件"))
				tab.addTab(title, ImageContext.TabTask, EconomicEventTab.getInstance().getSplMain(), title, false);
			else if (title.equals("假期公告"))
				tab.addTab(title, ImageContext.TabTask, EconomicHolidayTab.getInstance().getSplMain(), title, false);
			else if (title.equals("国债发行公告"))
				tab.addTab(title, ImageContext.TabTask, EconomicNationalDebtTab.getInstance().getSplMain(), title, false);
			else if (title.equals("形态出现频率分析"))
				tab.addTab(title, ImageContext.TabTask, Frequency.getInstance().getSplMain(), title, false);
			else if (title.equals("形态振幅分析"))
				tab.addTab(title, ImageContext.TabTask, Amplitude.getInstance().getSplMain(), title, false);
			else if (title.equals("振幅范围频率分析"))
				tab.addTab(title, ImageContext.TabTask, AmplitudeRate.getInstance().getSplMain(), title, false);
			else if (title.equals("历史订单"))
				tab.addTab(title, ImageContext.TabTask, OrdersTab.getInstance().getSplMain(), title, false);
			else if (title.equals("模拟行情图"))
				tab.addTab(title, ImageContext.TabTask, KLineSimulate.getInstance().getSimulateTab(), title, false);
			else {//默认为经济数据分析
//				title = "首页";
//				tab.addTab(title, ImageContext.TabTask, getMainImage(), title, false);
//				tab.setPersistTab(new String[] { "首页" });
				title = "经济数据分析";
				//tab.addTab(title, ImageContext.TabTask, com.economicAnalysis.economicData.EconomicDataTab.getInstance().getSplMain(), title, false);
			}
			tab.setSelected(title);
		} else {
			tab.setSelected(title);
		}
	}

	public SLabel getMainImage() {
		if (lImage == null)
			lImage = new SLabel("", ImageContext.MainView);
		lImage.setForeground(Color.red);
		lImage.setBackground(Color.red);
		return lImage;
	}

	public void addTab(String title, JComponent com) {
		AppMain.appMain.getAppTree().getTree().setSelectionRow(1);// 赋值一个无action的节点，防止最小化再最大化时触发时间
		tab.remove(title);
		tab.addTab(title, ImageContext.TabTask, com, title, false);
		tab.setSelectedIndex(tab.getTabCount() - 1);
	}

	public STabbedPane getTab() {
		return tab;
	}

}
