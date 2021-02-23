package de.fiducia.personen.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.fiducia.personen.PersonRepositoriy;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
	
	@Mock
	private PersonRepositoriy repositoryMock;
	@InjectMocks
	private PersonServiceImpl objectUnderTest;
	
	
	@Test
	void speichern_personParameterNull_throwsPersonServiceException() throws Exception{
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(null));
		assertEquals("Person darf nicht null sein.", ex.getMessage());
	
	}

}
