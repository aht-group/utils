package org.jeesl.factory.json.system.io.report.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.jeesl.model.json.system.io.report.xlsx.JsonXlsCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonXlsCellFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonXlsCellFactory.class);
	
	public static JsonXlsCell build(){return new JsonXlsCell();}
	
	public static JsonXlsCell build(Cell cell)
	{
		JsonXlsCell json = build();
		
		int type = cell.getCellType();
		
		if(type==1) {json.setValueString(cell.getStringCellValue());}
		else if(type==0) {json.setValueString(cell.getStringCellValue());}
		else
		{
			logger.error("You need to handle type="+type+" "+cell.getRowIndex()+" "+cell.getColumnIndex());
			logger.error(cell.getStringCellValue());
			System.exit(-1);
		}
		
//		CellType type = cell.getCellTypeEnum();
//		switch(type)
//		{
//			case STRING: json.setValueString(cell.getStringCellValue()); break;
//			default: logger.error("You need to handle "+type);System.exit(-1);
//		}
		
		return json;
	}
	
	
}