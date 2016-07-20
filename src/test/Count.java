package test;

import common.util.Math.UtilMath;

public class Count {
	public static void main(String[] params) {
		double stopRate = 0.2;// 最大止损金额占总金额比率
		double amount = 1500;// 总金额
		int stopLoosPoint =44;// 止损点数
		int takeProfitPoint =30;// 止盈点数
		int count = 5;// 操作次数
		double lots = 0.1;
		for (int i = 1; i <= count; i++) {
			double stopLoos = UtilMath.round(amount * stopRate, 2);
			lots = UtilMath.round(stopLoos / stopLoosPoint / 10, 1);// 确定lots
			if (lots > 5000)
				lots = 5000;
			stopLoos = UtilMath.round(lots * stopLoosPoint * 10, 2);// 在确定lots的前提下，重算止损
			double takeProfit = UtilMath.round(lots * takeProfitPoint * 10, 2);
			double profitRate = UtilMath.round(takeProfit * 100 / amount, 2);
			double stopLoosRate = UtilMath.round(stopLoos * 100 / amount, 2);
			String amountShow = "";
			if (amount > 100000000) {
				amountShow = UtilMath.round(amount / 100000000, 2) + "亿";
			} else if (amount > 10000000) {
				amountShow = UtilMath.round(amount / 10000000, 2) + "千万";
			} else if (amount > 1000000) {
				amountShow = UtilMath.round(amount / 1000000, 2) + "百万";
			} else {
				amountShow = amount + "";
			}
			String takeProfitShow = "";
			if (takeProfit > 100000000) {
				takeProfitShow = UtilMath.round(takeProfit / 100000000, 2) + "亿";
			} else if (takeProfit > 10000000) {
				takeProfitShow = UtilMath.round(takeProfit / 10000000, 2) + "千万";
			} else if (takeProfit > 1000000) {
				takeProfitShow = UtilMath.round(takeProfit / 1000000, 2) + "百万";
			} else {
				takeProfitShow = takeProfit + "";
			}
			String stopLoosShow = "";
			if (stopLoos > 100000000) {
				stopLoosShow = UtilMath.round(stopLoos / 100000000, 2) + "亿";
			} else if (stopLoos > 10000000) {
				stopLoosShow = UtilMath.round(stopLoos / 10000000, 2) + "千万";
			} else if (stopLoos > 1000000) {
				stopLoosShow = UtilMath.round(stopLoos / 1000000, 2) + "百万";
			} else {
				stopLoosShow = stopLoos + "";
			}

			int computerCount = (int) Math.ceil(lots / 50);// 机器数目
			System.out.println(i + "     " + amountShow + "   " + lots + "   " + computerCount + "   " + takeProfitPoint + "   " + takeProfitShow + "   " + profitRate + "%   "
					+ stopLoosPoint + "   " + stopLoosShow + "   " + stopLoosRate + "%");
			amount = amount + takeProfitPoint * lots * 10;
		}
	}
}
