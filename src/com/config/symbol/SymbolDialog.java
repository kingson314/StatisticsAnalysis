package com.config.symbol;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JTable;

import common.component.DialogTableModel;
import common.component.SLabel;
import common.component.SScrollPane;
import common.component.STextArea;
import common.component.STextField;
import common.component.ShowMsg;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import config.symbol.Symbol;
import config.symbol.SymbolDao;
import consts.ImageContext;

public class SymbolDialog extends DialogTableModel {
	private static final long serialVersionUID = 1L;
	private SLabel lSymbol;
	private SLabel lPoint;
	private SLabel lServerName;
	private JPanel pnlParam;
	private STextField txtSymbol;
	private STextField txtServer;
	private STextField txtPoint;
	private SLabel lState;
	private STextField txtState;
	private SLabel lOrd;
	private STextField txtOrd;
	private SLabel lMemo;
	private SScrollPane scrlMemo;
	private STextArea txtaMemo;
	private SLabel lDigits;
	private STextField txtDigits;

	private int Addmod;

	// 获取表格
	private JTable getTable() {
		final JTable table = new SymbolTable("").getJtable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				txtServer.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				txtSymbol.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
				txtPoint.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
				txtDigits.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
				txtState.setText(table.getValueAt(table.getSelectedRow(), 5).toString());
				txtOrd.setText(table.getValueAt(table.getSelectedRow(), 6).toString());
				txtaMemo.setText(table.getValueAt(table.getSelectedRow(), 7).toString());
				textEnabled(false);
			}
		});
		return table;
	}

	// 刷新表格
	private void refreshTable() {
		super.jTable = getTable();
		super.scrlTable.setViewportView(super.jTable);
	}

	// 构造
	public SymbolDialog() {
		super(0);
		try {
			super.setTitle("货币信息");
			this.setSize(626, 375);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.Symbol));
			super.jTable = getTable();
			super.ini();
			textEnabled(false);
		} catch (Exception e) {
			UtilLog.logError("货币信息对话框构造错误:", e);
		} finally {
		}
	}

	// 新增
	@Override
	protected void add() {
		try {
			textEnabled(true);
			textClear();
			super.add();
			Addmod = 0;
		} catch (Exception e) {
			UtilLog.logError("货币信息对话框新增错误:", e);
		} finally {
		}
	}

	// 修改
	@Override
	protected boolean mod() {
		boolean rs = false;
		try {
			if (txtSymbol.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要修改的记录！");
				return rs;
			}
			if (super.mod()) {
				textEnabled(true);
				Addmod = 1;
				rs = true;
			}
		} catch (Exception e) {
			UtilLog.logError("货币信息对话框修改错误:", e);
			return false;
		} finally {
		}
		return rs;
	}

	// 取消
	@Override
	protected void cancle() {
		try {
			textEnabled(false);
			super.cancle();
			textClear();
		} catch (Exception e) {
			UtilLog.logError("货币信息对话框取消错误:", e);
		} finally {
		}
	}

	// 删除
	@Override
	protected boolean del() {
		try {
			if (txtSymbol.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要删除的记录！");
				return false;
			}
			textEnabled(false);
			if (super.del() == true) {
				SymbolDao.getInstance().delSymbol(UtilString.isNil(this.jTable.getValueAt(this.jTable.getSelectedRow(), 0).toString()));
				refreshTable();
				textClear();
			}
		} catch (Exception e) {
			UtilLog.logError("货币信息对话框删除错误:", e);
			return false;
		} finally {
		}
		return true;
	}

	// 退出
	@Override
	protected void exit() {
		try {
			super.exit();
		} catch (Exception e) {
			UtilLog.logError("货币信息对话框退出错误:", e);
		} finally {
		}
	}

	// 保存
	@Override
	protected void post() {
		try {
			Symbol symbol = new Symbol();
			symbol.setServer(UtilString.isNil(txtServer.getText()));
			symbol.setSymbol(UtilString.isNil(txtSymbol.getText()));
			symbol.setPoint(Double.valueOf(UtilString.isNil(txtPoint.getText(), "0")));
			symbol.setDigits(Integer.valueOf(UtilString.isNil(txtDigits.getText(), "0")));
			symbol.setState(Integer.valueOf(UtilString.isNil(txtState.getText(), "0")));
			symbol.setOrd(Integer.valueOf(UtilString.isNil(txtOrd.getText(), "0")));
			symbol.setMemo(UtilString.isNil(txtaMemo.getText()));

			if (Addmod == 0) {
				SymbolDao.getInstance().addSymbol(symbol);
			} else if (Addmod == 1) {
				symbol.setId(UtilString.isNil(this.jTable.getValueAt(this.jTable.getSelectedRow(), 0).toString()));
				SymbolDao.getInstance().modSymbol(symbol);
			}
			textEnabled(false);
			super.post();
			refreshTable();
		} catch (Exception e) {
			UtilLog.logError("货币信息对话框保存错误:", e);
		} finally {
		}
	}

	// 获取参数面板
	@Override
	protected JPanel GetParamPanel() {
		pnlParam = new JPanel();
		try {
			pnlParam.setLayout(null);

			lServerName = new SLabel("服务器/公司");
			lServerName.setBounds(50, 20, 100, 20);
			pnlParam.add(lServerName);
			txtServer = new STextField("");
			txtServer.setBounds(150, 20, 250, 20);
			pnlParam.add(txtServer);

			lSymbol = new SLabel("货币名称");
			lSymbol.setBounds(400, 20, 100, 20);
			pnlParam.add(lSymbol);
			txtSymbol = new STextField("");
			txtSymbol.setBounds(500, 20, 250, 20);
			pnlParam.add(txtSymbol);

			lPoint = new SLabel("点差");
			lPoint.setBounds(50, 50, 100, 20);
			pnlParam.add(lPoint);
			{
				txtPoint = new STextField("0");
				txtPoint.setBounds(150, 50, 250, 20);
				pnlParam.add(txtPoint);
			}

			lDigits = new SLabel("小数位数");
			lDigits.setBounds(400, 50, 100, 20);
			pnlParam.add(lDigits);
			{
				txtDigits = new STextField();
				txtDigits.setBounds(500, 50, 250, 20);
				pnlParam.add(txtDigits);
			}

			lState = new SLabel("状态");
			lState.setBounds(50, 80, 100, 20);
			pnlParam.add(lState);
			{
				txtState = new STextField();
				txtState.setBounds(150, 80, 250, 20);
				pnlParam.add(txtState);
			}

			lOrd = new SLabel("顺序号");
			lOrd.setBounds(400, 80, 100, 20);
			pnlParam.add(lOrd);
			{
				txtOrd = new STextField("0");
				txtOrd.setBounds(500, 80, 250, 20);
				pnlParam.add(txtOrd);
			}

			lMemo = new SLabel("备注说明");
			lMemo.setBounds(50, 110, 100, 20);
			pnlParam.add(lMemo);
			{
				txtaMemo = new STextArea();
				scrlMemo = new SScrollPane(txtaMemo);
				pnlParam.add(scrlMemo, "bottom");
				scrlMemo.setBounds(150, 110, 250, 20);
				txtaMemo.setBounds(150, 110, 250, 20);
			}
		} catch (Exception e) {
			UtilLog.logError("货币信息对话框初始化jPanelParam错误:", e);
		} finally {
		}
		return pnlParam;
	}

	// 状态控制
	private void textEnabled(boolean flag) {
		txtServer.setEditable(flag);
		txtSymbol.setEditable(flag);
		txtPoint.setEditable(flag);
		txtDigits.setEditable(flag);
		txtaMemo.setEditable(flag);
		txtState.setEditable(flag);
		txtOrd.setEditable(flag);
	}

	// 清空txt
	private void textClear() {
		txtSymbol.setText("");
		txtServer.setText("");
		txtPoint.setText("");
		txtDigits.setText("");
		txtState.setText("");
		txtOrd.setText("");
		txtaMemo.setText("");
	}
}
