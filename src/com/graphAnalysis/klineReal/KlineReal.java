package com.graphAnalysis.klineReal;

/**
 * @msg:实时行情K线图
 * @date:2013-01-09
 */

import common.component.SScrollPane;
import common.component.STabbedPane;
import common.jfreechart.kline.BeanKLine;
import common.jfreechart.kline.KLine;
import common.timeSchedule.TimeSchedule;
import common.timeSchedule.TimeScheduleTask;
import consts.Const;
import consts.ImageContext;

public class KlineReal {
	private static KlineReal realKline = null;
	private SScrollPane scrl5;
	private SScrollPane scrl60;
	public TimeSchedule timeSchedule;

	private KlineReal() {
		scrl5 = new SScrollPane(null);
		scrl60 = new SScrollPane(null);
		timeSchedule = new TimeSchedule(new KlineRealTask(), 1000);
	}

	public static KlineReal getInstance() {
		if (realKline == null)
			realKline = new KlineReal();
		return realKline;
	}

	public STabbedPane getRealKLine(String symbol5, String symbol60) {
		STabbedPane tb = new STabbedPane(new String[] { symbol60, symbol5 });
		tb.addTab(symbol60, ImageContext.TabTask, scrl60, symbol60, false);
		tb.addTab(symbol5, ImageContext.TabTask, scrl5, symbol5, false);
		return tb;
	}

	class KlineRealTask extends TimeScheduleTask {// 继承TimerTask类
		public void run() {
			if (this.isRunning) {
				String symbol = "XAUUSD";
				BeanKLine bean5 = BeanKLine.getRealBean5(symbol,Const.DbName);
				BeanKLine bean60 = BeanKLine.getRealBean60(symbol,Const.DbName);
				scrl5.setViewportView(KLine.getInstance().getChartPanel(bean5));
				scrl60.setViewportView(KLine.getInstance().getChartPanel(bean60));
			}
		}
	}
}
