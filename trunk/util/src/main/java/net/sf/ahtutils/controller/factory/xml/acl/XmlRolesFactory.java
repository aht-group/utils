package net.sf.ahtutils.controller.factory.xml.acl;

import java.util.List;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRolesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlRolesFactory.class);
		
	private Roles qRoles;
	private String lang;
	
	public XmlRolesFactory(Roles qRoles, String lang)
	{
		this.qRoles=qRoles;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,U extends UtilsAclUsecase<L,D,CU,U>,R extends UtilsAclRole<L,D,CU,CR,U,R>>
		Roles getRoles(List<R> lRoles)
	{
		Roles roles = new Roles();
		
		if(qRoles.isSetRole())
		{
			XmlRoleFactory f = new XmlRoleFactory(qRoles.getRole().get(0),lang);
			for(R aclRole : lRoles)
			{
				roles.getRole().add(f.getRole(aclRole));
			}
		}
		return roles;
	}
}