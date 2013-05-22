package net.sf.ahtutils.controller.factory.ejb.acl;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAclProjectRoleFactory<L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAclProjectRoleFactory.class);
	
    final Class<L> langClass;
    final Class<D> descriptionClass;
    final Class<C> clCategory;
    final Class<R> clRole;
	
    public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
    	EjbAclProjectRoleFactory<L,D,C,R> createFactory(final Class<L> langClass,final Class<D> descriptionClass,final Class<C> clCategory,final Class<R> clRole)
    {
        return new EjbAclProjectRoleFactory<L,D,C,R>(langClass,descriptionClass,clCategory,clRole);
    }
    
    public EjbAclProjectRoleFactory(final Class<L> langClass,final Class<D> descriptionClass,final Class<C> clCategory,final Class<R> clRole)
    {
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.clCategory = clCategory;
        this.clRole = clRole;
    } 
    
    public R create(C category, String code)
    {
    	R ejb = null;
    	
    	try
    	{
			ejb = clRole.newInstance();
			ejb.setCode(code);
			ejb.setCategory(category);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}