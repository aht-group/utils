package net.sf.ahtutils.controller.factory.ejb.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EjbLangFactory<L extends UtilsLang>
{
	static Log logger = LogFactory.getLog(EjbLangFactory.class);
	
    final Class<L> langClass;
	
    public EjbLangFactory(final Class<L> langClass)
    {
        this.langClass = langClass;
    } 
    
    public static <L extends UtilsLang> EjbLangFactory<L> createFactory(final Class<L> langClass)
    {
        return new EjbLangFactory<L>(langClass);
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
	
	private L createLang(String key, String translation) throws InstantiationException, IllegalAccessException
	{
		L l = langClass.newInstance();
		l.setLkey(key);
		l.setLang(translation);
		return l;
	}
	
	public L createLang(Lang lang) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		if(lang.getKey()==null){throw new AhtUtilsIntegrityException("Key not set for: "+JaxbUtil.toString(lang, new AhtUtilsNsPrefixMapper()));}
		if(lang.getTranslation()==null){throw new AhtUtilsIntegrityException("Translation not set for: "+JaxbUtil.toString(lang, new AhtUtilsNsPrefixMapper()));}
		return createLang(lang.getKey(), lang.getTranslation());
	}
}