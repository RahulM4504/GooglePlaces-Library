package BaseTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class base {
	Workbook wb;
	Sheet sh;
	
	public Object[][] readexcel() throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("C:\\Users\\innoc\\eclipse-workspace\\API.Practice1.com\\src\\main\\java\\resources\\Addbook.xlsx");
		
		wb=WorkbookFactory.create(fis);
		sh = wb.getSheet("book");
		Object[][] data  = new Object[sh.getLastRowNum()][sh.getRow(0).getLastCellNum()];
		for(int i =0; i<sh.getLastRowNum(); i++) {
			for(int j=0;j<sh.getRow(0).getLastCellNum();j++) {
				data[i][j] = sh.getRow(i+1).getCell(j).toString();
			}
		}
		return data;
		
	}

}
