package de.fiducia.personen;

import java.util.List;
import java.util.Optional;

import de.fiducia.personen.models.Person;

public interface PersonRepositoriy {
	
	void save(Person person);
	void update(Person person);
	void delete(Person person);
	void delete(String id);
	Optional<Person> findById(String id);
	List<Person> findAll();

}
