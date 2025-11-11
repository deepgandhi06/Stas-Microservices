package com.ProjectService.repo;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ProjectService.entity.Project;
import com.ProjectService.enums.ProjectStatus;
import com.ProjectService.pojo.User;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    Optional<User> findByTitle(String title);    
    Optional<List<Project>> findByStatus(ProjectStatus status);
    List<Project> findByClientEmail(String email);
    Page findByClientEmail(String email, Pageable pageable);
    int countByManagerId(Long managerId);
    Optional<List<Project>> findByManager_Id(Long id);
}
