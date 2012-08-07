package net.sf.ahtutils.exception.processing;

import java.io.Serializable;

public class UtilsMailException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsMailException() 
	{ 
	} 
 
	public UtilsMailException(String s) 
	{ 
		super(s); 
	} 
}
