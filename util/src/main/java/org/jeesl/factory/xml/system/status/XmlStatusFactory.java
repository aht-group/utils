package org.jeesl.factory.xml.system.status;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;
import net.sf.ahtutils.xml.status.Parent;
import net.sf.ahtutils.xml.status.Status;

public class XmlStatusFactory<S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlStatusFactory.class);
		
	private Status q;
	private String localeCode;
	
	public XmlStatusFactory(Status q){this(null,q);}
	public XmlStatusFactory(String localeCode, Status q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public List<Status> build(List<S> list)
	{
		List<Status> xml = new ArrayList<Status>();
		for(S s : list){xml.add(build(s));}
		return xml;
	}
	
	public Status build(S ejb){return build(ejb,null);}
	public Status build(S ejb, String group)
	{
		Status xml = new Status();
		xml.setGroup(group);
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetStyle()){xml.setStyle(ejb.getStyle());}
		if(q.isSetImage()){xml.setImage(ejb.getImage());}
		if(q.isSetSymbol() && (ejb instanceof UtilsWithSymbol)){xml.setSymbol(ejb.getSymbol());}
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		if(q.isSetLabel() && localeCode!=null)
		{
			if(ejb.getName()!=null)
			{
				if(ejb.getName().containsKey(localeCode)){xml.setLabel(ejb.getName().get(localeCode).getLang());}
				else
				{
					String msg = "No translation "+localeCode+" available in "+ejb;
					logger.warn(msg);
					xml.setLabel(msg);
				}
			}
			else
			{
				String msg = "No @name available in "+ejb;
				logger.warn(msg);
				xml.setLabel(msg);
			}
		}
		
		if(q.isSetParent() && ejb.getParent()!=null)
		{
			Parent parent = new Parent();
			parent.setCode(ejb.getParent().getCode());
			xml.setParent(parent);
		}
		
		return xml;
	}
	
	public static <E extends Enum<E>> Status build(E code)
	{
		return create(code.toString());
	}
	
	public static Status create(String code)
	{
		Status xml = new Status();
		xml.setCode(code);
		return xml;
	}
	
	public static Status buildLabel(String code, String label)
	{
		Status xml = new Status();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
	
	public static Status id(){return id(0);}
	public static Status id(long id)
	{
		Status xml = new Status();
		xml.setId(id);
		return xml;
	}
}