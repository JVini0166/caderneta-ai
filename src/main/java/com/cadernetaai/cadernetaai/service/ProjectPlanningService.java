package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.Project;
import com.cadernetaai.cadernetaai.domain.ProjectActivity;
import com.cadernetaai.cadernetaai.domain.ProjectStage;
import com.cadernetaai.cadernetaai.repository.ProjectActivityRepository;
import com.cadernetaai.cadernetaai.repository.ProjectRepository;
import com.cadernetaai.cadernetaai.repository.ProjectStageRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectPlanningService {
    private final ProjectStageRepository stageRepository;
    private final ProjectActivityRepository activityRepository;
    private final ProjectRepository projectRepository;

    public ProjectPlanningService(ProjectStageRepository stageRepository, ProjectActivityRepository activityRepository, ProjectRepository projectRepository) {
        this.stageRepository = stageRepository;
        this.activityRepository = activityRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectStage> listStages(Long projectId) { return stageRepository.findByProject_Id(projectId); }
    public List<ProjectActivity> listActivities(Long stageId) { return activityRepository.findByStage_Id(stageId); }

    @Transactional
    public ProjectStage createStage(Long projectId, ProjectStage stage) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        stage.setProject(project);
        return stageRepository.save(stage);
    }

    @Transactional
    public ProjectStage updateStage(Long projectId, Long stageId, ProjectStage updated) {
        ProjectStage s = stageRepository.findById(stageId).orElseThrow();
        if (!s.getProject().getId().equals(projectId)) throw new IllegalArgumentException("Stage not in project");
        s.setName(updated.getName());
        s.setSortOrder(updated.getSortOrder());
        s.setPlannedStart(updated.getPlannedStart());
        s.setPlannedEnd(updated.getPlannedEnd());
        s.setDescription(updated.getDescription());
        return stageRepository.save(s);
    }

    @Transactional
    public void deleteStage(Long projectId, Long stageId) {
        ProjectStage s = stageRepository.findById(stageId).orElseThrow();
        if (!s.getProject().getId().equals(projectId)) throw new IllegalArgumentException("Stage not in project");
        stageRepository.delete(s);
    }

    @Transactional
    public ProjectActivity createActivity(Long stageId, ProjectActivity activity) {
        ProjectStage s = stageRepository.findById(stageId).orElseThrow();
        activity.setStage(s);
        return activityRepository.save(activity);
    }

    @Transactional
    public ProjectActivity updateActivity(Long stageId, Long activityId, ProjectActivity updated) {
        ProjectActivity a = activityRepository.findById(activityId).orElseThrow();
        if (!a.getStage().getId().equals(stageId)) throw new IllegalArgumentException("Activity not in stage");
        a.setName(updated.getName());
        a.setUnit(updated.getUnit());
        a.setTotalQuantity(updated.getTotalQuantity());
        a.setPlannedStart(updated.getPlannedStart());
        a.setPlannedEnd(updated.getPlannedEnd());
        a.setDescription(updated.getDescription());
        a.setStatus(updated.getStatus());
        return activityRepository.save(a);
    }

    @Transactional
    public void deleteActivity(Long stageId, Long activityId) {
        ProjectActivity a = activityRepository.findById(activityId).orElseThrow();
        if (!a.getStage().getId().equals(stageId)) throw new IllegalArgumentException("Activity not in stage");
        activityRepository.delete(a);
    }
}


