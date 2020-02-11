package org.jeesl.exception.factory;

import java.io.Serializable;

public class JeeslXmlFactoryException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public JeeslXmlFactoryException() 
	{ 
	} 
 
	public JeeslXmlFactoryException(String s) 
	{ 
		super(s); 
	} 
}
