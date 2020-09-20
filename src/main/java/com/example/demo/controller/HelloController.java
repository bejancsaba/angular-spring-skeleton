package com.example.demo.controller;

import com.example.demo.domain.DemoResponse;
import com.example.demo.domain.PersonModel;
import com.example.demo.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final PersonService personService;

    @ApiOperation(
            value = "Retrieves a simple object with the HelloWorld! text.",
            response = DemoResponse.class
    )
    @GetMapping("/hello")
    public DemoResponse hello() {
        return new DemoResponse("HelloWorld!");
    }

    @ApiOperation(
            value = "Retrieves all Persons currently present in the system.",
            response = PersonModel.class,
            responseContainer = "List"
    )
    @GetMapping("/person/all")
    public List<PersonModel> getAll() {
        log.info("Getting all person information");
        return personService.getAllPersons();
    }

    @ApiOperation(
            value = "Adds one person to the system."
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 201, message = "Created / Updated")
            }
    )
    @PostMapping("/person")
    public void addPerson(@RequestBody PersonModel person) {
        log.info("Persisting Person: " + person.getName());
        personService.update(person);
    }

    @ApiOperation(
            value = "Retrieves the name parameter multiplied by times in a text wrapped in an object.",
            response = DemoResponse.class
    )
    @GetMapping("/myname")
    public DemoResponse sayMyName(@RequestParam("name") String name, @RequestParam("times") Integer times) {
        return new DemoResponse((name + "\n").repeat(times));
    }
}
