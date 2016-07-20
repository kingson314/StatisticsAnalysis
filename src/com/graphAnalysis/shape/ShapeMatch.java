package com.graphAnalysis.shape;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import common.component.SButton;
import common.component.SCalendar;
import common.component.SComboBox;
import common.component.SLabel;
import common.component.SSplitPane;
import common.component.STextField;
//import common.util.array.UtilArray;
//import common.util.bean.KeyValue;
import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.string.UtilString;
import config.dictionary.DictionaryDao;
import consts.Const;
import consts.ImageContext;

public class ShapeMatch {
	private JSplitPane splt;
	private SLabel lType;
	private SComboBox cmbType;
	private SLabel lQueryTime;
	private SComboBox cmbQueryTime;
	private SLabel lBegDate;
	private STextField txtBegDate;
	private SButton btnBegDate;
	private SLabel lEndDate;
	private STextField txtEndDate;
	private SButton btnEndDate;
	private SButton btnQuery;
	private JScrollPane scrlTabel;
	private SLabel lSymbol;
	private SComboBox cmbSymbol;
	private static ShapeMatch shapeMatch = null;

	public static ShapeMatch getInstance() {
		if (shapeMatch == null)
			shapeMatch = new ShapeMatch();
		return shapeMatch;
	}

	private ShapeMatch() {
		splt = new SSplitPane(0, 150, false);
		splt.setPreferredSize(new java.awt.Dimension(610, 325));

		JPanel pnlQuery = new JPanel();
		pnlQuery.setLayout(null);
		{
			lBegDate = new SLabel("开始日期");
			lBegDate.setBounds(5, 12, 60, 17);
			pnlQuery.add(lBegDate);
		}
		{
			txtBegDate = new STextField();
			txtBegDate.setText(UtilConver.dateToStr(Const.fm_yyyyMMdd));
			txtBegDate.setBounds(60, 6, 70, 24);
			pnlQuery.add(txtBegDate);
		}
		{
			btnBegDate = new SButton("..");
			btnBegDate.setBounds(130, 6, 26, 24);
			btnBegDate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					new SCalendar(txtBegDate);
				}
			});
			pnlQuery.add(btnBegDate);
		}
		{
			lEndDate = new SLabel("结束日期");
			lEndDate.setBounds(5, 37, 60, 17);
			pnlQuery.add(lEndDate);
		}

		{
			txtEndDate = new STextField();
			txtEndDate.setText(UtilConver.dateToStr(Const.fm_yyyyMMdd));
			txtEndDate.setBounds(60, 31, 70, 24);
			pnlQuery.add(txtEndDate);
		}
		{
			btnEndDate = new SButton("..");
			btnEndDate.setBounds(130, 31, 26, 24);
			btnEndDate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					new SCalendar(txtEndDate);
				}
			});
			pnlQuery.add(btnEndDate);
		}
		{
			btnQuery = new SButton("<html>查<br>询</html>", ImageContext.Query); //
			btnQuery.setBounds(160, 6, 50, 50);
			btnQuery.setMargin(new Insets(1, 1, 1, 1));
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					query("");
				}
			});
			pnlQuery.add(btnQuery);
		}

		{
			lSymbol = new SLabel("货币名称");
			pnlQuery.add(lSymbol);
			lSymbol.setBounds(5, 67, 60, 17);
		}
		{
			cmbSymbol = new SComboBox(Const.SymbolArr);
			pnlQuery.add(cmbSymbol);
			cmbSymbol.setSelectedIndex(0);
			cmbSymbol.setBounds(60, 61, 150, 24);
		}

		{
			lQueryTime = new SLabel("查询时段");
			pnlQuery.add(lQueryTime);
			lQueryTime.setBounds(5, 97, 60, 17);
		}
		{
			cmbQueryTime = new SComboBox(DictionaryDao.getInstance().getDicionary("查询时段"));
			pnlQuery.add(cmbQueryTime);
			cmbQueryTime.setSelectedIndex(0);
			cmbQueryTime.setBounds(60, 91, 150, 24);
		}
		{
			lType = new SLabel("形态类型");
			lType.setBounds(5, 127, 60, 17);
			pnlQuery.add(lType);
		}
		{
			cmbType = new SComboBox(DictionaryDao.getInstance().getDicionary("形态分类"));
			cmbType.setBounds(60, 121, 150, 24);
			pnlQuery.add(cmbType);
		}
		splt.add(pnlQuery, JSplitPane.TOP);
		{
			scrlTabel = new JScrollPane();
		}
		splt.add(scrlTabel, JSplitPane.BOTTOM);

	}

	private void query(String test) {
		Connection con = UtilJDBCManager.getConnection(Const.DbName);
		String begDate = UtilString.isNil(txtBegDate.getText());
		String endDate = UtilString.isNil(txtEndDate.getText());
		String sql = "select min(a.id) id, a.Datelocal, min(TIMELOCAL) TIMELOCAL, '' shapeType " + "   from price_" + cmbSymbol.getSelectedItem().toString().trim() + "60 a "
				+ "  where DATELOCAL >= '" + begDate + "' " + "   and DATELOCAL <='" + endDate + "'" + "  group by Datelocal " 
//				+ " union all "
//				+ " select max(a.id) id, a.Datelocal, max(TIMELOCAL) TIMELOCAL, '' shapeType " + "  from price_" + cmbSymbol.getSelectedItem().toString().trim() + "60 a "
//				+ "  where DATELOCAL >='" + begDate + "' " + "   and DATELOCAL <= '" + endDate + "'" + "  group by Datelocal " 
				+ "  order by DATELOCAL asc, TIMELOCAL asc";
		System.out.println(sql);
		JTable table = new ShapeTable(con, sql, cmbSymbol.getSelectedItem().toString().trim()).getJtable();
		scrlTabel.setViewportView(table);

	}

//	private void query() {
//		Connection con = UtilJDBCManager.getConnection(Const.DbName);
//		String type = ((KeyValue) cmbType.getSelectedItem()).getName().toString();
//		String begDate = UtilString.isNil(txtBegDate.getText());
//		String endDate = UtilString.isNil(txtEndDate.getText());
//		String sql = "";
//		String[][] queryTimes = UtilArray.getArray2(cmbQueryTime.getSelectedItem().toString());
//		String beginSql = "select a.*,b.value as shapeType  from price_" + cmbSymbol.getSelectedItem().toString().trim() + "60 a,config_dictionary b  where  a.type=b.name(+) and "
//				+ Const.DateType + ">='" + begDate + "' and " + Const.DateType + " <='" + endDate + "'";
//		String typeSql = "";
//		if ("0".equals(type)) {
//			typeSql = "";
//		} else {
//			typeSql = " and type in(" + type + ") ";
//		}
//		String queryTimesSql = "";
//		if (queryTimes != null) {
//			queryTimesSql = " and (  ";
//			for (int i = 0; i < queryTimes.length; i++) {
//				if (queryTimes[i].length != 2)
//					continue;// 要求查询时段必须成对出现
//				if (i == queryTimes.length - 1)
//					queryTimesSql = queryTimesSql + "(" + Const.TimeType + ">='" + queryTimes[i][0] + "00" + "' and " + Const.TimeType + "<='" + queryTimes[i][1] + "00" + "')";
//				else {
//					queryTimesSql = queryTimesSql + "(" + Const.TimeType + ">='" + queryTimes[i][0] + "00" + "' and " + Const.TimeType + "<='" + queryTimes[i][1] + "00" + "') or ";
//				}
//			}
//			queryTimesSql = queryTimesSql + ")";
//		}
//		String orderSql = " order by " + Const.DateType + " asc, " + Const.TimeType + " asc ";
//		sql = beginSql + typeSql + queryTimesSql + orderSql;
//		System.out.println(sql);
//		JTable table = new ShapeTable(con, sql, cmbSymbol.getSelectedItem().toString().trim()).getJtable();
//		scrlTabel.setViewportView(table);
//	}

	public JSplitPane getSplt() {
		return splt;
	}
}
