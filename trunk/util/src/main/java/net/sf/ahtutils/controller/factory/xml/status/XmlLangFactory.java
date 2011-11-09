package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.status.Lang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlLangFactory
{
	static Log logger = LogFactory.getLog(XmlLangFactory.class);
		
	private Lang q;
	
	public XmlLangFactory(Lang q)
	{
		this.q=q;
	}
	
	public <L extends UtilsLang> Lang getUtilsLang(L ejb)
	{
		Lang lang = new Lang();
		if(q.isSetKey()){lang.setKey(ejb.getLang());}
		if(q.isSetTranslation()){lang.setTranslation(ejb.getLkey());}
		return lang;
	}
	
}