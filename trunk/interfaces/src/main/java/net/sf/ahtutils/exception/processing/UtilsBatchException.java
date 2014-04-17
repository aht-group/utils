package net.sf.ahtutils.exception.processing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class UtilsBatchException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	private List<Exception> exceptions;
	private List<String> errors;
	 
	public UtilsBatchException(String s) 
	{ 
		super(s);
		errors = new ArrayList<String>();
		exceptions = new ArrayList<Exception>();
	}
	
	@Deprecated
	public boolean isErrors()
	{
		return hasErrors();
	}
	
	public boolean hasErrors()
	{
		if(errors.size()>0 || exceptions.size()>0){return true;}
		return false;
	}
	
	@Deprecated
	public void addError(String txt)
	{
		errors.add(txt);
	}
	
	@Deprecated
	public List<String> getErrors()
	{
		return errors;
	}
	
	public void add(Exception e)
	{
		exceptions.add(e);
	}
	
	public List<Exception> getExceptions(){return exceptions;}
	
	public void uniquefyExceptions()
	{
		Set<String> setIds = new HashSet<String>();
		List<Exception> list = new ArrayList<Exception>();
		for(Exception e : exceptions)
		{
			if(e instanceof UtilsNotFoundException && ((UtilsNotFoundException) e).isWithDetails())
			{
				list.add(e);
				setIds.add(((UtilsNotFoundException) e).toHash());
			}
			else
			{
				list.add(e);
			}
		}
		exceptions = list;
	}
}
