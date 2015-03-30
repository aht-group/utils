package net.sf.ahtutils.factory.xml.security;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.xml.security.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRoleFactory<L extends UtilsLang,
	D extends UtilsDescription, 
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRoleFactory.class);
		
	private Role q;
	
	public XmlRoleFactory(Role q)
	{
		this.q=q;
	}
	
	public static Role create(String code, String label)
	{
		Role xml = new Role();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
	
	public Role build(R role)
	{
		Role xml = new Role();
		
		if(q.isSetCode()){xml.setCode(role.getCode());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(role.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(role.getDescription()));
		}
		
		if(q.isSetViews())
		{
			XmlViewsFactory<L,D,C,R,V,U,A,USER> f = new XmlViewsFactory<L,D,C,R,V,U,A,USER>(q.getViews());
			xml.setViews(f.build(role.getViews()));
		}
		
		if(q.isSetActions())
		{
			XmlActionsFactory<L,D,C,R,V,U,A,USER> f = new XmlActionsFactory<L,D,C,R,V,U,A,USER>(q.getActions());
			xml.setActions(f.build(role.getActions()));
		}
		
		if(q.isSetUsecases())
		{
			XmlUsecasesFactory<L,D,C,R,V,U,A,USER> f = new XmlUsecasesFactory<L,D,C,R,V,U,A,USER>(q.getUsecases());
			xml.setUsecases(f.build(role.getUsecases()));
		}
			
		return xml;
		
	}
}