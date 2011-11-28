package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclProjectRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.ProjectRole;
import net.sf.ahtutils.xml.aht.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlAclProjectRoleFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlAclProjectRoleFactory.class);
		
	private ProjectRole q;
	private String lang;
	
	public XmlAclProjectRoleFactory(Query uQ){this(uQ.getProjectRole(),uQ.getLang());}
	public XmlAclProjectRoleFactory(ProjectRole q,String lang)
	{
		this.q=q;
		this.lang=lang;
	}
	
    public <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclProjectRole<L,D,C,R>>
    ProjectRole create(UtilsAclProjectRole<L,D,C,R> ejb)
    {
    	ProjectRole xml = new ProjectRole();
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