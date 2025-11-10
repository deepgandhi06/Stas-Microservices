package com.Project_Service.entity;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import com.Project_Service.enums.ProjectStatus;
import com.Project_Service.pojo.Task;
import com.Project_Service.pojo.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob // Use @Lob for potentially long descriptions
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    // --- Timestamps for auditing ---
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // --- Relationships ---

    // A project is created by one client. Explicitly LAZY fetch.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    // A project is managed by one manager.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id") // Can be nullable if a project is pending assignment
    private User manager;

    // A project has a set of members. Cascade operations and remove orphans.
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectMember> members = new HashSet<>();

    // A project has a set of tasks. Cascade operations and remove orphans.
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    
    public void setStatus(ProjectStatus status) {
        if (status != ProjectStatus.COMPLETED && this.status != ProjectStatus.ONHOLD && endDate != null && endDate.isBefore(LocalDate.now())) {
            this.status = ProjectStatus.DELAYED;
        } else {
            this.status = status;
        }
    }

    public ProjectStatus getStatus() {
        if (this.status != ProjectStatus.COMPLETED && this.status != ProjectStatus.ONHOLD && endDate != null && endDate.isBefore(LocalDate.now())) {
            return ProjectStatus.DELAYED;
        }
        return this.status;
    }

    
    // --- Custom equals() and hashCode() for safe JPA operations ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id != null && Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    //constructors :
    public Project() {
		super();
	}
    
    
	public Project(Long id, String title, String description, LocalDate startDate, LocalDate endDate,
			ProjectStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, User client, User manager,
			Set<ProjectMember> members, Set<Task> tasks) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.client = client;
		this.manager = manager;
		this.members = members;
		this.tasks = tasks;
	}
	
	//getter-setters :
	
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

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Set<ProjectMember> getMembers() {
		return members;
	}

	public void setMembers(Set<ProjectMember> members) {
		this.members = members;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
	 
	
	
    
    
   
    
    
}
