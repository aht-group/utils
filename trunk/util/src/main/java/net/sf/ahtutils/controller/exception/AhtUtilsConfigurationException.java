package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilsConfigurationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilsConfigurationException() 
	{ 
	} 
 
	public AhtUtilsConfigurationException(String s) 
	{ 
		super(s); 
	} 
}
