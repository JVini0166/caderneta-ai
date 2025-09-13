package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.DocumentFile;
import com.cadernetaai.cadernetaai.service.DocumentService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    public static class UploadRequest {
        public String docType;
        public String name;
        public String originalName;
        public String contentType;
        public String storagePath;
        public Long sizeBytes;
        public Integer version;
        public List<Long> tagIds;
    }

    private final DocumentService documentService;
    public DocumentController(DocumentService documentService) { this.documentService = documentService; }

    @GetMapping
    public ResponseEntity<List<DocumentFile>> list(@RequestParam Long projectId) {
        return ResponseEntity.ok(documentService.listByProject(projectId));
    }

    @PostMapping
    public ResponseEntity<DocumentFile> upload(@RequestParam Long workspaceId, @RequestParam Long projectId, @RequestBody UploadRequest body) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        DocumentFile file = new DocumentFile();
        file.setDocType(body.docType);
        file.setName(body.name);
        file.setOriginalName(body.originalName);
        file.setContentType(body.contentType);
        file.setStoragePath(body.storagePath);
        file.setSizeBytes(body.sizeBytes);
        if (body.version != null) file.setVersion(body.version);
        return ResponseEntity.ok(documentService.upload(workspaceId, userId, projectId, file, body.tagIds));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long workspaceId, @RequestParam Long documentId) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        documentService.delete(workspaceId, userId, documentId);
        return ResponseEntity.noContent().build();
    }
}


