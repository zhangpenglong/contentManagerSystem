package com.yxb.cms.modules.msg.model;


import com.yxb.cms.util.BasePojo;

/**
 *
 *@date 2014-01
 *@author dongao
 *@version  V1.0
 */
@SuppressWarnings("serial")
public class Msg extends BasePojo implements java.io.Serializable {

	/****/
	private java.lang.Integer id;
	/**短信参数**/
	private java.lang.String msgParams;
	/**返回状态，0成功，**/
	private java.lang.Integer msgResult;
	/**错误信息**/
	private java.lang.String errmsg;
	/**用户的session内容**/
	private java.lang.String ext;
	/**短信计费条数**/
	private java.lang.String fee;
	/**本次发送标识 id，标识一次短信下发记录**/
	private java.lang.String sid;
	/****/
	private java.lang.Integer isValid;
	/****/
	private java.lang.Integer createUser;

	/****/
	private java.util.Date createDate;
	/**手机号**/
	private java.lang.String phoneNumber;

	private java.lang.String memberName;
	private java.lang.String userName;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	/**会员**/
	private java.lang.Integer memberId;

	public  java.lang.Integer getId() {
		return id;
	}
	public  java.lang.String getMsgParams() {
		return msgParams;
	}
	public  java.lang.Integer getMsgResult() {
		return msgResult;
	}
	public  java.lang.String getErrmsg() {
		return errmsg;
	}
	public  java.lang.String getExt() {
		return ext;
	}
	public  java.lang.String getFee() {
		return fee;
	}
	public  java.lang.String getSid() {
		return sid;
	}
	public  java.lang.Integer getIsValid() {
		return isValid;
	}
	public  java.lang.Integer getCreateUser() {
		return createUser;
	}
	public  java.util.Date getCreateDate() {
		return createDate;
	}


	public void setId( java.lang.Integer id) {
	    this.id = id;
	}
	public void setMsgParams( java.lang.String msgParams) {
	    this.msgParams = msgParams;
	}
	public void setMsgResult( java.lang.Integer msgResult) {
	    this.msgResult = msgResult;
	}
	public void setErrmsg( java.lang.String errmsg) {
	    this.errmsg = errmsg;
	}
	public void setExt( java.lang.String ext) {
	    this.ext = ext;
	}
	public void setFee( java.lang.String fee) {
	    this.fee = fee;
	}
	public void setSid( java.lang.String sid) {
	    this.sid = sid;
	}
	public void setIsValid( java.lang.Integer isValid) {
	    this.isValid = isValid;
	}
	public void setCreateUser( java.lang.Integer createUser) {
	    this.createUser = createUser;
	}
	public void setCreateDate( java.util.Date createDate) {
	    this.createDate = createDate;
	}
}
