package com.ProjectService.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.ProjectService.enums.ProjectStatus;

public class ProjectDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Instead of full User objects, use IDs or names for lightweight transfer
    private Long clientId;
    private String clientName;

    private Long managerId;
    private String managerName;

    // For members and tasks, you can use lists of IDs or custom DTOs
    private Set<Long> memberIds;
    private Set<Long> taskIds;

    // Constructors
    public ProjectDTO() {}

    public ProjectDTO(Long id, String title, String description, LocalDate startDate, LocalDate endDate,
                      ProjectStatus status, LocalDateTime createdAt, LocalDateTime updatedAt,
                      Long clientId, String clientName, Long managerId, String managerName,
                      Set<Long> memberIds, Set<Long> taskIds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.clientId = clientId;
        this.clientName = clientName;
        this.managerId = managerId;
        this.managerName = managerName;
        this.memberIds = memberIds;
        this.taskIds = taskIds;
    }

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Set<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(Set<Long> memberIds) {
		this.memberIds = memberIds;
	}

	public Set<Long> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(Set<Long> taskIds) {
		this.taskIds = taskIds;
	}

    // Getters and Setters
    // (Generate using Lombok @Data or manually)
    
    
}

