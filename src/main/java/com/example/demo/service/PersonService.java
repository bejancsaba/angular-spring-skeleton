package com.example.demo.service;

import com.example.demo.dao.entity.PersonEntity;
import com.example.demo.dao.repository.PersonRepository;
import com.example.demo.domain.PersonModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;

    public PersonModel update(PersonModel person) {
        PersonEntity entity = repository.save(PersonEntity.builder()
                .name(person.getName())
                .age(person.getAge())
                .build()
        );

        return toModel(entity);
    }

    public List<PersonModel> getAllPersons() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private PersonModel toModel(PersonEntity entity) {
        return PersonModel.builder().name(entity.getName()).age(entity.getAge()).build();
    }
}
