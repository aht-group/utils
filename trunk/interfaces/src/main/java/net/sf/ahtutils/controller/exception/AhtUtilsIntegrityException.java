package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilsIntegrityException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilsIntegrityException() 
	{ 
	} 
 
	public AhtUtilsIntegrityException(String s) 
	{ 
		super(s); 
	} 
}
