package com.ProjectService.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ProjectService.dto.ProjectDTO;
import com.ProjectService.entity.Project;
import com.ProjectService.enums.ProjectStatus;
import com.ProjectService.feignClients.TaskClient;
import com.ProjectService.feignClients.UserClient;
import com.ProjectService.pojo.User;
import com.ProjectService.repo.ProjectMemberRepo;
import com.ProjectService.repo.ProjectRepo;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class ProjectService {
   private  ProjectRepo projectRepository;
   private  ProjectMemberRepo projectMemberRepository;
   private  UserClient userClient;
   private  TaskClient taskClient;
   private HttpServletRequest httpRequest;
   
   
   //default ctor
   public ProjectService() {
	   
   }
   
   //ctor with arguments 
   public ProjectService(ProjectRepo projectRepository,
                             ProjectMemberRepo projectMemberRepository,
                             UserClient userClient,
                             TaskClient taskClient,
                             HttpServletRequest httprequest) {
       this.projectRepository = projectRepository;
       this.projectMemberRepository = projectMemberRepository;
       this.userClient = userClient;
       this.taskClient = taskClient;
       this.httpRequest = httprequest;
   }
   
   // ---------------------------
   // Public service methods
   // ---------------------------
   
   
   //1.createNewProject (@PostMapping)
   public ProjectDTO createNewProject(ProjectDTO newProject) {
	   // 1) Determine current client id
	   Long clientId = resolveCurrentUserIdOrFallback(newProject);
	   if (clientId == null) {
	       throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
	           "Unable to determine client identity. Ensure API Gateway forwards 'X-User-Id' or 'X-User-Email' header, or include clientId in request body.");
	   }
	   Project saved = new Project();
	   p.setTitle(newProject.getTitle());
	   p.setDescription(newProject.getDescription());
	   p.setStartDate(newProject.getStartDate());
	   p.setEndDate(newProject.getEndDate());
	   p.setStatus(ProjectStatus.PENDING);
	   
	   // set minimal client reference using only id (avoid loading or using remote User entity fields)
	   User clientRef = new User();
	   clientRef.setId(clientId);
	   p.setClient(clientRef);
	   // optional managerId in request
	   if (newProject.getManagerId() != null) {
	       // only resolve that manager exists in user-service, but do not copy full remote entity
	       User manager = userClient.getUserById(newProject.getManagerId());
	       if (manager != null) {
	          User managerRef = new User();
	           managerRef.setId(manager.getId());
	           p.setManager(managerRef);
	       } else {
	           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager id is invalid: " + newProject.getManagerId());
	       }
	   }
	   return new ProjectDTO(
			   saved.getId(),
			   saved.getTitle(),
			   saved.getDescription(),
			   saved.getStartDate(),
			   saved.getEndDate(),
			   saved.getStatus(),
			   saved.getClient().getId(),
			   saved.getManager().getId(),
			   saved.getCreatedAt(),
			   saved.getUpdatedAt()
			   );
	}

   
   
   public ProjectDto clientUpdateProject(Long projectId, NewProject newProject) {
       String currentEmail = extractCurrentUserEmail();
       if (currentEmail == null) throw new RuntimeException("Authenticated user not found");
       UserFeignDTO currentUser = userClient.getUserByEmail(currentEmail);
       if (currentUser == null) throw new RuntimeException("Authenticated user not found in user service");
       Project p = projectRepository.findById(projectId)
               .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));
       // ensure current user is the client (owner) of the project
       if (p.getClient() == null || !p.getClient().getId().equals(currentUser.getId())) {
           throw new RuntimeException("You are not the owner of this project");
       }
       // perform updates (only non-null fields)
       if (newProject.getTitle() != null) p.setTitle(newProject.getTitle());
       if (newProject.getDescription() != null) p.setDescription(newProject.getDescription());
       if (newProject.getStartDate() != null) p.setStartDate(newProject.getStartDate());
       if (newProject.getEndDate() != null) p.setEndDate(newProject.getEndDate());
       if (newProject.getManagerId() != null) {
           UserFeignDTO manager = userClient.getUserById(newProject.getManagerId());
           if (manager != null) {
               com.projectservice.entity.User managerRef = new com.projectservice.entity.User();
               managerRef.setId(manager.getId());
               p.setManager(managerRef);
           }
       }
       Project saved = projectRepository.save(p);
       return mapToDto(saved);
   }
   
   
   
   @Transactional(readOnly = true)
   public Page<ProjectDto> findProjectsForClient(int page, int limit) {
       String currentEmail = extractCurrentUserEmail();
       if (currentEmail == null) throw new RuntimeException("Authenticated user not found");
       UserFeignDTO currentUser = userClient.getUserByEmail(currentEmail);
       if (currentUser == null) throw new RuntimeException("Authenticated user not found in user service");
       PageRequest pageable = PageRequest.of(Math.max(0, page), Math.max(1, limit));
       Page<Project> projectPage = projectRepository.findByClient_Id(currentUser.getId(), pageable);
       List<ProjectDto> content = projectPage.getContent()
               .stream()
               .map(this::mapToDto)
               .collect(Collectors.toList());
       return new PageImpl<>(content, pageable, projectPage.getTotalElements());
   }


   
   @Transactional(readOnly = true)
   public ProjectDto getProjectById(Long id) {
       Project p = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found: " + id));
       return mapToDto(p);
   }
   
   
   public ProjectDto assignManager(Long projectId, Long managerId) {
       Project p = projectRepository.findById(projectId)
               .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));
       UserFeignDTO manager = userClient.getUserById(managerId);
       if (manager == null) throw new RuntimeException("Manager not found: " + managerId);
       com.projectservice.entity.User managerRef = new com.projectservice.entity.User();
       managerRef.setId(manager.getId());
       p.setManager(managerRef);
       Project saved = projectRepository.save(p);
       return mapToDto(saved);
   }
   
   
   public ProjectDto addMember(Long projectId, Long userId) {
       Project p = projectRepository.findById(projectId)
               .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));
       UserFeignDTO u = userClient.getUserById(userId);
       if (u == null) throw new RuntimeException("User not found: " + userId);
       ProjectMember pm = new ProjectMember();
       pm.setProject(p);
       com.projectservice.entity.User userRef = new com.projectservice.entity.User();
       userRef.setId(u.getId());
       pm.setUser(userRef);
       projectMemberRepository.save(pm);
       // keep bi-directional consistency
       p.getMembers().add(pm);
       projectRepository.save(p);
       return mapToDto(p);
   }


   
   public Long createTaskForProject(Long projectId, String title, String description, LocalDate dueDate, Long assignedById) {
       Project p = projectRepository.findById(projectId)
               .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));
       TaskCreateRequest req = new TaskCreateRequest();
       req.setTitle(title);
       req.setDescription(description);
       req.setDueDate(dueDate);
       req.setProjectId(projectId);
       req.setAssignedById(assignedById);
       TaskFeignResponse response = taskClient.createTask(req);
       if (response == null) throw new RuntimeException("Task service returned null.");
       // minimal local linking: create placeholder Task with id
       Task t = new Task();
       t.setId(response.getId());
       t.setTitle(response.getTitle());
       p.getTasks().add(t);
       projectRepository.save(p);
       return response.getId();
   }
   // ---------------------------
   // Helpers
   // ---------------------------
   private ProjectDto mapToDto(Project p) {
       ProjectDto dto = new ProjectDto();
       dto.setId(p.getId());
       dto.setTitle(p.getTitle());
       dto.setDescription(p.getDescription());
       dto.setStartDate(p.getStartDate());
       dto.setEndDate(p.getEndDate());
       dto.setStatus(p.getStatus());
       dto.setCreatedAt(p.getCreatedAt());
       dto.setUpdatedAt(p.getUpdatedAt());
       if (p.getClient() != null) dto.setClientId(p.getClient().getId());
       if (p.getManager() != null) dto.setManagerId(p.getManager().getId());
       if (p.getMembers() != null) {
           List<ProjectMemberDto> members = p.getMembers().stream().map(m -> {
               ProjectMemberDto md = new ProjectMemberDto();
               md.setId(m.getId());
               md.setUserId(m.getUser() != null ? m.getUser().getId() : null);
               return md;
           }).collect(Collectors.toList());
           dto.setMembers(members);
       }
       if (p.getTasks() != null) {
           List<Long> taskIds = p.getTasks().stream()
                   .map(Task::getId)
                   .collect(Collectors.toList());
           dto.setTaskIds(taskIds);
       }
       return dto;
   }
  
   
   
   //helper method :
   private Long resolveCurrentUserIdOrFallback(ProjectDTO newProject) {
	   // try X-User-Id header (preferred)
	   String userIdHeader = httpRequest.getHeader("X-User-Id");
	   if (userIdHeader != null && !userIdHeader.isEmpty()) {
	       try {
	           return Long.valueOf(userIdHeader);
	       } catch (NumberFormatException ex) {
	           // invalid header value — ignore and try next option
	       }
	   }
	   // try X-User-Email header
	   String userEmailHeader = httpRequest.getHeader("X-User-Email");
	   if (userEmailHeader != null && !userEmailHeader.isEmpty()) {
	       User user = userClient.getUserByEmail(userEmailHeader);
	       if (user != null) return user.getId();
	   }
	   // fallback to clientId in payload (explicit)
	   if (newProject != null && newProject.getClientId() != null) {
	       return newProject.getClientId();
	   }
	   // nothing found
	   return null;
	}
   
}
