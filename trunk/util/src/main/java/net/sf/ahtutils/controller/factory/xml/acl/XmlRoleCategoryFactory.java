package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.RoleCategory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRoleCategoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAclProjectRoleFactory.class);
		
	private RoleCategory qRc;
	private String lang;
	
	public XmlRoleCategoryFactory(RoleCategory qRc, String lang)
	{
		this.qRc=qRc;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,U extends UtilsAclUsecase<L,D,CU,U>,R extends UtilsAclRole<L,D,CU,CR,U,R>>
		RoleCategory getRoleCategory(CR aclRoleCategory)
	{
		RoleCategory rc = new RoleCategory();
		
		if(qRc.isSetCode()){rc.setCode(aclRoleCategory.getCode());}
		
		if(qRc.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(qRc.getLangs());
			rc.setLangs(f.getUtilsLangs(aclRoleCategory.getName()));
		}
		
		if(qRc.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(qRc.getDescriptions());
			rc.setDescriptions(f.create(aclRoleCategory.getDescription()));
		}
		
		if(qRc.isSetRoles())
		{
			XmlRolesFactory f = new XmlRolesFactory(qRc.getRoles(), lang);
			rc.setRoles(f.getRoles(aclRoleCategory.getRoles()));
		}
		
		return rc;
	}
}