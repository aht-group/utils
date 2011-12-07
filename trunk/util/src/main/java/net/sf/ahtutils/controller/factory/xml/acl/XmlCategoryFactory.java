package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCategoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAclRoleFactory.class);
		
	private Category qC;
	private String lang;
	
	public XmlCategoryFactory(Category qC, String lang)
	{
		this.qC=qC;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,U extends UtilsAclUsecase<L,D,CU,U>,R extends UtilsAclGroup<L,D,CU,CR,U,R>>
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
		
		if(qC.isSetGroups())
		{
			XmlGroupsFactory f = new XmlGroupsFactory(qC.getGroups(), lang);
			rc.setGroups(f.getRoles(aclRoleCategory.getRoles()));
		}
		
		return rc;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
	Category getProjectRoleCategory(C aclPrc)
	{
		Category prc = new Category();
		
		if(qC.isSetCode()){prc.setCode(aclPrc.getCode());}
		
		if(qC.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(qC.getLangs());
			prc.setLangs(f.getUtilsLangs(aclPrc.getName()));
		}
		
		if(qC.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(qC.getDescriptions());
			prc.setDescriptions(f.create(aclPrc.getDescription()));
		}
		
		if(qC.isSetRoles())
		{
			XmlAclRolesFactory f = new XmlAclRolesFactory(qC.getRoles(), lang);
			prc.setRoles(f.getProjectRoles(aclPrc.getRoles()));
		}
		
		return prc;
	}
}