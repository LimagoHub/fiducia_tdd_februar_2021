package de.fiducia.personen.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.fiducia.personen.PersonRepositoriy;
import de.fiducia.personen.models.Person;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
	
	private static final String VALID_FIRTSTNAME = "John";
	private static final String VALID_LASTNAME = "Doe";
	private static final String VALID_ID = UUID.randomUUID().toString();
	@Mock
	private PersonRepositoriy repositoryMock;
	
	@Mock
	private List<String> antipathenMock;
	
	@InjectMocks
	private PersonServiceImpl objectUnderTest;
	
	
	@Test
	void speichern_personParameterNull_throwsPersonServiceException() throws Exception{
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(null));
		assertEquals("Person darf nicht null sein.", ex.getMessage());
	
	}

	@Test
	void speichern_VornameNull_throwsPersonServiceException() throws Exception{
		
		Person person = Person.builder().id(VALID_ID).vorname(null).nachname(VALID_LASTNAME).build();
		
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Vorname muss min 2 Zeichen enthalten.", ex.getMessage());
	
	}

	@Test
	void speichern_VornameZuKurz_throwsPersonServiceException() throws Exception{
		
		Person person = Person.builder().id(VALID_ID).vorname("J").nachname(VALID_LASTNAME).build();
		
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Vorname muss min 2 Zeichen enthalten.", ex.getMessage());
	
	}

	@Test
	void speichern_NachnameNull_throwsPersonServiceException() throws Exception{
		
		Person person = Person.builder().id(VALID_ID).vorname(VALID_FIRTSTNAME).nachname(null).build();
		
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Nachname muss min 2 Zeichen enthalten.", ex.getMessage());
	
	}

	@Test
	void speichern_NachnameZuKurz_throwsPersonServiceException() throws Exception{
		
		Person person = Person.builder().id(VALID_ID).vorname(VALID_FIRTSTNAME).nachname("D").build();
		
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Nachname muss min 2 Zeichen enthalten.", ex.getMessage());
	
	}

	@Test
	void speichern_Antipath_throwsPersonServiceException() throws Exception{
		
		Person person = Person.builder().id(VALID_ID).vorname(VALID_FIRTSTNAME).nachname("Doe").build();
		
		when(antipathenMock.contains(anyString())).thenReturn(true);
		
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Antipath", ex.getMessage());
	
	}

	@Test
	void speichern_RuntimeExceptionInUnderlyingService_throwsPersonServiceException() throws Exception{
		
		Person person = Person.builder().id(VALID_ID).vorname(VALID_FIRTSTNAME).nachname(VALID_LASTNAME).build();
		
		doThrow(new ArrayIndexOutOfBoundsException()).when(repositoryMock).save(any());
		
		PersonServiceException ex = assertThrows(PersonServiceException.class, ()->objectUnderTest.speichern(person));
		assertEquals("Fehler im Service.", ex.getMessage());
	
	}

	@Test
	void speichern_ParameterPassedToUnderlyingService_ParameterReceived() throws Exception{
		
		final Person person = Person.builder().id(VALID_ID).vorname(VALID_FIRTSTNAME).nachname(VALID_LASTNAME).build();
		
		//doThrow(new ArrayIndexOutOfBoundsException()).when(repositoryMock).save(any());
		when(antipathenMock.contains(anyString())).thenReturn(false);
		
		objectUnderTest.speichern(person);
		verify(repositoryMock, times(1)).save(person);
	
	}

	@Test
	void speichern_HappyDay_PersonSavedInRepository() throws Exception{
		
		final Person person = Person.builder().id(VALID_ID).vorname(VALID_FIRTSTNAME).nachname(VALID_LASTNAME).build();
		
		//doThrow(new ArrayIndexOutOfBoundsException()).when(repositoryMock).save(any());
		when(antipathenMock.contains(anyString())).thenReturn(false);
		objectUnderTest.speichern(person);
		
		// Fachliche Rege wird überprüft (Kein Algorithmus)
		InOrder inOrder = inOrder(antipathenMock, repositoryMock);
		inOrder.verify(antipathenMock, times(1)).contains(VALID_FIRTSTNAME);
		inOrder.verify(repositoryMock, times(1)).save(person);
	
	}


}
