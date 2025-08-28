package Aug25;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;



public class DemoTestcases {

	  public static ExtentReports extent;
	    public static ExtentTest test;
	
	@Test
	public void m1()
	{
		test = extent.createTest("Valid Login Test");
        test.log(Status.INFO, "Entering username");
        test.log(Status.INFO, "Entering password");
        test.pass("Login successful");
		assertTrue(true);
		
	}
	@Test
	public void m2()
	{
		test = extent.createTest("Valid Login Test");
        test.log(Status.INFO, "Entering username");
        test.log(Status.INFO, "Entering password");
        test.pass("Login successful");
		assertTrue(true);
	}
	@Test
	public void m3()
	{
		test = extent.createTest("Valid Login Test");
        test.log(Status.INFO, "Entering username");
        test.log(Status.INFO, "Entering password");
        test.pass("Login successful");
		assertTrue(true);	
	}
	
	@Test
	public void m4()
	{
		
		test = extent.createTest("Invalid Login Test");
        test.log(Status.INFO, "Entering wrong username");
        test.log(Status.INFO, "Entering wrong password");
        test.fail("Login failed as expected");
	//	assertTrue(false);
		
	}
	@Test
	public void m5()
	{
		test = extent.createTest("Invalid Login Test");
        test.log(Status.INFO, "Entering wrong username");
        test.log(Status.INFO, "Entering wrong password");
        test.fail("Login failed as expected");
//		assertTrue(false);
	}
	
	 @BeforeSuite
	    public void setupReport() {
	        // Location of report
	        ExtentSparkReporter spark = new ExtentSparkReporter("./target/ExtentReport.html");

	        // Create ExtentReports and attach reporter
	        extent = new ExtentReports();
	        extent.attachReporter(spark);

	        // Add some system info (optional)
	        extent.setSystemInfo("OS", System.getProperty("os.name"));
	        extent.setSystemInfo("Tester", "Santosh");
	    }
	 
	 @AfterSuite
	    public void tearDownReport() {
	        // Write all logs to the report
	        extent.flush();
	    }
}

