package net.sf.ahtutils.exception.factory;

import java.io.Serializable;

public class UtilsXmlFactoryException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsXmlFactoryException() 
	{ 
	} 
 
	public UtilsXmlFactoryException(String s) 
	{ 
		super(s); 
	} 
}
