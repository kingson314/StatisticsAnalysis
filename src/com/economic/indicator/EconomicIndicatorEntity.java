package com.economic.indicator;

/**
 * @Description:
 * @date Mar 18, 2014
 * @author:fgq
 */
public class EconomicIndicatorEntity {
	private String id;
	// 国家
	private String country;
	// 指标名称
	private String indicator;
	// 时间戳
	private String createdate;
	// 备注
	private String memo;
	// 排序
	private String ord;
	// 状态
	private String state;
	// 指标解释
	private String indicatorexplain;
	// 指标影响
	private String indicatoreffect;
	// 关注原因
	private String attentionreason;
	// 统计方法
	private String statisticalMethod;
	// 统计结果
	private String statisticalResult;
	// 发布频率
	private String publishFrequency;
	// 发布结构
	private String publishOrganization;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIndicatorexplain() {
		return indicatorexplain;
	}

	public void setIndicatorexplain(String indicatorexplain) {
		this.indicatorexplain = indicatorexplain;
	}

	public String getIndicatoreffect() {
		return indicatoreffect;
	}

	public void setIndicatoreffect(String indicatoreffect) {
		this.indicatoreffect = indicatoreffect;
	}

	public String getAttentionreason() {
		return attentionreason;
	}

	public void setAttentionreason(String attentionreason) {
		this.attentionreason = attentionreason;
	}

	public String getStatisticalMethod() {
		return statisticalMethod;
	}

	public void setStatisticalMethod(String statisticalMethod) {
		this.statisticalMethod = statisticalMethod;
	}

	public String getStatisticalResult() {
		return statisticalResult;
	}

	public void setStatisticalResult(String statisticalResult) {
		this.statisticalResult = statisticalResult;
	}

	public String getPublishFrequency() {
		return publishFrequency;
	}

	public void setPublishFrequency(String publishFrequency) {
		this.publishFrequency = publishFrequency;
	}

	public String getPublishOrganization() {
		return publishOrganization;
	}

	public void setPublishOrganization(String publishOrganization) {
		this.publishOrganization = publishOrganization;
	}
}
