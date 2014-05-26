package net.sf.ahtutils.factory.xml.qa;

import net.sf.ahtutils.interfaces.model.qa.UtilsQaCategory;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTest;
import net.sf.ahtutils.interfaces.model.qa.UtilsQualityAssurarance;
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
							STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,QA>,
							QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,STATUS,CONDITION>,
							QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,STATUS,CONDITION>,
							QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,STATUS,CONDITION>,
							STATUS extends UtilsStatus<STATUS,L,D>,
							CONDITION extends UtilsStatus<CONDITION,L,D>>
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

		
		return xml;
		
	}
}