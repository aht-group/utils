package net.sf.ahtutils.exception.processing;

import java.io.Serializable;

public class UtilsAuthorizationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsAuthorizationException() 
	{ 
	} 
 
	public UtilsAuthorizationException(String s) 
	{ 
		super(s); 
	} 
}
