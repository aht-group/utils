package org.jeesl.exception.ejb;

import java.io.Serializable;

public class JeeslLockingException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public JeeslLockingException() 
	{ 
	} 
 
	public JeeslLockingException(String s) 
	{ 
		super(s); 
	} 
}
