package org.jeesl.model.pojo.exception;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.exception.processing.UtilsBatchException;

public class DatatableExceptions
{
	private List<DatatableException> list;
	
	public DatatableExceptions()
	{
		list = new ArrayList<DatatableException>();
	}
	
	public void add(UtilsBatchException ube)
	{
		for(Exception e : ube.getExceptions())
		{
			DatatableException de = new DatatableException();
			if(e instanceof JeeslNotFoundException && ((JeeslNotFoundException) e).isWithDetails())
			{
				JeeslNotFoundException unfe = (JeeslNotFoundException)e;
				de.setRecord(unfe.getWhen());
				de.setType(unfe.getWhatKey());
				de.setDetail(unfe.getWhereDetail());
			}
			else
			{
				de.setDetail(e.getMessage());
			}
			list.add(de);
		}
	}
	
	public List<DatatableException> getList() {return list;}
}