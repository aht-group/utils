package net.sf.ahtutils.factory.txt.sync;

import net.sf.ahtutils.xml.sync.DataUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtDataUpdateFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtDataUpdateFactory.class);
		
	public static String debug(DataUpdate du)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("[");
		if(du.isSetResult() && du.getResult().isSetStatus() && du.getResult().getStatus().isSetCode())
		{
			sb.append(du.getResult().getStatus().getCode());
		}
		else
		{
			sb.append("--UNKNOWN--");
		}
		sb.append("]");
		
		if(du.isSetResult() && du.getResult().isSetSuccess() && du.getResult().isSetTotal())
		{
			sb.append(" ").append(du.getResult().getSuccess()+"/"+du.getResult().getTotal());
		}
		
		return sb.toString();
	}
}