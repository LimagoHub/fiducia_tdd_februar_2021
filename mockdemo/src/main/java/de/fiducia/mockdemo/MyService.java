package de.fiducia.mockdemo;

public class MyService {
	
	private final MyDependencyInterface dependencyInterface;

	public MyService(MyDependencyInterface dependencyInterface) {
		this.dependencyInterface = dependencyInterface;
	}
	
	public int myServiceFunction(int a) {
		return dependencyInterface.intFunctionWithoutParam() + 10;
	}

}
