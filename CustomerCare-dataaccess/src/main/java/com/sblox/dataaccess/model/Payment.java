package com.sblox.dataaccess.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Payment extends IEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int numberOfCoreAgents;
	private int numberOfEnterpriseAgents;
	private int numberOfRecorderAgents;
	private int numberOfOutboundAgents;
	private String startDate;
	private String endDate;
	private String projectName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumberOfCoreAgents() {
		return numberOfCoreAgents;
	}

	public void setNumberOfCoreAgents(int numberOfCoreAgents) {
		this.numberOfCoreAgents = numberOfCoreAgents;
	}

	public int getNumberOfEnterpriseAgents() {
		return numberOfEnterpriseAgents;
	}

	public void setNumberOfEnterpriseAgents(int numberOfEnterpriseAgents) {
		this.numberOfEnterpriseAgents = numberOfEnterpriseAgents;
	}

	public int getNumberOfRecorderAgents() {
		return numberOfRecorderAgents;
	}

	public void setNumberOfRecorderAgents(int numberOfRecorderAgents) {
		this.numberOfRecorderAgents = numberOfRecorderAgents;
	}

	public int getNumberOfOutboundAgents() {
		return numberOfOutboundAgents;
	}

	public void setNumberOfOutboundAgents(int numberOfOutboundAgents) {
		this.numberOfOutboundAgents = numberOfOutboundAgents;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
