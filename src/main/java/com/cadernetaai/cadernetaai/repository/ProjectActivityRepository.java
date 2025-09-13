package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.ProjectActivity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectActivityRepository extends JpaRepository<ProjectActivity, Long> {
    List<ProjectActivity> findByStage_Id(Long stageId);
}


