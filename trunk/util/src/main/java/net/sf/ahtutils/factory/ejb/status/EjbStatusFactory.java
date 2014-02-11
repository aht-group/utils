package net.sf.ahtutils.factory.ejb.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbStatusFactory<S extends UtilsStatus<L,D>, L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(EjbStatusFactory.class);
	
	final Class<S> statusClass;
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    private EjbLangFactory<L> ejbLangFactory;
    private EjbDescriptionFactory<D> ejbDescFactory;
	
    public EjbStatusFactory(final Class<S> statusClass, final Class<L> langClass, final Class<D> descriptionClass)
    {
        this.statusClass = statusClass;
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        
        ejbLangFactory = EjbLangFactory.createFactory(langClass);
        ejbDescFactory = EjbDescriptionFactory.createFactory(descriptionClass);
    } 
    
    public static <S extends UtilsStatus<L,D>, L extends UtilsLang, D extends UtilsDescription> EjbStatusFactory<S, L, D>
    		createFactory(final Class<S> statusClass, final Class<L> langClass, final Class<D> descriptionClass)
    {
        return new EjbStatusFactory<S, L, D>(statusClass, langClass, descriptionClass);
    }
    
	public S create(Status status) throws InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		if(!status.isSetLangs()){throw new UtilsIntegrityException("No <langs> available for "+JaxbUtil.toString(status));}
        S s = statusClass.newInstance();
        s.setCode(status.getCode());
        if(status.isSetPosition()){s.setPosition(status.getPosition());}
        else{s.setPosition(0);}
        s.setName(ejbLangFactory.getLangMap(status.getLangs()));
        s.setDescription(ejbDescFactory.create(status.getDescriptions()));
        return s;
    }
	
	public S create(String code)
	{
        S s;
		try
		{
			s = statusClass.newInstance();
			s.setCode(code);
		}
		catch (InstantiationException e) {throw new RuntimeException(e);}
		catch (IllegalAccessException e) {throw new RuntimeException(e);}
        
        return s;
    }
	
	public Map<String,D> getDescriptionMap(Descriptions desciptions) throws InstantiationException, IllegalAccessException
	{
		if(desciptions!=null && desciptions.isSetDescription())
		{
			return getDescriptionMap(desciptions.getDescription());
		}
		else
		{
			return new Hashtable<String,D>();
		}
	}
	
	public Map<String,D> getDescriptionMap(List<Description> descList) throws InstantiationException, IllegalAccessException
	{
		Map<String,D> mapDesc = new Hashtable<String,D>();
		for(Description xmlD : descList)
		{
			D d = descriptionClass.newInstance();
			d.setLkey(xmlD.getKey());
			d.setLang(xmlD.getValue().trim());
			mapDesc.put(d.getLkey(), d);
		}
		return mapDesc;
	}
}