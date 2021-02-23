package de.fiducia.mockdemo;

public class MyService {
	
	private final MyDependencyInterface dependencyInterface;

	public MyService(final MyDependencyInterface dependencyInterface) {
		this.dependencyInterface = dependencyInterface;
	}
	
	public int myServiceFunction(int a) {
		return dependencyInterface.intFunctionWithoutParam() + 10;
	}

	public int myServiceDoSomethigswithVoidMethod(int a) throws MyServiceException {
		try {
			dependencyInterface.voidFunction("Hallo");
			return a + 10;
		} catch (Exception e) {
			throw new MyServiceException("upps",e);
		}
	}
	
	public int myServiceDiSomethingWithIntMethod(int a) throws MyServiceException{
		try {
			return dependencyInterface.intFunctionWithoutParam() + a;
		} catch (Exception e) {
			throw new MyServiceException("upps",e);
		}
	}

}
