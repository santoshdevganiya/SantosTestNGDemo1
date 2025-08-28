package Aug25;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;
public class test1 {


	
	
	
	
	

	@BeforeTest
	public void BeforeTest()
	{
		System.out.println("@@BeforeTest");
	}
	
	
	@Test
	public void test1()
	{
		System.out.println("@Test Method");
	}
	
	
	@BeforeMethod
	public void BeforeMethod()
	{
		System.out.println("@BeforeMethod");
	}
	
}

