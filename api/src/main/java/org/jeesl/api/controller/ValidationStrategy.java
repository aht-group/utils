package org.jeesl.api.controller;

import java.util.Hashtable;

import org.jeesl.interfaces.facade.JeeslFacade;

public interface ValidationStrategy
{	
	public Boolean 					 validate(Object object, String targetClass, String property);
	public void   					 setFacade(JeeslFacade facade);
	public void   					 setTempPropertyStore(Hashtable<String, Object> tempProperties);
	public Hashtable<String, Object> getTempPropertyStore();
}
