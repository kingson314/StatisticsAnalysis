package com.config.symbol;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


import common.component.SButton;
import common.component.SCheckBox;
import common.component.SDialog;
import common.component.SRadioButton;
import common.component.SSplitPane;
import common.util.log.UtilLog;
import config.symbol.SymbolDao;
import consts.ImageContext;

/**
 * @Description:
 * @date 2014-6-20
 * @author:fgq
 */
public class SymbolParamDialog extends SDialog {

    private static final long serialVersionUID = 1L;
    private SButton btnExit;
    private SButton btnSet;
    private JPanel pnlMain;
    private SSplitPane SplMain;
    private JPanel btnTool;
    private SRadioButton[] rbtnArr;
    private SCheckBox[] chkArr;
    private boolean isOK;
    private boolean isMultiSelect;

    public SymbolParamDialog(boolean isMultiSelect) {
	try {
	    this.isMultiSelect = isMultiSelect;
	    initGUI();
	} catch (Exception e) {
	    UtilLog.logError("", e);
	} finally {
	}
    }

    // 初始化界面
    private void initGUI() {
	try {
	    this.setSize(210, 260);
	    this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.Backup));
	    int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
	    int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
	    this.setLocation(w, h);
	    setTitle("货币参数选择框");
	    setModal(true);
	    GridLayout thisLayout = new GridLayout(1, 1);
	    thisLayout.setColumns(1);
	    thisLayout.setHgap(5);
	    thisLayout.setVgap(5);
	    {
		SplMain = new SSplitPane(0, this.getHeight() - 100, false);
		getContentPane().add(SplMain);
		SplMain.setPreferredSize(new java.awt.Dimension(707, 183));
		{
		    pnlMain = new JPanel();
		    pnlMain.setLayout(null);
		    pnlMain.setPreferredSize(new java.awt.Dimension(612, 180));
		    SplMain.add(pnlMain, SSplitPane.TOP);
		    {
			int y = 20;
			String[] symbolArr = SymbolDao.getInstance().getSymbolArr("");
			String[] symbolIdArr = SymbolDao.getInstance().getSymbolIdArr("");

			if (this.isMultiSelect) {
			    chkArr = new SCheckBox[symbolArr.length];
			    for (int i = 0; i < symbolArr.length; i++) {
				final SCheckBox chk = new SCheckBox(symbolArr[i]);
				chk.setName(symbolIdArr[i]);
				chkArr[i] = chk;
				if (i % 2 == 1) {
				    chk.setBounds(100, y, 80, 20);
				    y += 30;
				} else {
				    chk.setBounds(20, y, 80, 20);
				}
				pnlMain.add(chk);
			    }
			} else {
			    rbtnArr = new SRadioButton[symbolArr.length];
			    for (int i = 0; i < symbolArr.length; i++) {
				final SRadioButton rbtn = new SRadioButton(symbolArr[i]);
				rbtn.setName(symbolIdArr[i]);
				rbtnArr[i] = rbtn;
				if (i % 2 == 1) {
				    rbtn.setBounds(100, y, 80, 20);
				    y += 30;
				} else {
				    rbtn.setBounds(20, y, 80, 20);
				}
				if (i == 0)
				    rbtnArr[i].setSelected(true);
				pnlMain.add(rbtn);
				rbtn.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {
					radioButtonSeleted(rbtn);
				    }
				});
			    }
			}
		    }

		}
		{
		    btnTool = new JPanel();
		    SplMain.add(btnTool, SSplitPane.BOTTOM);
		    btnTool.setLayout(new FlowLayout());
		    btnTool.setPreferredSize(new java.awt.Dimension(612, 160));
		    {
			btnSet = new SButton("确定", ImageContext.Ok);
			btnTool.add(btnSet);
			btnSet.setSize(100, 25);
			btnSet.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent evt) {
				isOK = true;
				exit();
			    }
			});
		    }
		    {
			btnExit = new SButton("取  消", ImageContext.Exit);
			btnTool.add(btnExit);
			btnExit.setSize(100, 25);
			btnExit.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent evt) {
				isOK = false;
				exit();
			    }
			});
		    }
		}
	    }
	    this.setVisible(true);
	} catch (Exception e) {
	    UtilLog.logError("", e);
	} finally {
	}

    }

    private void radioButtonSeleted(SRadioButton rbtn) {
	for (SRadioButton item : this.rbtnArr) {
	    if (!item.getText().equals(rbtn.getText())) {
		item.setSelected(false);
	    }
	    rbtn.setSelected(true);
	}
    }

    public Object getSymbol() {
	if (!isOK) {
	    return null;
	}
	if (this.isMultiSelect) {
	    ArrayList<String> list = new ArrayList<String>();
	    for (SCheckBox chk : chkArr) {
		if (chk.isSelected()) {
		    list.add(chk.getText());
		}
	    }
	    return list;
	} else {
	    for (SRadioButton rbtn : rbtnArr) {
		if (rbtn.isSelected()) {
		    return rbtn.getText();
		}
	    }
	}
	return null;
    }
    
    public Object getSymbolId() {
	if (!isOK) {
	    return null;
	}
	if (this.isMultiSelect) {
	    ArrayList<String> list = new ArrayList<String>();
	    for (SCheckBox chk : chkArr) {
		if (chk.isSelected()) {
		    list.add(chk.getName());
		}
	    }
	    return list;
	} else {
	    for (SRadioButton rbtn : rbtnArr) {
		if (rbtn.isSelected()) {
		    return rbtn.getName();
		}
	    }
	}
	return null;
    }

    // 退出
    private void exit() {
	try {
	    this.dispose();
	} catch (Exception e) {
	    UtilLog.logError("备份数据对话框退出错误:", e);
	} finally {
	}
    }

}
