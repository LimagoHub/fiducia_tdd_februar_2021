package de.fiducia.mockdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MyServiceTest {
	@Mock
	private MyDependencyInterface dependencyInterfaceMock;
	
	@InjectMocks
	private MyService objectUnderTest;
	
	@Test
	void testThatDependencyIsInjected() {
		objectUnderTest.myServiceFunction(10);
	}
	
	@Test
	void testVoidMethod() throws Exception{
		
		//doNothing().when(dependencyInterfaceMock).voidFunction(anyString());
		
		assertEquals(20,objectUnderTest.myServiceDoSomethigswithVoidMethod(10));
		verify(dependencyInterfaceMock).voidFunction("Hallo");
	}

	@Test
	void testVoidMethodWithException() throws Exception{
		
		//doNothing().when(dependencyInterfaceMock).voidFunction(anyString());
		// Diese Version bei Void-Methoden
		doThrow(new ArithmeticException("Hallo")).when(dependencyInterfaceMock).voidFunction(anyString());
		MyServiceException ex = assertThrows(MyServiceException.class, ()->objectUnderTest.myServiceDoSomethigswithVoidMethod(5));
		assertEquals("upps", ex.getMessage());
	}

	@Test
	void testIntMethod() throws Exception{
		
		when(dependencyInterfaceMock.intFunctionWithoutParam()).thenReturn(42);
		
		assertEquals(52,objectUnderTest.myServiceDiSomethingWithIntMethod(10));
		verify(dependencyInterfaceMock).intFunctionWithoutParam();
	}

	@Test
	void testIntMethodWithException() throws Exception{
		
		//doNothing().when(dependencyInterfaceMock).voidFunction(anyString());
		// Diese Version bei Void-Methoden
		//doThrow(new ArithmeticException("Hallo")).when(dependencyInterfaceMock).voidFunction(anyString());
		when(dependencyInterfaceMock.intFunctionWithoutParam()).thenThrow(new ArithmeticException("Hallo"));
		
		MyServiceException ex = assertThrows(MyServiceException.class, ()->objectUnderTest.myServiceDiSomethingWithIntMethod(5));
		assertEquals("upps", ex.getMessage());
	}
	
	@Test
	void testMethod3() {
		when(dependencyInterfaceMock.intFunctionWithParam(anyString())).thenThrow(new ArithmeticException("Hallo"));
		MyServiceException ex = assertThrows(MyServiceException.class, ()->objectUnderTest.myServiceDoSomethingWithIntMethodWithParam(5));
		assertEquals("upps", ex.getMessage());
	}

	@Test
	void testMethod4()throws Exception {
		//when(dependencyInterfaceMock.intFunctionWithParam(anyString())).thenThrow(new ArithmeticException("Hallo"));
		objectUnderTest.myServiceDoSomethingWithIntMethodWithParam(5);
		verify(dependencyInterfaceMock).intFunctionWithParam("Hallo");
	}

	@Test
	void testMethod5()throws Exception {
		when(dependencyInterfaceMock.intFunctionWithParam(anyString())).thenReturn(10);
		assertEquals(15, objectUnderTest.myServiceDoSomethingWithIntMethodWithParam(5));
		verify(dependencyInterfaceMock).intFunctionWithParam("Hallo");
	}



}
