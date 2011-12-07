package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRoleCategoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAclProjectRoleFactory.class);
		
	private Category qC;
	private String lang;
	
	public XmlRoleCategoryFactory(Category qC, String lang)
	{
		this.qC=qC;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,U extends UtilsAclUsecase<L,D,CU,U>,R extends UtilsAclRole<L,D,CU,CR,U,R>>
	Category getRoleCategory(CR aclRoleCategory)
	{
		Category rc = new Category();
		
		if(qC.isSetCode()){rc.setCode(aclRoleCategory.getCode());}
		
		if(qC.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(qC.getLangs());
			rc.setLangs(f.getUtilsLangs(aclRoleCategory.getName()));
		}
		
		if(qC.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(qC.getDescriptions());
			rc.setDescriptions(f.create(aclRoleCategory.getDescription()));
		}
		
		if(qC.isSetRoles())
		{
			XmlRolesFactory f = new XmlRolesFactory(qC.getRoles(), lang);
			rc.setRoles(f.getRoles(aclRoleCategory.getRoles()));
		}
		
		return rc;
	}
}