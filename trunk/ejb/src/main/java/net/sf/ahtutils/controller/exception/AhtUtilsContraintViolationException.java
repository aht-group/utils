package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilsContraintViolationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilsContraintViolationException() 
	{ 
	} 
 
	public AhtUtilsContraintViolationException(String s) 
	{ 
		super(s); 
	} 
}
