package test.swjoa;

import java.sql.Connection;
import common.util.conver.UtilConver;
import common.util.jdbc.UtilJDBCManager;
import common.util.jdbc.UtilSql;
import common.util.string.UtilString;

import consts.Const;

/**
 * @Description:
 * @date Jul 28, 2014
 * @author:fgq
 */
public class Upgrade {
    private static String[] schemaArr = new String[] { "swjoa", "gzswjoa", "mzswjoa", "stswjoa", "jmswjoa", "sgswjoa", "fsswjoa", "zqswjoa", "hzswjoa", "zjswjoa", "mmswjoa" };
    private static String[] unitsArr = new String[] { "省局", "广州分局", "梅州分局", "汕头分局", "江门分局", "韶关分局", "佛山分局", "肇庆分局", "惠州分局", "湛江分局", "茂名分局" };

    public static String[] getTableNameArr(Connection con, String filter) {
	String sql = "select table_name TN from user_tables  where  table_name  like '" + filter.toUpperCase() + "%' order by table_name";
	return UtilSql.executeSql(con, sql, "TN");
    }

    public static int exp(String userName, String password, String dbName, String filePath, String[] ownerArr, String[] tableArr) throws Exception {
	String cmd = getExpCmd(userName, password, dbName, filePath, ownerArr, tableArr);
	return Runtime.getRuntime().exec(cmd).waitFor();
    }

    private static String getExpCmd(String userName, String password, String dbName, String filePath, String[] ownerArr, String[] tableArr) throws Exception {
	String cmd = "exp " + userName + "/" + password + "@" + dbName + " file=" + filePath;
	String owner = "";
	if (ownerArr != null) {
	    for (int i = 0; i < ownerArr.length; i++) {
		if (i == 0) {
		    owner = ownerArr[i];
		} else {
		    owner = owner + "," + ownerArr[i];
		}
	    }
	}
	if (!"".equals(owner)) {
	    cmd += " owner=(" + owner + ")";
	} else {
	    String table = "";
	    if (tableArr != null) {
		for (int i = 0; i < tableArr.length; i++) {
		    if (i == 0) {
			table = tableArr[i];
		    } else {
			table = table + "," + tableArr[i];
		    }
		}
		cmd += " tables=(" + table.toLowerCase() + ")";
	    }
	}
	return cmd;
    }

    public static int imp(String userName, String password, String dbName, String filePath, String fromUser, String toUser) throws Exception {
	String cmd = getImpCmd(userName, password, dbName, filePath, fromUser, toUser);
	return Runtime.getRuntime().exec(cmd).waitFor();
    }

    private static String getImpCmd(String userName, String password, String dbName, String filePath, String fromUser, String toUser) throws Exception {
	String cmd = "imp " + userName + "/" + password + "@" + dbName + " file=" + filePath;
	if (!"".equals(UtilString.isNil(fromUser))) {
	    cmd += " fromuser=" + fromUser;
	}
	if (!"".equals(UtilString.isNil(toUser))) {
	    cmd += " touser=" + toUser;
	}
	cmd += " ignore=y ";
	return cmd;
    }

    private static void getMenuSql(StringBuilder sbSql) {
	sbSql.append("--备份菜单").append("\n");
	sbSql.append("create table bak_pub_sysmenu as select * from pub_sysmenu;").append("\n");
	sbSql.append("--	修改pub_sysmenu表").append("\n");
	sbSql.append("-- Add/modify columns").append("\n");
	sbSql.append("alter table PUB_SYSMENU add appid VARCHAR2(40);").append("\n");
	sbSql.append("-- Add comments to the columns").append("\n");
	sbSql.append("comment on column PUB_SYSMENU.appid ").append("\n");
	sbSql.append("  is 'pub_application.app_id';").append("\n");
	sbSql.append("alter table PUB_SYSMENU ").append("\n");
	sbSql.append("  drop constraint PK_PUB_SYSMENU cascade; ").append("\n");
	sbSql.append("  -- Drop indexes  ").append("\n");
	sbSql.append("drop index PK_PUB_SYSMENU; ").append("\n");
	sbSql.append("  --更新广东省水文局菜单appid   ").append("\n");
	sbSql.append("update pub_sysmenu set appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';").append("\n");
	sbSql.append("alter table PUB_SYSMENU ").append("\n");
	sbSql.append("  add constraint PK_PUB_SYSMENU1 primary key (MENUID, APPID); ").append("\n");
	sbSql.append("--2	修改视图v_pub_sysmenu ").append("\n");
	sbSql.append("create or replace view v_pub_sysmenu as ").append("\n");
	sbSql.append("select t.MENUID,t.MENUNAME,t.PARENTID,t.URL,t.COUNTURL,t.target,t.SORTID,t.ICON,t.MLEVEL,t.PROTECTIVE,appid  from PUB_SYSMENU t ; ").append("\n");
	sbSql.append("").append("\n");
	sbSql.append("--增加动态表单相关的菜单 ").append("\n");
	sbSql.append("insert into pub_sysmenu(appid,menuName,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl) ").append("\n");
	sbSql.append("values('CC3B93FB-0841-4037-96F7-D63C0886C3F6','动态表单','','101','',2,0,'790e18e3-f29c-4e81-be9d-c1282a8d7954','3ACDD7C7-EEFE-4A3D-8292-BB6BAE2265EF','homepage',''); ")
		.append("\n");
	sbSql.append("insert into pub_sysmenu(appid,menuName,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl) ").append("\n");
	sbSql
		.append(
			"values('CC3B93FB-0841-4037-96F7-D63C0886C3F6','模块列表','/system/dynform/module/treelist.action','0','',3,0,'4e86d55a-c087-48d5-bd41-98f154ab654f','790e18e3-f29c-4e81-be9d-c1282a8d7954','homepage',''); ")
		.append("\n");
	sbSql.append("--删除多余的菜单 ").append("\n");
	sbSql.append("delete from pub_sysmenu where menuid='AE6AEED5-9677-4DD3-B6CA-753FF182E83C'; ").append("\n");
	sbSql.append("delete from pub_sysmenu where parentid='AE6AEED5-9677-4DD3-B6CA-753FF182E83C';").append("\n");
	sbSql.append("").append("\n");
	sbSql.append("--插入广州水文局菜单").append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '886F5976-E250-470F-9C40-3AB4693ECCB2'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入梅州水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, 'BCDF4FC8-4752-4C45-ADC0-3ECC6D97525C'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入汕头水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '88AB4805-69D5-41E3-853F-1455286C8355'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入江门水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '5B7CE37F-97FA-495C-91CC-5C9EE1E30574'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入韶关水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '34F44FA4-9B85-405E-82ED-53741EAB7861'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入佛山水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '93B4C3B8-5B40-42DF-B3BB-2374688999EE'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入肇庆水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '2227B61B-E72E-47CB-B052-91996A638A3B'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入惠州水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '6dad5bb5-f0fe-4c18-8760-19de78770c64'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入湛江水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '36d04f02-0307-40cc-9d9b-c101373ecfc5'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
	sbSql
		.append(
			" --插入茂名水文分局菜单                                                                                                                                                                                                     ;")
		.append("\n");
	sbSql
		.append(
			"insert into pub_sysmenu  select menuname,url,sortid,icon,mlevel,protective,menuid,parentid,target,counturl, '41edb16a-6f9f-404f-8ecc-3d51e23b0497'appid  from pub_sysmenu where appid='CC3B93FB-0841-4037-96F7-D63C0886C3F6';")
		.append("\n");
    }

    private static void getSqlYK(StringBuilder sbSql) {
	sbSql.append("---------------------- 动态表单数据表添加相关字段    YK -------------------------                                                   ".trim()).append("\n");
	sbSql.append("-------------------------------------------------------------------------------------------------                                   ".trim()).append("\n");
	sbSql.append("----pub_flow表中新增字段：是否为动态表单设计流程的标示：若dynFlag有值，即formid，当前流程为动态设计表单流程;否则为非动态设计表单流程".trim()).append("\n");
	sbSql.append("alter table swjoa.pub_flow add(dynFlag VARCHAR2(100));                                                                              ".trim()).append("\n");
	sbSql.append("--流程节点表：添加当前节点发送按钮是否弹出流程处理框的标示                                                                          ".trim()).append("\n");
	sbSql.append("alter table pub_defineflow_activity add(isAlert number(2) default 0);                                                               ".trim()).append("\n");
	sbSql.append("--流程下一节点表：添加流程发送到下一节点的选项是否选择用户的标示                                                                    ".trim()).append("\n");
	sbSql.append("alter table pub_defineflow_activity_next add(isAlert number(2) default 0) ;                                                         ".trim()).append("\n");
	sbSql.append("--流程节点表：添加当前节点发送按钮是否填写必须意见                                                                                  ".trim()).append("\n");
	sbSql.append("alter table pub_defineflow_activity add(isNullMind number(2) default 0);                                                            ".trim()).append("\n");
	sbSql.append("--流程下一节点表：添加节点处理模式                                                                                                  ".trim()).append("\n");
	sbSql.append("alter table pub_defineflow_activity_next add(stepModel number(2) default 0);                                                        ".trim()).append("\n");
	sbSql.append("--流程节点表：添加是否是结束节点标示                                                                                                ".trim()).append("\n");
	sbSql.append("alter table pub_defineflow_activity add(isend number(2) default 0);                                                                 ".trim()).append("\n");
	sbSql.append("--流程下一节点表：添加是否补发的标示                                                                                                ".trim()).append("\n");
	sbSql.append("alter table pub_defineflow_activity_next add(isreissue number(2) default 0);                                                        ".trim()).append("\n");
	
	for (int i = 1; i < schemaArr.length; i++) {
	    sbSql.append("--" + unitsArr[i]).append("\n");
	    sbSql.append("alter table "+schemaArr[i]+".GWGL_SEND_DOCUMENT modify sfsc default('0');       ").append("\n");  
	    sbSql.append("alter table "+schemaArr[i]+".GWGL_SEND_DOCUMENT modify sfgd default('0');       ").append("\n");
	    sbSql.append("alter table "+schemaArr[i]+".GWGL_SEND_DOCUMENT modify DIFFERENCE default('10');").append("\n");
	    sbSql.append("alter table "+schemaArr[i]+".GWGL_SEND_DOCUMENT modify sfsc default('0');               ").append("\n");
	}
	
    }

    private static void getSqlHHP(StringBuilder sbSql){
	sbSql.append("\n\n--2014-07-25 hhp                                                      ".trim()).append("\n");
	sbSql.append("  -- Add/add columns                                                    ".trim()).append("\n");
	sbSql.append("alter table pub_defineflow_activity add isExchange number(2) default 0 ;".trim()).append("\n");
	sbSql.append("comment on column pub_defineflow_activity.isExchange                    ".trim()).append("\n");
	sbSql.append("  is '是否显示主送机关、抄送机关';                                      ".trim()).append("\n");
	
	for (int i = 0; i < schemaArr.length; i++) {
	    sbSql.append("--" + unitsArr[i]).append("\n");
	    sbSql.append("  --2014-07-28 hhp                                                               ".trim()).append("\n");
	    sbSql.append("  -- Add/add columns                                                             ".trim()).append("\n");
	    sbSql.append("alter table "+schemaArr[i]+".DYN_DEFINE_OFFICES_PERMISSIONS add isOfficeEdit varchar2(50);".trim()).append("\n");
	    sbSql.append("comment on column "+schemaArr[i]+".DYN_DEFINE_OFFICES_PERMISSIONS.isOfficeEdit            ".trim()).append("\n");
	    sbSql.append("  is '是否可有操作权限';                                                         ".trim()).append("\n");
	}
    }
    
    private  static void getOtherSql(StringBuilder sbSql){
	sbSql.append("----20140818  修改操作日志相关SQL------------\n");
	sbSql.append("-- Add/modify columns                              \n");
	sbSql.append("alter table PUB_OPER_LOG add module VARCHAR2(4000);\n");
	sbSql.append("-- Add comments to the columns                     \n");
	sbSql.append("comment on column PUB_OPER_LOG.module              \n");
	sbSql.append("  is '功能模块';                                   \n");
	
	sbSql.append("update  t_form set fname =replace(fname,'gz_','分局'); \n");
	sbSql.append("update  t_form set fname =replace(fname,'gz','分局');  \n");
	sbSql.append("update  t_form set fname =replace(fname,'广州','分局');\n");
	
    }
    public static void main(String[] args) throws Exception {
	StringBuilder sbSql = new StringBuilder();
	getSqlYK(sbSql);
	
	sbSql.append("\n\n--fgq新增脚本\n");
	getMenuSql(sbSql);

	String userName = "swjoa";
	String password = "swjoa";
	String dbName_exp = "217";
	String filePath = "c:/swjoa" + dbName_exp + "_" + UtilConver.dateToStr(Const.fm_yyyyMMdd) + ".dmp";

	String dbName_imp = "orcl230";
	String fromUser = "swjoa";
	String toUser = "swjoa";

	Connection con_exp = UtilJDBCManager.getOracleCon("168.168.190.217", "1521", "orcl", "swjoa", "swjoa");
	Connection con_imp = UtilJDBCManager.getOracleCon("168.168.190.230", "1521", "orcl", "swjoa", "swjoa");
	String[] tableArr_exp = getTableNameArr(con_exp, "DYN_");
	String[] tableArr_imp = getTableNameArr(con_imp, "DYN_");
	sbSql.append("--删除" + dbName_imp + "数据库中的动态表单相关表").append("\n");
	for (String table : tableArr_imp) {
	    sbSql.append("drop table " + schemaArr[0] + "." + table.toLowerCase()).append(";\n");
	}
	sbSql.append("--！！！！导入省局动态表单相关表（必须执行完此步骤才能执行以下步骤，此步骤以dmp格式导入）-----！！！！！").append("\n");
	String[] ownerArr = null;

	String expCmd = getExpCmd(userName, password, dbName_exp, filePath, ownerArr, tableArr_exp);
	sbSql.append("--从" + dbName_exp + "导出动态表单相关表").append("\n");
	sbSql.append(expCmd).append("\n");

	String impCmd = getImpCmd(userName, password, dbName_imp, filePath, fromUser, toUser);
	sbSql.append("--导入动态表单相关表到：" + dbName_imp).append("\n");
	sbSql.append(impCmd).append("\n");
	
	sbSql.append("delete from swjoa.dyn_bianma;").append("\n");
	sbSql.append("delete from swjoa.dyn_button;").append("\n");
	sbSql.append("delete from swjoa.dyn_buttons_permissions;").append("\n");
	sbSql.append("delete from swjoa.dyn_button_his;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_attachs_permissions;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_buttoms;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_buttoms_his;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_buttons_permissions;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_fields;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_fields_his;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_fields_permissions;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_form;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_form_ckeditor;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_form_ck_map;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_form_his;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_offices_permissions;").append("\n");
	sbSql.append("delete from swjoa.dyn_define_views_permissions;").append("\n");
	sbSql.append("delete from swjoa.dyn_exchange_map;").append("\n");
	sbSql.append("delete from swjoa.dyn_field;").append("\n");
	sbSql.append("delete from swjoa.dyn_fieldhis;").append("\n");
	sbSql.append("delete from swjoa.dyn_fields_permissions;").append("\n");
	sbSql.append("delete from swjoa.dyn_field_his;").append("\n");
	sbSql.append("delete from swjoa.dyn_form;").append("\n");
	sbSql.append("delete from swjoa.dyn_formhis;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_approval;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_ckeditor;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_flow;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_flowaction;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_flowelement;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_flowrole;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_his;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_map;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_module;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_plugin;").append("\n");
	sbSql.append("delete from swjoa.dyn_form_view;").append("\n");
	sbSql.append("delete from swjoa.dyn_module;").append("\n");
	sbSql.append("delete from swjoa.dyn_opinions_permissions;").append("\n");
	sbSql.append("delete from swjoa.dyn_plugin;").append("\n");
	sbSql.append("delete from swjoa.dyn_process_manage;").append("\n");
	sbSql.append("delete from swjoa.dyn_process_property;").append("\n");
	sbSql.append("delete from swjoa.dyn_query_form_field;").append("\n");
	sbSql.append("delete from swjoa.dyn_related_data;").append("\n");
	sbSql.append("delete from swjoa.dyn_related_document;").append("\n");
	sbSql.append("delete from swjoa.dyn_relation;").append("\n");
	sbSql.append("delete from swjoa.dyn_relationdetail;").append("\n");
	sbSql.append("delete from swjoa.dyn_style;").append("\n");
	sbSql.append("delete from swjoa.dyn_view;").append("\n");
	sbSql.append("delete from swjoa.dyn_view_field;").append("\n");
	sbSql.append("delete from swjoa.dyn_view_his;").append("\n");
	
	sbSql.append("--把省局动态表单相关表导入到分局中").append("\n\n");
	sbSql.append("/*").append("\n");
	for (int i = 1; i < schemaArr.length; i++) {
	    sbSql.append("--" + unitsArr[i]).append("\n");
	    for (String table : tableArr_exp) {
		sbSql.append("drop table " + schemaArr[i] + "." + table.toLowerCase()).append(";\n");
		sbSql.append("create table " + schemaArr[i] + "." + table.toLowerCase() + " as select * from " + schemaArr[0] + "." + table.toLowerCase()).append(";\n");
	    }
	}
	sbSql.append("*/").append("\n");
	
	getSqlHHP(sbSql);
	getOtherSql(sbSql);
	sbSql.append("\n----另外须执行如下脚本：/swjoa/doc/脚本部署/pub_operation.sql");
	System.out.println(sbSql);
    }
}
