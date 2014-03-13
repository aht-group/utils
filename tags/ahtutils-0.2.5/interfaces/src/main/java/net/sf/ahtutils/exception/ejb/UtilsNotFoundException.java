package net.sf.ahtutils.exception.ejb;

import java.io.Serializable;

public class UtilsNotFoundException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public UtilsNotFoundException() 
	{ 
	} 
 
	public UtilsNotFoundException(String s) 
	{ 
		super(s); 
	} 
}
