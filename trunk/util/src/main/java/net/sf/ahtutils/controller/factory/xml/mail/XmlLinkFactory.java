package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.model.interfaces.link.UtilsLink;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.mail.Link;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLinkFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlLinkFactory.class);
	
	public static <S extends UtilsStatus<L>, L extends UtilsLang, LI extends UtilsLink<S,L>> Link create(LI ejb, String url)
	{
		Link xml = new Link();
		xml.setCode(ejb.getCode());
		xml.setUrl(url);
		return xml;
	}
	
}