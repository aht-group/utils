package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilsDeveloperException extends RuntimeException implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilsDeveloperException() 
	{ 
	} 
 
	public AhtUtilsDeveloperException(String s) 
	{ 
		super(s); 
	} 
}
