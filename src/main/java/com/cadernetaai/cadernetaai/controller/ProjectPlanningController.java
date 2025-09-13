package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.ProjectActivity;
import com.cadernetaai.cadernetaai.domain.ProjectStage;
import com.cadernetaai.cadernetaai.service.ProjectPlanningService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planning")
public class ProjectPlanningController {
    private final ProjectPlanningService planningService;
    public ProjectPlanningController(ProjectPlanningService planningService) { this.planningService = planningService; }

    // Stages
    @GetMapping("/stages")
    public ResponseEntity<List<ProjectStage>> listStages(@RequestParam Long projectId) {
        return ResponseEntity.ok(planningService.listStages(projectId));
    }

    @PostMapping("/stages")
    public ResponseEntity<ProjectStage> createStage(@RequestParam Long projectId, @Valid @RequestBody ProjectStage body) {
        return ResponseEntity.ok(planningService.createStage(projectId, body));
    }

    @PutMapping("/stages")
    public ResponseEntity<ProjectStage> updateStage(@RequestParam Long projectId, @RequestParam Long stageId, @Valid @RequestBody ProjectStage body) {
        return ResponseEntity.ok(planningService.updateStage(projectId, stageId, body));
    }

    @DeleteMapping("/stages")
    public ResponseEntity<Void> deleteStage(@RequestParam Long projectId, @RequestParam Long stageId) {
        planningService.deleteStage(projectId, stageId);
        return ResponseEntity.noContent().build();
    }

    // Activities
    @GetMapping("/activities")
    public ResponseEntity<List<ProjectActivity>> listActivities(@RequestParam Long stageId) {
        return ResponseEntity.ok(planningService.listActivities(stageId));
    }

    @PostMapping("/activities")
    public ResponseEntity<ProjectActivity> createActivity(@RequestParam Long stageId, @Valid @RequestBody ProjectActivity body) {
        return ResponseEntity.ok(planningService.createActivity(stageId, body));
    }

    @PutMapping("/activities")
    public ResponseEntity<ProjectActivity> updateActivity(@RequestParam Long stageId, @RequestParam Long activityId, @Valid @RequestBody ProjectActivity body) {
        return ResponseEntity.ok(planningService.updateActivity(stageId, activityId, body));
    }

    @DeleteMapping("/activities")
    public ResponseEntity<Void> deleteActivity(@RequestParam Long stageId, @RequestParam Long activityId) {
        planningService.deleteActivity(stageId, activityId);
        return ResponseEntity.noContent().build();
    }
}


