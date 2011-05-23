package net.sf.ahtutils.controller.factory;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilsStatusEjbFactory<S extends UtilsStatus<L>, L extends UtilsLang>
{
	static Log logger = LogFactory.getLog(UtilsStatusEjbFactory.class);
	
	final Class<S> statusClass;
    final Class<L> langClass;
	
    public UtilsStatusEjbFactory(final Class<S> statusClass, final Class<L> langClass)
    {
        this.statusClass = statusClass;
        this.langClass = langClass;
    } 
    
    public static <S extends UtilsStatus<L>, L extends UtilsLang> UtilsStatusEjbFactory<S, L>
    		createFactory(final Class<S> statusClass, final Class<L> langClass)
    {
        return new UtilsStatusEjbFactory<S, L>(statusClass, langClass);
    }
    
	public S create(Status status) throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
	{
		if(!status.isSetLangs()){throw new AhtUtilIntegrityException("No <langs> available for "+JaxbUtil.toString(status));}
        S s = statusClass.newInstance();
        s.setCode(status.getCode());
        s.setName(getLangMap(status.getLangs()));
        return s;
    }
	
	public Map<String,L> getLangMap(Langs langs) throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
	{
		return getLangMap(langs.getLang()); 
	}
	
	public Map<String,L> getLangMap(List<Lang> langList) throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
	{
		Map<String,L> map = new Hashtable<String,L>();
		for(Lang lang : langList)
		{
			L l = createLang(lang);
			map.put(l.getLkey(), l);
		}
		return map;
	}
	
	public L createLang(Lang lang) throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
	{
		if(lang.getKey()==null){throw new AhtUtilIntegrityException("Key not set for: "+JaxbUtil.toString(lang, new AhtUtilsNsPrefixMapper()));}
		if(lang.getTranslation()==null){throw new AhtUtilIntegrityException("Translation not set for: "+JaxbUtil.toString(lang, new AhtUtilsNsPrefixMapper()));}
		L l = langClass.newInstance();
		l.setLkey(lang.getKey());
		l.setLang(lang.getTranslation());
		return l;
	}
}
