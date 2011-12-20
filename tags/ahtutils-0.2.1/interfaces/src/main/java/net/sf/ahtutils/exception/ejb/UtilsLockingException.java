package net.sf.ahtutils.exception.ejb;

import java.io.Serializable;

public class UtilsLockingException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsLockingException() 
	{ 
	} 
 
	public UtilsLockingException(String s) 
	{ 
		super(s); 
	} 
}
