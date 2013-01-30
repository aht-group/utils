package net.sf.ahtutils.exception.processing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UtilsBatchException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	private List<String> errors;
	 
	public UtilsBatchException(String s) 
	{ 
		super(s);
		errors = new ArrayList<String>(); 
	}
	
	public boolean isErrors()
	{
		if(errors.size()>0){return true;}
		return false;
	}
	
	public void addError(String txt)
	{
		errors.add(txt);
	}
	
	public List<String> getErrors()
	{
		return errors;
	}
}
