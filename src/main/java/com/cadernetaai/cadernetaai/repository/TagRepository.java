package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByWorkspace_Id(Long workspaceId);
}


