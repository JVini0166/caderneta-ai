package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.Company;
import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.repository.CompanyRepository;
import com.cadernetaai.cadernetaai.repository.WorkspaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final WorkspaceRepository workspaceRepository;

    public CompanyService(CompanyRepository companyRepository, WorkspaceRepository workspaceRepository) {
        this.companyRepository = companyRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public List<Company> list(Long workspaceId) { return companyRepository.findByWorkspace_Id(workspaceId); }

    @Transactional
    public Company create(Long workspaceId, Company company) {
        Workspace ws = workspaceRepository.findById(workspaceId).orElseThrow();
        company.setWorkspace(ws);
        return companyRepository.save(company);
    }

    @Transactional
    public Company update(Long workspaceId, Long companyId, Company updated) {
        Company c = companyRepository.findById(companyId).orElseThrow();
        if (!c.getWorkspace().getId().equals(workspaceId)) throw new IllegalArgumentException("Company not in workspace");
        c.setName(updated.getName());
        c.setCnpj(updated.getCnpj());
        c.setStateReg(updated.getStateReg());
        c.setPhone(updated.getPhone());
        c.setEmail(updated.getEmail());
        c.setAddress(updated.getAddress());
        return companyRepository.save(c);
    }

    @Transactional
    public void delete(Long workspaceId, Long companyId) {
        Company c = companyRepository.findById(companyId).orElseThrow();
        if (!c.getWorkspace().getId().equals(workspaceId)) throw new IllegalArgumentException("Company not in workspace");
        companyRepository.delete(c);
    }
}


