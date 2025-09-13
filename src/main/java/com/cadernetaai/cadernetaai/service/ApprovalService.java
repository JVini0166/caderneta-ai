package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.Approval;
import com.cadernetaai.cadernetaai.domain.Project;
import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.repository.ApprovalRepository;
import com.cadernetaai.cadernetaai.repository.ProjectRepository;
import com.cadernetaai.cadernetaai.repository.WorkspaceRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalService {
    private final ApprovalRepository approvalRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ProjectRepository projectRepository;
    private final RbacService rbacService;

    public ApprovalService(ApprovalRepository approvalRepository, WorkspaceRepository workspaceRepository, ProjectRepository projectRepository, RbacService rbacService) {
        this.approvalRepository = approvalRepository;
        this.workspaceRepository = workspaceRepository;
        this.projectRepository = projectRepository;
        this.rbacService = rbacService;
    }

    public List<Approval> list(Long projectId) { return approvalRepository.findByProject_Id(projectId); }

    @Transactional
    public Approval request(Long workspaceId, Long userId, Long projectId, Approval approval) {
        if (!rbacService.canSignReports(workspaceId, userId) && !rbacService.canManageProjects(workspaceId, userId)) {
            throw new SecurityException("Not allowed to request approvals");
        }
        Workspace ws = workspaceRepository.findById(workspaceId).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();
        approval.setWorkspace(ws);
        approval.setProject(project);
        approval.setRequestedBy(userId);
        approval.setStatus("pending");
        return approvalRepository.save(approval);
    }

    @Transactional
    public Approval sign(Long workspaceId, Long userId, Long approvalId) {
        if (!rbacService.canSignReports(workspaceId, userId)) throw new SecurityException("Not allowed to sign");
        Approval a = approvalRepository.findById(approvalId).orElseThrow();
        a.setSignedBy(userId);
        a.setSignedAt(OffsetDateTime.now());
        a.setStatus("signed");
        return approvalRepository.save(a);
    }

    @Transactional
    public Approval setStatus(Long workspaceId, Long userId, Long approvalId, String status) {
        if (!rbacService.canSignReports(workspaceId, userId) && !rbacService.canManageProjects(workspaceId, userId)) {
            throw new SecurityException("Not allowed to change status");
        }
        Approval a = approvalRepository.findById(approvalId).orElseThrow();
        a.setStatus(status);
        return approvalRepository.save(a);
    }
}


