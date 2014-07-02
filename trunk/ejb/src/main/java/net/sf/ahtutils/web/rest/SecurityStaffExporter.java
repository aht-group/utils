package net.sf.ahtutils.web.rest;

import net.sf.ahtutils.factory.xml.security.XmlStaffFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.util.query.SecurityQuery;
import net.sf.ahtutils.xml.security.Staffs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityStaffExporter <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>,STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,DOMAIN extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityStaffExporter.class);
	
	private UtilsSecurityFacade fSecurity;
	private final Class<STAFF> cStaff;
	
	private SecurityStaffExporter(UtilsSecurityFacade fSecurity,final Class<STAFF> cStaff)
	{
		this.fSecurity=fSecurity;
		this.cStaff=cStaff;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>,STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,DOMAIN extends EjbWithId>
	SecurityStaffExporter<L,D,C,R,V,U,A,USER,STAFF,DOMAIN>
		factory(UtilsSecurityFacade fSecurity, final Class<STAFF> cStaff)
	{
		return new SecurityStaffExporter<L,D,C,R,V,U,A,USER,STAFF,DOMAIN>(fSecurity,cStaff);
	}

	public Staffs exportStaffs()
	{
		XmlStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN> f = new XmlStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN>(SecurityQuery.exStaff());
		
		Staffs staffs = new Staffs();
		
		for(STAFF ejb : fSecurity.all(cStaff))
		{
			staffs.getStaff().add(f.build(ejb));
		}
		
		return staffs;
	}
}