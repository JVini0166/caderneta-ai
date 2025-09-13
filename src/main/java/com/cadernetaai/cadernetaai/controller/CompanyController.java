package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.Company;
import com.cadernetaai.cadernetaai.service.CompanyService;
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
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;
    public CompanyController(CompanyService companyService) { this.companyService = companyService; }

    @GetMapping
    public ResponseEntity<List<Company>> list(@RequestParam Long workspaceId) {
        return ResponseEntity.ok(companyService.list(workspaceId));
    }

    @PostMapping
    public ResponseEntity<Company> create(@RequestParam Long workspaceId, @Valid @RequestBody Company body) {
        return ResponseEntity.ok(companyService.create(workspaceId, body));
    }

    @PutMapping
    public ResponseEntity<Company> update(@RequestParam Long workspaceId, @RequestParam Long companyId, @Valid @RequestBody Company body) {
        return ResponseEntity.ok(companyService.update(workspaceId, companyId, body));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long workspaceId, @RequestParam Long companyId) {
        companyService.delete(workspaceId, companyId);
        return ResponseEntity.noContent().build();
    }
}


