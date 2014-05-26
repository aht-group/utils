package net.sf.ahtutils.interfaces.model.qa;

import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsQaTest<L extends UtilsLang,
					D extends UtilsDescription,
					C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
					R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
					V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
					U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
					A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
					USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
					STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,QA>,
					QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,STATUS,CONDITION>,
					QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,STATUS,CONDITION>,
					QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,STATUS,CONDITION>,
					STATUS extends UtilsStatus<STATUS,L,D>,
					CONDITION extends UtilsStatus<CONDITION,L,D>>
			extends EjbWithId,EjbWithName,EjbWithCode
{
	QAC getCategory();
	void setCategory(QAC category);
	
	String getReference();
	void setReference(String reference);
	
	String getDescription();
	void setDescription(String description);
	
}