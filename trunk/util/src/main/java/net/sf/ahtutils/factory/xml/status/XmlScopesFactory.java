package net.sf.ahtutils.factory.xml.status;

import java.util.List;

import net.sf.ahtutils.exception.xml.UtilsXmlStructureException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Scopes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlScopesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlScopesFactory.class);
		
	private Scopes q;
	private String lang;
	
	public XmlScopesFactory(Scopes q, String lang)
	{
		this.q=q;
		this.lang=lang;
	}
	
	public <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription> Scopes build(List<S> ejbs) throws UtilsXmlStructureException
	{
		Scopes xml = new Scopes();
		
		if(xml.isSetSize())
		{
			if(ejbs!=null){xml.setSize(ejbs.size());}
			else{xml.setSize(0);}
		}
		
		if(q.isSetScope())
		{
			if(ejbs!=null)
			{
				XmlScopeFactory f = new XmlScopeFactory(q.getScope().get(0),lang);
				for(S s : ejbs)
				{
					xml.getScope().add(f.build(s));
				}
			}
		}
		
		return xml;
	}	
}