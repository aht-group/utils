package net.sf.ahtutils.factory.xml.qa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.qa.UtilsQaCategory;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaGroup;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaResult;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStaff;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStakeholder;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTest;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestDiscussion;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestInfo;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaUsability;
import net.sf.ahtutils.interfaces.model.qa.UtilsQualityAssurarance;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.xml.qa.Group;

public class XmlGroupFactory<L extends UtilsLang,
D extends UtilsDescription,
C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
GROUP extends UtilsQaGroup<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAU extends UtilsQaUsability<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATI extends UtilsQaTestInfo<L,D,C,R,V,U,A,USER,STAFF,GROUP,QA,QAC,QAT,QAU,QAR,QAS,QATD,QATI,QATC,QATS,QARS,QAUS>,
QATC extends UtilsStatus<QATC,L,D>,
QATS extends UtilsStatus<QATS,L,D>,
QARS extends UtilsStatus<QARS,L,D>,
QAUS extends UtilsStatus<QAUS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlGroupFactory.class);
		
	@SuppressWarnings("unused")
	private Group q;
	
	public XmlGroupFactory(Group q)
	{
		this.q=q;
	}
	
	public Group build(GROUP group)
	{
		Group xml = new Group();
		xml.setId(group.getId());
		
		xml.setName(group.getName());
		xml.setDescription(net.sf.ahtutils.factory.xml.status.XmlDescriptionFactory.build(group.getDescription()));
		
		
		return xml;
	}
	
	public static Group build(String name)
	{
		Group xml = new Group();
		xml.setName(name);
		return xml;
	}
}