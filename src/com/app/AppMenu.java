package com.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import app.AppConfig;
import app.AppLookAndFeel;
import app.AppStatus;

import com.graphAnalysis.klineReal.KlineReal;
import com.news.Mt4;

import common.component.SMenu;
import common.component.SMenuItem;
import common.util.log.UtilLog;
import common.util.swing.UtilComponent;
import common.util.xml.UtilXml;
import consts.Const;
import consts.ImageContext;

/**
 * @info 程序菜单
 * 
 * @author fgq 20120831
 * 
 */
public class AppMenu {

	private JMenuBar mb;
	private SMenu mMsg;
	private SMenu mSys;
	private SMenu mHelp;
	private SMenu mTool;
	private SMenuItem miExit;
	private SMenuItem miAbout;
	private SMenuItem miDbConn;
	private SMenu miLook;
	private SMenuItem miLock;
	private SMenu mRun;
	private SMenuItem miStartReal;
	private SMenuItem miStopReal;
	private JMenu mTrade;
	private JMenuItem miAccount;
	private SMenuItem miSymbol;
	private SMenuItem miDictionary;
	private SMenuItem miPlaceOrder;
	private SMenuItem miStartMt4News;
	private AbstractButton miStopMt4News;
	private static AppMenu menu;

	public static AppMenu getInstance() {
		if (menu == null)
			menu = new AppMenu();
		return menu;
	}

	// 系统菜单
	private void initSys() {
		mSys = new SMenu("系统(S)", KeyEvent.VK_S);
		mb.add(mSys);

		{
			mSys.addSeparator();
		}
		{
			mSys.addSeparator();
		}
		{
			miLock = new SMenuItem("锁定程序(L)", KeyEvent.VK_L, ImageContext.SystemLock);
			mSys.add(miLock);
			miLock.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					AppFun.getInstance().systemLock();
				}
			});
		}
		{
			mSys.addSeparator();
		}

		{
			miExit = new SMenuItem("退出程序(E)", KeyEvent.VK_E, ImageContext.SystemExit);
			mSys.add(miExit);
			miExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					AppFun.getInstance().systemClosing();
				}
			});
		}
	}

	// 信息菜单
	private void initMsg() {
		mMsg = new SMenu("信息维护(M)", KeyEvent.VK_M);
		mb.add(mMsg);
		{
			miDbConn = new SMenuItem("数据源配置(D)", KeyEvent.VK_D, ImageContext.DbCon);
			mMsg.add(miDbConn);
			miDbConn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					AppFun.getInstance().dbConnDialog();
				}
			});
		}

	}

	// 工具菜单
	private void initTool() {
		mTool = new SMenu("工具(T)", KeyEvent.VK_T);
		mb.add(mTool);

	}

	// 运行菜单
	private void initRun() {
		mRun = new SMenu("运行(R)", KeyEvent.VK_R);
		mb.add(mRun);

		{
			miPlaceOrder = new SMenuItem("新订单(P)", KeyEvent.VK_P, ImageContext.PlaceOrder);
			mRun.add(miPlaceOrder);
			miPlaceOrder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					AppFun.getInstance().placeOrderDialog();
				}
			});
		}
		{
			miStartReal = new SMenuItem("启动实时行情(R)", KeyEvent.VK_B, ImageContext.Start);
			mRun.add(miStartReal);
			miStartReal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					KlineReal.getInstance().timeSchedule.start();
				}
			});

		}
		{
			miStopReal = new SMenuItem("停止实时行情(S)", KeyEvent.VK_S, ImageContext.Stop);
			mRun.add(miStopReal);
			miStopReal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					KlineReal.getInstance().timeSchedule.stop();
				}
			});

		}

		{
			miStartMt4News = new SMenuItem("启动接收Mt4新闻", 0, ImageContext.Start);
			mRun.add(miStartMt4News);
			miStartMt4News.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					// Mt4.getInstance().start(1000);
					Mt4.getInstance().update();
				}
			});

		}
		{
			miStopMt4News = new SMenuItem("停止接收Mt4新闻", 0, ImageContext.Stop);
			mRun.add(miStopMt4News);
			miStopMt4News.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					Mt4.getInstance().stop();
				}
			});

		}
	}

	/**
	 * @fun :交易类信息维护
	 * @date :2013-01-13
	 */
	private void initTrade() {
		mTrade = new SMenu("交易信息(T)", KeyEvent.VK_T);
		mb.add(mTrade);

		{
			miDictionary = new SMenuItem("字典信息(D)", KeyEvent.VK_D, ImageContext.Dictionary);
			mTrade.add(miDictionary);
			miDictionary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					AppFun.getInstance().dictionaryDialog();
				}
			});

		}
		{
			miAccount = new SMenuItem("账户信息(Z)", KeyEvent.VK_Z, ImageContext.Account);
			mTrade.add(miAccount);
			miAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					AppFun.getInstance().acccountDialog();
				}
			});

		}
		{
			miSymbol = new SMenuItem("货币信息(S)", KeyEvent.VK_S, ImageContext.Symbol);
			mTrade.add(miSymbol);
			miSymbol.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					AppFun.getInstance().symbolDialog();
				}
			});

		}
	}

	// 帮助菜单
	private void initHelp() {
		mHelp = new SMenu("帮助(H)", KeyEvent.VK_H);
		mb.add(mHelp);
		{
			{
				miLook = new SMenu("皮肤(L)", KeyEvent.VK_L, ImageContext.LookAndFeel);
				mHelp.add(miLook);
				final JRadioButtonMenuItem[] miLf = new JRadioButtonMenuItem[AppLookAndFeel.LookAndFeelCnName.length];
				for (int i = 0; i < AppLookAndFeel.LookAndFeelCnName.length - 1; i++) {
					miLf[i] = new JRadioButtonMenuItem(AppLookAndFeel.LookAndFeelCnName[i]);
					miLf[i].setFont(Const.tfont);
					if (AppLookAndFeel.LookAndFeelCnName[i].equals(AppConfig.getInstance().getMapAppConfig().get("appLookAndFeel"))) {
						miLf[i].setSelected(true);
					} else {
						miLf[i].setSelected(false);
					}
					final int index = i;
					miLf[index].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							UtilComponent.enableRadion(miLf, index, false);
							// AppLookAndFeel.getInstance().setLookAndFeel(
							// AppMain.appMain, miLf[i] .getText());
							AppLookAndFeel.getInstance().updateLookAndFeel(miLf[index].getText());
							UtilXml.updateXml(Const.XmlAppConfig, Const.XmlAppConfig, "appLookAndFeel", miLf[index].getText());
							AppStatus.getInstance().autoRefresh();
						}
					});
					miLook.add(miLf[i]);
				}
			}

			{
				miAbout = new SMenuItem("关于(A)", KeyEvent.VK_A, ImageContext.About);
				mHelp.add(miAbout);
				miAbout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						AppFun.getInstance().aboutDialog();
					}
				});
			}

		}
	}

	// 构造
	public AppMenu() {
		try {
			mb = new JMenuBar();
			mb.setOpaque(true);
			initSys();
			initMsg();
			initTrade();
			initRun();
			initTool();
			initHelp();

		} catch (Exception e) {
			UtilLog.logError("主程序创建菜单栏错误:", e);
		} finally {
		}

	}

	public JMenuBar getMb() {
		return mb;
	}
	// public SMenu getMMsg() {
	// return mMsg;
	// }
	//
	// public SMenu getMSys() {
	// return mSys;
	// }
	//
	// public SMenu getMHelp() {
	// return mHelp;
	// }
	//
	// public SMenu getMTool() {
	// return mTool;
	// }
	//
	// public SMenuItem getMiFtp() {
	// return miFtp;
	// }
	//
	// public SMenuItem getMiMail() {
	// return miMail;
	// }
	//
	// public SMenuItem getMiMonitor() {
	// return miMonitor;
	// }
	//
	// public SMenuItem getMiMonitorGroup() {
	// return miMonitorGroup;
	// }
	//
	// public SMenuItem getMiSettings() {
	// return miSettings;
	// }
	//
	// public SMenuItem getMiSys() {
	// return miSys;
	// }
	//
	// public SMenuItem getMiDbConn() {
	// return miDbConn;
	// }

}
