package net.sf.ahtutils.factory.xml.status;

import java.util.Map;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.status.Langs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLangsFactory <L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(XmlLangsFactory.class);
		
	private Langs q;
	
	public XmlLangsFactory(Langs q)
	{
		this.q=q;
	}
	
	public Langs getUtilsLangs(Map<String,L> mapLangs)
	{
		Langs langs = new Langs();
		
		if(q.isSetLang())
		{
			XmlLangFactory<L> f = new XmlLangFactory<L>(q.getLang().get(0));
			for(L ahtLang : mapLangs.values())
			{
				langs.getLang().add(f.getUtilsLang(ahtLang));
			}
		}
		
		return langs;
	}	
}