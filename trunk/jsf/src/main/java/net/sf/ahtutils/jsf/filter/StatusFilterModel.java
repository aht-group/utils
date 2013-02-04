package net.sf.ahtutils.jsf.filter;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusFilterModel <L extends UtilsLang, D extends UtilsDescription, S extends UtilsStatus<L,D>>
{    
	final static Logger logger = LoggerFactory.getLogger(StatusFilterModel.class);
	
	private List<UtilsStatusFilter<L,D,S>> list;

	public StatusFilterModel(List<S> data)
    {  
		list = new ArrayList<UtilsStatusFilter<L,D,S>>();
        for(S s : data)
        {
        	list.add(new UtilsStatusFilter<L,D,S>(s,true));
        }
    }
	
	public void toggle(UtilsStatusFilter<L,D,S> sf)
	{
//		logger.info("Toggle");
		UtilsStatusFilter<L,D,S> sfList = list.get(list.indexOf(sf));
		sfList.setActive(!sfList.isActive());	
	}

	public List<UtilsStatusFilter<L,D,S>> getValues(){return list;}
	
	public List<S> toSelection()
	{
		List<S> result = new ArrayList<S>();
		for(UtilsStatusFilter<L,D,S> sf : list)
		{
			if(sf.isActive()){result.add(sf.getValue());}
		}
		return result;
	}
}