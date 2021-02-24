package com.fiducia.personenprojekt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiducia.personenprojekt.repositories.entities.Person;
import com.fiducia.personenprojekt.services.PersonService;
import com.fiducia.personenprojekt.services.PersonenServiceException;

@RestController
@RequestMapping("/personen")
public class PersonController {
	
	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@GetMapping(path = "/person/{id}")
	public ResponseEntity<Person> findPersonById(@PathVariable String id) throws PersonenServiceException{
		return ResponseEntity.of(personService.findePersonMitId(id));
	}
	
	

}
