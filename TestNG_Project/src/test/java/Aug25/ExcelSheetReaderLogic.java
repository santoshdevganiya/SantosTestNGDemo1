package Aug25;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelSheetReaderLogic {


	public static Object[][] getSheetData() throws Throwable {

		Object[][] data = null;
		FileInputStream fi = new FileInputStream("./Resources/LoginDetails.xlsx");

		Workbook wb = WorkbookFactory.create(fi);

		Sheet sheet = wb.getSheet("Login");

		DataFormatter formatter = new DataFormatter(); // formats numeric, string, date, etc.

		int rowCount = sheet.getLastRowNum(); // last row index
		int colCount = sheet.getRow(0).getLastCellNum();

		// skipping header row (row 0)
		data = new Object[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {
	        Row row = sheet.getRow(i);

	        // Row index
	        data[i - 1][0] = i;

	        // Username = column 1
	        data[i - 1][1] = formatter.formatCellValue(row.getCell(1));

	        // Password = column 2
	        Cell passwordCell = row.getCell(2);
	        data[i - 1][2] = formatter.formatCellValue(passwordCell);
	        System.out.println("DEBUG: Password read from Excel row " + i + " = " + data[i - 1][2]);
	    }
		return data;
	}

}
