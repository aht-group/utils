package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilsXmlStructureException extends RuntimeException implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilsXmlStructureException() 
	{ 
	} 
 
	public AhtUtilsXmlStructureException(String s) 
	{ 
		super(s); 
	} 
}
