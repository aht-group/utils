package net.sf.ahtutils.jsf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeSafetyMapToList
{
	final static Logger logger = LoggerFactory.getLogger(TypeSafetyMapToList.class);
	
	public static List<Long> convert(Map<Long,Boolean> map)
	{
		List<Long> list = new ArrayList<Long>();
		for(Long id : map.keySet())
	    {
			String x = ""+map.get(id);
			if(x.equals("true")){list.add(id);}
	    }
		return list;
	}
}
