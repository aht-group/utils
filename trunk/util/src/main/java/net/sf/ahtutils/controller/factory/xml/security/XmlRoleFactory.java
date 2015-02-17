package net.sf.ahtutils.controller.factory.xml.security;

import net.sf.ahtutils.factory.xml.security.XmlActionsFactory;
import net.sf.ahtutils.factory.xml.security.XmlUsecasesFactory;
import net.sf.ahtutils.factory.xml.security.XmlViewsFactory;
import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRoleFactory <L extends UtilsLang,
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
	private net.sf.ahtutils.xml.security.Role qSec;
	private String lang;
	
	public XmlRoleFactory(Role q){this(q,null);}
	public XmlRoleFactory(Role q,String lang)
	{
		this.q=q;
		this.lang=lang;
	}
	
	public XmlRoleFactory(net.sf.ahtutils.xml.security.Role qSec){this(qSec,null);}
	public XmlRoleFactory(net.sf.ahtutils.xml.security.Role qSec,String lang)
	{
		this.qSec=qSec;
		this.lang=lang;
	}
	
    public Role create(R ejb)
    {
    	Role xml = new Role();
    	if(q.isSetCode()){xml.setCode(ejb.getCode());}
    	if(q.isSetName())
    	{
    		if(ejb.getName()!=null && ejb.getName().containsKey(lang))
    		{
    			xml.setName(ejb.getName().get(lang).getLang());
    		}
    	}
    	
    	if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
    	
    	return xml;
    }
    
    public net.sf.ahtutils.xml.security.Role build(R role)
    {
    	net.sf.ahtutils.xml.security.Role xml = new net.sf.ahtutils.xml.security.Role();
    	if(qSec.isSetCode()){xml.setCode(role.getCode());}
    	
    	if(qSec.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(qSec.getLangs());
			xml.setLangs(f.getUtilsLangs(role.getName()));
		}
    	
		if(qSec.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(qSec.getDescriptions());
			xml.setDescriptions(f.create(role.getDescription()));
		}
		
		if(qSec.isSetViews())
		{
			XmlViewsFactory<L,D,C,R,V,U,A,USER> f = new XmlViewsFactory<L,D,C,R,V,U,A,USER>(qSec.getViews());
			xml.setViews(f.build(role.getViews()));
		}
		
		if(qSec.isSetActions())
		{
			XmlActionsFactory<L,D,C,R,V,U,A,USER> f = new XmlActionsFactory<L,D,C,R,V,U,A,USER>(qSec.getActions());
			xml.setActions(f.build(role.getActions()));
		}
		
		if(qSec.isSetUsecases())
		{
			XmlUsecasesFactory<L,D,C,R,V,U,A,USER> f = new XmlUsecasesFactory<L,D,C,R,V,U,A,USER>(qSec.getUsecases());
			xml.setUsecases(f.build(role.getUsecases()));
		}
    	
    	return xml;
    }
    
    public static net.sf.ahtutils.xml.security.Role id()
    {
    	net.sf.ahtutils.xml.security.Role role = new net.sf.ahtutils.xml.security.Role();
    	role.setId(0);
    	return role;
    }
    
    public static net.sf.ahtutils.xml.security.Role build(String code)
    {
    	net.sf.ahtutils.xml.security.Role role = new net.sf.ahtutils.xml.security.Role();
    	role.setCode(code);
    	return role;
    }
}