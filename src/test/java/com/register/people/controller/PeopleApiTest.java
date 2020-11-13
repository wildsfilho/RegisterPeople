package com.register.people.controller;

import com.register.people.domain.Contact;
import com.register.people.domain.People;
import com.register.people.service.PeopleService;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class PeopleApiTest {

    private static final String NAME_PEOPLE = "TESTENAME";
    private static final Integer ID = 1;
    private static final Integer PAGE = 0;
    private static final Integer LINES_PER_PAGE = 24;
    private static final String CPF = "12345678912";

    @Mock
    private PeopleService peopleService;
    @InjectMocks
    private PeopleApi api;
    @Rule
    private ExpectedException expectedException = ExpectedException.none();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_get_peoples() {
        People people = mock(People.class);
        Contact contact = mock(Contact.class);
        when(people.getContact()).thenReturn(Arrays.asList(contact));
        when(people.getNome()).thenReturn(NAME_PEOPLE);
        List<People> peoples = Arrays.asList(people);
        Page<People> pagedResponse = new PageImpl(peoples);
        when(peopleService.findPage(0, 24)).thenReturn(pagedResponse);
        ResponseEntity<Page<People>> peoplePage = api.getPeoplePage(PAGE, LINES_PER_PAGE);
        verify(peopleService, times(1)).findPage(PAGE, LINES_PER_PAGE);
        Assertions.assertEquals(1, peoplePage.getBody().getTotalElements());
        Assertions.assertEquals(1, peoplePage.getBody().getContent().get(0).getContact().size());
        Assertions.assertEquals(NAME_PEOPLE, peoplePage.getBody().getContent().get(0).getNome());
    }

    @Test
    public void should_get_peoples_not_exist_in_contact() {
        People people = mock(People.class);
        when(people.getNome()).thenReturn(NAME_PEOPLE);
        List<People> peoples = Arrays.asList(people);
        Page<People> pagedResponse = new PageImpl(peoples);
        when(peopleService.findPage(PAGE, LINES_PER_PAGE)).thenReturn(pagedResponse);
        ResponseEntity<Page<People>> peoplePage = api.getPeoplePage(PAGE, LINES_PER_PAGE);
        verify(peopleService, times(1)).findPage(PAGE, LINES_PER_PAGE);
        Assertions.assertEquals(1, peoplePage.getBody().getTotalElements());
        Assertions.assertEquals(0, peoplePage.getBody().getContent().get(0).getContact().size());
        Assertions.assertEquals(NAME_PEOPLE, peoplePage.getBody().getContent().get(0).getNome());
    }

    @Test
    public void should_get_people_by_name() {
        People peopleMock = mock(People.class);
        Contact contactMock = mock(Contact.class);
        when(peopleMock.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleMock.getContact()).thenReturn(Arrays.asList(contactMock));
        when(peopleService.getPeopleByName(NAME_PEOPLE)).thenReturn(peopleMock);
        ResponseEntity<People> people = api.getPeopleByName(NAME_PEOPLE);
        verify(peopleService, times(1)).getPeopleByName(NAME_PEOPLE);
        People body = people.getBody();
        Assertions.assertEquals(peopleMock, body);
        Assertions.assertEquals(NAME_PEOPLE, body.getNome());
        Assertions.assertEquals(1, body.getContact().size());
        Assertions.assertEquals(contactMock, body.getContact().get(0));
    }

    @Test
    public void should_get_people_by_name_not_contains_contact() {
        People peopleMock = mock(People.class);
        when(peopleMock.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleService.getPeopleByName(NAME_PEOPLE)).thenReturn(peopleMock);
        ResponseEntity<People> people = api.getPeopleByName(NAME_PEOPLE);
        verify(peopleService, times(1)).getPeopleByName(NAME_PEOPLE);
        People body = people.getBody();
        Assertions.assertEquals(peopleMock, body);
        Assertions.assertEquals(peopleMock.getNome(), body.getNome());
    }

    @Test
    public void should_get_people_by_name_not_exist() {
        when(peopleService.getPeopleByName(NAME_PEOPLE)).thenReturn(null);
        ResponseEntity<People> people = api.getPeopleByName(NAME_PEOPLE);
        verify(peopleService, times(1)).getPeopleByName(NAME_PEOPLE);
        People body = people.getBody();
        Assertions.assertNull(body);
    }

    @Test
    public void should_get_people_by_id() {
        People peopleMock = mock(People.class);
        Contact contactMock = mock(Contact.class);
        when(peopleMock.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleMock.getContact()).thenReturn(Arrays.asList(contactMock));
        when(peopleService.getPeopleById(ID)).thenReturn(peopleMock);
        ResponseEntity<People> people = api.getPeopleById(ID);
        verify(peopleService, times(1)).getPeopleById(ID);
        People body = people.getBody();
        Assertions.assertEquals(peopleMock, body);
        Assertions.assertEquals(NAME_PEOPLE, body.getNome());
        Assertions.assertEquals(1, body.getContact().size());
        Assertions.assertEquals(contactMock, body.getContact().get(0));
    }

    @Test
    public void should_get_people_by_id_not_contains_contact() {
        People peopleMock = mock(People.class);
        when(peopleMock.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleService.getPeopleById(ID)).thenReturn(peopleMock);
        ResponseEntity<People> people = api.getPeopleById(ID);
        verify(peopleService, times(1)).getPeopleById(ID);
        People body = people.getBody();
        Assertions.assertEquals(peopleMock, body);
        Assertions.assertEquals(NAME_PEOPLE, body.getNome());
    }

    @Test
    public void should_get_people_by_id_not_exist() {
        when(peopleService.getPeopleById(ID)).thenReturn(null);
        ResponseEntity<People> people = api.getPeopleById(ID);
        verify(peopleService, times(1)).getPeopleById(ID);
        People body = people.getBody();
        Assertions.assertNull(body);
    }

    @Test
    public void should_create_people() throws Exception {
        People peopleMock = mock(People.class);
        Contact contactMock = mock(Contact.class);
        when(peopleMock.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleMock.getData_nascimento()).thenReturn(LocalDate.now());
        when(peopleMock.getCpf()).thenReturn(CPF);
        when(peopleMock.getContact()).thenReturn(Arrays.asList(contactMock));
        api.createPeople(peopleMock);
        verify(peopleService, times(1)).createPeople(peopleMock);
    }

    @Test
    public void should_delete_people() {
        api.deletePeopleById(ID);
        verify(peopleService, times(1)).deletePeople(ID);
    }

    @Test
    void update() {
        People peopleMock = mock(People.class);
        People peopleNew = mock(People.class);
        Contact contactMock = mock(Contact.class);
        LocalDate localDate = LocalDate.now();
        localDate.plusDays(1L);
        when(peopleMock.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleMock.getData_nascimento()).thenReturn(localDate);
        when(peopleMock.getContact()).thenReturn(Arrays.asList(contactMock));
        when(peopleNew.getNome()).thenReturn(NAME_PEOPLE);
        when(peopleNew.getCpf()).thenReturn(CPF);
        api.update(peopleNew, ID);
        verify(peopleService).update(peopleNew, ID);
    }
}