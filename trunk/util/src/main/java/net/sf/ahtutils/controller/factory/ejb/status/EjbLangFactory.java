package net.sf.ahtutils.controller.factory.ejb.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLangFactory<L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLangFactory.class);
	
    final Class<L> langClass;
	
    public EjbLangFactory(final Class<L> langClass)
    {
        this.langClass = langClass;
    } 
    
    public static <L extends UtilsLang> EjbLangFactory<L> createFactory(final Class<L> langClass)
    {
        return new EjbLangFactory<L>(langClass);
    }
	
	public Map<String,L> getLangMap(Langs langs) throws InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		return getLangMap(langs.getLang()); 
	}
	
	public Map<String,L> getLangMap(List<Lang> langList) throws InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		Map<String,L> map = new Hashtable<String,L>();
		for(Lang lang : langList)
		{
			L l = createLang(lang);
			map.put(l.getLkey(), l);
		}
		return map;
	}
	
	public Map<String,L> createEmpty(String[] keys) throws InstantiationException, IllegalAccessException
	{
		Map<String,L> map = new Hashtable<String,L>();
		for(String key : keys)
		{
			map.put(key, createLang(key,""));
		}
		return map;
	}
	
	public Map<String,L> createLangMap(String key, String translation) throws InstantiationException, IllegalAccessException
	{
		Map<String,L> map = new Hashtable<String,L>();
		map.put(key, createLang(key, translation));
		return map;
	}
	
	public Map<String,L> createLangMap(L... langs) 
	{
		Map<String,L> map = new Hashtable<String,L>();
		for(L l : langs)
		{
			map.put(l.getLkey(), l);
		}
		return map;
	}
	
	public L createLang(String key, String translation)
	{
		try
		{
			L l = langClass.newInstance();
			l.setLkey(key);
			l.setLang(translation);
			return l;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		logger.error("Something went terribly wrong, see stacktrace. Unfortunately null is returned here!");
		return null;
	}
	
	public L createLang(Lang lang) throws UtilsIntegrityException
	{
		if(lang.getKey()==null){throw new UtilsIntegrityException("Key not set for: "+JaxbUtil.toString(lang, new AhtUtilsNsPrefixMapper()));}
		if(lang.getTranslation()==null){throw new UtilsIntegrityException("Translation not set for: "+JaxbUtil.toString(lang, new AhtUtilsNsPrefixMapper()));}
		return createLang(lang.getKey(), lang.getTranslation());
	}
}