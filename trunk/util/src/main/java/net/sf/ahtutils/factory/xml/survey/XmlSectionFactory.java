package net.sf.ahtutils.factory.xml.survey;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionFactory;
import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSurveyFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurvey;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyAnswer;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyCorrelation;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyData;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyOption;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyQuestion;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveySection;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyTemplate;
import net.sf.ahtutils.xml.survey.Section;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSectionFactory<L extends UtilsLang,D extends UtilsDescription,SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,CORRELATION extends UtilsSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSectionFactory.class);
		
	private UtilsSurveyFacade<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey;
	private Class<SECTION> cSection;
	
	private Section q;
	
	public XmlSectionFactory(Section q)
	{
		this.q=q;
	}
	
	public void lazyLoad(UtilsSurveyFacade<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey,Class<SECTION> cSection)
	{
		this.fSurvey=fSurvey;
		this.cSection=cSection;
	}
	
	public Section build(SECTION ejb)
	{
		if(fSurvey!=null){ejb = fSurvey.load(cSection,ejb);}
		Section xml = new Section();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		
		if(q.isSetDescription()){xml.setDescription(XmlDescriptionFactory.build(ejb.getName()));}
		if(q.isSetRemark() && ejb.getRemark()!=null){xml.setRemark(XmlRemarkFactory.build(ejb.getRemark()));}
		
		if(q.isSetQuestion())
		{
			XmlQuestionFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> f = new XmlQuestionFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getQuestion().get(0));
			for(QUESTION question : ejb.getQuestions())
			{
				xml.getQuestion().add(f.build(question));
			}
		}
		
		return xml;
	}
	
	
}