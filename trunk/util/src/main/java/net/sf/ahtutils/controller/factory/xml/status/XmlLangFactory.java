package net.sf.ahtutils.controller.factory.xml.status;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlLangFactory
{
	static Log logger = LogFactory.getLog(XmlLangFactory.class);
		
	private net.sf.ahtutils.xml.status.Lang qLangUtils;
	
	public XmlLangFactory(net.sf.ahtutils.xml.status.Lang qLangUtils)
	{
		this.qLangUtils=qLangUtils;
	}
	
	public <L extends UtilsLang> net.sf.ahtutils.xml.status.Lang getUtilsLang(L ejb)
	{
		net.sf.ahtutils.xml.status.Lang lang = new net.sf.ahtutils.xml.status.Lang();
		if(qLangUtils.isSetKey()){lang.setKey(ejb.getLang());}
		if(qLangUtils.isSetTranslation()){lang.setTranslation(ejb.getLkey());}
		return lang;
	}
	
}