package de.fiducia.personen.services;

import java.util.List;
import java.util.UUID;

import de.fiducia.personen.PersonRepositoriy;
import de.fiducia.personen.models.Person;

public class PersonServiceImpl {

	private final PersonRepositoriy repository;
	private final List<String> antipathen;

	public PersonServiceImpl(final PersonRepositoriy repository, final List<String> antipathen) {
		this.repository = repository;
		this.antipathen = antipathen;
	}
	
	// 1. Parameter darf nicht null sein. -> PersonServiceException ##Häkchen
	// 2. Vorname darf nicht null und nicht weniger als 2 enthalten  -> PersonServiceException ##Häkchen
	// 3. Nachname darf nicht null und nicht weniger als 2 enthalten  -> PersonServiceException ##Häkchen
	// 4. Vorname darf nicht Attila sein  -> PersonServiceException ##Häkchen
	// 5. Exception im Repo -> PersonServiceException
	// guter Fall. Person im Repo speichern
	public void speichern(Person person) throws PersonServiceException{
		try {
			speichernImpl(person);
		} catch (RuntimeException e) {
			throw new PersonServiceException("Fehler im Service.");
		}
		
	}

	public void speichern(String vorname, String nachname) throws PersonServiceException{
		
		Person person = Person.builder().vorname(vorname).nachname(nachname).build();
		speichern(person);
	}

	private void speichernImpl(Person person) throws PersonServiceException {
		checkPerson(person);
		createId(person);
		repository.save(person);
	}

	private void createId(Person person) {
		person.setId(UUID.randomUUID().toString());
		
	}

	private void checkPerson(Person person) throws PersonServiceException {
		validatedPerson(person);
		businessCheck(person);
	}

	private void businessCheck(Person person) throws PersonServiceException {
		if(antipathen.contains(person.getVorname())) 
			throw new PersonServiceException("Antipath");
	}

	private void validatedPerson(Person person) throws PersonServiceException {
		if(person == null) 
			throw new PersonServiceException("Person darf nicht null sein.");
		if(person.getVorname() == null || person.getVorname().length() < 2) 
			throw new PersonServiceException("Vorname muss min 2 Zeichen enthalten.");
		if(person.getNachname() == null || person.getNachname().length() < 2) 
			throw new PersonServiceException("Nachname muss min 2 Zeichen enthalten.");
	}
}
