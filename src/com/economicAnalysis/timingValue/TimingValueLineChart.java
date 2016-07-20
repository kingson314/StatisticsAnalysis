package com.economicAnalysis.timingValue;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import common.jfreechart.line.Line;

/**
 * @Description:
 * @date 2014-4-13
 * @author:fgq
 */
public class TimingValueLineChart {
	String titleChart = "时点价差分析";
	String titleSide = "价差";
	String titleUnder = "时点";

	public ChartPanel getChartPanel(String economicDataId, String symbol, String date, String time) {
		DefaultCategoryDataset dataSet = null;
		dataSet = TimingValueDao.getInstance().getDataSert(symbol, economicDataId, date, time);
		return new Line(titleChart, titleSide, titleUnder, dataSet).getChart();
	}
}
