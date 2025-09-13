package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.Approval;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByProject_Id(Long projectId);
}


