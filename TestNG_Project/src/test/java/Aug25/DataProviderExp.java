package Aug25;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v132.fedcm.model.LoginState;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.ReportStats;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DataProviderExp {

	WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest test;

	@Parameters({ "Browser" })
	@BeforeMethod
	public void setup(String brw) {

		switch (brw) {
		case "Edge": {

			File edgeDriverPath = new File("C:\\Users\\devga\\Desktop\\edge\\edgedriver_win64\\msedgedriver.exe");

			EdgeDriverService service = new EdgeDriverService.Builder().usingDriverExecutable(edgeDriverPath)
					.usingAnyFreePort().build();

			driver = new EdgeDriver(service);

			break;
		}
		case "Chrome": {

			driver = new ChromeDriver();
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + brw);
		}

	}

	@Test(dataProvider = "dp")
	public void AdminLogin(String user, String pass) throws Throwable {
		driver.get("http://orangehrm.qedgetech.com/symfony/web/index.php/auth/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.findElement(By.id("txtUsername")).clear();
		driver.findElement(By.id("txtUsername")).sendKeys(user);
		driver.findElement(By.id("txtPassword")).clear();
		driver.findElement(By.id("txtPassword")).sendKeys(pass);
		driver.findElement(By.id("btnLogin")).click();
		String expected = "dashboard";
		String actual = driver.getCurrentUrl();
		System.out.println(actual);

		if (actual.contains(expected)) {
			test = extent.createTest("Valid Login Test");
	        test.log(Status.PASS, "Entering username");
	        
	        test.pass("Login successful");
		} else {
			test = extent.createTest("Invalid Login Test");
	        test.log(Status.FAIL, "Entering wrong username");
	        
	        test.fail("Login failed as expected");
		}

	}

	@DataProvider
	public Object[][] dp() {
		Object login[][] = { { "Santosh", "Test@123" }, { "Sagar", "Test@123" }, { "Admin", "Qedge123!@#" },
				{ "Ketan", "Ketan@123" }, { "Mehul", "Mehul@123" }, { "Pratik", "Pratik@123" } };

		return login;
	}

	@AfterMethod
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

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
