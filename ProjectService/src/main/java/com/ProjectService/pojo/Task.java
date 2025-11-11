package com.ProjectService.pojo;

import java.time.LocalDate;
import java.util.List;

import com.ProjectService.entity.Project;
import com.ProjectService.enums.TaskStatus;

public class Task {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private Project project;
    private User manager; // Role = Manager
    private List<UserTask> assignedDevelopers;
    private List<TaskSkill> requiredSkills;

    // No-args constructor
    public Task() {
    }

    // All-args constructor
    public Task(Long id, String title, String description, TaskStatus status, LocalDate dueDate,
                Project project, User manager, List<UserTask> assignedDevelopers, List<TaskSkill> requiredSkills) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.project = project;
        this.manager = manager;
        this.assignedDevelopers = assignedDevelopers;
        this.requiredSkills = requiredSkills;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        if (status != TaskStatus.COMPLETED && status != TaskStatus.INREVIEW &&
            dueDate != null && dueDate.isBefore(LocalDate.now())) {
            return status; // You can adjust logic if needed
        }
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Project getProject() {
        return project;
    }

    public User getManager() {
        return manager;
    }

    public List<UserTask> getAssignedDevelopers() {
        return assignedDevelopers;
    }

    public List<TaskSkill> getRequiredSkills() {
        return requiredSkills;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        if (status != TaskStatus.COMPLETED && status != TaskStatus.INREVIEW &&
            dueDate != null && dueDate.isBefore(LocalDate.now())) {
            this.status = TaskStatus.OVERDUE;
        } else {
            this.status = status;
        }
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dueDate=" + dueDate +
                ", project=" + project +
                ", manager=" + manager +
                ", assignedDevelopers=" + assignedDevelopers +
                ", requiredSkills=" + requiredSkills +
                '}';
    }

   
    
}

