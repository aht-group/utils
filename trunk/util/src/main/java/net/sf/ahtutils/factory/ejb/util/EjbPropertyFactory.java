package net.sf.ahtutils.factory.ejb.util;

import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.xml.utils.Property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbPropertyFactory<P extends UtilsProperty>
{
	final static Logger logger = LoggerFactory.getLogger(EjbPropertyFactory.class);
	
	final Class<P> cProperty;
    
	public EjbPropertyFactory(final Class<P> cProperty)
	{       
        this.cProperty = cProperty;
	}
	
	public static <P extends UtilsProperty> EjbPropertyFactory<P> factory(final Class<P> cProperty)
	{
		return new EjbPropertyFactory<P>(cProperty);
	}
    
	public P build(Property property)
	{
		return build(property.getKey(),property.getValue());
    }
	
	public P build(String code, String value){return build(code,value,false);}
	public P build(String code, String value, boolean frozen)
	{
		P ejb = null;
		try
		{
			ejb = cProperty.newInstance();
			ejb.setKey(code);
			ejb.setValue(value);
			ejb.setFrozen(frozen);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}

}