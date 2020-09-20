package com.example.demo.service;

import com.example.demo.dao.entity.PersonEntity;
import com.example.demo.dao.repository.PersonRepository;
import com.example.demo.domain.PersonModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonService personService;

    @Test
    void shouldPersistPerson() {
        PersonModel model = new PersonModel("Name", new BigDecimal(123));

        when(personRepository.save(any(PersonEntity.class)))
                .thenReturn(new PersonEntity("id", "dummy", BigDecimal.valueOf(1L)));

        personService.update(model);

        verify(personRepository).save(argThat(entity -> {
            assertThat(entity).isNotNull();
            assertThat(entity.getName()).isEqualTo(model.getName());
            assertThat(entity.getAge()).isEqualTo(model.getAge());
            return true;
        }));
    }

    @Test
    void shouldRetrievePerson() {
        when(personRepository.findAll())
            .thenReturn(Arrays.asList(
                new PersonEntity("id1", "Name1", BigDecimal.valueOf(1L)),
                new PersonEntity("id2", "Name2", BigDecimal.valueOf(2L))
            ));

        List<PersonModel> persons = personService.getAllPersons();
        assertThat(persons).hasSize(2);
    }
}
