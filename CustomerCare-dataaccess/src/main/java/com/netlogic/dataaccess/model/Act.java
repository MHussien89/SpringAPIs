package com.netlogic.dataaccess.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sblox.dataaccess.model.IEntity;

/**
 *
 * @author Mostafa Hussien
 */
@Entity
public class Act {


	@Column(name="ProjectId")
	@Id
	private long id;

	private String ProjectName;

	private Date EndDate;

	private int Inbound;

	private int OutBound;
	
	private int CCEnterprise;

	private int Chat;

	private int Email;

	private int status;
	
	private int SocialMedia;

	private int Recording;

	private String AdminUserName;

//	private int AgentsCount;

	private int SupersCount;

//	private String ConCallsCount;

//	private String CheckSum;
	private String password;
	private Date StartDate;

	private Date LastUpdate;
	
	@Column(name="EditFlag")
	private boolean editFlag;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProjectName() {
		return ProjectName;
	}

	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}

	public int getInbound() {
		return Inbound;
	}

	public void setInbound(int inbound) {
		Inbound = inbound;
	}

	public int getOutBound() {
		return OutBound;
	}

	public void setOutBound(int outBound) {
		OutBound = outBound;
	}

	public int getChat() {
		return Chat;
	}

	public void setChat(int chat) {
		Chat = chat;
	}

	public int getEmail() {
		return Email;
	}

	public void setEmail(int email) {
		Email = email;
	}

	public int getSocialMedia() {
		return SocialMedia;
	}

	public void setSocialMedia(int socialMedia) {
		SocialMedia = socialMedia;
	}

	public int getRecording() {
		return Recording;
	}

	public void setRecording(int recording) {
		Recording = recording;
	}

	public String getAdminUserName() {
		return AdminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		AdminUserName = adminUserName;
	}

//	public int getAgentsCount() {
//		return AgentsCount;
//	}
//
//	public void setAgentsCount(int agentsCount) {
//		AgentsCount = agentsCount;
//	}

	public int getSupersCount() {
		return SupersCount;
	}

	public void setSupersCount(int supersCount) {
		SupersCount = supersCount;
	}

//	public String getConCallsCount() {
//		return ConCallsCount;
//	}
//
//	public void setConCallsCount(String conCallsCount) {
//		ConCallsCount = conCallsCount;
//	}

//	public String getCheckSum() {
//		return CheckSum;
//	}
//
//	public void setCheckSum(String checkSum) {
//		CheckSum = checkSum;
//	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getLastUpdate() {
		return LastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		LastUpdate = lastUpdate;
	}

	public int getCCEnterprise() {
		return CCEnterprise;
	}

	public void setCCEnterprise(int cCEnterprise) {
		CCEnterprise = cCEnterprise;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
