package net.sf.ahtutils.exception.factory;

import java.io.Serializable;

public class UtilsEjbFactoryException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsEjbFactoryException() 
	{ 
	} 
 
	public UtilsEjbFactoryException(String s) 
	{ 
		super(s); 
	} 
}
