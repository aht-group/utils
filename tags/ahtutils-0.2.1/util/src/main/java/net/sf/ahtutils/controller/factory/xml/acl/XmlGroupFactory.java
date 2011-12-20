package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlGroupFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlGroupFactory.class);
		
	private Group qRole;
	
	private String lang;
	
	public XmlGroupFactory(Group qRole, String lang)
	{
		this.qRole=qRole;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,U extends UtilsAclView<L,D,CU,U>,R extends UtilsAclGroup<L,D,CU,CR,U,R>>
		Group getRole(R aclRole)
	{
		Group role = new Group();
		
		if(qRole.isSetCode()){role.setCode(aclRole.getCode());}
		
		if(qRole.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(qRole.getLangs());
			role.setLangs(f.getUtilsLangs(aclRole.getName()));
		}
		
		if(qRole.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(qRole.getDescriptions());
			role.setDescriptions(f.create(aclRole.getDescription()));
		}
		
		if(qRole.isSetGroups())
		{
			XmlGroupsFactory f = new XmlGroupsFactory(qRole.getGroups(), lang);
			role.setGroups(f.getRoles(aclRole.getRoles()));
		}
		
		if(qRole.isSetViews())
		{
			XmlViewsFactory f = new XmlViewsFactory(qRole.getViews(), lang);
			role.setViews(f.getUsecases(aclRole.getUsecases()));
		}
		
		return role;
	}
}