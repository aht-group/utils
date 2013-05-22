package net.sf.ahtutils.exception.ejb;

import java.io.Serializable;

public class UtilsContraintViolationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsContraintViolationException() 
	{ 
	} 
 
	public UtilsContraintViolationException(String s) 
	{ 
		super(s); 
	} 
}
