package com.ProjectService.pojo;

import java.time.LocalDateTime;

import com.ProjectService.entity.Project;

public class Feedback {

    private Long id;
    private int rating; // A quantitative rating from 1 to 5
    private String content; // The qualitative text feedback
    private LocalDateTime createdAt;
    private User author;     // Person who gave the feedback
    private User recipient;  // Person who received the feedback (can be null)
    private Project project; // Subject of feedback (can be null)
    private Task task;       // Subject of feedback (can be null)

    // No-args constructor
    public Feedback() {
    }

    // All-args constructor
    public Feedback(Long id, int rating, String content, LocalDateTime createdAt,
                    User author, User recipient, Project project, Task task) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.recipient = recipient;
        this.project = project;
        this.task = task;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public User getRecipient() {
        return recipient;
    }

    public Project getProject() {
        return project;
    }

    public Task getTask() {
        return task;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", rating=" + rating +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", author=" + author +
                ", recipient=" + recipient +
                ", project=" + project +
                ", task=" + task +
                '}';
    }
}
