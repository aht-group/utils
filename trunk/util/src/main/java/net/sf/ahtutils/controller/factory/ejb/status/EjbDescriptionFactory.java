package net.sf.ahtutils.controller.factory.ejb.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDescriptionFactory<D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDescriptionFactory.class);
	
    final Class<D> clDescription;
	
    public EjbDescriptionFactory(final Class<D> clDescription)
    {
        this.clDescription = clDescription;
    } 
    
    public static <D extends UtilsDescription> EjbDescriptionFactory<D> createFactory(final Class<D> clDescription)
    {
        return new EjbDescriptionFactory<D>(clDescription);
    }
	
	public D create(Description description) throws InstantiationException, IllegalAccessException
	{
    	return create(description.getKey(),description.getValue());
	}
    
	public D create(String key, String value) throws InstantiationException, IllegalAccessException
	{
		D d = clDescription.newInstance();
    	d.setLang(value);
    	d.setLkey(key);
    	return d;
	}
	
	public Map<String,D> create(Descriptions descriptions) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		return create(descriptions.getDescription()); 
	}
	
	public Map<String,D> create(List<Description> lDescriptions) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		Map<String,D> map = new Hashtable<String,D>();
		for(Description desc : lDescriptions)
		{
			D d = create(desc);
			map.put(d.getLkey(), d);
		}
		return map;
	}
}