package com.fiducia.personenprojekt.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fiducia.personenprojekt.repositories.entities.Person;
import com.fiducia.personenprojekt.services.PersonService;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ActiveProfiles("test")
@Sql({"create.sql","insert.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class PersonControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private PersonService personServiceMock;
	
	@Test
	//@Sql({"create.sql","insert.sql"})
	void findPersonById_PersonExists_getBrittni() throws Exception{
		
		Person validPerson = Person.builder().id("012345678901234567890123456789012345").vorname("Max").nachname("Mustermann").build();
		
		when(personServiceMock.findePersonMitId(anyString())).thenReturn(Optional.of(validPerson));
		
		Person person = restTemplate.getForObject("/personen/person/cef9e0e9-c898-442a-ba30-7ef62b7569b9", Person.class);
		assertEquals("012345678901234567890123456789012345", person.getId());
		assertEquals("Max", person.getVorname());
		assertEquals("Mustermann", person.getNachname());
		
	}
	@Test
	//@Sql({"create.sql","insert.sql"})
	void findPersonById_PersonGefunden_StatusIs200OK() throws Exception{
		
		Person validPerson = Person.builder().id("012345678901234567890123456789012345").vorname("Max").nachname("Mustermann").build();
		
		when(personServiceMock.findePersonMitId(anyString())).thenReturn(Optional.of(validPerson));
		
		ResponseEntity<Person> entity = restTemplate.getForEntity("/personen/person/cef9e0e9-c898-442a-ba30-7ef62b7569b9", Person.class);
		Person p = entity.getBody();
		
		assertEquals("012345678901234567890123456789012345", p.getId());
		assertEquals("Max", p.getVorname());
		assertEquals("Mustermann", p.getNachname());
		
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		
	}
	
	
	@Test
	//@Sql({"create.sql","insert.sql"})
	void findPersonById_PersonNichtGefunden_StatusIs404NotFound() throws Exception{
		
		
		
		when(personServiceMock.findePersonMitId(anyString())).thenReturn(Optional.empty());
		
		ResponseEntity<Person> entity = restTemplate.getForEntity("/personen/person/cef9e0e9-c898-442a-ba30-7ef62b7569b9", Person.class);
			
		assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
		
	}

	
//	@Test
//	void findPersonById_PersonExists_getBrittni_version2() throws Exception{
//		String string = restTemplate.getForObject("/personen/person/cef9e0e9-c898-442a-ba30-7ef62b7569b9", String.class);
//		
//		System.out.println(string);
//	}
//	
	
}
