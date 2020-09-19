package com.example.demo.controller;

import com.example.demo.domain.DemoResponse;
import com.example.demo.domain.PersonModel;
import com.example.demo.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final PersonService personService;

    @GetMapping("/hello")
    public DemoResponse hello () {
        return new DemoResponse("HelloWorld!");
    }

    @GetMapping("/person/all")
    public List<PersonModel> getAll() {
        log.info("Getting all person information");
        return personService.getAllPersons();
    }

    @PostMapping("/person")
    public void addPerson(@RequestBody PersonModel person) {
        log.info("Persisting Person: " + person.getName());
        personService.update(person);
    }

    @GetMapping("/myname")
    public DemoResponse sayMyName(@RequestParam("name") String name, @RequestParam("times") Integer times) {
        return new DemoResponse((name + "\n").repeat(times));
    }
}
