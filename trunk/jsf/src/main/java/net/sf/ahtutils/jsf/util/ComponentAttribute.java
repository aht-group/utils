package net.sf.ahtutils.jsf.util;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentAttribute
{
	final static Logger logger = LoggerFactory.getLogger(ComponentAttribute.class);
	
	public static boolean getBoolean(String attribute, FacesContext context, UIComponent component) throws UtilsNotFoundException
	{
		String value = get(attribute,null,context,component);
		if(value==null){throw new UtilsNotFoundException("No attribute in component: "+attribute);}
		return new Boolean(value);
	}
	
	public static boolean getBoolean(String attribute, boolean defaultValue, FacesContext context, UIComponent component)
	{
		String value = get(attribute,null,context,component);
		if(value==null){value=""+defaultValue;}
		return new Boolean(value);
	}
	
	public static int getInteger(String attribute, FacesContext context, UIComponent component) throws UtilsNotFoundException
	{
		String value = get(attribute,null,context,component);
		if(value==null){throw new UtilsNotFoundException("No attribute in component: "+attribute);}
		return new Integer(value);
	}
	
	public static long getLong(String attribute, boolean defaultValue, FacesContext context, UIComponent component)
	{
		String value = get(attribute,null,context,component);
		if(value==null){value=""+defaultValue;}
		return new Long(value);
	}
	
	public static long getLong(String attribute, FacesContext context, UIComponent component) throws UtilsNotFoundException
	{
		String value = get(attribute,null,context,component);
		if(value==null){throw new UtilsNotFoundException("No attribute in component: "+attribute);}
		return new Long(value);
	}
	
	public static double getDouble(String attribute, boolean defaultValue, FacesContext context, UIComponent component)
	{
		String value = get(attribute,null,context,component);
		if(value==null){value=""+defaultValue;}
		return new Double(value);
	}
	
	public static double getDouble(String attribute, FacesContext context, UIComponent component) throws UtilsNotFoundException
	{
		String value = get(attribute,null,context,component);
		if(value==null){throw new UtilsNotFoundException("No attribute in component: "+attribute);}
		return new Double(value);
	}
	
	public static String get(String attribute, FacesContext context, UIComponent component) throws UtilsNotFoundException
	{
		String value = get(attribute,null,context,component);
		if(value==null){throw new UtilsNotFoundException("No attribute in component: "+attribute);}
		return value;
	}
	
	public static int getInteger(String attribute, boolean defaultValue, FacesContext context, UIComponent component)
	{
		String value = get(attribute,null,context,component);
		if(value==null){value=""+defaultValue;}
		return new Integer(value);
	}
	
	public static String get(String attribute, String defaultValue, FacesContext context, UIComponent component)
	{
		String value = null;
		if(context.getAttributes().containsKey(attribute))
		{
			value = context.getAttributes().get(attribute).toString();
		}
		else
		{
			ValueExpression ve = component.getValueExpression(attribute);
			if(ve!=null)
			{
				value=ve.getValue(context.getELContext()).toString();
			}
		}
		if(value==null){value=defaultValue;}
		return value;
	}
}