package net.sf.ahtutils.test.report;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.ahtutils.report.ReportUtilXls;
import net.sf.ahtutils.test.AbstractAhtUtilTest;

public class TestRemoveEmptyCells extends AbstractAhtUtilTest{
	
	public void convert(){
		OutputStream os = ReportUtilXls.RemoveEmptyCells("src/test/resources/reports/excelPoiTest.xls");
		OutputStream outputStreamProcessed;
		try {
			outputStreamProcessed = new FileOutputStream ("src/test/resources/reports/excelPoiResult.xls");
			((ByteArrayOutputStream)os).writeTo(outputStreamProcessed);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		TestRemoveEmptyCells test = new TestRemoveEmptyCells();
		test.convert();
	}

}
