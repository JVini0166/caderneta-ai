package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.DocumentFile;
import com.cadernetaai.cadernetaai.domain.Project;
import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.repository.DocumentFileRepository;
import com.cadernetaai.cadernetaai.repository.ProjectRepository;
import com.cadernetaai.cadernetaai.repository.WorkspaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentService {
    private final DocumentFileRepository documentRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ProjectRepository projectRepository;
    private final RbacService rbacService;

    public DocumentService(DocumentFileRepository documentRepository, WorkspaceRepository workspaceRepository, ProjectRepository projectRepository, RbacService rbacService) {
        this.documentRepository = documentRepository;
        this.workspaceRepository = workspaceRepository;
        this.projectRepository = projectRepository;
        this.rbacService = rbacService;
    }

    public List<DocumentFile> listByProject(Long projectId) { return documentRepository.findByProject_Id(projectId); }

    @Transactional
    public DocumentFile upload(Long workspaceId, Long userId, Long projectId, DocumentFile file, java.util.List<Long> tagIds) {
        if (!rbacService.canUploadDocuments(workspaceId, userId)) throw new SecurityException("Not allowed");
        Workspace ws = workspaceRepository.findById(workspaceId).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();
        file.setWorkspace(ws);
        file.setProject(project);
        file.setUploadedBy(userId);
        // Persist file first; tagging can be implemented later
        return documentRepository.save(file);
    }

    @Transactional
    public void delete(Long workspaceId, Long userId, Long documentId) {
        if (!rbacService.canUploadDocuments(workspaceId, userId)) throw new SecurityException("Not allowed");
        documentRepository.deleteById(documentId);
    }
}


