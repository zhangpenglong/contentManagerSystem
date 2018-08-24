package com.yxb.cms.modules.balanceLogs.model;


import com.yxb.cms.util.BasePojo;

/**
 *
 *@date 2014-01
 *@author dongao
 *@version  V1.0
 */
@SuppressWarnings("serial")
public class BalanceLogs extends BasePojo implements java.io.Serializable {

	/****/
	private java.lang.Integer id;
	/**会员Id**/
	private java.lang.Integer memberId;
	/**短信Id**/
	private java.lang.Integer msgId;
	/**操作类型，1.扣费，0充值**/
	private java.lang.Integer logsType;
	/**金额**/
	private java.math.BigDecimal balance;
	/**操作后的余额**/
	private java.math.BigDecimal afterBalance;
	/****/
	private java.lang.Integer createUser;
	/****/
	private java.util.Date createDate;
	/****/
	private java.lang.Integer isValid;

	private java.lang.String userName;
	private java.lang.String memberName;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private java.lang.String remarks;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public  java.lang.Integer getId() {
		return id;
	}
	public  java.lang.Integer getMemberId() {
		return memberId;
	}
	public  java.lang.Integer getMsgId() {
		return msgId;
	}
	public  java.lang.Integer getLogsType() {
		return logsType;
	}
	public  java.math.BigDecimal getBalance() {
		return balance;
	}
	public  java.math.BigDecimal getAfterBalance() {
		return afterBalance;
	}
	public  java.lang.Integer getCreateUser() {
		return createUser;
	}
	public  java.util.Date getCreateDate() {
		return createDate;
	}
	public  java.lang.Integer getIsValid() {
		return isValid;
	}


	public void setId( java.lang.Integer id) {
	    this.id = id;
	}
	public void setMemberId( java.lang.Integer memberId) {
	    this.memberId = memberId;
	}
	public void setMsgId( java.lang.Integer msgId) {
	    this.msgId = msgId;
	}
	public void setLogsType( java.lang.Integer logsType) {
	    this.logsType = logsType;
	}
	public void setBalance( java.math.BigDecimal balance) {
	    this.balance = balance;
	}
	public void setAfterBalance( java.math.BigDecimal afterBalance) {
	    this.afterBalance = afterBalance;
	}
	public void setCreateUser( java.lang.Integer createUser) {
	    this.createUser = createUser;
	}
	public void setCreateDate( java.util.Date createDate) {
	    this.createDate = createDate;
	}
	public void setIsValid( java.lang.Integer isValid) {
	    this.isValid = isValid;
	}
}
