package com.register.people.controller;

import com.register.people.domain.People;
import com.register.people.domain.dto.ResponsePeopleDto;
import com.register.people.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/people")
public class PeopleApi {

    @Autowired
    private PeopleService peopleService;

    @GetMapping("/get")
    public ResponseEntity<Page<People>> getPeoplePage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage) {
        Page<People> list = peopleService.findPage(page, linesPerPage);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{name}")
    public ResponseEntity<People> getPeopleByName(@PathVariable("name") String name) {
        People peopleByName = peopleService.getPeopleByName(name);
        return ResponseEntity.ok().body(peopleByName);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<People> getPeopleById(@PathVariable("id") Integer id) {
        People peopleById = peopleService.getPeopleById(id);
        return ResponseEntity.ok().body(peopleById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeopleById(@PathVariable("id") Integer id) {
        peopleService.deletePeople(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ResponsePeopleDto> createPeople(@Valid @RequestBody People people) throws Exception {
        ResponsePeopleDto newpeople = peopleService.createPeople(people);
        return ResponseEntity.ok().body(newpeople);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody People people, @PathVariable Integer id) {
        peopleService.update(people, id);
        return ResponseEntity.noContent().build();
    }
}
