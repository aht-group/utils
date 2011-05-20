package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilIntegrityException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilIntegrityException() 
	{ 
	} 
 
	public AhtUtilIntegrityException(String s) 
	{ 
		super(s); 
	} 
}
