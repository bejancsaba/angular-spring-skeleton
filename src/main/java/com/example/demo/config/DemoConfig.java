package com.example.demo.config;

import com.example.demo.dao.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.example.demo")
public class DemoConfig {

    @Bean
    public PersonService personService(PersonRepository repository) {
        return new PersonService(repository);
    }
}
