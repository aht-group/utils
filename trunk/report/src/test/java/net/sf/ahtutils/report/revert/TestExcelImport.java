package net.sf.ahtutils.report.revert;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import net.sf.ahtutils.report.revert.excel.DummyEntity;
import net.sf.ahtutils.report.revert.excel.ExcelStatusImporter;
import net.sf.ahtutils.test.AbstractAhtUtilsReportTest;
import net.sf.ahtutils.test.AhtUtilsReportBootstrap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExcelImport extends AbstractAhtUtilsReportTest {
	
	final static Logger logger = LoggerFactory.getLogger(TestExcelImport.class);

	private String filename = "src/test/resources/data/xlsx/importTest.xlsx";
	
	
	
	@Test
	public void test() throws Exception {
		
		// Initialize Logging
		AhtUtilsReportBootstrap.init();
		
		// Initialize the importer
		ExcelStatusImporter statusImporter = new ExcelStatusImporter(filename);
		
		// Select the first sheet in Excel file to be the active one
		statusImporter.selectFirstSheet();
		
		// Show the content of the first row (where column titles should be)
		statusImporter.debugHeader();
		
		// Show first after header (2nd) where first data should be found
		statusImporter.debugFirstRow();
		
		// Define what column should be associated with which property of the entity
		// WARNING: Make sure that the properties are of the correct data type as to be found in Excel sheet:
		// - Double for numeric cells
		// - String for text cells
		// - Date   for cells formatted as date 
		Hashtable<Short, String> associationTable = new Hashtable<Short, String>();
		associationTable.put((short) 0, "ValueDouble");
		associationTable.put((short) 1, "ValueDate");
		associationTable.put((short) 2, "ValueString");
		
		// Let the importer set the given column values to entity properties and get a list of entities for all rows
		ArrayList<Object> importedEntities = statusImporter.createEntitiesFromData(associationTable, true, DummyEntity.class);
		
		// Specify how to format dates in debug output
		DateFormat df = SimpleDateFormat.getDateInstance();
		
		// Debug the entity properties
		for (Object importedEntity : importedEntities)
		{
			DummyEntity entity = (DummyEntity) importedEntity;
			logger.info("Imported ValueString Property = " +entity.getValueString());
			logger.info("Imported ValueDouble Property = " +entity.getValueDouble());
			logger.info("Imported ValueDate   Property = " +df.format(entity.getValueDate()));
		}
		
	}

}
