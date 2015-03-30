package net.sf.ahtutils.factory.xml.survey;

import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
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
import net.sf.ahtutils.xml.survey.Survey;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSurveyFactory<L extends UtilsLang,D extends UtilsDescription,SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,CORRELATION extends UtilsSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSurveyFactory.class);
	
	private UtilsSurveyFacade<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey;
	private Class<SURVEY> cSurvey;
	private Class<DATA> cData;
	
	private Survey q;
	
	public XmlSurveyFactory(Survey q)
	{
		this.q=q;
	}
	
	public void lazyLoad(UtilsSurveyFacade<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey,Class<SURVEY> cSurvey,Class<DATA> cData)
	{
		this.fSurvey=fSurvey;
		this.cSurvey=cSurvey;
		this.cData=cData;
	}
	
	public Survey build(SURVEY ejb)
	{
		if(fSurvey!=null){ejb = fSurvey.load(cSurvey,ejb);}
		
		Survey xml = new Survey();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetName()){xml.setName(ejb.getName());}
		if(q.isSetValidFrom()){xml.setValidFrom(DateUtil.toXmlGc(ejb.getStartDate()));}
		if(q.isSetValidTo()){xml.setValidTo(DateUtil.toXmlGc(ejb.getEndDate()));}
		
		if(q.isSetStatus())
		{
			XmlStatusFactory f = new XmlStatusFactory(q.getStatus());
			xml.setStatus(f.build(ejb.getStatus()));
		}
		
		if(q.isSetTemplate())
		{
			XmlTemplateFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> f = new XmlTemplateFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getTemplate());
//			if(fSurvey!=null){f.lazyLoad(fSurvey,cSection);}
			xml.setTemplate(f.build(ejb.getTemplate()));
		}
		
		if(q.isSetData())
		{
			XmlDataFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> f = new XmlDataFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getData().get(0));
			f.lazyLoad(fSurvey, cData);
			for(DATA data : ejb.getSurveyData())
			{
				xml.getData().add(f.build(data));
			}
		}
		return xml;
	}
	
	public static Survey id()
	{
		Survey xml = new Survey();
		xml.setId(0);
		return xml;
	}
}