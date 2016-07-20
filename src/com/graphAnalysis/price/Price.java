package com.graphAnalysis.price;

import java.util.Date;

/**
 * @Description:
 * @date Apr 11, 2014
 * @author:fgq
 */
public class Price {
	private String id;
	private String dateserver;
	private String timeserver;
	private String datelocal;
	private String timelocal;
	private double ask;
	private double bid;
	private double open;
	private double close;
	private double high;
	private double low;
	private double ma5;
	private double ma20;
	private double ma60;
	private double kdj;
	private long bars;
	private double volume;
	private Date createdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateserver() {
		return dateserver;
	}

	public void setDateserver(String dateserver) {
		this.dateserver = dateserver;
	}

	public String getTimeserver() {
		return timeserver;
	}

	public void setTimeserver(String timeserver) {
		this.timeserver = timeserver;
	}

	public String getDatelocal() {
		return datelocal;
	}

	public void setDatelocal(String datelocal) {
		this.datelocal = datelocal;
	}

	public String getTimelocal() {
		return timelocal;
	}

	public void setTimelocal(String timelocal) {
		this.timelocal = timelocal;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getMa5() {
		return ma5;
	}

	public void setMa5(double ma5) {
		this.ma5 = ma5;
	}

	public double getMa20() {
		return ma20;
	}

	public void setMa20(double ma20) {
		this.ma20 = ma20;
	}

	public double getMa60() {
		return ma60;
	}

	public void setMa60(double ma60) {
		this.ma60 = ma60;
	}

	public double getKdj() {
		return kdj;
	}

	public void setKdj(double kdj) {
		this.kdj = kdj;
	}

	public long getBars() {
		return bars;
	}

	public void setBars(long bars) {
		this.bars = bars;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

}
