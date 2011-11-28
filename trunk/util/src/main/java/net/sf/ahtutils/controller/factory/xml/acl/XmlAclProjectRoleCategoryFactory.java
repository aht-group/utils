package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclProjectRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.ProjectRoleCategory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlAclProjectRoleCategoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAclProjectRoleCategoryFactory.class);
		
	private ProjectRoleCategory qPrc;
	private String lang;
	
	public XmlAclProjectRoleCategoryFactory(ProjectRoleCategory qPrc, String lang)
	{
		this.qPrc=qPrc;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclProjectRole<L,D,C,R>>
		ProjectRoleCategory getProjectRoleCategory(C aclPrc)
	{
		ProjectRoleCategory prc = new ProjectRoleCategory();
		
		if(qPrc.isSetCode()){prc.setCode(aclPrc.getCode());}
		
		if(qPrc.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(qPrc.getLangs());
			prc.setLangs(f.getUtilsLangs(aclPrc.getName()));
		}
		
		if(qPrc.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(qPrc.getDescriptions());
			prc.setDescriptions(f.create(aclPrc.getDescription()));
		}
		
		if(qPrc.isSetProjectRoles())
		{
			XmlAclProjectRolesFactory f = new XmlAclProjectRolesFactory(qPrc.getProjectRoles(), lang);
			prc.setProjectRoles(f.getProjectRoles(aclPrc.getRoles()));
		}
		
		return prc;
	}
}