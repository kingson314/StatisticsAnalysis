//package com.graphAnalysis.klineSimulate;
//
///**
// * @Description:
// * @date Apr 10, 2014
// * @author:fgq
// */
//public class BeanSimulate {
//	private String symbol;
//	private int bars;
//	// 多周期分页图
//	private boolean isPageView;
//	// 两个周期的对比图
//	private boolean isComppareView;
//	// 不管是分页图，还是对比图，选中的周期都放进这个数组里
//	private int[] periodArr;
//
//	private String begDate;
//	private String begTime;
//	private String endDate;
//	private String endTime;
//	// 定时器每次执行，获取行情的时间与上次获取行情时间的差；这个值可以手动调节
//	private int seconds;
//	// 执行次数
//	private int count = 0;
//
//	public int getCount() {
//		return count;
//	}
//
//	public void setCount(int count) {
//		this.count = count;
//	}
//
//	public String getSymbol() {
//		return symbol;
//	}
//
//	public void setSymbol(String symbol) {
//		this.symbol = symbol;
//	}
//
//	public int getBars() {
//		return bars;
//	}
//
//	public void setBars(int bars) {
//		this.bars = bars;
//	}
//
//	public boolean isPageView() {
//		return isPageView;
//	}
//
//	public void setPageView(boolean isPageView) {
//		this.isPageView = isPageView;
//	}
//
//	public boolean isComppareView() {
//		return isComppareView;
//	}
//
//	public void setComppareView(boolean isComppareView) {
//		this.isComppareView = isComppareView;
//	}
//
//	public int[] getPeriodArr() {
//		return periodArr;
//	}
//
//	public void setPeriodArr(int[] periodArr) {
//		this.periodArr = periodArr;
//	}
//
//	public String getBegDate() {
//		return begDate;
//	}
//
//	public void setBegDate(String begDate) {
//		this.begDate = begDate;
//	}
//
//	public String getBegTime() {
//		return begTime;
//	}
//
//	public void setBegTime(String begTime) {
//		this.begTime = begTime;
//	}
//
//	public String getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//
//	public String getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(String endTime) {
//		this.endTime = endTime;
//	}
//
//	public int getSeconds() {
//		return seconds;
//	}
//
//	public void setSeconds(int seconds) {
//		this.seconds = seconds;
//	}
//}
