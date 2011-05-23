package net.sf.ahtutils.controller.factory;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilsStatusEjbFactory<T extends UtilsLang>
{
	static Log logger = LogFactory.getLog(UtilsStatusEjbFactory.class);
	
	public Map<String,UtilsLang> getLangMap(Langs langs)
	{
		return getLangMap(langs.getLang()); 
	}
	
	public Map<String,UtilsLang> getLangMap(List<Lang> langList)
	{
		Map<String,UtilsLang> mapLangs = new Hashtable<String,UtilsLang>();
		for(Lang jaxbLang : langList)
		{
			UtilsLang ahtLang = new AhtUtilsLang();
			ahtLang.setLkey(jaxbLang.getKey());
			ahtLang.setLang(jaxbLang.getTranslation());
			mapLangs.put(ahtLang.getLkey(), ahtLang);
		}
		return mapLangs;
	}
}
