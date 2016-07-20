package com.economic.news;

/**
 * @Description: 财经事件
 * @date Feb 8, 2014
 * @author:fgq
 */
public class EconomicNewsEntity {
	// guid
	private String id;
	// 发生日期YYYYMMDD
	private String occurDate;
	// 发生时间 HHmmss
	private String occurTime;
	// 新闻
	private String news;
	// 新闻详细内容
	private String newsDetail;
	// 状态 0 表示正常 1 表示不可用
	private int state;
	// 排序
	private int ord;
	// 说明
	private String memo;

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

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

	public String getNewsDetail() {
		return newsDetail;
	}

	public void setNewsDetail(String newsDetail) {
		this.newsDetail = newsDetail;
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
