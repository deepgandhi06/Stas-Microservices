package com.ProjectService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ProjectService.dto.ProjectDTO;
import com.ProjectService.entity.Project;
import com.ProjectService.pojo.AvailableManager;
import com.ProjectService.service.ProjectService;

import java.util.List;
@RestController
@RequestMapping("/api/client")
public class ClientController {
   @Autowired
   private ClientService clientService;
   @Autowired
   private ProjectService projectService;
   @GetMapping("/dashboard-data")
   public ResponseEntity<Object> getClientDashboardData() {
       return ResponseEntity.ok(clientService.getClientDashboardData());
   }
   @GetMapping("/projects")
   public ResponseEntity<Object> getProjects(
           @RequestParam(value = "page", defaultValue = "0") int page,
           @RequestParam(value = "limit", defaultValue = "5") int limit) {
       return ResponseEntity.ok(projectService.findProjectsForClient(page, limit));
   }
   @GetMapping("/projects/{id}")
   public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
       return ResponseEntity.ok(projectService.getProjectById(id));
   }
   @PutMapping("/projects/{projectId}")
   public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long projectId,
                                                   @RequestBody Project newProject) {
       return ResponseEntity.ok(projectService.clientUpdateProject(projectId, newProject));
   }
   @PostMapping("/project")
   public ResponseEntity<?> createNewProject(@RequestBody Project newProject) {
       try {
           return ResponseEntity.ok(projectService.createNewProject(newProject));
       } catch (Exception e) {
           return ResponseEntity.internalServerError().body("Error creating project: " + e.getMessage());
       }
   }
   @GetMapping("/available-managers")
   public ResponseEntity<List<AvailableManager>> getAvailableManagers() {
       return ResponseEntity.ok(clientService.getAvailableManagers());
   }
}
