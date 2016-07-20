package com.relate.indicatorSymbol;

/**
 * @Description: 经济指标与货币对应关系，用于自动任务分析经济数据时，自动分析相应的指标价格走势
 * @date Jul 4, 2014
 * @author:fgq
 */
public class RelateIndicatorSysmbolEntity {
    private String id;
    private String indicatorId;// economic_indicator.id
    private String symbolId;// config_symbol.id

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getIndicatorId() {
	return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
	this.indicatorId = indicatorId;
    }

    public String getSymbolId() {
	return symbolId;
    }

    public void setSymbolId(String symbolId) {
	this.symbolId = symbolId;
    }

}
