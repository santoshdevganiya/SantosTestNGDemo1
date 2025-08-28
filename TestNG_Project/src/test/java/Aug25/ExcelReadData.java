package Aug25;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;

public class ExcelReadData {

	@Test
	public void setup() throws Throwable {


		FileInputStream fi = new FileInputStream("./Resources/LoginDetails.xlsx");

		Workbook wb = WorkbookFactory.create(fi);

		Sheet sheet = wb.getSheet("Login");

		DataFormatter formatter = new DataFormatter(); // formats numeric, string, date, etc.

		int lastRow = sheet.getLastRowNum(); // last row index (0-based)

		for (int i = 0; i <= lastRow; i++) {
			Row row = sheet.getRow(i);
			if (row == null)
			 {
				continue; // skip empty rows
			}

			int lastCol = row.getLastCellNum(); // total columns in this row

			for (int j = 0; j < lastCol; j++) {
				Cell cell = row.getCell(j);
				String value = formatter.formatCellValue(cell);
				System.out.print(value + "\t");
			}
			System.out.println();
		}

		wb.close();
		fi.close();
	}
}
