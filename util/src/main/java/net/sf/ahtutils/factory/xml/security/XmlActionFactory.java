package net.sf.ahtutils.factory.xml.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.security.Action;

public class XmlActionFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlActionFactory.class);
		
	private net.sf.ahtutils.xml.security.Action q;
	private net.sf.ahtutils.xml.access.Action qAcl;
	
	public XmlActionFactory(net.sf.ahtutils.xml.security.Action q)
	{
		this.q=q;
	}
	public XmlActionFactory(net.sf.ahtutils.xml.access.Action qAcl)
	{
		this.qAcl=qAcl;
	}

	public net.sf.ahtutils.xml.security.Action build(A action)
	{
		Action xml = new Action();
		if(q.isSetCode()){xml.setCode(action.getCode());}
		if(q.isSetPosition()){xml.setPosition(action.getPosition());}
		if(q.isSetVisible()){xml.setVisible(action.isVisible());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(action.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(action.getDescription()));
		}
		
		if(q.isSetView())
		{
			XmlViewFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlViewFactory<L,D,C,R,V,U,A,AT,USER>(q.getView());
			xml.setView(f.build(action.getView()));
		}
		
		return xml;
	}
	
	public net.sf.ahtutils.xml.access.Action create(A action)
	{
		net.sf.ahtutils.xml.access.Action xml = new net.sf.ahtutils.xml.access.Action();
		if(qAcl.isSetCode()){xml.setCode(action.getCode());}
		
		if(qAcl.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(qAcl.getLangs());
			xml.setLangs(f.getUtilsLangs(action.getName()));
		}
		
		if(qAcl.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(qAcl.getDescriptions());
			xml.setDescriptions(f.create(action.getDescription()));
		}
		return xml;
	}
	
	public static net.sf.ahtutils.xml.security.Action build(String code)
	{
		net.sf.ahtutils.xml.security.Action xml = new net.sf.ahtutils.xml.security.Action();
		xml.setCode(code);
		return xml;
	}
}