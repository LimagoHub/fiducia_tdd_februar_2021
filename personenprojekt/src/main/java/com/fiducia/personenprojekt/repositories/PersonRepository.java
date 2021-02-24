package com.fiducia.personenprojekt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fiducia.personenprojekt.repositories.entities.Person;

public interface PersonRepository extends CrudRepository<Person, String> {

	List<Person> findByVorname(String vorname);
	List<Person> findAllByNachname(String nachname);
}
