package de.fiducia.mockdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MyServiceTest {
	@Mock
	private MyDependencyInterface dependencyInterfaceMock;
	@InjectMocks
	private MyService objectUnderTest;
	
	
	@Test
	public void test_injection() {
		Mockito.when(dependencyInterfaceMock.intFunctionWithoutParam()).thenReturn(10);
		
		assertEquals(20, objectUnderTest.myServiceFunction(5));
		
	}

}
