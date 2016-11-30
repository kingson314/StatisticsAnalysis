package com.graphAnalysis.klineSimulate;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;


import common.component.SScrollPane;
import common.component.SSplitPane;
import common.component.STabbedPane;
import common.jfreechart.kline.BeanKLine;
import common.jfreechart.kline.KLine;
import common.timeSchedule.TimeSchedule;
import common.timeSchedule.TimeScheduleTask;
import common.util.date.UtilDate;
import consts.Const;
import consts.ImageContext;

/**
 * @Description: 模拟
 * @date 2014-4-9
 * @author:fgq
 */
public class KLineSimulate {
	private static KLineSimulate klineSimulate = null;
	private STabbedPane tb;
	private SScrollPane[] scrl;
	private TimeSchedule timeSchedule;
	private List<BeanKLine> listBeanKLine;
	private SSplitPane spltCompare;

	// 多周期分页图
	private boolean isPageView;
	// 两个周期的对比图
	private boolean isComppareView;
	// 定时器每次执行，获取行情的时间与上次获取行情时间的差；这个值可以手动调节
	private int seconds;

	private KLineSimulate() {
		tb = new STabbedPane();
		listBeanKLine = new ArrayList<BeanKLine>();
		listBeanKLine.clear();
		timeSchedule = new TimeSchedule(new KlineSimulateTask(), 1000);
		scrl = new SScrollPane[Const.PeriodArr.length];
		spltCompare = new SSplitPane(1, 0.5, false);
	}

	public static KLineSimulate getInstance() {
		if (klineSimulate == null)
			klineSimulate = new KLineSimulate();
		return klineSimulate;
	}

	// 返回Tab
	public STabbedPane getSimulateTab() {
		return tb;
	}

	// 初始化Tab变量
	public void setSimulateTab(List<BeanKLine> listBeanKLine) {
		if (isPageView) {
			String[] tabName = new String[listBeanKLine.size()];
			for (int i = 0; i < listBeanKLine.size(); i++) {
				BeanKLine beanKLine = listBeanKLine.get(i);
				tabName[i] = beanKLine.getSymbol();
				tb.addTab(beanKLine.getSymbol() + beanKLine.getPeriod(), ImageContext.TabTask, getScrl(i), beanKLine.getSymbol() + beanKLine.getPeriod(), false);
			}
			tb.setPersistTab(tabName);
		} else if (isComppareView) {
			String[] tabName = new String[listBeanKLine.size()];
			for (int i = 0; i < listBeanKLine.size(); i++) {
				BeanKLine beanKLine = listBeanKLine.get(i);
				tabName[i] = beanKLine.getSymbol() + beanKLine.getPeriod();
			}
			String tabTitle = tabName[0] + " 与 " + tabName[1] + " 对比图";
			tb.addTab(tabTitle, ImageContext.TabTask, spltCompare, tabTitle, false);
			spltCompare.add(getScrl(0), SSplitPane.LEFT);
			spltCompare.add(getScrl(1), SSplitPane.RIGHT);
		}
	}

	private SScrollPane getScrl(int i) {
		if (scrl[i] == null)
			scrl[i] = new SScrollPane(null);
		return scrl[i];
	}

	class KlineSimulateTask extends TimeScheduleTask {// 继承TimerTask类
		private boolean isFinished = true;

		public void run() {
			if (!isFinished)
				return;
			if (this.isRunning) {
				try {
					isFinished = false;
					KLine kLine = KLine.getInstance();
					// if (isComppareView()) {// 对比图
					// SSplitPane split =
					// kLine.getCombinationKline(listBeanKLine.get(1),
					// listBeanKLine.get(0));
					// getScrl(0).setViewportView(split);
					// } else if (isPageView()) {// 分页图
					ChartPanel chartPanel = null;
					for (int i = 0; i < listBeanKLine.size(); i++) {
						chartPanel = kLine.getChartPanel(listBeanKLine.get(i));
						getScrl(i).setViewportView(chartPanel);
					}
					// }
					// 为下次显示时间赋值
					String sDate = listBeanKLine.get(0).getSDate();
					String sTime = listBeanKLine.get(0).getSTime();

					String nextDateTime = UtilDate.addSecond(sDate + " " + sTime, seconds, Const.fm_yyyyMMdd_HHmmss);
					String nextDate = nextDateTime.substring(0, 8);
					String nextTime = nextDateTime.substring(9);
					String endDateTimeSimulate = "";
					for (int i = 0; i < listBeanKLine.size(); i++) {
						listBeanKLine.get(i).setSDate(nextDate);
						listBeanKLine.get(i).setSTime(nextTime);
						endDateTimeSimulate = listBeanKLine.get(i).getEndDataSimulate() + " " + listBeanKLine.get(i).getEndTimeSimulate();
					}
					if (nextDateTime.compareTo(endDateTimeSimulate) > 0) {
						this.isRunning = false;
						KLineSimulateDialog.getInstance().setStart();
					}
				} catch (Exception e) {
				} finally {
					isFinished = true;
				}
			}
		}
	}

	public TimeSchedule getTimeSchedule() {
		return timeSchedule;
	}

	public List<BeanKLine> getListBeanKLine() {
		return listBeanKLine;
	}

	public void setListBeanKLine(List<BeanKLine> listBeanKLine) {
		this.listBeanKLine = listBeanKLine;
	}

	public boolean isPageView() {
		return isPageView;
	}

	public void setPageView(boolean isPageView) {
		this.isPageView = isPageView;
	}

	public boolean isComppareView() {
		return isComppareView;
	}

	public void setComppareView(boolean isComppareView) {
		this.isComppareView = isComppareView;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public void setTimeSchedule(TimeSchedule timeSchedule) {
		this.timeSchedule = timeSchedule;
	}
}
