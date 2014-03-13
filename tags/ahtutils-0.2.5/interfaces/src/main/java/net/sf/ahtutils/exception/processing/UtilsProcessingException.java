package net.sf.ahtutils.exception.processing;

import java.io.Serializable;

public class UtilsProcessingException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsProcessingException() 
	{ 
	} 
 
	public UtilsProcessingException(String s) 
	{ 
		super(s); 
	} 
}
