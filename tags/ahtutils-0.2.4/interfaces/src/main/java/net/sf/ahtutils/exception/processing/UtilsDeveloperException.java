package net.sf.ahtutils.exception.processing;

import java.io.Serializable;

public class UtilsDeveloperException extends RuntimeException implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsDeveloperException() 
	{ 
	} 
 
	public UtilsDeveloperException(String s) 
	{ 
		super(s); 
	} 
}
