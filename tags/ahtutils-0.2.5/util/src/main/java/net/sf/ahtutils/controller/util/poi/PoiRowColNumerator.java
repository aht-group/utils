package net.sf.ahtutils.controller.util.poi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiRowColNumerator
{
	final static Logger logger = LoggerFactory.getLogger(PoiRowColNumerator.class);
	
	public static String create(int row, int col)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("row:"+(row+1));
		sb.append(" col:"+translateColumnIndexToName(col));
		return sb.toString();
	}
	
	protected static String translateColumnIndexToName(int index)
	{
        int quotient = (index)/ 26;
        if (quotient > 0)
        {
            return translateColumnIndexToName(quotient-1) + (char) ((index % 26) + 65);
        }
        else
        {
            return "" + (char) ((index % 26) + 65);
        }
    }
	
	public static int translateNameToIndex(String input)
	{
		int r=0;
		for(int b:input.getBytes())
		{
			r=26*r+b-64;
		}
		return r-1;
	}
	
}