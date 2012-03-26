package net.sf.ahtutils.exception.xml;

import java.io.Serializable;

public class AhtUtilsXmlStructureException extends Exception implements Serializable
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
