package net.sf.ahtutils.controller.factory;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
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
    
	public S create(Status status) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		if(!status.isSetLangs()){throw new AhtUtilsIntegrityException("No <langs> available for "+JaxbUtil.toString(status));}
        S s = statusClass.newInstance();
        s.setCode(status.getCode());
        s.setName(getLangMap(status.getLangs()));
        return s;
    }
	
	public Map<String,L> getLangMap(Langs langs) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		return getLangMap(langs.getLang()); 
	}
	
	public Map<String,L> getLangMap(List<Lang> langList) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		Map<String,L> map = new Hashtable<String,L>();
		for(Lang lang : langList)
		{
			L l = createLang(lang);
			map.put(l.getLkey(), l);
		}
		return map;
	}
	
	public Map<String,L> createLangMap(String key, String translation) throws InstantiationException, IllegalAccessException
	{
		Map<String,L> map = new Hashtable<String,L>();
		map.put(key, createLang(key, translation));
		return map;
	}
	
	public L createLang(Lang lang) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		if(lang.getKey()==null){throw new AhtUtilsIntegrityException("Key not set for: "+JaxbUtil.toString(lang, new AhtUtilsNsPrefixMapper()));}
		if(lang.getTranslation()==null){throw new AhtUtilsIntegrityException("Translation not set for: "+JaxbUtil.toString(lang, new AhtUtilsNsPrefixMapper()));}
		return createLang(lang.getKey(), lang.getTranslation());
	}
	
	private L createLang(String key, String translation) throws InstantiationException, IllegalAccessException
	{
		L l = langClass.newInstance();
		l.setLkey(key);
		l.setLang(translation);
		return l;
	}
}