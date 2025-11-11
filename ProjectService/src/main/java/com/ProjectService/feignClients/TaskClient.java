package com.ProjectService.feignClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.ProjectService.pojo.Task;
@FeignClient(name = "task-service", url = "${feign.task-service.url:http://localhost:8082}")
public interface TaskClient {
@PostMapping("/api/tasks")
Task createTask(Task request);
}
