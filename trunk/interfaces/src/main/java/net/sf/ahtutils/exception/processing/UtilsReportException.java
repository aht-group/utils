package net.sf.ahtutils.exception.processing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsReportException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UtilsReportException.class);

	private List<Exception> exceptions;
	private List<String> errors;
	 
	public UtilsReportException(String s) 
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
	
	public void addAll(List<Exception> exceptions)
	{
		this.exceptions.addAll(exceptions);
	}
	
	public void add(Exception e)
	{
		exceptions.add(e);
	}
	
	public List<Exception> getExceptions(){return exceptions;}
	
	public void uniquefyExceptions()
	{
		logger.info("Actual: "+exceptions.size());
		Set<String> setIds = new HashSet<String>();
		List<Exception> list = new ArrayList<Exception>();
		for(Exception e : exceptions)
		{
			logger.info(e.toString());
			if(e instanceof UtilsNotFoundException && ((UtilsNotFoundException) e).isWithDetails())
			{
				if(!setIds.contains(((UtilsNotFoundException) e).toHash()))
				{
					list.add(e);
					setIds.add(((UtilsNotFoundException) e).toHash());
				}
			}
			else
			{
				list.add(e);
			}
		}
		exceptions = list;
		logger.info("Uniquefy: "+exceptions.size());
	}
	
	public void throwIfErrors() throws UtilsReportException
	{
		if(this.hasErrors()){throw this;}
	}
}
