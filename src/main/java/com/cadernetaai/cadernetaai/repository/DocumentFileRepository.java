package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.DocumentFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFileRepository extends JpaRepository<DocumentFile, Long> {
    List<DocumentFile> findByProject_Id(Long projectId);
}


