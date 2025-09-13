package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.Approval;
import com.cadernetaai.cadernetaai.service.ApprovalService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {
    private final ApprovalService approvalService;
    public ApprovalController(ApprovalService approvalService) { this.approvalService = approvalService; }

    @GetMapping
    public ResponseEntity<List<Approval>> list(@RequestParam Long projectId) {
        return ResponseEntity.ok(approvalService.list(projectId));
    }

    @PostMapping
    public ResponseEntity<Approval> request(@RequestParam Long workspaceId, @RequestParam Long projectId, @RequestBody Approval body) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        return ResponseEntity.ok(approvalService.request(workspaceId, userId, projectId, body));
    }

    @PutMapping("/sign")
    public ResponseEntity<Approval> sign(@RequestParam Long workspaceId, @RequestParam Long approvalId) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        return ResponseEntity.ok(approvalService.sign(workspaceId, userId, approvalId));
    }

    @PutMapping("/status")
    public ResponseEntity<Approval> setStatus(@RequestParam Long workspaceId, @RequestParam Long approvalId, @RequestParam String status) {
        Long userId = com.cadernetaai.cadernetaai.controller.security.AuthUtil.currentUserId();
        return ResponseEntity.ok(approvalService.setStatus(workspaceId, userId, approvalId, status));
    }
}


