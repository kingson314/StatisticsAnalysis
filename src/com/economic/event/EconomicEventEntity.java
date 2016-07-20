package com.economic.event;

/**
 * @Description: 财经事件
 * @date Feb 8, 2014
 * @author:fgq
 */
public class EconomicEventEntity {
	// guid
	private String id;
	// 发生日期YYYYMMDD
	private String occurDate;
	// 发生时间 HHmmss
	private String occurTime;
	// 国家
	private String country;
	// 地点
	private String site;
	// 重要性
	private String importance;
	// 事件
	private String event;
	// 状态 0 表示正常 1 表示不可用
	private int state;
	// 排序
	private int ord;
	// 说明
	private String memo;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
}
