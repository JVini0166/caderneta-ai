package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.ProjectStage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStageRepository extends JpaRepository<ProjectStage, Long> {
    List<ProjectStage> findByProject_Id(Long projectId);
}


