package net.sf.ahtutils.controller.factory.xml.acl;

import java.util.List;

import net.sf.ahtutils.controller.factory.xml.security.XmlRoleFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlAclRolesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAclRolesFactory.class);
		
	private Roles qProjectRoles;
	
	private String lang;
	
	public XmlAclRolesFactory(Roles qProjectRoles, String lang)
	{
		this.qProjectRoles=qProjectRoles;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
			Roles getProjectRoles(List<R> lRoles)
	{
		Roles roles = new Roles();
		
		if(qProjectRoles.isSetRole())
		{
			XmlRoleFactory f = new XmlRoleFactory(qProjectRoles.getRole().get(0),lang);
			for(R aclProjectRole : lRoles)
			{
				roles.getRole().add(f.create(aclProjectRole));
			}
		}
		return roles;
	}
}