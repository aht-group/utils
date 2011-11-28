package net.sf.ahtutils.controller.factory.ejb.acl;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclProjectRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAclProjectRoleCategoryFactory<L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclProjectRole<L,D,C,R>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAclProjectRoleCategoryFactory.class);
	
    final Class<L> langClass;
    final Class<D> descriptionClass;
    final Class<C> clCategory;
    final Class<R> clRole;
	
    public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclProjectRole<L,D,C,R>>
    	EjbAclProjectRoleCategoryFactory<L,D,C,R> createFactory(final Class<L> langClass,final Class<D> descriptionClass,final Class<C> clCategory,final Class<R> clRole)
    {
        return new EjbAclProjectRoleCategoryFactory<L,D,C,R>(langClass,descriptionClass,clCategory,clRole);
    }
    
    public EjbAclProjectRoleCategoryFactory(final Class<L> langClass,final Class<D> descriptionClass,final Class<C> clCategory,final Class<R> clRole)
    {
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        this.clCategory = clCategory;
        this.clRole = clRole;
    } 
    
    public C create(String code)
    {
    	C ejb = null;
    	
    	try
    	{
			ejb = clCategory.newInstance();
			ejb.setCode(code);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}