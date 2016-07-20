package com.economic.data;

/**
 * @Description: 经济数据
 * @date Feb 8, 2014
 * @author:fgq
 */
public class EconomicDataEntity {
	// guid
	private String id;
	// 发布日期YYYYMMDD
	private String publishDate;
	// 公布时间 HHmmss
	private String publishTime;
	// 国家
	private String country;
	// 指标名称
	private String indicator;
	// 重要性
	private String importance;
	// 前值
	private String previousValue;
	// 预测值
	private String predictedValue;
	// 公布值
	private String publishedValue;
	// 状态 0 表示正常 1 表示不可用
	private int state;
	// 排序
	private int ord;
	// 说明
	private String memo;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}

	public String getPredictedValue() {
		return predictedValue;
	}

	public void setPredictedValue(String predictedValue) {
		this.predictedValue = predictedValue;
	}

	public String getPublishedValue() {
		return publishedValue;
	}

	public void setPublishedValue(String publishedValue) {
		this.publishedValue = publishedValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
