package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Role;
import net.sf.ahtutils.xml.aht.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlAclRoleFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAclRoleFactory.class);
		
	private Role q;
	private String lang;
	
	public XmlAclRoleFactory(Query uQ){this(uQ.getRole(),uQ.getLang());}
	public XmlAclRoleFactory(Role q,String lang)
	{
		this.q=q;
		this.lang=lang;
	}
	
    public <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
    Role create(UtilsAclRole<L,D,C,R> ejb)
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