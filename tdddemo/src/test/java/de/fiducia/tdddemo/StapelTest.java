package de.fiducia.tdddemo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StapelTest {
	
	private Stapel objectUnderTest;
	
	@BeforeEach
	public void setUp() {
		objectUnderTest = new Stapel();
	}

	@Test
	public void isEmpty_EmptyStack_returnsTrue() {  // Welche Methode, welcher Fall, welche Erwartung
		// Arrange
		
		
		// Action
		boolean ergebnis = objectUnderTest.isEmpty();
		
		// Assertion
		assertTrue(ergebnis);
		
	}
	
	@Test
	public void isEmpty_NotEmptyStack_returnsFalse() throws Exception{  // Welche Methode, welcher Fall, welche Erwartung
		// Arrange
		
		objectUnderTest.push(new Object());
		
		// Action
		boolean ergebnis = objectUnderTest.isEmpty();
		
		// Assertion
		assertFalse(ergebnis);
		
	}
	
	@Test
	public void isEmpty_StackIsEmptyAgain_returnsTrue() throws Exception{  // Welche Methode, welcher Fall, welche Erwartung
		// Arrange
		
		objectUnderTest.push(new Object());
		objectUnderTest.pop();
		
		// Action
		boolean ergebnis = objectUnderTest.isEmpty();
		
		// Assertion
		assertTrue(ergebnis);
		
	}
	
	@Test
	public void isFull_IsNotFull_returnsFalse() throws Exception{
		
		boolean ergebnis = objectUnderTest.isFull();;
		
		assertFalse(ergebnis);
		
		
	}
	
	
	@Test
	public void isFull_StapelIsFull_returnsTrue() throws Exception{
		fillUpToLimit();
		
		boolean ergebnis = objectUnderTest.isFull();;
		
		assertTrue(ergebnis);
		
		
	}
	
	@Test
	public void push_Overflow_throwsStapelException() throws Exception{
		fillUpToLimit();
		
		StapelException ex = assertThrows(StapelException.class, ()->objectUnderTest.push(new Object()));
		assertEquals("Overflow", ex.getMessage());
		
	}

	private void fillUpToLimit() throws StapelException {
		for (int i = 0; i < 10; i++) {
			objectUnderTest.push(new Object());
		}
	}
	
	
	
}
