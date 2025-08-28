package Aug25;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class DataproviderwithExcelDemo extends ExcelSheetReaderLogic {
	WebDriver driver;
	private static Workbook wb;

	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();

	}

	@DataProvider
	public Object[][] getData() throws Throwable {
		// String filePath = "src/test/resources/TestData.xlsx";
		return getSheetData();
	}

	@Test(dataProvider = "getData")
	public void AdminLogin(int rowIndex, String user, String pass) throws Throwable {

		System.out.println("Testing Row: " + rowIndex + " | " + user + " | " + pass);

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
			System.out.println("PASS");
			AddResult("Pass", rowIndex);
			System.out.println(rowIndex);
			test = extent.createTest("Valid Login Test");
	        test.log(Status.PASS, "Entering username");

	        test.pass("Login successful");
		} else {
			test = extent.createTest("Invalid Login Test");
	        test.log(Status.FAIL, "Entering wrong username");

	        test.fail("Login failed as expected");
			AddResult("Failed", rowIndex);
			System.out.println("FAILED");

			System.out.println(rowIndex);
		}

	}

	@AfterMethod
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

	}

	@BeforeSuite
	public void before_Save() throws Exception, IOException {

		// Location of report
				ExtentSparkReporter spark = new ExtentSparkReporter("./target/ExtentReport.html");
				spark.config().setEncoding("utf-8");
				spark.config().enableOfflineMode(true);

				// Create ExtentReports and attach reporter
				extent = new ExtentReports();
				extent.attachReporter(spark);

				// Add some system info (optional)
				extent.setSystemInfo("OS", System.getProperty("os.name"));
				extent.setSystemInfo("Tester", "Santosh");

		FileInputStream fi = new FileInputStream("./Resources/LoginDetails.xlsx");

		wb = WorkbookFactory.create(fi);
		fi.close();

	}

	public void AddResult(String Status, int rowIndex) throws Throwable {
		Sheet sheet = wb.getSheet("Login");

		Row headerRow = sheet.getRow(0);
		int lastColIndex = headerRow.getLastCellNum(); // last col + 1
		int resultColIndex = -1;

		// Search for existing "Result" column
		for (int i = 0; i < lastColIndex; i++) {
			Cell cell = headerRow.getCell(i);
			if (cell != null && "Result".equalsIgnoreCase(cell.getStringCellValue())) {
				resultColIndex = i;
				break;
			}
		}

		// If not found, create new column at the end
		if (resultColIndex == -1) {
			resultColIndex = lastColIndex;
			Cell newHeader = headerRow.createCell(resultColIndex);
			newHeader.setCellValue("Result");
			System.out.println("✅ Result column created successfully.");
		}

		CellStyle style = wb.createCellStyle();

		// --- Write Test Result in that column ---
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}

		Cell resultCell = row.getCell(resultColIndex);
		if (resultCell == null) {
			resultCell = row.createCell(resultColIndex);
		}

		if ("Pass".equalsIgnoreCase(Status)) {
			resultCell.setCellValue("PASS");
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			resultCell.setCellStyle(style);
			System.out.println("✅ PASS written for row " + rowIndex);
		} else {
			resultCell.setCellValue("FAIL");
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			resultCell.setCellStyle(style);
			System.out.println("❌ FAIL written for row " + rowIndex);
		}

	}

	@AfterSuite
	public void SaveExcel() throws Exception {
		FileOutputStream fos = new FileOutputStream("./Resources/Results.xlsx");
		wb.write(fos);
		wb.close();
		fos.close();
		extent.flush();
	}
}