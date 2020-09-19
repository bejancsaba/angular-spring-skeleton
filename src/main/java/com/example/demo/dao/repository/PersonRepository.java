package com.example.demo.dao.repository;

import com.example.demo.dao.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, String> {
}
