package org.jeesl.exception.ejb;

import java.io.Serializable;

public class JeeslConstraintViolationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public JeeslConstraintViolationException() 
	{
		
	} 
 
	public JeeslConstraintViolationException(String s) 
	{ 
		super(s); 
	}
	
	public JeeslConstraintViolationException(String s, Throwable t) 
	{ 
		super(s,t); 
	} 
}
