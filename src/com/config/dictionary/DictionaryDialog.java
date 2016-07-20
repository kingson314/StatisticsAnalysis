package com.config.dictionary;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;


import common.component.DialogTableModel;
import common.component.SButton;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SScrollPane;
import common.component.STextArea;
import common.component.STextField;
import common.component.ShowMsg;
import common.util.log.UtilLog;
import common.util.string.UtilString;
import config.dictionary.Dictionary;
import config.dictionary.DictionaryDao;
import consts.ImageContext;

public class DictionaryDialog extends DialogTableModel {
    private static final long serialVersionUID = 1L;
    private JPanel pnlParam;
    private int Addmod;
    private SLabel lGroupName;
    private STextField txtGroupName;
    private SLabel lCode;
    private STextField txtName;
    private SLabel lValue;
    private STextField txtValue;
    private SLabel lState;
    private STextField txtState;
    private SLabel lOrd;
    private STextField txtOrd;
    private SLabel lMemo;
    private SScrollPane scrlMemo;
    private STextArea txtaMemo;

    // 获取表格
    private JTable getTable(String group, String code) {
	final JTable table = new DictionaryTable(group, code).getJtable();
	table.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent evt) {
		txtGroupName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
		txtName.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
		txtValue.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
		txtOrd.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
		txtState.setText(table.getValueAt(table.getSelectedRow(), 5).toString());
		txtaMemo.setText(table.getValueAt(table.getSelectedRow(), 6).toString());
		textEnabled(false);
	    }
	});
	return table;
    }

    // 刷新表格
    private void refreshTable() {
	super.jTable = getTable("", "");
	super.scrlTable.setViewportView(super.jTable);
    }

    // 构造
    public DictionaryDialog() {
	super(0);
	try {
	    super.setTitle("字典信息");
	    this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.Dictionary));
	    super.jTable = getTable("", "");
	    super.setParamsPanelHeight(130);
	    super.ini();
	    this.setSize(900, 550);
	    int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
	    int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
	    this.setLocation(w, h);
	    textEnabled(false);
	} catch (Exception e) {
	    UtilLog.logError("参数对话框构造错误:", e);
	} finally {
	}
    }

    protected JToolBar getQueryTb() {
	JToolBar tbQuery = new JToolBar();
	tbQuery.setBounds(0, 0, 840, 120);
	tbQuery.setEnabled(false);
	tbQuery.setLayout(null);
	int x = 20;
	{
	    SLabel lModule1 = new SLabel("字典分组");

	    lModule1.setBounds(x, 6, 60, 21);
	    tbQuery.add(lModule1);
	    lModule1.setMaximumSize(new java.awt.Dimension(52, 21));
	    lModule1.setMinimumSize(new java.awt.Dimension(52, 21));
	    x += lModule1.getWidth();
	}
	final SComboBox cmbModule = new SComboBox(DictionaryDao.getInstance().getGroupArr());
	{

	    tbQuery.add(cmbModule);
	    cmbModule.setBounds(x, 4, 120, 21);
	    cmbModule.setPreferredSize(new java.awt.Dimension(24, 21));
	    cmbModule.setMaximumSize(new java.awt.Dimension(32767, 21));
	    x += cmbModule.getWidth();

	}
	x += 10;
	{
	    SLabel lParanmName = new SLabel("字典标志");
	    tbQuery.add(lParanmName);
	    lParanmName.setLocation(new java.awt.Point(0, 0));
	    lParanmName.setBounds(x, 6, 60, 21);
	    x += lParanmName.getWidth();
	}
	final SComboBox cmbParamName = new SComboBox(DictionaryDao.getInstance().getNameArr());
	{

	    tbQuery.add(cmbParamName);
	    cmbParamName.setMaximumRowCount(20);
	    cmbParamName.setBounds(x, 4, 150, 21);
	    cmbParamName.setPreferredSize(new java.awt.Dimension(24, 21));
	    cmbParamName.setMaximumSize(new java.awt.Dimension(32767, 21));
	    x += cmbParamName.getWidth();

	}
	{
	    SButton btnQuery = new SButton("查 询", ImageContext.Query);
	    tbQuery.add(btnQuery);
	    btnQuery.setBounds(x + 10, 2, 90, 25);
	    btnQuery.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		    JTable tbl = getTable(cmbModule.getSelectedItem().toString(), cmbParamName.getSelectedItem().toString());
		    setTable(tbl);
		}
	    });

	}
	return tbQuery;
    }

    private void setTable(JTable tbl) {
	super.scrlTable.setViewportView(tbl);
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
	    UtilLog.logError("参数对话框新增错误:", e);
	} finally {
	}
    }

    // 修改
    @Override
    protected boolean mod() {
	boolean rs = false;
	try {
	    if (txtGroupName.getText().length() < 1) {
		ShowMsg.showMsg("请选择需要修改的记录！");
		return rs;
	    }
	    if (super.mod()) {
		textEnabled(true);
		Addmod = 1;
		rs = true;
	    }
	} catch (Exception e) {
	    UtilLog.logError("参数对话框修改错误:", e);
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
	    UtilLog.logError("参数对话框取消错误:", e);
	} finally {
	}
    }

    // 删除
    @Override
    protected boolean del() {
	try {
	    if (txtGroupName.getText().length() < 1) {
		ShowMsg.showMsg("请选择需要删除的记录！");
		return false;
	    }
	    textEnabled(false);
	    if (super.del() == true) {
		DictionaryDao.getInstance().delDictionary(UtilString.isNil(this.jTable.getValueAt(this.jTable.getSelectedRow(), 0).toString()));
		refreshTable();
		textClear();
	    }
	} catch (Exception e) {
	    UtilLog.logError("参数对话框删除错误:", e);
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
	    UtilLog.logError("参数对话框退出错误:", e);
	} finally {
	}
    }

    // 保存
    @Override
    protected void post() {
	try {
	    Dictionary bean = new Dictionary();
	    bean.setGroupName(UtilString.isNil(txtGroupName.getText()));
	    bean.setName(UtilString.isNil(txtName.getText()));
	    bean.setValue(UtilString.isNil(txtValue.getText()));
	    bean.setOrd(Integer.valueOf(UtilString.isNil(txtOrd.getText(), "0")));
	    bean.setState(Integer.valueOf(UtilString.isNil(txtState.getText(), "0")));
	    bean.setMemo(UtilString.isNil(txtaMemo.getText()));

	    if (Addmod == 0) {
		DictionaryDao.getInstance().addDictionary(bean);
	    } else if (Addmod == 1) {
		bean.setId(UtilString.isNil(this.jTable.getValueAt(this.jTable.getSelectedRow(), 0).toString()));
		DictionaryDao.getInstance().modDictionary(bean);
	    }
	    textEnabled(false);
	    super.post();
	    refreshTable();
	} catch (Exception e) {
	    ShowMsg.showWarn("参数对话框保存错误");
	} finally {
	}
    }

    // 获取参数面板
    @Override
    protected JPanel GetParamPanel() {
	pnlParam = new JPanel();
	try {
	    pnlParam.setLayout(null);

	    lGroupName = new SLabel("字典组名称");
	    lGroupName.setBounds(50, 20, 100, 20);
	    pnlParam.add(lGroupName);
	    txtGroupName = new STextField("");
	    txtGroupName.setBounds(150, 20, 250, 20);
	    pnlParam.add(txtGroupName);

	    lCode = new SLabel("字典名称");
	    lCode.setBounds(430, 20, 100, 20);
	    pnlParam.add(lCode);
	    txtName = new STextField("");
	    txtName.setBounds(530, 20, 250, 20);
	    pnlParam.add(txtName);

	    lValue = new SLabel("字典值");
	    lValue.setBounds(50, 50, 100, 20);
	    pnlParam.add(lValue);
	    txtValue = new STextField("");
	    txtValue.setBounds(150, 50, 250, 20);
	    pnlParam.add(txtValue);

	    lOrd = new SLabel("字典顺序号");
	    lOrd.setBounds(430, 50, 100, 20);
	    pnlParam.add(lOrd);
	    txtOrd = new STextField("0");
	    txtOrd.setBounds(530, 50, 250, 20);
	    pnlParam.add(txtOrd);

	    lState = new SLabel("状态");
	    lState.setBounds(50, 80, 100, 20);
	    pnlParam.add(lState);
	    txtState = new STextField("1");
	    txtState.setBounds(150, 80, 250, 20);
	    pnlParam.add(txtState);

	    lMemo = new SLabel("备注");
	    lMemo.setBounds(430, 80, 100, 20);
	    pnlParam.add(lMemo);
	    {
		txtaMemo = new STextArea();
		scrlMemo = new SScrollPane(txtaMemo);
		pnlParam.add(scrlMemo, "bottom");
		scrlMemo.setBounds(530, 80, 250, 30);
	    }
	} catch (Exception e) {
	    UtilLog.logError("参数对话框初始化错误:", e);
	} finally {
	}
	return pnlParam;
    }

    // 状态控制
    private void textEnabled(boolean flag) {
	txtGroupName.setEditable(flag);
	txtName.setEditable(flag);
	txtValue.setEditable(flag);
	txtaMemo.setEditable(flag);
	txtState.setEditable(flag);
	txtOrd.setEditable(flag);
    }

    // 清空txt
    private void textClear() {
	txtGroupName.setText("");
	txtName.setText("");
	txtValue.setText("");
	txtState.setText("");
	txtOrd.setText("");
	txtaMemo.setText("");
    }
}
