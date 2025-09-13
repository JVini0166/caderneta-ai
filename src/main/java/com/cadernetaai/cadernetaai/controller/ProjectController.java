package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.Project;
import com.cadernetaai.cadernetaai.service.ProjectService;
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
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) { this.projectService = projectService; }

    @GetMapping
    public ResponseEntity<List<Project>> list(@RequestParam Long workspaceId) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        return ResponseEntity.ok(projectService.listByWorkspace(workspaceId, userId));
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestParam Long workspaceId, @Valid @RequestBody Project project) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        return ResponseEntity.ok(projectService.create(workspaceId, userId, project));
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestParam Long workspaceId,
                                          @RequestParam Long projectId,
                                          @Valid @RequestBody Project project) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        return ResponseEntity.ok(projectService.update(workspaceId, userId, projectId, project));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long workspaceId, @RequestParam Long projectId) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        projectService.delete(workspaceId, userId, projectId);
        return ResponseEntity.noContent().build();
    }
}
