package com.fiducia.personenprojekt.services;

import java.util.List;
import java.util.Optional;

import com.fiducia.personenprojekt.repositories.entities.Person;

public interface PersonService {
	
	public boolean speichernOderAendern(Person p) throws PersonenServiceException;
	public boolean loeschen(Person p)throws PersonenServiceException;
	public boolean loeschen(String id)throws PersonenServiceException;
	Optional<Person> findePersonMitId(String id)throws PersonenServiceException;
	List<Person> findeAlle()throws PersonenServiceException;

}
