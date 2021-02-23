package de.fiducia.personen.services;

import de.fiducia.personen.PersonRepositoriy;
import de.fiducia.personen.models.Person;

public class PersonServiceImpl {

	private final PersonRepositoriy repository;

	public PersonServiceImpl(final PersonRepositoriy repository) {
		this.repository = repository;
	}
	
	// 1. Parameter darf nicht null sein. -> PersonServiceException
	// 2. Vorname darf nicht null und nicht weniger als 2 enthalten  -> PersonServiceException
	// 3. Nachname darf nicht null und nicht weniger als 2 enthalten  -> PersonServiceException
	// 4. Vorname darf nicht Attila sein  -> PersonServiceException
	// 5. Exception im Repo -> PersonServiceException
	// guter Fall. Person im Repo speichern
	public void speichern(Person person) throws PersonServiceException{
		
	}
}
