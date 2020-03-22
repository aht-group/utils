package org.jeesl.controller.handler.ui;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UiEditHandler <T extends EjbWithId> implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UiEditHandler.class);

	private final Set<T> set; 
	
	private boolean allow; public boolean isAllow() {return allow;}
	public boolean isDeny() {return !allow;}

	public UiEditHandler()
	{
		set = new HashSet<>();
		reset();
	}
	
	private void reset()
	{
		set.clear();
		allow = false;
	}

	public void update(T item)
	{
		if(EjbIdFactory.isUnSaved(item)) {allow=true;}
		else
		{
			allow = set.contains(item);
		}	
	}
	
	public void saved(T item)
	{
		set.add(item);
		update(item);
	}
}