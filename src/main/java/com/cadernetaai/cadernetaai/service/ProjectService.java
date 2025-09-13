package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.Project;
import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.repository.ProjectRepository;
import com.cadernetaai.cadernetaai.repository.WorkspaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final WorkspaceRepository workspaceRepository;
    private final RbacService rbacService;
    public ProjectService(ProjectRepository projectRepository, WorkspaceRepository workspaceRepository, RbacService rbacService) {
        this.projectRepository = projectRepository;
        this.workspaceRepository = workspaceRepository;
        this.rbacService = rbacService;
    }

    public List<Project> listByWorkspace(Long workspaceId, Long userId) {
        return projectRepository.findByWorkspace_Id(workspaceId);
    }

    @Transactional
    public Project create(Long workspaceId, Long userId, Project project) {
        if (!rbacService.canManageProjects(workspaceId, userId)) throw new SecurityException("Not allowed");
        Workspace ws = workspaceRepository.findById(workspaceId).orElseThrow();
        project.setWorkspace(ws);
        project.setCreatedBy(userId);
        return projectRepository.save(project);
    }

    @Transactional
    public Project update(Long workspaceId, Long userId, Long projectId, Project updated) {
        if (!rbacService.canManageProjects(workspaceId, userId)) throw new SecurityException("Not allowed");
        Project p = projectRepository.findById(projectId).orElseThrow();
        if (!p.getWorkspace().getId().equals(workspaceId)) throw new IllegalArgumentException("Project not in workspace");
        p.setName(updated.getName());
        p.setDescription(updated.getDescription());
        p.setAddress(updated.getAddress());
        p.setContractedEntity(updated.getContractedEntity());
        p.setContractorCompanyId(updated.getContractorCompanyId());
        p.setContractorPersonId(updated.getContractorPersonId());
        p.setContractDate(updated.getContractDate());
        p.setStartDate(updated.getStartDate());
        p.setPlannedEndDate(updated.getPlannedEndDate());
        return projectRepository.save(p);
    }

    @Transactional
    public void delete(Long workspaceId, Long userId, Long projectId) {
        if (!rbacService.canManageProjects(workspaceId, userId)) throw new SecurityException("Not allowed");
        Project p = projectRepository.findById(projectId).orElseThrow();
        if (!p.getWorkspace().getId().equals(workspaceId)) throw new IllegalArgumentException("Project not in workspace");
        projectRepository.delete(p);
    }
}


