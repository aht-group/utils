package net.sf.ahtutils.jsf.util;

import javax.faces.context.FacesContext;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class FacesContextParameter
{
	public static String get(String key) throws UtilsNotFoundException
	{
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey(key))
		{
			return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
		}
		else
		{
			throw new UtilsNotFoundException("HTTP Request Paramater '"+key+"' not available");
		}
	}
}
