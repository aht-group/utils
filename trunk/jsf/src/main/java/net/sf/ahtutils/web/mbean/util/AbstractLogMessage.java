package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractLogMessage <L extends UtilsLang,
D extends UtilsDescription,
C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
USER extends UtilsUser<L,D,C,R,V,U,A,USER>>	 implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractLogMessage.class);
	private static final long serialVersionUID = 1L;
	
	public static String postConstruct(){return "@PostConstruct";}
	
	 public static String addEntity(Class<?> cl)
	 {
	        StringBuffer sb = new StringBuffer();
	        sb.append("Adding new ").append(cl.getSimpleName());
	        return sb.toString();
	 }

	    public static <T extends EjbWithId> String addEntity(T t)
	    {
	        StringBuffer sb = new StringBuffer();
	        sb.append("Adding ").append(t.getClass().getSimpleName());
	        sb.append(": ").append(t.toString());
	        return sb.toString();
	    }

	    public static <T extends EjbWithId> String selectEntity(T t)
	    {
	        StringBuffer sb = new StringBuffer();
	        sb.append("Selecting ").append(t.getClass().getSimpleName());
	        sb.append(": ").append(t.toString());
	        return sb.toString();
	    }

	    public static <T extends EjbWithId> String saveEntity(T t)
	    {
	        StringBuffer sb = new StringBuffer();
	        sb.append("Save ").append(t.getClass().getSimpleName());
	        sb.append(": ").append(t.toString());
	        return sb.toString();
	    }
}
