package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclGroup;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRoleFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlRoleFactory.class);
		
	private Role qRole;
	
	private String lang;
	
	public XmlRoleFactory(Role qRole, String lang)
	{
		this.qRole=qRole;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,CR extends UtilsAclCategoryRole<L,D,CU,CR,U,R>,U extends UtilsAclUsecase<L,D,CU,U>,R extends UtilsAclGroup<L,D,CU,CR,U,R>>
		Role getRole(R aclRole)
	{
		Role role = new Role();
		
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
		
		if(qRole.isSetRoles())
		{
			XmlRolesFactory f = new XmlRolesFactory(qRole.getRoles(), lang);
			role.setRoles(f.getRoles(aclRole.getRoles()));
		}
		
		if(qRole.isSetUsecases())
		{
			XmlUsecasesFactory f = new XmlUsecasesFactory(qRole.getUsecases(), lang);
			role.setUsecases(f.getUsecases(aclRole.getUsecases()));
		}
		
		return role;
	}
}