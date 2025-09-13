package com.cadernetaai.cadernetaai.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "approvals", schema = "public")
public class Approval extends BaseEntities {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(name = "target_type", nullable = false)
    private String targetType;
    @Column(name = "target_id")
    private Long targetId;
    @Column(name = "status", nullable = false)
    private String status = "pending";
    @Column(name = "requested_by")
    private Long requestedBy;
    @Column(name = "signed_by")
    private Long signedBy;
    @Column(name = "signed_at")
    private OffsetDateTime signedAt;
    @Column(name = "evidence_doc_id")
    private Long evidenceDocId;

    public Workspace getWorkspace() { return workspace; }
    public void setWorkspace(Workspace workspace) { this.workspace = workspace; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getRequestedBy() { return requestedBy; }
    public void setRequestedBy(Long requestedBy) { this.requestedBy = requestedBy; }
    public Long getSignedBy() { return signedBy; }
    public void setSignedBy(Long signedBy) { this.signedBy = signedBy; }
    public OffsetDateTime getSignedAt() { return signedAt; }
    public void setSignedAt(OffsetDateTime signedAt) { this.signedAt = signedAt; }
    public Long getEvidenceDocId() { return evidenceDocId; }
    public void setEvidenceDocId(Long evidenceDocId) { this.evidenceDocId = evidenceDocId; }
}


