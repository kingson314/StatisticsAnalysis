package com.trade;

import java.awt.event.MouseWheelEvent;


import com.config.account.Account;
import com.config.account.AccountDao;

import common.component.STextField;
import common.util.Math.UtilMath;
import config.dictionary.DictionaryDao;
import config.symbol.SymbolDao;

public class FunTrade {

	public static String loginAccountId = "75304";
	public static Account account = AccountDao.getInstance().getBean(loginAccountId);
	public static String[] symbol = SymbolDao.getInstance().getSymbolArr(account.getServer());

	public static void setLot(STextField txt, MouseWheelEvent e, Account account) {
		int systemScrolPages = Integer.valueOf(DictionaryDao.getInstance().getValue("系统参数", "鼠标滑轮滚动行数"));
		double defautLots = Double.valueOf(txt.getText());
		int point = UtilMath.getDigits(defautLots);
		// 小数位数
		double digits = systemScrolPages * Math.pow(10, point);
		double maxLot = Double.valueOf(account.getMaxLot());
		double minLot = Double.valueOf(account.getMinLot());
		int count = e.getWheelRotation();
		if (count < 0) {// 向上
			defautLots = defautLots + e.getScrollAmount() / digits;
			if (defautLots > maxLot)
				defautLots = maxLot;
		} else {// 向下
			defautLots = defautLots - e.getScrollAmount() / digits;
			if (defautLots < minLot)
				defautLots = minLot;
		}
		// System.out.println(defautLots);
		defautLots = (Math.round(defautLots * Math.pow(10, point)) / Math.pow(10, point));
		txt.setText(String.valueOf(defautLots));
	}

	public static void setValue(STextField txt, MouseWheelEvent e) {
		int systemScrolPages = Integer.valueOf(DictionaryDao.getInstance().getValue("系统参数", "鼠标滑轮滚动行数"));
		double defautLots = Double.valueOf(txt.getText());
		int point = UtilMath.getDigits(defautLots);
		// 小数位数
		double digits = systemScrolPages * Math.pow(10, point);
		int count = e.getWheelRotation();
		if (count < 0) {// 向上
			defautLots = defautLots + e.getScrollAmount() / digits;
		} else {// 向下
			defautLots = defautLots - e.getScrollAmount() / digits;
		}
		// System.out.println(defautLots);
		defautLots = (Math.round(defautLots * Math.pow(10, point)) / Math.pow(10, point));
		txt.setText(String.valueOf(defautLots));
	}
}
