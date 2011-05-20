package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilNotFoundException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilNotFoundException() 
	{ 
	} 
 
	public AhtUtilNotFoundException(String s) 
	{ 
		super(s); 
	} 
}
