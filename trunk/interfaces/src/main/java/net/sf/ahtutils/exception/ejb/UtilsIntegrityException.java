package net.sf.ahtutils.exception.ejb;

import java.io.Serializable;

public class UtilsIntegrityException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsIntegrityException() 
	{ 
	} 
 
	public UtilsIntegrityException(String s) 
	{ 
		super(s); 
	} 
}
