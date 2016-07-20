package com.relate.indicatorSymbol;

import java.sql.Connection;


import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import consts.Const;

/**
 * @Description:
 * @date Jul 4, 2014
 * @author:fgq
 */
public class RelateIndicatorSysmbolDao {
    private static RelateIndicatorSysmbolDao dao = null;
    private Connection con;

    public static RelateIndicatorSysmbolDao getInstance() {
	if (dao == null)
	    dao = new RelateIndicatorSysmbolDao();
	return dao;
    }

    // 构造
    private RelateIndicatorSysmbolDao() {
	this.con = UtilJDBCManager.getConnection(Const.DbName);
    }

    public boolean isExist(String indicatorId, String symbolId) throws Exception {
	String sql = "select count(1) from relate_indicator_symbol where indicatorId=? and symbolId=? ";
	return UtilSql.isExist(con, sql, new Object[] { indicatorId, symbolId });
    }

    public void addRelation(String indicatorId, String symbolId) throws Exception {
	String sql = "insert into relate_indicator_symbol(indicatorId,symbolId)values(?,?)";
	UtilSql.executeUpdate(con, sql, new Object[] { indicatorId, symbolId });
    }
}
