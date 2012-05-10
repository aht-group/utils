package net.sf.ahtutils.controller.util.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiSsCellType
{
	final static Logger logger = LoggerFactory.getLogger(PoiSsCellType.class);
	
	public static String translate(int type)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Cell-Type (");
		switch(type)
		{
			case Cell.CELL_TYPE_NUMERIC: sb.append("numeric");break;
			case Cell.CELL_TYPE_STRING: sb.append("string");break;
			case Cell.CELL_TYPE_FORMULA: sb.append("formula");break;
			case Cell.CELL_TYPE_BLANK: sb.append("blank");break;
			case Cell.CELL_TYPE_BOOLEAN: sb.append("boolean");break;
			case Cell.CELL_TYPE_ERROR: sb.append("error");break;
		}
		sb.append(")");
		return sb.toString();
	}
}