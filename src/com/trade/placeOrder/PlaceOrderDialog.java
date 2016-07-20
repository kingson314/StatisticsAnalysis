package com.trade.placeOrder;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;


import com.trade.FunTrade;
import common.component.IComponent;
import common.component.SComboBox;
import common.component.SDialog;
import common.component.SLabel;
import common.component.SSplitPane;
import common.component.STabbedPane;
import common.component.STextField;

import config.dictionary.DictionaryDao;
import consts.ImageContext;

/**
 * 
 * @fun 下单窗体
 * @date 2013-01-21
 * 
 */
public class PlaceOrderDialog extends SDialog {
    private static final long serialVersionUID = -1744284865246333509L;
    private JSplitPane spltMain;
    private JSplitPane splOrder;
    private STextField txtLots;
    private STextField txtStopLoss;
    private STextField txtTakeProfit;
    private STextField txtMemo;

    public PlaceOrderDialog() {
	initGUI();
    }

    private void initGUI() {
	try {
	    this.setSize(800, 400);
	    this.setResizable(false);
	    this.setAlwaysOnTop(true);
	    this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.PlaceOrder));
	    int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
	    int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
	    this.setLocation(w, h);
	    setModal(true);
	    this.setTitle("订单");
	    {
		spltMain = new SSplitPane(1, 0.4, false);
		spltMain.setDividerSize(1);
		getContentPane().add(spltMain, BorderLayout.CENTER);
	    }
	    {
		splOrder = new SSplitPane(0, 0.5, false);
		splOrder.setDividerSize(1);
		spltMain.add(splOrder, JSplitPane.RIGHT);
		splOrder.add(getOrderMsg(), JSplitPane.TOP);
		splOrder.add(getOrderOperate(), JSplitPane.BOTTOM);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private STabbedPane getOrderMsg() {
	String title = "基本信息";
	STabbedPane tab = new STabbedPane(new String[] { title });
	JPanel pnl = new JPanel();
	pnl.setLayout(null);
	int x = 10;
	{

	    SLabel lsymbol = new SLabel("货币");
	    lsymbol.setBounds(20, x, 60, 21);
	    pnl.add(lsymbol);
	    SComboBox cmbSymbol = new SComboBox(FunTrade.symbol);
	    cmbSymbol.setBounds(80, x, 320, 21);
	    pnl.add(cmbSymbol);
	}
	x += IComponent.RowSpacing;
	{
	    SLabel lLots = new SLabel("单量");
	    lLots.setBounds(20, x, 60, 21);
	    pnl.add(lLots);
	    txtLots = new STextField(DictionaryDao.getInstance().getValue("交易参数", "默认下单量"));
	    txtLots.addMouseWheelListener(new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
		    FunTrade.setLot(txtLots, e, FunTrade.account);
		}
	    });
	    txtLots.setBounds(80, x, 120, 21);
	    pnl.add(txtLots);
	}
	x += IComponent.RowSpacing;
	{
	    SLabel lStopLoss = new SLabel("止损价");
	    lStopLoss.setBounds(20, x, 60, 21);
	    pnl.add(lStopLoss);
	    txtStopLoss = new STextField(DictionaryDao.getInstance().getValue("交易参数", "默认止损值"));
	    txtStopLoss.addMouseWheelListener(new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
		    FunTrade.setValue(txtStopLoss, e);
		}
	    });
	    txtStopLoss.setBounds(80, x, 120, 21);
	    pnl.add(txtStopLoss);
	}
	{
	    SLabel lTakeProfit = new SLabel("止盈价");
	    lTakeProfit.setBounds(220, x, 60, 21);
	    pnl.add(lTakeProfit);
	    txtTakeProfit = new STextField(DictionaryDao.getInstance().getValue("交易参数", "默认止盈值"));
	    txtTakeProfit.addMouseWheelListener(new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
		    FunTrade.setValue(txtTakeProfit, e);
		}
	    });
	    txtTakeProfit.setBounds(280, x, 120, 21);
	    pnl.add(txtTakeProfit);
	}
	x += IComponent.RowSpacing;
	{
	    SLabel lMemo = new SLabel("注释");
	    lMemo.setBounds(20, x, 60, 21);
	    pnl.add(lMemo);
	    txtMemo = new STextField(DictionaryDao.getInstance().getValue("交易参数", "默认注释"));
	    txtMemo.setBounds(80, x, 320, 21);
	    pnl.add(txtMemo);
	}
	tab.addTab(title, pnl);
	return tab;
    }

    private STabbedPane getOrderOperate() {
	String[] title = new String[] { "即时成交", "挂单交易", "修改订单" };
	STabbedPane tab = new STabbedPane(title);

	// 即时成交
	JPanel pnlReal = new JPanel();
	pnlReal.setLayout(null);
	@SuppressWarnings("unused")
	int x = 10;
	tab.addTab(title[0], pnlReal);

	// 挂单交易
	JPanel pnlFuture = new JPanel();
	pnlFuture.setLayout(null);

	x = 10;
	tab.addTab(title[1], pnlFuture);

	// 修改订单
	JPanel pnlModify = new JPanel();
	pnlModify.setLayout(null);
	x = 10;
	tab.addTab(title[2], pnlModify);
	return tab;
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
	PlaceOrderDialog placeOrderDialog = new PlaceOrderDialog();
	placeOrderDialog.show();
    }
}
