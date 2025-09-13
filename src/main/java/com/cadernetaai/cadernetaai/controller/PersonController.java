package com.cadernetaai.cadernetaai.controller;

import com.cadernetaai.cadernetaai.domain.Person;
import com.cadernetaai.cadernetaai.service.PersonService;
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
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;
    public PersonController(PersonService personService) { this.personService = personService; }

    @GetMapping
    public ResponseEntity<List<Person>> list(@RequestParam Long workspaceId) {
        return ResponseEntity.ok(personService.list(workspaceId));
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestParam Long workspaceId, @Valid @RequestBody Person body) {
        return ResponseEntity.ok(personService.create(workspaceId, body));
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestParam Long workspaceId, @RequestParam Long personId, @Valid @RequestBody Person body) {
        return ResponseEntity.ok(personService.update(workspaceId, personId, body));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long workspaceId, @RequestParam Long personId) {
        personService.delete(workspaceId, personId);
        return ResponseEntity.noContent().build();
    }
}


