package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByWorkspace_Id(Long workspaceId);
}


