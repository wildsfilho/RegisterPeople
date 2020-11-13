package com.register.people.service.validator;

import com.register.people.domain.Contact;
import com.register.people.domain.People;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class ValidatePeopleTest {

    private static final String CPF = "123456789";
    private static final String NOME = "nomeTeste";

    @InjectMocks
    private ValidatePeople validatePeople;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_validate_people_all_validation_ok(){
        People people = new People();
        Contact contact = new Contact();
        people.setContact(Arrays.asList(contact));
        people.setCpf(CPF);
        people.setData_nascimento(LocalDate.now());
        people.setNome(NOME);
        List<String> strings = validatePeople.validatePeople(people);
        Assert.assertTrue(strings.isEmpty());
    }

    @Test
    public void should_validate_people_validation_date_of_birth(){
        People people = new People();
        Contact contact = new Contact();
        people.setContact(Arrays.asList(contact));
        people.setCpf(CPF);
        people.setData_nascimento(LocalDate.now().plusDays(2L));
        people.setNome(NOME);
        List<String> strings = validatePeople.validatePeople(people);
        Assert.assertFalse(strings.isEmpty());
        Assert.assertEquals("Data de nascimento invalida.", strings.get(0));
    }

    @Test
    public void should_validate_people_validation_contact(){
        People people = new People();
        people.setContact(Arrays.asList());
        people.setCpf(CPF);
        people.setData_nascimento(LocalDate.now());
        people.setNome(NOME);
        List<String> strings = validatePeople.validatePeople(people);
        Assert.assertFalse(strings.isEmpty());
        Assert.assertEquals("É necessário pelo menos um contato", strings.get(1));
    }

    @Test
    public void should_validate_people_validation_all_error(){
        People people = new People();
        people.setContact(Arrays.asList());
        people.setCpf(CPF);
        people.setData_nascimento(LocalDate.now().plusDays(2L));
        people.setNome(NOME);
        List<String> strings = validatePeople.validatePeople(people);
        Assert.assertFalse(strings.isEmpty());
        Assert.assertEquals("Data de nascimento invalida.", strings.get(0));
        Assert.assertEquals("É necessário pelo menos um contato", strings.get(1));
    }

}