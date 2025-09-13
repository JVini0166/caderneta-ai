package com.cadernetaai.cadernetaai.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "projects", schema = "public")
public class Project extends BaseEntities {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "contracted_entity")
    private String contractedEntity;

    @Column(name = "contractor_company_id")
    private Long contractorCompanyId;

    @Column(name = "contractor_person_id")
    private Long contractorPersonId;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "planned_end_date")
    private LocalDate plannedEndDate;

    @Column(name = "created_by")
    private Long createdBy;

    public Workspace getWorkspace() { return workspace; }
    public void setWorkspace(Workspace workspace) { this.workspace = workspace; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContractedEntity() { return contractedEntity; }
    public void setContractedEntity(String contractedEntity) { this.contractedEntity = contractedEntity; }
    public Long getContractorCompanyId() { return contractorCompanyId; }
    public void setContractorCompanyId(Long contractorCompanyId) { this.contractorCompanyId = contractorCompanyId; }
    public Long getContractorPersonId() { return contractorPersonId; }
    public void setContractorPersonId(Long contractorPersonId) { this.contractorPersonId = contractorPersonId; }
    public LocalDate getContractDate() { return contractDate; }
    public void setContractDate(LocalDate contractDate) { this.contractDate = contractDate; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getPlannedEndDate() { return plannedEndDate; }
    public void setPlannedEndDate(LocalDate plannedEndDate) { this.plannedEndDate = plannedEndDate; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
}


