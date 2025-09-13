package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.repository.WorkspaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    public WorkspaceService(WorkspaceRepository workspaceRepository) { this.workspaceRepository = workspaceRepository; }

    public List<Workspace> list() { return workspaceRepository.findAll(); }

    @Transactional
    public Workspace create(Workspace workspace) { return workspaceRepository.save(workspace); }
}


