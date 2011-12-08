package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilsLockingException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilsLockingException() 
	{ 
	} 
 
	public AhtUtilsLockingException(String s) 
	{ 
		super(s); 
	} 
}
