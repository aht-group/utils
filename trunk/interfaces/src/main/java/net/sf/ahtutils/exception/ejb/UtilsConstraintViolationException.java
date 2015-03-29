package net.sf.ahtutils.exception.ejb;

import java.io.Serializable;

public class UtilsConstraintViolationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsConstraintViolationException() 
	{
		
	} 
 
	public UtilsConstraintViolationException(String s) 
	{ 
		super(s); 
	}
	
	public UtilsConstraintViolationException(String s, Throwable t) 
	{ 
		super(s,t); 
	} 
}
