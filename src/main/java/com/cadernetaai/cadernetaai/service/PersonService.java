package com.cadernetaai.cadernetaai.service;

import com.cadernetaai.cadernetaai.domain.Person;
import com.cadernetaai.cadernetaai.domain.Workspace;
import com.cadernetaai.cadernetaai.repository.PersonRepository;
import com.cadernetaai.cadernetaai.repository.WorkspaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final WorkspaceRepository workspaceRepository;
    public PersonService(PersonRepository personRepository, WorkspaceRepository workspaceRepository) {
        this.personRepository = personRepository;
        this.workspaceRepository = workspaceRepository;
    }

    public List<Person> list(Long workspaceId) { return personRepository.findByWorkspace_Id(workspaceId); }

    @Transactional
    public Person create(Long workspaceId, Person person) {
        Workspace ws = workspaceRepository.findById(workspaceId).orElseThrow();
        person.setWorkspace(ws);
        return personRepository.save(person);
    }

    @Transactional
    public Person update(Long workspaceId, Long personId, Person updated) {
        Person p = personRepository.findById(personId).orElseThrow();
        if (!p.getWorkspace().getId().equals(workspaceId)) throw new IllegalArgumentException("Person not in workspace");
        p.setFullName(updated.getFullName());
        p.setCpf(updated.getCpf());
        p.setRg(updated.getRg());
        p.setCrea(updated.getCrea());
        p.setProfession(updated.getProfession());
        p.setPhone(updated.getPhone());
        p.setEmail(updated.getEmail());
        p.setAddress(updated.getAddress());
        p.setNotes(updated.getNotes());
        p.setCompany(updated.getCompany());
        p.setUserId(updated.getUserId());
        return personRepository.save(p);
    }

    @Transactional
    public void delete(Long workspaceId, Long personId) {
        Person p = personRepository.findById(personId).orElseThrow();
        if (!p.getWorkspace().getId().equals(workspaceId)) throw new IllegalArgumentException("Person not in workspace");
        personRepository.delete(p);
    }
}


