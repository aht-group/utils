package net.sf.ahtutils.factory.xml.security;

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
import net.sf.ahtutils.xml.security.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStaffFactory<L extends UtilsLang,
							D extends UtilsDescription,
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
							STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,
							DOMAIN extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(XmlStaffFactory.class);
		
	private Staff q;
	
	public XmlStaffFactory(Staff q)
	{
		this.q=q;
	}
	
	public static Staff build()
	{
		Staff xml = new Staff();

		return xml;
	}
	
	public Staff build(STAFF staff)
	{
		Staff xml = new Staff();
		
		if(q.isSetId()){xml.setId(staff.getId());}
		
		if(q.isSetRole())
		{
			XmlRoleFactory<L,D,C,R,V,U,A,USER> f = new XmlRoleFactory<L,D,C,R,V,U,A,USER>(q.getRole());
			xml.setRole(f.build(staff.getRole()));
		}
		
		return xml;
		
	}
}