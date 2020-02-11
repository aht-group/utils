package org.jeesl.exception.processing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.exception.ejb.JeeslNotFoundException;
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
			if(e instanceof JeeslNotFoundException && ((JeeslNotFoundException) e).isWithDetails())
			{
				if(!setIds.contains(((JeeslNotFoundException) e).toHash()))
				{
					list.add(e);
					setIds.add(((JeeslNotFoundException) e).toHash());
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
