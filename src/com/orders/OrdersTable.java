package com.orders;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JTable;

import common.component.STable;
import common.component.STableBean;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;

public class OrdersTable {
	private JTable jtable;

	public OrdersTable(String dbName, String sql) {
		try {
			String[] columnNameArr = new String[] { "账户名称", "订单编号", "开仓日期", "开仓时间", "交易货币", "订单类型", "单量", "订单获利", "开仓价格", "平仓价格", "止损价格", "止盈价格", "平仓日期", "平仓时间", "备注", "税金",
					"库存费", "手续费" };
			String[] fieldsArr = new String[] { "accountName", "orderNo", "opendate", "opentime", "symbol", "ordertype", "lots", "profit", "openprice", "closeprice", "stoploss",
					"takeprofit", "closedate", "closetime", "memo", "tax", "cost", "fee" };
			Vector<String> columnName = UtilConver.arrayToVector(columnNameArr);
			Vector<?> tableValue = OrdersDao.getInstance().getVector(dbName, sql, fieldsArr);
			int[] cellEditableColumn = null;
			int[] columnWidth = { 100, 100, 80, 60, 60, 60, 70, 70, 70, 70, 70, 70, 80, 60, 200 };
			int[] columnHide = null;
			boolean isChenckHeader = false;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);

			STable table = new STable(bean);
			jtable = table.getJtable();
			this.jtable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					// mouseDoubleClick();
				}
			});
		} catch (Exception e) {
			UtilLog.logError("数据源配置列表构造错误：", e);
		} finally {
		}
	}

	public JTable getJtable() {
		return jtable;
	}
}
