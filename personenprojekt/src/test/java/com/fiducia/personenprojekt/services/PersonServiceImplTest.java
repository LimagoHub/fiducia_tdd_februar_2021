package com.fiducia.personenprojekt.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiducia.personenprojekt.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
	@Mock
	private PersonRepository personRepositoryMock;
	@InjectMocks
	private PersonServiceImpl objectUnderTest;
	
	@Test
	void speichernOderAendern_ParameterIsNull_throwsPersonenServiceException() throws Exception{
		assertThrows(PersonenServiceException.class, ()->objectUnderTest.speichernOderAendern(null));
	}

}
