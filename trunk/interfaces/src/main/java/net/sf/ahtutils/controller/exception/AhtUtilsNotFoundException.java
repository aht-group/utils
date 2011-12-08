package net.sf.ahtutils.controller.exception;

import java.io.Serializable;

public class AhtUtilsNotFoundException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public AhtUtilsNotFoundException() 
	{ 
	} 
 
	public AhtUtilsNotFoundException(String s) 
	{ 
		super(s); 
	} 
}
