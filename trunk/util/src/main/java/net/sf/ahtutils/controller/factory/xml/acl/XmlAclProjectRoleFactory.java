package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclProjectRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.ProjectRole;
import net.sf.ahtutils.xml.aht.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlAclProjectRoleFactory
{
	static Log logger = LogFactory.getLog(XmlAclProjectRoleFactory.class);
		
	private ProjectRole q;
	private String lang;
	
	public XmlAclProjectRoleFactory(Query q){this(q.getProjectRole(),q.getLang());}
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
    	return xml;
    }
}