package com.config.dictionary;

import java.util.Vector;

import javax.swing.JTable;


import common.component.STable;
import common.component.STableBean;
import common.util.conver.UtilConver;
import common.util.log.UtilLog;
import config.dictionary.DictionaryDao;

public class DictionaryTable {
    private JTable jtable;

    // 构造
    public DictionaryTable(String group, String code) {
	try {
	    String[] arr = new String[] { "序列号", "字典组名称", "字典名称", "字典值", "字典顺序号", "字典状态", "字典备注" };
	    Vector<String> columnName = UtilConver.arrayToVector(arr);
	    Vector<?> tableValue = DictionaryDao.getInstance().getVector(group, code);
	    int[] cellEditableColumn = null;
	    int[] columnWidth = new int[] { 50, 100, 100, 100, 100, 100, 200 };
	    int[] columnHide = { 0 };
	    boolean isChenckHeader = false;
	    boolean isReorderingAllowed = false;
	    boolean isResizingAllowed = true;
	    STableBean bean = new STableBean(columnName, tableValue, cellEditableColumn, columnWidth, columnHide, isChenckHeader, isReorderingAllowed, isResizingAllowed);
	    STable table = new STable(bean);
	    jtable = table.getJtable();
	} catch (Exception e) {
	    UtilLog.logError("信息列表构造错误:", e);
	} finally {
	}
    }

    // 获取信息表格
    public JTable getJtable() {
	return jtable;
    }

}
