package net.sf.ahtutils.controller.factory.ejb.status;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;

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
	
	public D create(String lang, String value) throws InstantiationException, IllegalAccessException
	{
		D d = clDescription.newInstance();
    	d.setLang(lang);
    	d.setLkey(lang);
    	return d;
	}
}