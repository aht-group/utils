package net.sf.ahtutils.controller.factory.xml.status;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.status.Langs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLangsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlLangsFactory.class);
		
	private Langs q;
	
	public XmlLangsFactory(Langs q)
	{
		this.q=q;
	}
	
	public <L extends UtilsLang> Langs getUtilsLangs(Map<String,L> mapLangs)
	{
		net.sf.ahtutils.xml.status.Langs langs = new net.sf.ahtutils.xml.status.Langs();
		
		if(q.isSetLang())
		{
			XmlLangFactory f = new XmlLangFactory(q.getLang().get(0));
			for(L ahtLang : mapLangs.values())
			{
				langs.getLang().add(f.getUtilsLang(ahtLang));
			}
		}
		return langs;
	}	
}