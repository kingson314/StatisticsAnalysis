package com.graphAnalysis.klineSimulate;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import common.component.SBorder;
import common.component.SButton;
import common.component.SCalendar;
import common.component.SCheckBox;
import common.component.SComboBox;
import common.component.SDialog;
import common.component.SLabel;
import common.component.SRadioButton;
import common.component.STextField;
import common.component.ShowMsg;
import common.jfreechart.kline.BeanKLine;
import common.timeSchedule.TimeSchedule;
import common.util.conver.UtilConver;
import common.util.date.UtilDate;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import consts.Const;
import consts.ImageContext;

public class KLineSimulateDialog extends SDialog {
	private static final long serialVersionUID = 20120223l;
	private SLabel lBegdate;
	private SButton btnBegdate;
	private SButton btnStart;
	private SButton btnCancel;
	private JPanel pnlMain;
	private SButton btnEnddate;
	private STextField txtEndDate;
	private SLabel lEnddate;
	private STextField txtBegDate;
	private SLabel lBegTime;
	private STextField txtBegTime;
	private SLabel lEndTime;
	private STextField txtEndTime;
	private JLabel lTipBegTime;
	private JLabel lTipEndTime;
	private SCheckBox chkMinute1;
	private SCheckBox chkMinute240;
	private SCheckBox chkMinute60;
	private SCheckBox chkMinute30;
	private SCheckBox chkMinute15;
	private SCheckBox chkMinute5;
	private SComboBox cmbFirstPeriod;
	private SComboBox cmbSecondPeriod;
	private SLabel lSymbol;
	private SComboBox cmbSymbol;
	private SRadioButton rdPage;
	private SRadioButton rdCompare;
	private Component lBars;
	private STextField txtBars;
	private JSlider secondsSlider;
	private SButton btnReady;
	private SButton btnReStart;
	private STextField txtTimes;
	private JLabel lTimes;
	private static KLineSimulateDialog kLineSimulateDialog = null;

	public static KLineSimulateDialog getInstance() {
		if (kLineSimulateDialog == null)
			kLineSimulateDialog = new KLineSimulateDialog();
		return kLineSimulateDialog;
	}

	public KLineSimulateDialog() {
		try {
			initGUI();
		} catch (Exception e) {
			UtilLog.logError("参数输入对话框对话框构造错误:", e);
		}
	}

	public void setDateTime(String sDate, String sTime) {
		try {
			sTime = sTime.replace(":", "");
			String endDateTime = UtilDate.addMinute(sDate + " " + sTime, 180, "yyyyMMdd HHmm");
			txtBegDate.setText(sDate);
			txtEndDate.setText(endDateTime.substring(0, 8));

			txtBegTime.setText(sTime);
			txtEndTime.setText(endDateTime.substring(9));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 初始界面
	private void initGUI() {
		try {
			this.setSize(530, 350);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.DialogManuExecTaskParam));
			this.setModal(true);
			this.setAlwaysOnTop(false);
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
			this.setLocation(w, h);
			this.setTitle("SimulatePrice");
			int y = 30;
			getContentPane().setLayout(null);
			{
				lSymbol = new SLabel("货币名称");
				getContentPane().add(lSymbol);
				lSymbol.setBounds(31, 5 + y, 57, 14);

				cmbSymbol = new SComboBox(Const.SymbolArr);
				getContentPane().add(cmbSymbol);
				cmbSymbol.setBounds(94, 2 + y, 90, 21);
				cmbSymbol.setSelectedIndex(1);
			}
			{
				lBars = new SLabel("K线数量");
				getContentPane().add(lBars);
				lBars.setBounds(190, 5 + y, 57, 14);

				txtBars = new STextField("60");
				getContentPane().add(txtBars);
				txtBars.setBounds(250, 2 + y, 41, 21);
			}
			{
				rdCompare = new SRadioButton("对比图:");
				getContentPane().add(rdCompare);
				rdCompare.setBounds(25, 32 + y, 68, 14);
				rdCompare.setSelected(true);
				rdCompare.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (rdCompare.isSelected()) {
							rdPage.setSelected(false);
							txtBars.setText("60");
						}
					}
				});

				cmbFirstPeriod = new SComboBox(Const.PeriodStrArr);
				getContentPane().add(cmbFirstPeriod);
				cmbFirstPeriod.setBounds(94, 32 + y, 90, 21);
				cmbFirstPeriod.setSelectedItem("60分钟");

				cmbSecondPeriod = new SComboBox(Const.PeriodStrArr);
				getContentPane().add(cmbSecondPeriod);
				cmbSecondPeriod.setBounds(200, 32 + y, 90, 21);
				cmbSecondPeriod.setSelectedItem("5分钟");
			}
			{
				rdPage = new SRadioButton("分页图:");
				getContentPane().add(rdPage);
				rdPage.setBounds(25, 62 + y, 68, 14);
				rdPage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (rdPage.isSelected()) {
							rdCompare.setSelected(false);
							txtBars.setText("160");
						}
					}
				});

				chkMinute1 = new SCheckBox("1分钟");
				getContentPane().add(chkMinute1);
				chkMinute1.setBounds(94, 62 + y, 60, 14);

				chkMinute5 = new SCheckBox("5分钟");
				getContentPane().add(chkMinute5);
				chkMinute5.setBounds(154, 62 + y, 60, 14);
				chkMinute5.setSelected(true);

				chkMinute15 = new SCheckBox("15分钟");
				getContentPane().add(chkMinute15);
				chkMinute15.setBounds(214, 62 + y, 70, 14);

				chkMinute30 = new SCheckBox("30分钟");
				getContentPane().add(chkMinute30);
				chkMinute30.setBounds(284, 62 + y, 70, 14);

				chkMinute60 = new SCheckBox("60分钟");
				getContentPane().add(chkMinute60);
				chkMinute60.setBounds(354, 62 + y, 70, 14);
				chkMinute60.setSelected(true);

				chkMinute240 = new SCheckBox("240分钟");
				getContentPane().add(chkMinute240);
				chkMinute240.setBounds(424, 62 + y, 90, 14);
			}
			{
				lBegdate = new SLabel("开始日期");
				getContentPane().add(lBegdate);
				lBegdate.setBounds(31, 95 + y, 57, 14);

				txtBegDate = new STextField();
				getContentPane().add(txtBegDate);
				txtBegDate.setBounds(94, 92 + y, 85, 21);
				txtBegDate.setText(UtilConver.dateToStr(Const.fm_yyyyMMdd));

				btnBegdate = new SButton("..");
				getContentPane().add(btnBegdate);
				btnBegdate.setBounds(180, 92 + y, 22, 21);
				btnBegdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						new SCalendar(txtBegDate);
					}
				});

				lBegTime = new SLabel("开始时间");
				getContentPane().add(lBegTime);
				lBegTime.setBounds(230, 95 + y, 57, 14);

				txtBegTime = new STextField("0001");
				getContentPane().add(txtBegTime);
				txtBegTime.setBounds(290, 92 + y, 85, 21);

				lTipBegTime = new JLabel();
				lTipBegTime.setText("HHmm");
				lTipBegTime.setFont(Const.tfont);
				getContentPane().add(lTipBegTime);
				lTipBegTime.setBounds(375, 95 + y, 50, 14);
			}

			{
				lEnddate = new SLabel("结束日期");
				getContentPane().add(lEnddate);
				lEnddate.setBounds(31, 125 + y, 57, 14);
				txtEndDate = new STextField();
				getContentPane().add(txtEndDate);
				txtEndDate.setBounds(94, 122 + y, 85, 21);
				txtEndDate.setText(UtilConver.dateToStr(Const.fm_yyyyMMdd));

				btnEnddate = new SButton("..");
				getContentPane().add(btnEnddate);
				btnEnddate.setBounds(180, 122 + y, 22, 21);
				btnEnddate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						new SCalendar(txtEndDate);
					}
				});

				lEndTime = new SLabel("结束时间");
				getContentPane().add(lEndTime);
				lEndTime.setBounds(230, 125 + y, 57, 14);

				txtEndTime = new STextField("2359");
				getContentPane().add(txtEndTime);
				txtEndTime.setBounds(290, 122 + y, 85, 21);

				lTipEndTime = new JLabel();
				lTipEndTime.setText("HHmm");
				lTipEndTime.setFont(Const.tfont);
				getContentPane().add(lTipEndTime);
				lTipEndTime.setBounds(375, 125 + y, 50, 14);
			}
			{
				Box boxSilder = getSilderBox();
				boxSilder.setBounds(31, 175 + y, 450, 60);
				getContentPane().add(boxSilder);
			}
			{
				lTimes = new JLabel("*");
				lTimes.setBounds(482, 180 + y, 15, 14);
				getContentPane().add(lTimes);
			}
			{
				txtTimes = new STextField("6");
				txtTimes.setBounds(495, 175 + y, 22, 21);
				getContentPane().add(txtTimes);
				txtTimes.addKeyListener(new KeyListener() {

					public void keyPressed(KeyEvent e) {
					}

					public void keyReleased(KeyEvent e) {
						String times = UtilString.isNil(txtTimes.getText());
						if ("".equals(times)) {
							times = "1";
						} else if ("0".equals(times)) {
							times = "1";
						}
						System.out.println("当前滑动条的值为：" + secondsSlider.getValue() * Integer.valueOf(times));
						KLineSimulate.getInstance().setSeconds(secondsSlider.getValue() * Integer.valueOf(times));
					}

					public void keyTyped(KeyEvent e) {
						/**
						 * @Description:
						 * @date 2014-4-13
						 * @author:fgq
						 */
					}
				});
			}
			{
				pnlMain = new JPanel();
				getContentPane().add(pnlMain);
				pnlMain.setBounds(0, 235 + y, 530, 45);
				pnlMain.setBorder(SBorder.getTitledBorder());
				{

					btnReady = new SButton("准  备", ImageContext.Ready);
					pnlMain.add(btnReady);
					btnReady.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnReady();
						}
					});

					btnStart = new SButton("启  动", ImageContext.Start);
					pnlMain.add(btnStart);
					btnStart.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnStart();
						}
					});
					btnReStart = new SButton("重新开始", ImageContext.Start);
					pnlMain.add(btnReStart);
					btnReStart.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnReStart();
						}
					});
					btnCancel = new SButton("\u53d6  \u6d88", ImageContext.Exit);
					pnlMain.add(btnCancel);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnCancel();
						}
					});
				}
			}
		} catch (Exception e) {
			UtilLog.logError("对话框初始化界面错误:", e);
		} finally {
		}
	}

	private Box getSilderBox() {
		Box sliderBox = new Box(BoxLayout.Y_AXIS);
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				JSlider source = (JSlider) event.getSource();
				String times = UtilString.isNil(txtTimes.getText());
				if ("".equals(times)) {
					times = "1";
				} else if ("0".equals(times)) {
					times = "1";
				}
				System.out.println("当前滑动条的值为：" + source.getValue() * Integer.valueOf(times));
				KLineSimulate.getInstance().setSeconds(source.getValue() * Integer.valueOf(times));
			}
		};
		secondsSlider = new JSlider(0, 60);
		secondsSlider.setValue(10);
		// 设置绘制刻度
		secondsSlider.setPaintTicks(true);
		// 设置主、次刻度的间距
		secondsSlider.setMajorTickSpacing(5);
		secondsSlider.setMinorTickSpacing(1);
		// 设置绘制刻度标签，默认绘制数值刻度标签
		secondsSlider.setPaintLabels(true);
		secondsSlider.addChangeListener(listener);
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(secondsSlider);
		sliderBox.add(box);
		return sliderBox;
	}

	// 重新开始
	private void btnReStart() {
		KLineSimulate kLineSimulate = KLineSimulate.getInstance();
		TimeSchedule timeSchedule = kLineSimulate.getTimeSchedule();
		timeSchedule.stop();
		int bars = Integer.valueOf(txtBars.getText().trim());
		List<BeanKLine> listBeanKLine = kLineSimulate.getListBeanKLine();
		for (BeanKLine beanKLine : listBeanKLine) {
			beanKLine.setSDate(UtilString.isNil(txtBegDate.getText()));
			String begTime = UtilString.isNil(txtBegTime.getText());
			beanKLine.setSTime(begTime + "00");
			beanKLine.setBars(bars);
			beanKLine.setLeftlBars(bars);
			beanKLine.setRigthBars(0);
		}
		enabled(false);
		btnStart.setText("暂  停");
		btnStart.setIcon(new ImageIcon(ImageContext.Stop));
		timeSchedule.start();
	}

	// 取消
	private void btnCancel() {
		try {
			KLineSimulate.getInstance().getTimeSchedule().stop();
		} catch (Exception e) {
			UtilLog.logError("对话框退出错误:", e);
		} finally {
			this.setVisible(false);
		}
	}

	// 启动
	private void btnStart() {
		KLineSimulate kLineSimulate = KLineSimulate.getInstance();
		TimeSchedule timeSchedule = kLineSimulate.getTimeSchedule();
		if (timeSchedule.isRunning == false) {
			btnStart.setText("暂  停");
			btnStart.setIcon(new ImageIcon(ImageContext.Stop));
			enabled(false);
			timeSchedule.start();
		} else {
			btnStart.setText("开  始");
			btnStart.setIcon(new ImageIcon(ImageContext.Start));
			enabled(true);
			timeSchedule.stop();
		}
	}

	public void setStart() {
		btnStart.setText("开  始");
		btnStart.setIcon(new ImageIcon(ImageContext.Start));
		enabled(true);
		ShowMsg.showMsg("模拟完成了");
	}

	// 准备

	private void btnReady() {
		try {
			if("全部".equals(UtilString.isNil(cmbSymbol.getSelectedItem()))){
				ShowMsg.showMsg("请选择货币！");
				return;
			}
			KLineSimulate kLineSimulate = KLineSimulate.getInstance();
			TimeSchedule timeSchedule = kLineSimulate.getTimeSchedule();
			timeSchedule.stop();
			List<BeanKLine> listBeanKLine = kLineSimulate.getListBeanKLine();
			kLineSimulate.setComppareView(rdCompare.isSelected());
			kLineSimulate.setPageView(rdPage.isSelected());
			kLineSimulate.setSeconds(secondsSlider.getValue());

			int bars = Integer.valueOf(txtBars.getText().trim());
			BeanKLine beanKLine = new BeanKLine();
			beanKLine.setDbName(Const.DbName);
			beanKLine.setSymbol(UtilString.isNil(cmbSymbol.getSelectedItem()));
			beanKLine.setLeftlBars(bars);
			beanKLine.setRigthBars(0);
			beanKLine.setBars(bars);
			beanKLine.setSDate(UtilString.isNil(txtBegDate.getText()));
			String begTime = UtilString.isNil(txtBegTime.getText());
			beanKLine.setSTime(begTime + "00");
			beanKLine.setSimulate(true);
			beanKLine.setEndDataSimulate(UtilString.isNil(txtEndDate.getText()));
			String endTime = UtilString.isNil(txtEndTime.getText());
			beanKLine.setEndTimeSimulate(endTime + "00");

			if (rdCompare.isSelected()) {
				int period0 = Const.PeriodArr[cmbFirstPeriod.getSelectedIndex()];
				BeanKLine bean0 = (BeanKLine) beanKLine.clone();
				bean0.setTableName("simulate_price" + period0);
				bean0.setPeriod(period0);
				listBeanKLine.add(bean0);

				int period1 = Const.PeriodArr[cmbSecondPeriod.getSelectedIndex()];
				BeanKLine bean1 = (BeanKLine) beanKLine.clone();
				bean1.setTableName("simulate_price" + period1);
				bean1.setPeriod(period1);
				listBeanKLine.add(bean1);
			} else {
				if (chkMinute1.isSelected()) {
					BeanKLine bean1 = (BeanKLine) beanKLine.clone();
					bean1.setTableName("simulate_price" + 1);
					bean1.setPeriod(1);
					listBeanKLine.add(bean1);
				}
				if (chkMinute5.isSelected()) {
					BeanKLine bean5 = (BeanKLine) beanKLine.clone();
					bean5.setTableName("simulate_price" + 5);
					bean5.setPeriod(5);
					listBeanKLine.add(bean5);
				}
				if (chkMinute15.isSelected()) {
					BeanKLine bean15 = (BeanKLine) beanKLine.clone();
					bean15.setTableName("simulate_price" + 15);
					bean15.setPeriod(15);
					listBeanKLine.add(bean15);
				}
				if (chkMinute30.isSelected()) {
					BeanKLine bean30 = (BeanKLine) beanKLine.clone();
					bean30.setTableName("simulate_price" + 30);
					bean30.setPeriod(30);
					listBeanKLine.add(bean30);
				}
				if (chkMinute60.isSelected()) {
					BeanKLine bean60 = (BeanKLine) beanKLine.clone();
					bean60.setTableName("simulate_price" + 60);
					bean60.setPeriod(60);
					listBeanKLine.add(bean60);
				}
				if (chkMinute240.isSelected()) {
					BeanKLine bean240 = (BeanKLine) beanKLine.clone();
					bean240.setTableName("simulate_price" + 240);
					bean240.setPeriod(240);
					listBeanKLine.add(bean240);
				}
			}
			dataReady(listBeanKLine, UtilString.isNil(txtBegDate.getText()), UtilString.isNil(txtBegTime.getText()), UtilString.isNil(txtEndDate.getText()), UtilString.isNil(txtEndTime.getText()));
			kLineSimulate.setSimulateTab(listBeanKLine);
			System.out.println("一切准备就绪...");
			ShowMsg.showMsg("一切准备就绪...");
		} catch (Exception e) {
			UtilLog.logError("对话框确定错误:", e);
			ShowMsg.showError(e.getMessage());
		} finally {
		}
	}

	// 准备模拟数据
	private void dataReady(List<BeanKLine> listBeanKLine, String begDate, String begTime_HHmm, String endDate, String endTime_HHmm) throws Exception {
		Connection con = UtilJDBCManager.getConnection(Const.DbName);
		String begDateTime = begDate + " " + begTime_HHmm+ "00";

		String endDateTime = endDate + " " + endTime_HHmm + "00";
		String[] deleteSqlArr = new String[listBeanKLine.size() * 2];
		String[] insertlSqlArr = new String[listBeanKLine.size() * 2];
		for (int i = 0; i < listBeanKLine.size(); i++) {
			BeanKLine beanKLine = listBeanKLine.get(i);
			deleteSqlArr[i * 2] = " truncate table " + beanKLine.getTableName();
			deleteSqlArr[i * 2 + 1] = " truncate table " + beanKLine.getTableName() + "_detail";
			begDateTime = UtilDate.addDateMinut(begDateTime, -beanKLine.getPeriod() * beanKLine.getBars(), Const.fm_yyyyMMdd_HHmmss);
			begDate = begDateTime.substring(0, 8);
			insertlSqlArr[i * 2] = "insert into " + beanKLine.getTableName() + "(dateserver,timeserver,datelocal,timelocal,open,close,high,low,ma5,ma20,ma60,kdj,volume) "
					+ "select dateserver,timeserver,datelocal,timelocal,open,close,high,low,ma5,ma20,ma60,kdj,volume  from price_" + beanKLine.getSymbol() + beanKLine.getPeriod()
					+ "   where " + Const.DateType + " >= '" + begDate + "' and " + Const.DateType + "<='" + endDate + "' and concat(" + Const.DateType + ",' '," + Const.TimeType + ") >= '"
					+ begDateTime + "' and  concat(" + Const.DateType + ",' '," + Const.TimeType + ")<='" + endDateTime + "'";

			insertlSqlArr[i * 2 + 1] = "insert into "
					+ beanKLine.getTableName()
					+ "_detail(dateserver,timeserver,datelocal,timelocal,open,close,high,low,ma5,ma20,ma60,kdj,volume) "
					+ "select dateserver,timeserver,datelocal,timelocal,max(open),max(close),max(high),max(low),max(ma5),max(ma20),max(ma60),max(kdj),max(volume)  from price_"
					+ beanKLine.getSymbol() + beanKLine.getPeriod() + "  where " + Const.DateType + " >= '" + begDate + "' and " + Const.DateType + "<='" + endDate + "' and concat("
					+ Const.DateType + ",' '," + Const.TimeType + ") >= '" + begDateTime + "' and  concat(" + Const.DateType + ",' '," + Const.TimeType + ")<='" + endDateTime
					+ "' group by dateserver,timeserver,datelocal,timelocal";
			System.out.println(deleteSqlArr[i * 2]);
			System.out.println(deleteSqlArr[i * 2 + 1]);
			System.out.println(insertlSqlArr[i * 2]);
			System.out.println(insertlSqlArr[i * 2 + 1]);
		}
		// 1.清空相应周期的模拟行情表
		UtilSql.executeUpdateBatch(con, deleteSqlArr);
		// 2.插入相应周期模拟数据（ 1、2步骤主要是因为用到明细表，数据量太大）
		UtilSql.executeUpdateBatch(con, insertlSqlArr);
		UtilSql.close(con);
	}

	private void enabled(boolean flag) {
		cmbSymbol.setEnabled(flag);
		txtBars.setEnabled(flag);
		rdPage.setEnabled(flag);
		cmbFirstPeriod.setEnabled(flag);
		cmbSecondPeriod.setEnabled(flag);
		chkMinute1.setEnabled(flag);
		chkMinute5.setEnabled(flag);
		chkMinute15.setEnabled(flag);
		chkMinute30.setEnabled(flag);
		chkMinute60.setEnabled(flag);
		chkMinute240.setEnabled(flag);
		txtBegDate.setEnabled(flag);
		txtBegTime.setEnabled(flag);
		txtEndDate.setEnabled(flag);
		txtEndTime.setEnabled(flag);
		rdCompare.setEnabled(flag);
	}

	public static void main(String[] args) {
		KLineSimulateDialog.getInstance();
	}
}
