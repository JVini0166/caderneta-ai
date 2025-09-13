package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.Company;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByWorkspace_Id(Long workspaceId);
}


