package com.fiducia.personenprojekt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiducia.personenprojekt.repositories.PersonRepository;
import com.fiducia.personenprojekt.repositories.entities.Person;

@Service
@Transactional(rollbackFor = PersonenServiceException.class)
public class PersonServiceImpl implements PersonService {
	
	
	private final PersonRepository personRepository;
	
	

	public PersonServiceImpl(PersonRepository personRepository) throws PersonenServiceException{
		this.personRepository = personRepository;
	}

	@Override
	public boolean speichernOderAendern(Person p) throws PersonenServiceException {
		try {
			boolean updateFlag =  personRepository.existsById(p.getId());
			
			personRepository.save(p);
			return updateFlag;
			
		} catch (Exception e) {
			throw new PersonenServiceException("Fehler beim Speichern",e);
		}
	}

	@Override
	public boolean loeschen(Person p) throws PersonenServiceException{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loeschen(String id) throws PersonenServiceException{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Person> findePersonMitId(String id) throws PersonenServiceException{
		
		try {
			return personRepository.findById(id);
		} catch (Exception e) {
			throw new PersonenServiceException("Häh?", e);
		}
	}

	@Override
	public List<Person> findeAlle()throws PersonenServiceException{
		try {
			List<Person> retval = new ArrayList<>();
			personRepository.findAll().forEach(retval::add);
			return retval;
		} catch (Exception e) {
			throw new PersonenServiceException("Häh?", e);
		}
	}

}
