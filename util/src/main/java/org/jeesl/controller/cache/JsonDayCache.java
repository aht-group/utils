package org.jeesl.controller.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.model.json.util.time.JsonDay;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonDayCache
{
	final static Logger logger = LoggerFactory.getLogger(JsonDayCache.class);
	public static final long serialVersionUID=1;
	
	private Map<String,JsonDay> map;

	public JsonDayCache()
	{
		map = new HashMap<>();
	}
	
	public void init(List<JsonDay> list)
	{
		map.clear();
		for(JsonDay day : list)
		{
			String key = day.getYear()+"-"+day.getMonth()+"-"+day.getNr();
			map.put(key,day);
		}
	}
	
	public boolean contains(Date record) {return map.containsKey(key(record));}
	
	private String key(Date d)
	{
		DateTime dt = new DateTime(d);
		StringBuilder sb = new StringBuilder();
		sb.append(dt.getYear()).append("-");
		sb.append(dt.getMonthOfYear()).append("-");
		sb.append(dt.getDayOfMonth());
		return sb.toString();
	}
	
	public JsonDay get(Date record) {return map.get(key(record));}
}