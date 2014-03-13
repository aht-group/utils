package net.sf.ahtutils.controller.factory.xml.security;

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
import net.sf.ahtutils.xml.aht.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRoleFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlRoleFactory.class);
		
	private Role q;
	private String lang;
	
	public XmlRoleFactory(Query uQ){this(uQ.getRole(),uQ.getLang());}
	public XmlRoleFactory(Role q,String lang)
	{
		this.q=q;
		this.lang=lang;
	}
	
    public <L extends UtilsLang,
	 	D extends UtilsDescription,
	 	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	 	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	 	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	 	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	 	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	 	USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
    Role create(R ejb)
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
			XmlLangsFactory f = new XmlLangsFactory(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
    	
    	return xml;
    }
}