package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}


