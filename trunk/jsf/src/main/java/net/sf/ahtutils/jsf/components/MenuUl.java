package net.sf.ahtutils.jsf.components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="net.sf.ahtutils.jsf.components.MenuUl")
public class MenuUl extends UINamingContainer
{
	final static Logger logger = LoggerFactory.getLogger(MenuUl.class);

	public String styleClass(boolean active, boolean first, boolean last, String cssActive, String cssFirst, String cssLast)
	{
		StringBuffer sb = new StringBuffer();
		if(active){sb.append(cssActive);}
		if(first && cssFirst!=null){sb.append(" ").append(cssFirst);}
		if(last && cssLast!=null){sb.append(" ").append(cssLast);}
		return sb.toString();
	}
}