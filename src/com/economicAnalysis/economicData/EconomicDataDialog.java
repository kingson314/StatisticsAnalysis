package com.economicAnalysis.economicData;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import common.component.SBorder;
import common.component.SButton;
import common.component.SDialog;
import common.component.SLabel;
import common.component.STextField;
import common.component.ShowMsg;
import common.util.log.UtilLog;
import common.util.string.UtilString;

import consts.Const;
import consts.ImageContext;

public class EconomicDataDialog extends SDialog {
	private static final long serialVersionUID = 20120223l;
	private SButton btnCancel;
	private JPanel pnlMain;
	private SLabel lEffect;
	private SButton btnSave;
	private SLabel lMatchrate;
	private STextField txtMatchrate;
	private Component lAnalysisreport;
	private JScrollPane scrlAnalysisreport;
	private JTextArea txtaAnalysisreport;
	private JTable table;
	private int selectRow=0;
	private JScrollPane scrlEffect;
	private JTextArea txtaEffect;
	public EconomicDataDialog(JTable table,int row) {
		try {
			this.table = table;
			this.selectRow=row;
			initGUI();
		} catch (Exception e) {
			UtilLog.logError("对话框对话框构造错误:", e);
		}
	}

	// 初始界面
	private void initGUI() {
		try {
			this.setSize(650, 540);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.DialogManuExecTaskParam));
			this.setModal(true);
			this.setAlwaysOnTop(true);
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
			this.setLocation(w, h);
			this.setTitle("EconomicData");
			int y = 30;
			getContentPane().setLayout(null);
			{
				lEffect = new SLabel("实际影响");
				getContentPane().add(lEffect);
				lEffect.setBounds(31, 5 + y, 57, 14);
				{
					scrlEffect = new JScrollPane();
					getContentPane().add(scrlEffect, "bottom");
					scrlEffect.setBounds(94, 2 + y, 500, 180);
					{
						txtaEffect = new JTextArea();
						
						scrlEffect.setViewportView(txtaEffect);
						txtaEffect.setFont(Const.tfont);
						txtaEffect.setLineWrap(true);
						if (!"".equals(UtilString.isNil(this.table.getValueAt(this.selectRow, 16).toString()))) {
								txtaEffect.setText(UtilString.isNil(this.table.getValueAt(this.selectRow, 16)));
						}else{
							txtaEffect.setText("一、预测结果\n"
															+"1、\n"
															+"二、比较结果\n"
															+"1、\n"
															+"三、消息面影响\n"
															+"1、"	);
						}
					}
				}
			}
			{
				lMatchrate = new SLabel("匹配度");
				getContentPane().add(lMatchrate);
				lMatchrate.setBounds(31, 193 + y, 57, 14);

				txtMatchrate = new STextField("0");
				if (!"".equals(UtilString.isNil(this.table.getValueAt(this.selectRow, 17).toString()))) {
					txtMatchrate.setText(UtilString.isNil(this.table.getValueAt(this.selectRow, 17)));
				}else{
					txtMatchrate.setText("0");
				}
				getContentPane().add(txtMatchrate);
				txtMatchrate.setBounds(94, 190 + y, 500, 21);
			}

			{
				lAnalysisreport = new SLabel("分析报告");
				getContentPane().add(lAnalysisreport);
				lAnalysisreport.setBounds(31, 223 + y, 57, 14);

				{
					scrlAnalysisreport = new JScrollPane();
					getContentPane().add(scrlAnalysisreport, "bottom");
					scrlAnalysisreport.setBounds(94, 220 + y, 500, 180);
					{
						txtaAnalysisreport = new JTextArea();
						txtaAnalysisreport.setText(UtilString.isNil(this.table.getValueAt(this.selectRow, 18)));
						scrlAnalysisreport.setViewportView(txtaAnalysisreport);
						txtaAnalysisreport.setFont(Const.tfont);
						txtaAnalysisreport.setLineWrap(true);
					}
				}
			}
			{
				pnlMain = new JPanel();
				getContentPane().add(pnlMain);
				pnlMain.setBounds(0, 425 + y, 650, 45);
				pnlMain.setBorder(SBorder.getTitledBorder());
				{

					btnSave = new SButton("保存", ImageContext.Ok);
					pnlMain.add(btnSave);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							save();
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
			this.setVisible(true);
		} catch (Exception e) {
			UtilLog.logError("对话框初始化界面错误:", e);
		} finally {
		}
	}

	private void save() {
		try {
			int row = table.getSelectedRow();
			String id = UtilString.isNil(this.table.getValueAt(row, 0));
			String updateEconomicDatasql = "update economic_data  set effect='" + UtilString.isNil(txtaEffect.getText()) + "'  where id='" + id + "'";
			String updateIndicatorSql = "update economic_indicator set matchrate='" + UtilString.isNil(txtMatchrate.getText()) + "',analysisreport='"
					+ UtilString.isNil(txtaAnalysisreport.getText()) + "' where id='" + UtilString.isNil(this.table.getValueAt(row, 1)) + "'";
			System.out.println(updateEconomicDatasql);
			System.out.println(updateIndicatorSql);
			EconomicDataDao.getInstance().save(updateEconomicDatasql);
			EconomicDataDao.getInstance().save(updateIndicatorSql);
			this.table.setValueAt(UtilString.isNil(txtaEffect.getText()), row, 16);
			this.table.setValueAt(UtilString.isNil(txtMatchrate.getText()), row, 17);
			this.table.setValueAt(UtilString.isNil(txtaAnalysisreport.getText()), row, 18);
			this.dispose();
		} catch (Exception e) {
			ShowMsg.showError(e.getMessage());
		}
	}

	// 取消
	private void btnCancel() {
		try {
			this.dispose();
		} catch (Exception e) {
			UtilLog.logError("对话框退出错误:", e);
		} finally {
		}
	}

}
