package com.cadernetaai.cadernetaai.repository;

import com.cadernetaai.cadernetaai.domain.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByWorkspace_Id(Long workspaceId);
}


