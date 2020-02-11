package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.model.interfaces.link.UtilsLink;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.xml.system.io.mail.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLinkFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlLinkFactory.class);
	
	public static <S extends JeeslStatus<S,L,D>, L extends JeeslLang, LI extends UtilsLink<S,L,D>, D extends JeeslDescription> Link create(LI ejb, String url)
	{
		Link xml = new Link();
		xml.setCode(ejb.getCode());
		xml.setUrl(url);
		return xml;
	}
}