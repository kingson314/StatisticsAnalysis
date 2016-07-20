package com.config.account;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;


import common.component.DialogTableModel;
import common.component.SLabel;
import common.component.SScrollPane;
import common.component.STextArea;
import common.component.STextField;
import common.component.ShowMsg;
import common.util.log.UtilLog;
import common.util.security.UtilCrypt;
import common.util.string.UtilString;
import consts.ImageContext;

public class AccountDialog extends DialogTableModel {
	private static final long serialVersionUID = 1L;
	private JPanel pnlParam;
	private int Addmod;
	private SLabel lName;
	private STextField txtName;
	private SLabel lType;
	private STextField txtType;
	private SLabel lCompany;
	private STextField txtCompany;
	private SLabel lEquity;
	private STextField txtEquity;
	private SLabel lBalance;
	private STextField txtBalance;
	private STextField txtProfit;
	private SLabel lProfit;
	private STextField txtLeverage;
	private SLabel lLeverage;
	private SLabel lMargin;
	private STextField txtMargin;
	private STextField txtServer;
	private SLabel lServer;
	private STextField txtOpendate;
	private SLabel lOpendate;
	private STextField txtAgent;
	private SLabel lAgent;
	private STextField txtAgentMsg;
	private SLabel lAgentMsg;
	private SLabel lSoftware;
	private STextField txtSoftware;
	private STextField txtSoftwareIp;
	private SLabel lSoftwareIp;
	private STextField txtSoftwareDir;
	private SLabel lSoftwareDir;
	private SLabel lPassword;
	private JPasswordField txtPassword;
	private SLabel lMaxLot;
	private STextField txtMaxLot;
	private STextField txtMinLot;
	private SLabel lMinLot;
	private SLabel lState;
	private STextField txtState;
	private SLabel lOrd;
	private STextField txtOrd;
	private SLabel lMemo;
	private SScrollPane scrlMemo;
	private STextArea txtaMemo;

	// 获取表格
	private JTable getTable() {
		final JTable table = new AccountTable("").getJtable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				txtName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				txtPassword.setText(UtilCrypt.getInstance().decryptAES(table.getValueAt(table.getSelectedRow(), 2).toString(), UtilCrypt.key));
				txtType.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
				txtCompany.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
				txtEquity.setText(table.getValueAt(table.getSelectedRow(), 5).toString());
				txtBalance.setText(table.getValueAt(table.getSelectedRow(), 6).toString());
				txtProfit.setText(table.getValueAt(table.getSelectedRow(), 7).toString());
				txtLeverage.setText(table.getValueAt(table.getSelectedRow(), 8).toString());
				txtMargin.setText(table.getValueAt(table.getSelectedRow(), 9).toString());
				txtServer.setText(table.getValueAt(table.getSelectedRow(), 10).toString());
				txtOpendate.setText(table.getValueAt(table.getSelectedRow(), 11).toString());
				txtAgent.setText(table.getValueAt(table.getSelectedRow(), 12).toString());
				txtAgentMsg.setText(table.getValueAt(table.getSelectedRow(), 13).toString());
				txtSoftware.setText(table.getValueAt(table.getSelectedRow(), 14).toString());
				txtSoftwareIp.setText(table.getValueAt(table.getSelectedRow(), 15).toString());
				txtSoftwareDir.setText(table.getValueAt(table.getSelectedRow(), 16).toString());
				txtMaxLot.setText(table.getValueAt(table.getSelectedRow(), 17).toString());
				txtMinLot.setText(table.getValueAt(table.getSelectedRow(), 18).toString());
				txtState.setText(table.getValueAt(table.getSelectedRow(), 19).toString());
				txtOrd.setText(table.getValueAt(table.getSelectedRow(), 20).toString());
				txtaMemo.setText(table.getValueAt(table.getSelectedRow(), 21).toString());
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
	public AccountDialog() {
		super(0);
		try {
			super.setTitle("账户信息");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.Account));
			super.jTable = getTable();
			super.setParamsPanelHeight(380);
			super.ini();
			this.setSize(900, 780);
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
			this.setLocation(w, h);
			textEnabled(false);
		} catch (Exception e) {
			UtilLog.logError("账户信息对话框构造错误:", e);
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
			UtilLog.logError("账户信息对话框新增错误:", e);
		} finally {
		}
	}

	// 修改
	@Override
	protected boolean mod() {
		boolean rs = false;
		try {
			if (txtName.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要修改的记录！");
				return rs;
			}
			if (super.mod()) {
				textEnabled(true);
				Addmod = 1;
				rs = true;
			}
		} catch (Exception e) {
			UtilLog.logError("账户信息对话框修改错误:", e);
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
			UtilLog.logError("账户信息对话框取消错误:", e);
		} finally {
		}
	}

	// 删除
	@Override
	protected boolean del() {
		try {
			if (txtName.getText().length() < 1) {
				ShowMsg.showMsg("请选择需要删除的记录！");
				return false;
			}
			textEnabled(false);
			if (super.del() == true) {
				AccountDao.getInstance().delaccount(UtilString.isNil(this.jTable.getValueAt(this.jTable.getSelectedRow(), 0).toString()));
				refreshTable();
				textClear();
			}
		} catch (Exception e) {
			UtilLog.logError("账户信息对话框删除错误:", e);
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
			UtilLog.logError("账户信息对话框退出错误:", e);
		} finally {
		}
	}

	// 保存

	@SuppressWarnings("deprecation")
	@Override
	protected void post() {
		try {
			Account account = new Account();
			account.setName(UtilString.isNil(txtName.getText()));
			account.setPassword(UtilCrypt.getInstance().encryptAES(UtilString.isNil(txtPassword.getText()), UtilCrypt.key));
			account.setType(UtilString.isNil(txtType.getText()));
			account.setCompany(UtilString.isNil(txtCompany.getText()));
			account.setEquity(UtilString.isNil(txtEquity.getText()));
			account.setBalance(UtilString.isNil(txtBalance.getText()));
			account.setProfit(UtilString.isNil(txtProfit.getText()));
			account.setLeverage(UtilString.isNil(txtLeverage.getText()));
			account.setMargin(UtilString.isNil(txtMargin.getText()));
			account.setServer(UtilString.isNil(txtServer.getText()));
			account.setOpendate(UtilString.isNil(txtOpendate.getText()));
			account.setAgent(UtilString.isNil(txtAgent.getText()));
			account.setAgentMsg(UtilString.isNil(txtAgentMsg.getText()));
			account.setSoftware(UtilString.isNil(txtSoftware.getText()));
			account.setSoftwareIp(UtilString.isNil(txtSoftwareIp.getText()));
			account.setSoftwareDir(UtilString.isNil(txtSoftwareDir.getText()));
			account.setMaxLot(UtilString.isNil(txtMaxLot.getText()));
			account.setMinLot(UtilString.isNil(txtMinLot.getText()));
			account.setState(Integer.valueOf(UtilString.isNil(txtState.getText(), "0")));
			account.setOrd(Integer.valueOf(UtilString.isNil(txtOrd.getText(), "0")));
			account.setMemo(UtilString.isNil(txtaMemo.getText()));

			if (Addmod == 0) {
				AccountDao.getInstance().addaccount(account);
			} else if (Addmod == 1) {
				account.setId(UtilString.isNil(this.jTable.getValueAt(this.jTable.getSelectedRow(), 0).toString()));
				AccountDao.getInstance().modaccount(account);
			}
			textEnabled(false);
			super.post();
			refreshTable();
		} catch (Exception e) {
			UtilLog.logError("账户信息对话框保存错误:", e);
		} finally {
		}
	}

	// 获取参数面板
	@Override
	protected JPanel GetParamPanel() {
		pnlParam = new JPanel();
		try {
			pnlParam.setLayout(null);
			pnlParam.setPreferredSize(new java.awt.Dimension(1086, 900));

			lName = new SLabel("账户编号");
			lName.setBounds(50, 20, 100, 20);
			pnlParam.add(lName);
			txtName = new STextField("");
			txtName.setBounds(150, 20, 250, 20);
			pnlParam.add(txtName);

			lPassword = new SLabel("账户密码");
			lPassword.setBounds(430, 20, 100, 20);
			pnlParam.add(lPassword);
			txtPassword = new JPasswordField("");
			txtPassword.setBounds(532, 20, 250, 20);
			pnlParam.add(txtPassword);

			lType = new SLabel("账户类型");
			lType.setBounds(50, 49, 100, 20);
			pnlParam.add(lType);
			txtType = new STextField("");
			txtType.setBounds(150, 49, 250, 20);
			pnlParam.add(txtType);

			lCompany = new SLabel("账户公司名");
			lCompany.setBounds(430, 50, 100, 20);
			pnlParam.add(lCompany);
			txtCompany = new STextField("");
			txtCompany.setBounds(532, 50, 250, 20);
			pnlParam.add(txtCompany);

			lEquity = new SLabel("账户资产净值");
			lEquity.setBounds(430, 79, 100, 20);
			pnlParam.add(lEquity);
			txtEquity = new STextField("");
			txtEquity.setBounds(532, 79, 250, 20);
			pnlParam.add(txtEquity);

			lBalance = new SLabel("账户余额");
			lBalance.setBounds(50, 80, 100, 20);
			pnlParam.add(lBalance);
			txtBalance = new STextField("");
			txtBalance.setBounds(150, 80, 250, 20);
			pnlParam.add(txtBalance);

			lLeverage = new SLabel("账户杠杆比率");
			lLeverage.setBounds(50, 110, 100, 20);
			pnlParam.add(lLeverage);
			txtLeverage = new STextField("");
			txtLeverage.setBounds(150, 110, 250, 20);
			pnlParam.add(txtLeverage);

			lProfit = new SLabel("账户利润");
			lProfit.setBounds(430, 110, 100, 20);
			pnlParam.add(lProfit);
			txtProfit = new STextField("");
			txtProfit.setBounds(532, 110, 250, 20);
			pnlParam.add(txtProfit);

			lMaxLot = new SLabel("最大单量");
			lMaxLot.setBounds(50, 140, 100, 20);
			pnlParam.add(lMaxLot);
			txtMaxLot = new STextField("");
			txtMaxLot.setBounds(150, 140, 250, 20);
			pnlParam.add(txtMaxLot);

			lMinLot = new SLabel("最小单量");
			lMinLot.setBounds(430, 140, 100, 20);
			pnlParam.add(lMinLot);
			txtMinLot = new STextField("");
			txtMinLot.setBounds(532, 140, 250, 20);
			pnlParam.add(txtMinLot);

			lServer = new SLabel("服务器");
			lServer.setBounds(50, 176, 100, 20);
			pnlParam.add(lServer);
			txtServer = new STextField("");
			txtServer.setBounds(150, 176, 250, 20);
			pnlParam.add(txtServer);

			lMargin = new SLabel("帐户保证金");
			lMargin.setBounds(430, 175, 100, 20);
			pnlParam.add(lMargin);
			txtMargin = new STextField("");
			txtMargin.setBounds(532, 175, 250, 20);
			pnlParam.add(txtMargin);

			lAgent = new SLabel("开户代理人(IB)");
			lAgent.setBounds(50, 206, 100, 20);
			pnlParam.add(lAgent);
			txtAgent = new STextField("");
			txtAgent.setBounds(150, 206, 250, 20);
			pnlParam.add(txtAgent);

			lAgentMsg = new SLabel("开户代理人基本信息");
			lAgentMsg.setBounds(380, 205, 150, 20);
			pnlParam.add(lAgentMsg);
			txtAgentMsg = new STextField("");
			txtAgentMsg.setBounds(532, 205, 250, 20);
			pnlParam.add(txtAgentMsg);

			lOpendate = new SLabel("开户日期");
			lOpendate.setBounds(50, 235, 100, 20);
			pnlParam.add(lOpendate);
			txtOpendate = new STextField("");
			txtOpendate.setBounds(150, 235, 250, 20);
			pnlParam.add(txtOpendate);

			lSoftware = new SLabel("操作软件");
			lSoftware.setBounds(430, 236, 100, 20);
			pnlParam.add(lSoftware);
			txtSoftware = new STextField("");
			txtSoftware.setBounds(532, 236, 250, 20);
			pnlParam.add(txtSoftware);

			lSoftwareIp = new SLabel("操作软件计算机Ip");
			lSoftwareIp.setBounds(380, 265, 150, 20);
			pnlParam.add(lSoftwareIp);
			txtSoftwareIp = new STextField("");
			txtSoftwareIp.setBounds(532, 265, 250, 20);
			pnlParam.add(txtSoftwareIp);

			lSoftwareDir = new SLabel("操作软件所在目录");
			lSoftwareDir.setBounds(0, 266, 150, 20);
			pnlParam.add(lSoftwareDir);
			txtSoftwareDir = new STextField("");
			txtSoftwareDir.setBounds(150, 266, 250, 20);
			pnlParam.add(txtSoftwareDir);

			lState = new SLabel("状态");
			lState.setBounds(50, 296, 100, 20);
			pnlParam.add(lState);
			txtState = new STextField();
			txtState.setBounds(150, 295, 250, 20);
			pnlParam.add(txtState);

			lOrd = new SLabel("顺序号");
			lOrd.setBounds(430, 296, 100, 20);
			pnlParam.add(lOrd);
			txtOrd = new STextField("0");
			txtOrd.setBounds(532, 295, 250, 20);
			pnlParam.add(txtOrd);

			lMemo = new SLabel("备注说明");
			lMemo.setBounds(50, 326, 100, 20);
			pnlParam.add(lMemo);
			txtaMemo = new STextArea();
			scrlMemo = new SScrollPane(txtaMemo);
			pnlParam.add(scrlMemo, "bottom");
			scrlMemo.setBounds(150, 325, 250, 20);
		} catch (Exception e) {
			UtilLog.logError("账户信息对话框初始化jPanelParam错误:", e);
		} finally {
		}
		return pnlParam;
	}

	// 状态控制
	private void textEnabled(boolean flag) {
		txtName.setEditable(flag);
		txtPassword.setEditable(flag);
		txtType.setEditable(flag);
		txtCompany.setEditable(flag);
		txtEquity.setEditable(flag);
		txtBalance.setEditable(flag);
		txtProfit.setEditable(flag);
		txtLeverage.setEditable(flag);
		txtMargin.setEditable(flag);
		txtServer.setEditable(flag);
		txtOpendate.setEditable(flag);
		txtAgent.setEditable(flag);
		txtAgentMsg.setEditable(flag);
		txtSoftware.setEditable(flag);
		txtSoftwareIp.setEditable(flag);
		txtSoftwareDir.setEditable(flag);
		txtMaxLot.setEditable(flag);
		txtMinLot.setEditable(flag);
		txtaMemo.setEditable(flag);
		txtState.setEditable(flag);
		txtOrd.setEditable(flag);
	}

	// 清空txt
	private void textClear() {
		txtName.setText("");
		txtPassword.setText("");
		txtType.setText("");
		txtCompany.setText("");
		txtEquity.setText("");
		txtBalance.setText("");
		txtProfit.setText("");
		txtLeverage.setText("");
		txtMargin.setText("");
		txtServer.setText("");
		txtOpendate.setText("");
		txtAgent.setText("");
		txtAgentMsg.setText("");
		txtSoftware.setText("");
		txtSoftwareIp.setText("");
		txtSoftwareDir.setText("");
		txtMaxLot.setText("");
		txtMinLot.setText("");
		txtState.setText("");
		txtOrd.setText("");
		txtaMemo.setText("");
	}
}
