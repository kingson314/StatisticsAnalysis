package com.app;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import module.about.DialogAbout;
import module.dbconnection.DbConnectionDialog;
import app.AppConfig;
import com.config.account.AccountDialog;
import com.config.dictionary.DictionaryDialog;
import com.config.symbol.SymbolDialog;
import com.graphAnalysis.shape.ShapeMatch;
import com.trade.placeOrder.PlaceOrderDialog;
import common.component.DoManager;
import common.component.ShortcutManager;
import common.component.ShowMsg;
import common.util.array.UtilArray;
import common.util.log.UtilLog;
import consts.Const;
import consts.ImageContext;

/**
 * @info 程序级公共函数
 * 
 * @author fgq 20120831
 * 
 */
public class AppFun {
	private static AppFun appFun = null;

	public static AppFun getInstance() {
		if (appFun == null)
			appFun = new AppFun();
		return appFun;
	}

	// 初始化参数、全局变量
	public void initParam() {
		try {
			File excelDir = new File(Const.TEMP_DIR);
			if (!excelDir.exists())
				excelDir.mkdir();
		} catch (Exception e) {
			UtilLog.logError("主程序初始化参数信息错误:", e);
		} finally {
		}
	}

	// 数据库连接对话框

	@SuppressWarnings("deprecation")
	public void dbConnDialog() {
		try {
			DbConnectionDialog connDialog = new DbConnectionDialog();
			connDialog.show(true);
		} catch (Exception e) {
			UtilLog.logError("主程序打开数据源配置对话框错误:", e);
		}
	}

	// about对话框
	@SuppressWarnings("deprecation")
	public void aboutDialog() {
		try {
			DialogAbout aboutDialog = new DialogAbout();
			aboutDialog.show(true);
		} catch (Exception e) {
			UtilLog.logError("主程序打开关于对话框错误:", e);
		}
	}

	// 主程序关闭事件
	public void systemClosing() {
		int msg = ShowMsg.showConfig("确定退出程序? ");
		if (msg == 0) {
			AppExit.getInstance().execExitThread();
		}
	}

	// 程序锁屏事件
	public void systemLock() {
		if (AppMain.appMain.isVisible()) {
			AppMain.appMain.setVisible(false);
			StatisticsAnalysis appLogin = new StatisticsAnalysis("锁屏");
			appLogin.setTitle(AppMain.appMain.getTitle());
			appLogin.setVisible(true);
		}
	}

	// 判断程序是否运行
	public boolean isRunning(String applicationName) {
		boolean rv = false;
		try {
			String path = System.getProperty("user.dir") + "\\temp";
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			RandomAccessFile fis = new RandomAccessFile(path + "\\" + applicationName + ".lock", "rw");
			FileChannel lockfc = fis.getChannel();
			FileLock flock = lockfc.tryLock();
			if (flock == null) {
				rv = true;
			}
		} catch (Exception e) {
		}
		return rv;
	}

	// 设置全局快捷键
	public void setGlobalShortCut() {
		try {
			boolean isRight = true;
			{
				// undo快捷键
				String appUnDoShortKey = AppConfig.getInstance().getMapAppConfig().get("appUnDoShortKey");
				String[] appUnDoShortKeyArr = appUnDoShortKey.split("\\+");
				int[] unDoShortCutKeyArr = new int[appUnDoShortKeyArr.length];
				for (int i = 0; i < appUnDoShortKeyArr.length; i++) {
					int index = UtilArray.getArrayIndex(ShortcutManager.ShortCut, appUnDoShortKeyArr[i]);
					// 如果有一个设错了，则不设置快捷键
					if (index >= 0) {
						unDoShortCutKeyArr[i] = ShortcutManager.ShortCutKey[index];
					} else {
						isRight = false;
						break;
					}
				}
				if (isRight) {
					ShortcutManager.getInstance().addShortcutListener(new ShortcutManager.ShortcutListener() {
						public void handle() {
							DoManager.getInstance().unDo();
						}
					}, unDoShortCutKeyArr);
				}
			}
			isRight = true;
			{
				// redo快捷键
				String appReDoShortKey = AppConfig.getInstance().getMapAppConfig().get("appReDoShortKey");
				String[] appReDoShortKeyArr = appReDoShortKey.split("\\+");
				int[] reDoShortCutKeyArr = new int[appReDoShortKeyArr.length];
				for (int i = 0; i < appReDoShortKeyArr.length; i++) {
					int index = UtilArray.getArrayIndex(ShortcutManager.ShortCut, appReDoShortKeyArr[i]);
					// 如果有一个设错了，则不设置快捷键
					if (index >= 0) {
						reDoShortCutKeyArr[i] = ShortcutManager.ShortCutKey[index];
					} else {
						isRight = false;
						break;
					}
				}
				if (isRight) {
					ShortcutManager.getInstance().addShortcutListener(new ShortcutManager.ShortcutListener() {
						public void handle() {
							DoManager.getInstance().reDo();
						}
					}, reDoShortCutKeyArr);
				}
			}
			isRight = true;
			{
				// 设置锁屏快捷键
				String appLockShortKey = AppConfig.getInstance().getMapAppConfig().get("appLockShortKey");
				String[] appLockShortKeyArr = appLockShortKey.split("\\+");
				int[] lockShortCutKeyArr = new int[appLockShortKeyArr.length];
				for (int i = 0; i < appLockShortKeyArr.length; i++) {
					int index = UtilArray.getArrayIndex(ShortcutManager.ShortCut, appLockShortKeyArr[i]);
					// 如果有一个设错了，则不设置快捷键
					if (index >= 0) {
						lockShortCutKeyArr[i] = ShortcutManager.ShortCutKey[index];
					} else {
						isRight = false;
						break;
					}
				}
				if (isRight) {
					ShortcutManager.getInstance().addShortcutListener(new ShortcutManager.ShortcutListener() {
						public void handle() {
							systemLock();
						}
					}, lockShortCutKeyArr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 显示形态对比图列表
	public void showShapeMatch() {
		AppMain.appMain.getTpNavigate().addTab("形态对照", ImageContext.TabTask, ShapeMatch.getInstance().getSplt(), "形态对照", false);
		AppMain.appMain.getTpNavigate().setSelected("形态对照");
		AppMain.appMain.getAppTree().getTree().setSelectionRow(0);
	}

	/**
	 * @fun ：账户信息窗体
	 * @param:
	 * @date ：2013-01-13
	 */
	@SuppressWarnings("deprecation")
	public void acccountDialog() {
		try {
			AccountDialog accountDialog = new AccountDialog();
			accountDialog.show(true);
		} catch (Exception e) {
			UtilLog.logError("主程序打开账户信息对话框错误:", e);
		}
	}

	/**
	 * @fun ：货币信息窗体
	 * @param:
	 * @date ：2013-01-13
	 */
	@SuppressWarnings("deprecation")
	public void symbolDialog() {
		try {
			SymbolDialog symbolDialog = new SymbolDialog();
			symbolDialog.show(true);
		} catch (Exception e) {
			UtilLog.logError("主程序打开货币信息对话框错误:", e);
		}
	}

	/**
	 * @fun ：字典信息窗体
	 * @param:
	 * @date ：2013-01-19
	 */
	@SuppressWarnings("deprecation")
	public void dictionaryDialog() {
		try {
			DictionaryDialog dialog = new DictionaryDialog();
			dialog.show(true);
		} catch (Exception e) {
			UtilLog.logError("主程序打开字典信息对话框错误:", e);
		}
	}

	/**
	 * @fun ：下单窗体
	 * @param:
	 * @date ：2013-01-22
	 */
	@SuppressWarnings("deprecation")
	public void placeOrderDialog() {
		try {
			PlaceOrderDialog dialog = new PlaceOrderDialog();
			dialog.show(true);
		} catch (Exception e) {
			UtilLog.logError("主程序打开下单对话框错误:", e);
		}
	}
}
