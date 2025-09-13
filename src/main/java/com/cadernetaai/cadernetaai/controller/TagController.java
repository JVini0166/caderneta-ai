package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.Tag;
import com.cadernetaai.cadernetaai.service.TagService;
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
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;
    public TagController(TagService tagService) { this.tagService = tagService; }

    @GetMapping
    public ResponseEntity<List<Tag>> list(@RequestParam Long workspaceId) {
        return ResponseEntity.ok(tagService.list(workspaceId));
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestParam Long workspaceId, @Valid @RequestBody Tag body) {
        return ResponseEntity.ok(tagService.create(workspaceId, body));
    }

    @PutMapping
    public ResponseEntity<Tag> update(@RequestParam Long workspaceId, @RequestParam Long tagId, @Valid @RequestBody Tag body) {
        return ResponseEntity.ok(tagService.update(workspaceId, tagId, body));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long workspaceId, @RequestParam Long tagId) {
        tagService.delete(workspaceId, tagId);
        return ResponseEntity.noContent().build();
    }
}


