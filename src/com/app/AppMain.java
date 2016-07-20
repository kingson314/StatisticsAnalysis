package com.app;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import app.AppConfig;
import app.AppStatus;
import app.AppToolBar;
import app.AppTree;
import common.component.SSplitPane;
import common.component.STabbedPane;

import consts.ImageContext;

/**
 * @info 主程序
 * 
 * @author fgq 20120831
 * 
 */
public class AppMain extends JFrame {
	private static final long serialVersionUID = 20111030;
	private SSplitPane spltView;
	private SSplitPane spltMain;
	private STabbedPane tpNavigate;
	private AppTree appTree;
	private AppTableView tabView;
	public static AppMain appMain;

	// 构造函数，执行参数初始化、界面初始化
	public AppMain() {
		try {
			AppFun.getInstance().setGlobalShortCut();
			this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
			int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
			int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
			this.setLocation(w, h);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage(ImageContext.Sys));
			this.setLayout(new BorderLayout());
			this.setTitle(AppConfig.getInstance().getMapAppConfig().get("appTitle"));
			this.setSize(1200, 800);
			this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			// 添加窗体退出事件
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent evt) {
					AppFun.getInstance().systemClosing();
				}
			});
			// 菜单
			this.setJMenuBar(AppMenu.getInstance().getMb());
			// 主界面分割窗体
			spltMain = new SSplitPane(0, 30, false);
			spltMain.setEnabled(false);
			{
				// 工具栏
				{
					spltMain.add(AppToolBar.getInstance().getToolBar(), SSplitPane.TOP);
				}
				// 视图
				{
					spltView = new SSplitPane(1, 230, true);
					// // 导航Jtree
					{
						tpNavigate = new STabbedPane(new String[] { "导航" });
						spltView.add(tpNavigate, SSplitPane.LEFT);
						appTree = AppTree.getInstance();
						tpNavigate.addTab("导航", ImageContext.TabTask, appTree.getTreeView(), "导航", false);

						tpNavigate.addMouseListener(new MouseListener() {
							public void mouseClicked(MouseEvent evt) {
								if ("导航".equals(tpNavigate.getTitleAt(tpNavigate.getSelectedIndex()))) {
									tabView.addTab("主页");
								}
							}

							public void mouseEntered(MouseEvent e) {
							}

							public void mouseExited(MouseEvent e) {
							}

							public void mousePressed(MouseEvent e) {
							}

							public void mouseReleased(MouseEvent e) {
							}
						});
					}
					{
						tabView = AppTableView.getInstance();
						spltView.add(tabView.getTab(), SSplitPane.RIGHT);
						{
							tabView.addTab("主页");
						}
					}
				}
				spltMain.add(spltView, SSplitPane.BOTTOM);
			}
			this.add(spltMain, BorderLayout.CENTER);
			// 状态栏
			this.add(AppStatus.getInstance().getStatusbar(), BorderLayout.PAGE_END);
			this.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	public STabbedPane getTpNavigate() {
		return tpNavigate;
	}

	public AppTree getAppTree() {
		return appTree;
	}
}
