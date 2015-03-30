package net.sf.ahtutils.controller.factory.ejb.acl;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclRole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAclCategoryRoleFactory<L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAclCategoryRoleFactory.class);
	
    final Class<L> clLang;
    final Class<D> clDescription;
    final Class<C> clCategory;
    final Class<R> clRole;
	
    public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclRole<L,D,C,R>>
    	EjbAclCategoryRoleFactory<L,D,C,R> createFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole)
    {
        return new EjbAclCategoryRoleFactory<L,D,C,R>(clLang,clDescription,clCategory,clRole);
    }
    
    public EjbAclCategoryRoleFactory(final Class<L> clLang,final Class<D> clDescription,final Class<C> clCategory,final Class<R> clRole)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
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