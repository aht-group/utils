package org.jeesl.factory.xml.system.status;

import net.sf.ahtutils.xml.status.Scope;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlScopeFactory<L extends JeeslLang, D extends JeeslDescription,S extends JeeslStatus<S,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlScopeFactory.class);
	
	private String localeCode;
	private Scope q;

	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	
	public XmlScopeFactory(Scope q){this(null,q);}
	public XmlScopeFactory(String localeCode, Scope q)
	{
		this.localeCode=localeCode;
		this.q=q;
		
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
	}
	
	public static Scope build(String code)
	{
		Scope xml = new Scope();
		xml.setCode(code);
		return xml;
	}
	
	public Scope build(S ejb)
	{
		Scope xml = new Scope();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(ejb.getDescription()));}
		
		if(q.isSetLabel() && localeCode!=null && ejb.getName()!=null && ejb.getName().containsKey(localeCode))
		{
			xml.setLabel(ejb.getName().get(localeCode).getLang());
		}
		
		return xml;
	}
}