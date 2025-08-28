package Aug25;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class OrangeHrm {




	WebDriver driver;

	@Parameters({"url"})
	@BeforeTest
	public void setup(String appUrl)
	{

		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(appUrl);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	@Parameters({"username","password"})
	@Test
	public void login(String username,String password)
	{

		driver.findElement(By.id("txtUsername")).sendKeys(username);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).click();

	}

	@AfterMethod
	public void tearDown()
	{

		driver.quit();
	}
}
