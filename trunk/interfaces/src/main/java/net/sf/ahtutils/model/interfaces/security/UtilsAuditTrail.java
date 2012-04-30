package net.sf.ahtutils.model.interfaces.security;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsAuditTrail<L extends UtilsLang,
								 D extends UtilsDescription,
								 C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
								 R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
								 V extends UtilsSecurityView<L,D,C,R,V,U,A>,
								 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
								 A extends UtilsSecurityAction<L,D,C,R,V,U,A>,
								 US extends UtilsUser<L,D,C,R,V,U,A>,
								 TY extends UtilsStatus<L>>
					extends EjbWithId
{
	public US getUser();
	public void setUser(US user);
	
	public TY getType();
	public void setType(TY type);
	
	public Date getRecord();
	public void setRecord(Date record);
}