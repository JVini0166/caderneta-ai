package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.Tag;
import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.repository.TagRepository;
import com.cadernetaai.cadernetaai.repository.WorkspaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final WorkspaceRepository workspaceRepository;
    public TagService(TagRepository tagRepository, WorkspaceRepository workspaceRepository) {
        this.tagRepository = tagRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public List<Tag> list(Long workspaceId) { return tagRepository.findByWorkspace_Id(workspaceId); }

    @Transactional
    public Tag create(Long workspaceId, Tag tag) {
        Workspace ws = workspaceRepository.findById(workspaceId).orElseThrow();
        tag.setWorkspace(ws);
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag update(Long workspaceId, Long tagId, Tag updated) {
        Tag t = tagRepository.findById(tagId).orElseThrow();
        if (!t.getWorkspace().getId().equals(workspaceId)) throw new IllegalArgumentException("Tag not in workspace");
        t.setName(updated.getName());
        t.setDescription(updated.getDescription());
        t.setColor(updated.getColor());
        return tagRepository.save(t);
    }

    @Transactional
    public void delete(Long workspaceId, Long tagId) {
        Tag t = tagRepository.findById(tagId).orElseThrow();
        if (!t.getWorkspace().getId().equals(workspaceId)) throw new IllegalArgumentException("Tag not in workspace");
        tagRepository.delete(t);
    }
}


