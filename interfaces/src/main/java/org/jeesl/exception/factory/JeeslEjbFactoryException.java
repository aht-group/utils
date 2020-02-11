package org.jeesl.exception.factory;

import java.io.Serializable;

public class JeeslEjbFactoryException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public JeeslEjbFactoryException() 
	{ 
	} 
 
	public JeeslEjbFactoryException(String s) 
	{ 
		super(s); 
	} 
}
