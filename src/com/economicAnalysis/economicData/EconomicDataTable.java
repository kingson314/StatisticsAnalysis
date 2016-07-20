package com.economicAnalysis.economicData;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import com.app.AppTableView;
import com.config.symbol.SymbolParamDialog;
import com.economicAnalysis.timingMaximinRangeRate.TimingMaximinRangeRateTab;
import com.economicAnalysis.timingValue.TimingValueTab;
import com.economicAnalysis.timingmaximin.TimingMaximinTab;
import com.economicAnalysis.timingmaximin30.TimingMaximinTab30;
import com.graphAnalysis.klineSimulate.KLineSimulate;
import com.graphAnalysis.klineSimulate.KLineSimulateDialog;
import common.component.SMenuItem;
import common.component.STable;
import common.component.STableBean;
import common.jfreechart.kline.KLine;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;
import consts.ImageContext;

public class EconomicDataTable {
	private JTable jtable;
	private Vector<String> columnName;
	private int row = 0;

	// 构造
	public EconomicDataTable(boolean isSimple, boolean isAuto, String sql) {
		try {
			createTable(isSimple, isAuto, sql);
		} catch (Exception e) {
			UtilLog.logError("经济数据列表构造错误:", e);
		} finally {
		}
	}

	private void createTable(final boolean isSimple, boolean isAuto, String sql) {
		// String[] arr = new String[] { "ID", "指标Id", "来源", "指标", "发布日期",
		// "发布时间", "更新时间", "国家", "指标名称", "重要性", "前值", "预测值", "公布值", "比较结果",
		// "指标影响",
		// "实际影响", "符合预期影响", "振幅", "灵敏度", "操作建议", "备注说明" };

		String[] arr = new String[] { "ID", "指标Id", "来源", "指标", "发布日期", "发布点", "更新点", "国家", "指标名称", "重要性", "前值", "预测值", "预测结果", "公布值", "比较结果",
				"指标影响", "实际影响", "匹配度", "分析报告" };
		columnName = UtilConver.arrayToVector(arr);
		Vector<?> tableValue = EconomicDataDao.getInstance().getVector(isAuto, sql);
		int[] cellEditableColumn = null;
		int[] columnWidth = new int[] { 0, 0, 0, 150, 80, 40, 50, 55, 200, 40, 50, 50, 90, 50, 120, 145, 100, 50, 350 };
		int[] columnHide = new int[] { 0, 1, 2 };
		boolean isChenckHeader = false;
		boolean isReorderingAllowed = false;
		boolean isResizingAllowed = true;
		STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed,
				isResizingAllowed);
		STable table = new STable(bean);
		if (!isSimple) {
			table.setPmenu(this.getPmenu());
		}
		jtable = table.getJtable();

		jtable.getColumn(columnName.get(9)).setCellRenderer(this.getImportanceRender());
		jtable.getColumn(columnName.get(10)).setCellRenderer(this.getValueRender());
		jtable.getColumn(columnName.get(11)).setCellRenderer(this.getValueRender());
		jtable.getColumn(columnName.get(12)).setCellRenderer(this.getValueRender());
		jtable.getColumn(columnName.get(13)).setCellRenderer(this.getValueRender());

		this.jtable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!isSimple) {
					mouseClick();
				}
				if (e.getClickCount() > 1) {
					mouseDoubleClick();
				}
				// 设置选择行同日期同时间的行颜色
				String publishDate = jtable.getValueAt(jtable.getSelectedRow(), 4).toString().substring(4);
				String publishTime = jtable.getValueAt(jtable.getSelectedRow(), 5).toString();
				setRowRender(publishDate, publishTime, 4, 5);
			}
		});

		this.jtable.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				Point point = e.getPoint();
				row = jtable.rowAtPoint(point);
				int col = jtable.columnAtPoint(point);
				String value = jtable.getValueAt(row, col).toString();
				if (value != null) {
					// if (col == 4) {
					// try {
					// jtable.setToolTipText("星期" +
					// UtilDate.getDayOfWeek(value));
					// } catch (ParseException e1) {
					// e1.printStackTrace();
					// }
					// } else {
					jtable.setToolTipText(value);
					// }
				}
			}
		});
	}

	private DefaultTableCellRenderer getValueRender() {
		DefaultTableCellRenderer renderValue = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (column == 13 || column == 10 || column == 11 || column == 12) {
					((JLabel) cell).setHorizontalAlignment(SwingConstants.RIGHT);
					return cell;
				} else
					return cell;
			}
		};
		return renderValue;
	}

	private DefaultTableCellRenderer getImportanceRender() {
		DefaultTableCellRenderer renderImportance = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (column == 9) {
					if (table.getValueAt(row, 9) != null) {
						if (table.getValueAt(row, 9).equals("高")) {
							cell.setForeground(Const.ColorHigh);
						} else if (table.getValueAt(row, 9).equals("中")) {
							cell.setForeground(Const.ColorMiddle);
						} else {
							cell.setForeground(Const.ColorLow);
						}
					}
					cell.setFont(Const.tTablefont);
					((JLabel) cell).setHorizontalAlignment(SwingConstants.CENTER);
					return cell;
				} else
					return cell;
			}
		};
		return renderImportance;
	}

	public void setRowRender(String publishDate, String publishTime, int colIndexPublishDate, int colIndexPublishTime) {
		final List<Integer> listEquals = new ArrayList<Integer>();
		for (int i = 0; i < this.jtable.getRowCount(); i++) {
			String tmpPublishDate = this.jtable.getValueAt(i, colIndexPublishDate).toString().substring(4);// 4
			String tmpPublishTime = this.jtable.getValueAt(i, colIndexPublishTime).toString();// 5
			if (publishDate.equals(tmpPublishDate) && publishTime.equals(tmpPublishTime)) {
				listEquals.add(i);
			}
		}
		DefaultTableCellRenderer rowRender = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (listEquals.indexOf(row) >= 0) {
					cell.setBackground(Const.ColorSelection);
				} else {
					cell.setBackground(Color.WHITE);
				}
				if (column == 9) {
					if (table.getValueAt(row, 9) != null) {
						if (table.getValueAt(row, 9).equals("高")) {
							cell.setForeground(Color.red);
						} else if (table.getValueAt(row, 9).equals("中")) {
							cell.setForeground(Color.blue);
						} else {
							cell.setForeground(Color.green);
						}
					}
					cell.setFont(Const.tTablefont);
					((JLabel) cell).setHorizontalAlignment(SwingConstants.CENTER);
				} else if (column == 13 || column == 10 || column == 11 || column == 12) {
					((JLabel) cell).setHorizontalAlignment(SwingConstants.RIGHT);
					cell.setForeground(Color.black);
				} else {
					cell.setForeground(Color.black);
					((JLabel) cell).setHorizontalAlignment(SwingConstants.LEFT);
				}
				return cell;
			}

		};
		jtable.selectAll();
		for (int j = 0; j < this.jtable.getColumnCount(); j++) {
			jtable.getColumn(columnName.get(j)).setCellRenderer(rowRender);
		}
		jtable.clearSelection();
	}

	private void mouseDoubleClick() {
		new EconomicDataDialog(this.jtable, this.row);
	}

	/**
	 * @Description:获取关联时点经济数据以及即时新闻数据
	 * @date 2014-4-27
	 * @author:fgq
	 */
	private void mouseClick() {
		String id = this.jtable.getValueAt(this.jtable.getSelectedRow(), 0).toString();
		String publishDate = this.jtable.getValueAt(this.jtable.getSelectedRow(), 4).toString().substring(4);// 4
		String publishTime = this.jtable.getValueAt(this.jtable.getSelectedRow(), 5).toString();
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("id", id);
		mapParam.put("publishDate", publishDate);
		mapParam.put("publishTime", publishTime);

		// 获取关联时点经济数据,去掉本id的经济数据
		// EconomicDataTab.getInstance().queryEconomicDataDateTimeTablel(mapParam);
		// 获取即时新闻
		EconomicDataTab.getInstance().queryEconomicNewsTablel(mapParam);
	}

	// 浮动菜单
	private JPopupMenu getPmenu() {
		JPopupMenu menu = new JPopupMenu();
		try {
			final SMenuItem miViewHis = new SMenuItem("查看指标历史", ImageContext.LogView);
			miViewHis.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							viewHis();
						}
					});

			menu.add(miViewHis);
			final SMenuItem miViewKLine = new SMenuItem("查看K线图", ImageContext.LogView);
			miViewKLine.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							String[] tabName = new String[] { "K线图" };
							SymbolParamDialog symbolParamDialog = new SymbolParamDialog(false);
							String symbol = UtilString.isNil(symbolParamDialog.getSymbol());
							if ("".equals(symbol))
								return;
							AppTableView.getInstance().addTab(tabName[0], getShapeMatch(symbol));
						}
					});
			menu.add(miViewKLine);
			final SMenuItem miViewTimingPrice = new SMenuItem("查看时点价差", ImageContext.LogView);
			miViewTimingPrice.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							SymbolParamDialog symbolParamDialog = new SymbolParamDialog(false);
							String symbol = UtilString.isNil(symbolParamDialog.getSymbol());
							if ("".equals(symbol))
								return;
							String[] tabName = new String[] { "时点价差分析" };
							Map<String, String> map = new HashMap<String, String>();
							map.put("symbol", symbol);
							map.put("indicatorId", jtable.getValueAt(row, 1).toString());
							map.put("indicator", jtable.getValueAt(row, 3).toString());
							map.put("country", jtable.getValueAt(row, 7).toString());
							AppTableView.getInstance().addTab(tabName[0], TimingValueTab.getInstance().getSplMain(map));
						}
					});

			menu.add(miViewTimingPrice);

			final SMenuItem miTImingMaximin30 = new SMenuItem("时点价差极值分析30", ImageContext.LogView);
			miTImingMaximin30.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							SymbolParamDialog symbolParamDialog = new SymbolParamDialog(false);
							String symbol = UtilString.isNil(symbolParamDialog.getSymbol());
							if ("".equals(symbol))
								return;
							Map<String, String> map = new HashMap<String, String>();
							map.put("symbol", symbol);
							map.put("indicatorId", jtable.getValueAt(row, 1).toString());
							map.put("indicator", jtable.getValueAt(row, 3).toString());
							map.put("country", jtable.getValueAt(row, 7).toString());
							AppTableView.getInstance().addTab("时点价差极值分析30", TimingMaximinTab30.getInstance().getSplMain(map));
						}
					});
			menu.add(miTImingMaximin30);

			final SMenuItem miTImingMaximin = new SMenuItem("时点价差极值分析", ImageContext.LogView);
			miTImingMaximin.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							SymbolParamDialog symbolParamDialog = new SymbolParamDialog(false);
							String symbol = UtilString.isNil(symbolParamDialog.getSymbol());
							if ("".equals(symbol))
								return;
							AppTableView.getInstance().addTab("时点价差极值分析");
							Map<String, String> map = new HashMap<String, String>();
							map.put("symbol", symbol);
							map.put("indicatorId", jtable.getValueAt(row, 1).toString());
							map.put("indicator", jtable.getValueAt(row, 3).toString());
							map.put("country", jtable.getValueAt(row, 7).toString());
							TimingMaximinTab.getInstance().query(map);
						}
					});
			menu.add(miTImingMaximin);

			final SMenuItem miTImingMaximinRangeRate = new SMenuItem("时点价差极值波动频率分析", ImageContext.LogView);
			miTImingMaximinRangeRate.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							SymbolParamDialog symbolParamDialog = new SymbolParamDialog(false);
							String symbol = UtilString.isNil(symbolParamDialog.getSymbol());
							if ("".equals(symbol))
								return;
							AppTableView.getInstance().addTab("时点价差极值波动频率分析");
							Map<String, String> map = new HashMap<String, String>();
							map.put("symbol", symbol);
							map.put("indicatorId", jtable.getValueAt(row, 1).toString());
							map.put("indicator", jtable.getValueAt(row, 3).toString());
							map.put("country", jtable.getValueAt(row, 7).toString());
							TimingMaximinRangeRateTab.getInstance().query(map);
						}
					});
			menu.add(miTImingMaximinRangeRate);
			menu.addSeparator();
			//
			// final SMenuItem miSyncResult = new SMenuItem("同步时点分析结果",
			// ImageContext.NowDate);
			// miSyncResult.addActionListener(new ActionListener() {// 浮动菜单
			// public void actionPerformed(ActionEvent arg0) {
			// syncResult();
			// }
			// });
			// menu.add(miSyncResult);

			final SMenuItem miSimulate = new SMenuItem("行情模拟", ImageContext.NowDate);
			miSimulate.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							showSimulateKLine();
						}
					});
			menu.add(miSimulate);

			final SMenuItem miMatchIndicatorAndCountry = new SMenuItem("匹配指标与国家", ImageContext.Match);
			miMatchIndicatorAndCountry.addActionListener(new ActionListener() {// 浮动菜单
						public void actionPerformed(ActionEvent arg0) {
							try {
								for (int i = 0; i < jtable.getSelectedRowCount(); i++) {
									EconomicDataDao.getInstance().match(
											jtable.getValueAt(jtable.getSelectedRows()[i], 0).toString(),
											jtable.getValueAt(jtable.getSelectedRows()[i], 8).toString(),
											Integer.valueOf(UtilString.iif("".equals(UtilString.isNil(jtable.getValueAt(jtable.getSelectedRows()[i],
													2))), "-1", UtilString.isNil(jtable.getValueAt(jtable.getSelectedRows()[i], 2)))));
								}
								EconomicDataTab.getInstance().query(false);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

			menu.add(miMatchIndicatorAndCountry);
		} catch (Exception e) {
			UtilLog.logError("错误:", e);
		} finally {
		}
		return menu;
	}

	// private void syncResult() {
	// try {
	// String id = this.jtable.getValueAt(this.row, 0).toString();
	// String sql = "update ECONOMIC_DATA a "
	// +
	// " set (a.effect,a.isfitexpecteffect,a.amplitude,a.sensitivity,a.advice,a.memo)=(select b.effect,b.isfitexpecteffect,b.amplitude,b.sensitivity,b.advice,b.memo "
	// + " from ECONOMIC_DATA b " + " where  b.id='" + id + "' )"
	// +
	// " where exists (select 1 from economic_data c where   a.publishDate=c.publishDate and a.publishTime=c.publishTime and c.id='"
	// + id + "' )";
	// EconomicDataDao.getInstance().syncResult(sql);
	// viewHis();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	private void showSimulateKLine() {
		AppTableView.getInstance().addTab("行情模拟", KLineSimulate.getInstance().getSimulateTab());
		String publishDate = this.jtable.getValueAt(this.row, 4).toString().substring(4);// 4
		String publishTime = this.jtable.getValueAt(this.row, 5).toString();
		KLineSimulateDialog.getInstance().setDateTime(publishDate, publishTime);
		KLineSimulateDialog.getInstance().setVisible(true);
	}

	private void viewHis() {
		String indicatorId = this.jtable.getValueAt(this.row, 1).toString();
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("indicatorId", indicatorId);
		EconomicDataTab.getInstance().query(mapParam);
	}

	// private void viewDateTime() {
	// String publishDate = this.jtable.getValueAt(this.row,
	// 4).toString();
	// String publishTime = this.jtable.getValueAt(this.row,
	// 5).toString();
	// Map<String, String> mapParam = new HashMap<String, String>();
	// mapParam.put("publishDate", publishDate);
	// mapParam.put("publishTime", publishTime);
	// EconomicDataTab.getInstance().query(mapParam);
	// }

	// 获取60分钟K线与5分钟K线的比对图
	private JComponent getShapeMatch(String symbol) {
		String date = this.jtable.getValueAt(this.row, 4).toString().substring(4);// 4
		String time = this.jtable.getValueAt(this.row, 5).toString() + "00";
		return KLine.getInstance().getCombinationKline(Const.DbName, symbol, date, time);
	}

	// 获取信息表格
	public JTable getJtable() {
		return jtable;
	}

}
