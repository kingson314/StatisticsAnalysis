package com.config.account;

/**
 * 
 * @msg :账户信息Bean
 * @date :2013-01-13
 */
public class Account {
	// 序列号
	private String id;
	// 账户名称
	private String name;
	// 账户密码
	private String password;
	// 账户类型(美金/美分)
	private String type;
	// 账户公司名
	private String company;
	// 账户杠杆比率
	private String leverage;
	// 帐户的保证金
	private String margin;
	// 当前账户返回资产净值。资产净值取决于交易服务器的设置
	private String equity;
	// 账户余额(账户中相当数量的价格值金钱)
	private String balance;
	// 账户利润
	private String profit;
	// 账户最大单量
	private String maxLot;
	// 账户最小单量
	private String minLot;
	// 连接服务器的名称
	private String server;
	// 开户日期
	private String opendate;
	// 开户代理人(IB)
	private String agent;
	// 开户代理人基本信息
	private String agentMsg;
	// 该账户使用的操作软件名称
	private String software;
	// 该账户使用的操作软件所在目录
	private String softwareDir;
	// 该账户使用的操作软件所在计算机Ip地址
	private String softwareIp;

	// 状态 0 表示正常 1 表示不可用
	private int state;
	// 排序
	private int ord;
	// 说明
	private String memo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLeverage() {
		return leverage;
	}

	public void setLeverage(String leverage) {
		this.leverage = leverage;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getEquity() {
		return equity;
	}

	public void setEquity(String equity) {
		this.equity = equity;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getMaxLot() {
		return maxLot;
	}

	public void setMaxLot(String maxLot) {
		this.maxLot = maxLot;
	}

	public String getMinLot() {
		return minLot;
	}

	public void setMinLot(String minLot) {
		this.minLot = minLot;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getOpendate() {
		return opendate;
	}

	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAgentMsg() {
		return agentMsg;
	}

	public void setAgentMsg(String agentMsg) {
		this.agentMsg = agentMsg;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getSoftwareDir() {
		return softwareDir;
	}

	public void setSoftwareDir(String softwareDir) {
		this.softwareDir = softwareDir;
	}

	public String getSoftwareIp() {
		return softwareIp;
	}

	public void setSoftwareIp(String softwareIp) {
		this.softwareIp = softwareIp;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
