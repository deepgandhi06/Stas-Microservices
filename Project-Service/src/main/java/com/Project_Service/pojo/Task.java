package com.Project_Service.pojo;

import com.Project_Service.enums.TaskStatus;

public class Task {
	private Long id;
	private String title;
	private String description;
	private TaskStatus status;
	
	
	
	public Task() {
		super();
	}



	public Task(Long id, String title, String description, TaskStatus status) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
	}



	public Task(Task newTask) {
		id = newTask.getId();
		title = newTask.getTitle();
		description = newTask.getDescription();
		status = newTask.getStatus();
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



	public TaskStatus getStatus() {
		return status;
	}



	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	
	
	
	
}
