package com.economic.indicator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import com.config.symbol.SymbolParamDialog;
import com.relate.indicatorSymbol.RelateIndicatorSysmbolDao;
import common.component.SMenuItem;
import common.component.STable;
import common.component.STableBean;
import common.component.ShowMsg;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;
import common.util.swing.UtilComponent;
import consts.ImageContext;

public class EconomicIndicatorTable {
	private JTable jtable;

	// 构造
	public EconomicIndicatorTable(String sql) {
		try {
			String[] arr = new String[] { "", "ID", "国家", "指标名称", "指标名称(FH)", "重要性", "发布频率", "统计方法", "指标解释", "指标影响", "关注原因", "统计机构" };
			Vector<String> columnName = UtilConver.arrayToVector(arr);
			Vector<?> tableValue = EconomicIndicatorDao.getInstance().getVector(sql);
			int[] cellEditableColumn = new int[] { 0 };
			int[] columnWidth = new int[] { 20, 0, 60, 150, 150, 60, 60, 100, 200, 150, 200, 100 };
			int[] columnHide = new int[] { 1 };
			boolean isChenckHeader = true;
			boolean isReorderingAllowed = false;
			boolean isResizingAllowed = true;
			STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
			STable table = new STable(bean);
			table.setPmenu(this.getPmenu());
			jtable = table.getJtable();
		} catch (Exception e) {
			UtilLog.logError("列表构造错误:", e);
		} finally {
		}
	}

	// 获取信息表格
	public JTable getJtable() {
		return jtable;
	}

	// 浮动菜单
	private JPopupMenu getPmenu() {
		JPopupMenu menu = new JPopupMenu();
		try {
			final SMenuItem miHigh = new SMenuItem("设置高", ImageContext.Mod);
			miHigh.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							updateImportance("高");
						}
					});
			menu.add(miHigh);

			final SMenuItem miMiddle = new SMenuItem("设置中", ImageContext.Mod);
			miMiddle.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							updateImportance("中");
						}
					});

			menu.add(miMiddle);

			final SMenuItem miLow = new SMenuItem("设置低", ImageContext.Mod);
			miLow.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							updateImportance("低");
						}
					});

			menu.add(miLow);
			menu.addSeparator();
			final SMenuItem miSymbol = new SMenuItem("添加关联货币", ImageContext.Symbol);
			miSymbol.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							addIndicatorSymbol();
						}
					});

			menu.add(miSymbol);
		} catch (Exception e) {
			UtilLog.logError("错误:", e);
		} finally {
		}
		return menu;
	}

	/**
	 * @Description:添加指标与货币对应关系，用于自动任务分析经济数据时，自动分析相应的指标价格走势
	 * @param symbolId
	 *            void
	 * @date Jul 3, 2014
	 * @author:fgq
	 */
	@SuppressWarnings("unchecked")
	private void addIndicatorSymbol() {
		try {
			if (this.jtable == null) {
				ShowMsg.showMsg("请勾选记录");
				return;
			}
			int selectedCount = UtilComponent.getTableSelectedCount(this.jtable);
			if (selectedCount == 0) {
				ShowMsg.showMsg("请勾选记录");
				return;
			}
			SymbolParamDialog symbolParamDialog = new SymbolParamDialog(true);
			List<String> symbolIdList = (List<String>) symbolParamDialog.getSymbolId();
			if (symbolIdList == null || symbolIdList.size() == 0)
				return;
			for (int i = 0; i < this.jtable.getRowCount(); i++) {
				Boolean selected = Boolean.valueOf(this.jtable.getValueAt(i, 0).toString());
				if (selected) {
					String indicatorId = this.jtable.getValueAt(i, 1).toString();
					for (String symbolId : symbolIdList) {
						if (!RelateIndicatorSysmbolDao.getInstance().isExist(indicatorId, symbolId)) {
							RelateIndicatorSysmbolDao.getInstance().addRelation(indicatorId, symbolId);
						}
					}
				}
			}
			ShowMsg.showMsg("添加指标货币关联成功！");
		} catch (Exception e) {
			UtilLog.logError("错误:", e);
		}
	}

	/**
	 * @Description:更新重要性
	 * @param importance
	 *            void
	 * @date Jul 3, 2014
	 * @author:fgq
	 */
	private void updateImportance(String importance) {
		try {
			if (this.jtable == null) {
				ShowMsg.showMsg("请勾选记录");
				return;
			}
			int selectedCount = UtilComponent.getTableSelectedCount(this.jtable);
			if (selectedCount == 0) {
				ShowMsg.showMsg("请勾选记录");
				return;
			}

			for (int i = 0; i < this.jtable.getRowCount(); i++) {
				Boolean selected = Boolean.valueOf(this.jtable.getValueAt(i, 0).toString());
				if (selected) {
					String indicatorId = this.jtable.getValueAt(i, 1).toString();
					EconomicIndicatorDao.getInstance().updateImportance(indicatorId, importance);
				}
			}
		} catch (Exception e) {
			UtilLog.logError("更新指标重要性错误:", e);
		} finally {
		}
	}
}
