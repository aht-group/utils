package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import net.sf.ahtutils.interfaces.model.qa.UtilsQaCategory;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaResult;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStaff;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStakeholder;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTest;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestDiscussion;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestInfo;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaUsability;
import net.sf.ahtutils.interfaces.model.qa.UtilsQualityAssurarance;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsQaFacade extends UtilsFacade
{	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
	STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAU extends UtilsQaUsability<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATC extends UtilsStatus<QATC,L,D>,
	QATS extends UtilsStatus<QATS,L,D>,
	QARS extends UtilsStatus<QARS,L,D>,
	QAUS extends UtilsStatus<QAUS,L,D>>
		QA load(Class<QA> clQa, QA qa);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
	STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAU extends UtilsQaUsability<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATC extends UtilsStatus<QATC,L,D>,
	QATS extends UtilsStatus<QATS,L,D>,
	QARS extends UtilsStatus<QARS,L,D>,
	QAUS extends UtilsStatus<QAUS,L,D>>
		QAC load(Class<QAC> clQac, QAC category);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
	STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAU extends UtilsQaUsability<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATC extends UtilsStatus<QATC,L,D>,
	QATS extends UtilsStatus<QATS,L,D>,
	QARS extends UtilsStatus<QARS,L,D>,
	QAUS extends UtilsStatus<QAUS,L,D>>
		QAT load(Class<QAT> clTest, QAT test);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
	STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAU extends UtilsQaUsability<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATC extends UtilsStatus<QATC,L,D>,
	QATS extends UtilsStatus<QATS,L,D>,
	QARS extends UtilsStatus<QARS,L,D>,
	QAUS extends UtilsStatus<QAUS,L,D>>
		List<QAT> fQaTests(Class<QAT> clTest, Class<QAC> clCategory, Class<QA> clQa, QA qa);
	
	<L extends UtilsLang,
	D extends UtilsDescription,
	C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
	R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
	V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
	U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
	A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
	USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
	STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAU extends UtilsQaUsability<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
	QATC extends UtilsStatus<QATC,L,D>,
	QATS extends UtilsStatus<QATS,L,D>,
	QARS extends UtilsStatus<QARS,L,D>,
	QAUS extends UtilsStatus<QAUS,L,D>>
		List<QAT> fQaTests(Class<QAT> clTest, Class<QAC> clCategory, List<QAC> category);
}
