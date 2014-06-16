package net.sf.ahtutils.factory.xml.qa;

import net.sf.ahtutils.interfaces.model.qa.UtilsQaCategory;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaResult;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStaff;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStakeholder;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTest;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestDiscussion;
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
import net.sf.ahtutils.xml.qa.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTestFactory<L extends UtilsLang,
							D extends UtilsDescription,
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
							STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
							QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
							QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
							QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
							QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
							QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
							QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
							QATS extends UtilsStatus<QATS,L,D>,
							QARS extends UtilsStatus<QARS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTestFactory.class);
		
	private Test q;
	
	public XmlTestFactory(Test q)
	{
		this.q=q;
	}
	
	public static Test build()
	{
		Test xml = new Test();

		return xml;
	}
	
	public Test build(QAT test)
	{
		Test xml = new Test();
		
		if(q.isSetId()){xml.setId(test.getId());}
		if(q.isSetCode()){xml.setCode(test.getCode());}
		if(q.isSetName()){xml.setName(test.getName());}

		if(q.isSetReference() && test.getReference()!=null){xml.setReference(XmlReferenceFactory.build(test.getReference()));}
		if(q.isSetDescription() && test.getDescription()!=null){xml.setDescription(XmlDescriptionFactory.build(test.getDescription()));}
		if(q.isSetPreCondition() && test.getPreCondition()!=null){xml.setPreCondition(XmlPreConditionFactory.build(test.getPreCondition()));}
		if(q.isSetSteps() && test.getSteps()!=null){xml.setSteps(XmlStepsFactory.build(test.getSteps()));}
		
		return xml;
	}
}