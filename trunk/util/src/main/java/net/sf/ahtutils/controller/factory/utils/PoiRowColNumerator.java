package net.sf.ahtutils.controller.factory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiRowColNumerator
{
	final static Logger logger = LoggerFactory.getLogger(PoiRowColNumerator.class);
	
	public static String create(int row, int col)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("row:"+(row+1));
		sb.append(" ");
		sb.append(" col:"+(col+1));
		return sb.toString();
	}
}