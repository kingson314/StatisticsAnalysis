package com.app;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.sql.Connection;

import javax.swing.JProgressBar;
import javax.swing.JWindow;

import app.AppCon;
import app.AppConfig;
import app.AppLookAndFeel;

import common.component.SLabel;
import common.component.SSplitPane;
import common.component.ShowMsg;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.log.UtilLog;
import common.util.string.UtilString;

import consts.ImageContext;

/**
 * @info 程序启动
 * 
 * @author fgq 20120831
 * 
 */
public class AppBoot extends JWindow {
	private static final long serialVersionUID = 1L;
	private SSplitPane splt;
	private JProgressBar pbBoot;
	private SLabel lImag;
	private Thread thread;
	private final int[] pgValue = new int[] { 10, 20, 30, 90, 100 };
	private final String[] pgMsg = new String[] { "程序自动检测更新", "程序初始化", "程序获取运行时日期", "程序初始化界面", "程序启动成功" };
	public static int hadNowDate;
	public static Connection FxCon;

	private static AppBoot dialogBoot = null;

	// private static long bootTime;

	public static AppBoot getInstance() {
		if (dialogBoot == null)
			dialogBoot = new AppBoot();
		return dialogBoot;
	}

	// 构造
	private AppBoot() {
		try {
			initGUI();
			String appLookAndFell = UtilString.isNil(AppConfig.getInstance().getMapAppConfig().get("appLookAndFeel"), "默认风格");
			if (!"默认风格".equals(appLookAndFell)) {
				AppLookAndFeel.getInstance().updateLookAndFeel(appLookAndFell);
			}
		} catch (Exception e) {
			UtilLog.logError("启动对话框构造错误:", e);
		}
	}

	// 设置进度条信息
	private void setprogressAndMsg(int index) {
		pbBoot.setValue(pgValue[index]);
		pbBoot.setString(pgMsg[index]);
	}

	// 设置延时进度条
	@SuppressWarnings("unused")
	private void setprogress() {
		int value = pbBoot.getValue() + 4;
		if (value <= 90) {
			pbBoot.setValue(value);
		}
	}

	// 启动系统线程
	public void execBootThread() {
		try {
			// bootTime = System.currentTimeMillis();
			this.thread = new Thread() {
				public void run() {
					try {
						dialogBoot.setprogressAndMsg(0);
						try {
							new AppCon();// 获取App连接
						} catch (Exception e) {// 系统数据库连接错误时处理
							try {
								dialogBoot.setprogressAndMsg(1);
								AppMain.appMain = new AppMain();
								ShowMsg.showMsg("系统数据库错误:\n  " + e.getMessage());
								dialogBoot.dispose();
								AppMain.appMain.setVisible(true);
							} catch (Exception e1) {
							}
							return;
						}
						dialogBoot.setprogressAndMsg(1);
						AppFun.getInstance().initParam();

						if (AppFun.getInstance().isRunning(// 已有运行实例时退出本次运行
								AppConfig.getInstance().getMapAppConfig().get("appTitle"))) {
							pbBoot.setValue(0);
							ShowMsg.showWarn(dialogBoot, AppConfig.getInstance().getMapAppConfig().get("appTitle") + " 正在运行中...");
							dialogBoot.dispose();
							System.exit(0);
						}
						dialogBoot.setprogressAndMsg(3);
						AppMain.appMain = new AppMain();
						dialogBoot.setprogressAndMsg(4);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						dialogBoot.dispose();
						AppMain.appMain.setVisible(true);
						tryConn();
					} catch (Exception e) {
						ShowMsg.showError("启动错误:" + e.getMessage());
						dialogBoot.dispose();
						System.exit(0);
					}
				}
			};
			this.thread.start();
		} catch (Exception e) {
			ShowMsg.showError("启动错误:" + e.getMessage());
			this.dispose();
			System.exit(0);
		}
	}

	// 初始化界面
	private void initGUI() {
		try {
			this.setSize(530, 340);

			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
			this.setLocation(w, h);

			{
				splt = new SSplitPane(0, this.getHeight() - 25, false);
				splt.setDividerSize(1);
				getContentPane().add(splt, BorderLayout.CENTER);
				{
					lImag = new SLabel("", ImageContext.SystemBootUI);
					splt.add(lImag, SSplitPane.TOP);
				}
				{
					pbBoot = new JProgressBar();
					pbBoot.setValue(0);
					pbBoot.setString(pgMsg[0]);
					pbBoot.setDoubleBuffered(true);
					pbBoot.setBorder(null);
					pbBoot.setForeground(new java.awt.Color(17, 106, 196));
					pbBoot.setStringPainted(true);
				}
				splt.add(pbBoot, SSplitPane.BOTTOM);
				this.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			UtilLog.logError("启动对话框初始化界面错误:", e);
		}
	}

	// 先连接一下，免的登录用时等待
	public void tryConn() throws Exception {
		String sql = "select count(1) from settings ";
		Connection con = UtilJDBCManager.getConnection(AppCon.DbconApp);
		UtilSql.queryForCount(con, sql, new Object[0]);
	}

	// 强制退出启动
	@SuppressWarnings("unused")
	private void close() {
		int msg = ShowMsg.showConfig(this, "是否强制退出程序?");
		if (msg == 0) {
			try {
				this.thread.interrupt();
			} catch (Exception e) {
				UtilLog.logError("程序强制退出错误:", e);
			} finally {
				this.dispose();
				System.exit(0);
			}
		}
	}
}
