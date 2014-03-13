package net.sf.ahtutils.jsf.util;

import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentVisibleManager<E extends Enum<E>>
{
	final static Logger logger = LoggerFactory.getLogger(ComponentVisibleManager.class);
	
	private Map<String,Boolean> visible;
	
	public ComponentVisibleManager(Class<E> e)
	{
		visible = new Hashtable<String,Boolean>();
	   	for(E v : e.getEnumConstants())
	   	{
	   		logger.trace("construct with "+v);
	   	}
	}
	
	public void clear()
	{
		visible.clear();
	}
	
	private void verifyFalse(E e)
	{
		if(!visible.containsKey(e.toString())){visible.put(e.toString(), false);}
	}
	
	public void edit(E e, boolean value)
	{
		edit(e.toString(),value);
	}
	public void edit(String key , boolean value)
	{
		logger.trace("edit key:"+key+" value:"+value);
		visible.put(key, value);
	}
	
	public Map<String, Boolean> getVisible() {return visible;}
	public boolean isShow(E e)
	{
		verifyFalse(e);
		return visible.get(e.toString());
	}
	
	public void toggle(E e)
	{
		verifyFalse(e);
		if(visible.get(e.toString())) {edit(e,false);}
		else {edit(e,true);}
	}
}