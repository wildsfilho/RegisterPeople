package com.register.people.service;

import com.register.people.domain.Contact;
import com.register.people.domain.People;
import com.register.people.repository.PeopleRepository;
import com.register.people.service.validator.ValidatePeople;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PeopleServiceTest {


    private static final String NAME_PEOPLE = "TESTENAME";
    private static final Integer ID = 1;
    private static final Integer PAGE = 0;
    private static final Integer LINES_PER_PAGE = 24;
    private static final String CPF = "12345678912";

    @Mock
    private PeopleRepository peopleRepository;
    @Mock
    private ValidatePeople validatePeople;
    @Rule
    private ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private PeopleService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_get_peoples() {

        List<People> list = new ArrayList<>();
        People people1 = mock(People.class);
        People people2 = mock(People.class);
        list.add(people1);
        list.add(people2);
        Page<People> pagedResponse = new PageImpl(list);
        PageRequest pageRequest = PageRequest.of(PAGE.intValue(), LINES_PER_PAGE.intValue());
        when(peopleRepository.findAll(pageRequest)).thenReturn(pagedResponse);
        Page<People> page = service.findPage(PAGE, LINES_PER_PAGE);
        List<People> content = page.getContent();
        assertEquals(2, content.size());
        assertEquals(people1, content.get(0));
        assertEquals(people2, content.get(1));
    }

    @Test
    void should_get_people_by_name() {

        People people1 = mock(People.class);
        when(people1.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleRepository.findByNome(NAME_PEOPLE)).thenReturn(people1);
        People people = service.getPeopleByName(NAME_PEOPLE);
        assertEquals(people1, people);
        assertEquals(NAME_PEOPLE, people.getNome());

    }

    @Test
    void should_get_people_by_id() {
        People peopleMock = mock(People.class);
        when(peopleMock.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleRepository.findById(ID)).thenReturn(Optional.of(peopleMock));
        People people = service.getPeopleById(ID);
        assertEquals(peopleMock, people);
        assertEquals(NAME_PEOPLE, people.getNome());

    }

    @Test
    void should_get_people_by_id_not_exist() {
        when(peopleRepository.findById(ID)).thenReturn(Optional.ofNullable(null));
        People people = service.getPeopleById(ID);
        assertNull(people);

    }

    @Test
    void should_delete_b_id() {
        service.deletePeople(ID);
        verify(peopleRepository).deleteById(ID);

    }

    @Test
    void should_create_people_is_invalid() throws Exception {
        People people = mock(People.class);
        LocalDate date = LocalDate.now().plusDays(2);
        Contact contact = mock(Contact.class);
        when(people.getContact()).thenReturn(Arrays.asList(contact));
        when(people.getData_nascimento()).thenReturn(date);
        when(people.getNome()).thenReturn(NAME_PEOPLE);
        when(validatePeople.validatePeople(any())).thenReturn(Arrays.asList("Error"));
        service.createPeople(people);
        verify(peopleRepository,never()).save(people);

    }

    @Test
    void should_create_people() throws Exception {
        People people = mock(People.class);
        Contact contact = mock(Contact.class);
        when(people.getContact()).thenReturn(Arrays.asList(contact));
        when(people.getNome()).thenReturn(NAME_PEOPLE);
        service.createPeople(people);
        verify(peopleRepository).save(people);

    }

    @Test
    void should_update_people() {
        People people = new People();
        People newPeople = new People();
        Contact contact = new Contact();
        people.setContact(Arrays.asList(contact));
        people.setNome(NAME_PEOPLE);
        newPeople.setNome(NAME_PEOPLE);
        newPeople.setCpf(CPF);
        when(peopleRepository.findById(ID)).thenReturn(Optional.of(people));
        when(peopleRepository.save(people)).thenReturn(newPeople);
        People update = service.update(newPeople, ID);
        assertEquals(NAME_PEOPLE, update.getNome());
        assertEquals(CPF, update.getCpf());

    }
}