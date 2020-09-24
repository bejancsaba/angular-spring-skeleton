package com.example.demo.controller;

import com.example.demo.domain.DemoResponse;
import com.example.demo.domain.PersonModel;
import com.example.demo.domain.PersonsMessage;
import com.example.demo.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private static final String TOPIC_ROOT = "/topic/";
    private static final String TOPIC = "/topic/persons";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private final PersonService personService;
    private final SimpMessagingTemplate template;

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
    @Secured("ROLE_READ")
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
        template.convertAndSend(TOPIC, new PersonsMessage(personService.getAllPersons()));
    }

    @Scheduled(fixedRate = 3000)
    public void shareTime() {
        personService.getAllPersons().stream()
                .map(PersonModel::getName)
                .forEach(name -> template.convertAndSend(
                        TOPIC_ROOT + name,
                        "The time on this topic called [" + name + "] is: " + DATE_FORMAT.format(new Date())));
    }

    @ApiOperation(
            value = "Retrieves the name parameter multiplied by times in a text wrapped in an object.",
            response = DemoResponse.class
    )
    @GetMapping("/myname")
    public DemoResponse sayMyName(@RequestParam("name") String name, @RequestParam("times") Integer times) {
        return new DemoResponse((name + "\n").repeat(times));
    }

    @GetMapping("/person/deleteAll")
    public void deleteAll() {
        personService.deleteAll();
    }
}
