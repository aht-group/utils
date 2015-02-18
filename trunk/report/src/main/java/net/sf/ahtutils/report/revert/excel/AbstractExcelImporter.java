package net.sf.ahtutils.report.revert.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExcelImporter <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription, C extends Serializable, I extends ImportStrategy> {

	final static Logger logger = LoggerFactory.getLogger(AbstractExcelImporter.class);
	
	private File                       excelFile;
	private XSSFWorkbook               workbook;
	private Sheet                      activeSheet;
	private UtilsFacade                facade;
	private Hashtable<String, Class>   handler;
	private short                      primaryKey;
	private Hashtable<String, C>       entities;
	private Hashtable<String, Object>  tempPropertyStore;
	
	public AbstractExcelImporter(String filename) throws IOException
	{
		// Prepare file to be read
		this.excelFile      = new File(filename);
		FileInputStream fis = new FileInputStream(excelFile);
		
		// Read Excel workbook from given file(name)
		this.workbook       = new XSSFWorkbook(fis);
	}
	
	public void setFacade(UtilsFacade facade)
	{
		this.facade = facade;
	}
	
	public void setPrimaryKey(Integer columnNumber)
	{
		this.primaryKey = columnNumber.shortValue();
	}
	
	public void setHandler(Hashtable<String, Class> strategies)
	{
		this.handler = strategies;
	}
	
	public void selectSheetByName(String name)
	{
		// Select a sheet from the Excel workbook by name
		activeSheet         = workbook.getSheet(name);
	}
	
	public void selectFirstSheet()
	{
		// Select a sheet from the Excel workbook by name
		activeSheet         = workbook.getSheetAt(0);
	}
	
	public void debugHeader()
	{
		debugRow(0);
	}
	
	public void debugFirstRow()
	{
		debugRow(1);
	}
	
	public void debugRow(Integer rowIndex)
	{
		// Using a StringBuffer to create one line with all column titles
		StringBuffer sb = new StringBuffer();
		sb.append("Debugging Row " +rowIndex +" ... ");
		
		// Selecting first row since this should be the place where the column titles should be placed 
		Row firstRow    = activeSheet.getRow(rowIndex);
		
		// Iterating through all cells in first row
		for (short i = firstRow.getFirstCellNum() ; i < firstRow.getLastCellNum() ; i++)
		{
			Cell cell = firstRow.getCell(i);
			// Get the Cell Value as Object
			Object object = getCellValue(cell);
			
			// Get a String representation of the value
			String cellValue = getStringValue(object);
			
			// Add the content of the cell to StringBuffer
			sb.append("Column " +i +": '" +cellValue + "' ");
		}
		
		// Show the StringBuffer content in logging
		logger.info(sb.toString());
	}
	
		
	public Object getCellValue(Cell cell)
	{
		Object value = new Object();
		
		// Prevent a NullPointerException
		if (cell != null)
		{
			// Depending on the cell type, the value is read using Apache POI methods
			switch (cell.getCellType()) {
			
				// String are easy to handle
				case Cell.CELL_TYPE_STRING : 
					logger.trace("Found string " +cell.getStringCellValue());
					value = cell.getStringCellValue();
					break;
					
				// Since date formatted cells are also of the numeric type, this needs to be processed
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell))
					{
						Date date = cell.getDateCellValue();
						DateFormat df = SimpleDateFormat.getDateInstance();
						logger.trace("Found date " +df.format(date));
						value = date;
					}
					else
					{
						logger.trace("Found general number " +cell.getNumericCellValue());
						value = cell.getNumericCellValue();
					}
					break;
			}
		}
		else
		{
			logger.trace("Found cell with NULL value");
		}
		return value;
	}
	
	public String getStringValue(Object object)
	{
		// Since a simple "toString()" is not sufficient because the value can also be null, 
		// a more complex method has been chosen here to get a String representation of a cell value 
		String value = "";
		if (object.getClass().equals(String.class))
		{
			value = (String) object;
		}
		else if (object.getClass().equals(Date.class))
		{
			Date date = (Date) object;
			DateFormat df = SimpleDateFormat.getDateInstance();
			value = df.format(date);
		}
		else if (object.getClass().equals(Double.class))
		{
			Double doubleValue = (Double) object;
			value = doubleValue.toString();
		}
		else
		{
			value = "CELL IS NULL";
		}
		return value;
	}
	
	public ArrayList<C> createEntitiesFromData(Hashtable<Short, String> associationTable, Boolean skipTitle, Class<C> entityObject) throws Exception
	{
		// Create a list to hold the Entity classes to be created
		ArrayList<C> entities = new ArrayList<C>();
		
		// Define the rows to begin with and to end with, whether with or without first row
		Integer end   = activeSheet.getLastRowNum();
		Integer start = activeSheet.getFirstRowNum();
		if (skipTitle) {start++;}
		
		// Iterate through all given rows
		for (int i = start; i < end+1; i++)
		{
			// Get the next row
			Row row = activeSheet.getRow(i);
			
			// See if there is already an instance created for this key, otherwise create a new one
			Object entityKey = row.getCell(primaryKey);
			C entity = null;
			if (this.entities.containsKey(entityKey))
			{
				entity = this.entities.get(entityKey);
			}
			else
			{
				entity = entityObject.newInstance();
			}
			
			// Iterate through the columns and assign data as given in the association table
			for (short j = row.getFirstCellNum() ; j < row.getLastCellNum() ; j++)
			{
				Cell cell = row.getCell(j);
				
				// Get the Cell Value as Object
				Object object = getCellValue(cell);
				
				// Read the name of the property that should be filled with the data from this column
				String propertyName = associationTable.get(j);
			
			    // Assign the data to the entity using the setter
				logger.trace("Cell " +row.getRowNum() +"," +j);
				if (propertyName!=null)
				{
					logger.trace("Setting value of type " +object.getClass().getCanonicalName());
					invokeMethod("set" +propertyName,
							      new Object[] { object },
							      entity.getClass(),
							      entity);
				}
			}
			//facade.save(entity);
			entities.add(entity);
		}
		return entities;
		
	}
		
	 private void invokeMethod(String   methodName, 
			 							Object[] parameters,
			 							Class    targetClass,
			 							Object   target)        throws Exception
	 {
	 	logger.trace("Invoking " +methodName);
	 	
	 	// Now find the correct method
	 	Method[] methods = targetClass.getMethods();
	 	Class parameter  = null;
	 	Method m         = null;
	 	for (Method method : methods)
	 	{
	 		if (method.getName().equals(methodName))
	 		{
	 			parameter = method.getParameterTypes()[0];
	 			m = method;
	 		}
	 	}
	 	
        if (Modifier.isPrivate(m.getModifiers()))
        {
            m.setAccessible(true);
        }
        
        // Determine parameter type of setter
        // Type t = m.getGenericParameterTypes()[0];
        // String parameterClass = t.getTypeName();
        
        String parameterClass = parameter.getCanonicalName();
        
        // Lets see if the setter is accepting a data type that is available in Excel (String, Double, Date)
        // Otherwise assume that it is used with a lookup table
        if (!(parameterClass.equals("java.lang.Double") || parameterClass.equals("java.util.Date") || parameterClass.equals("java.lang.String")))
        {
        	logger.debug("Loading import strategy for " +parameterClass +" : " +handler.get(parameterClass).getCanonicalName());
        	// Instantiate new strategy to handle import
        	ImportStrategy strategy = (ImportStrategy) handler.get(parameterClass).newInstance();
        	
        	// Pass database connection and current set of temporary properties
        	strategy.setFacade(facade);
        	strategy.setTempPropertyStore(tempPropertyStore);
        	
        	// Process import step
        	parameters[0]    = strategy.handleObject(parameters[0], parameterClass);
        	
        	// Sync new temporary properties if any added
        	tempPropertyStore = strategy.getTempPropertyStore();
        }
        
        m.invoke(target, parameters);
	 }
}
