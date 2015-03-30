package net.sf.ahtutils.factory.xml.survey;

import net.sf.ahtutils.factory.xml.status.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.status.XmlDescriptionFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
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
import net.sf.ahtutils.xml.survey.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTemplateFactory<L extends UtilsLang,D extends UtilsDescription,SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,CORRELATION extends UtilsSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlTemplateFactory.class);
	
	private UtilsSurveyFacade<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey;
	private Class<TEMPLATE> cTemplate;
	private Class<SECTION> cSection;
	
	private Template q;
	
	public XmlTemplateFactory(Template q)
	{
		this.q=q;
	}
	
	public void lazyLoad(UtilsSurveyFacade<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey,Class<TEMPLATE> cTemplate,Class<SECTION> cSection)
	{
		this.fSurvey=fSurvey;
		this.cTemplate=cTemplate;
		this.cSection=cSection;
	}
	
	public Template build(TEMPLATE ejb)
	{
		if(fSurvey!=null){ejb = fSurvey.load(cTemplate,ejb);}
		
		Template xml = new Template();
		if(q.isSetId()){xml.setId(ejb.getId());}
		
		if(q.isSetDescription()){xml.setDescription(XmlDescriptionFactory.build(ejb.getName()));}
		if(q.isSetRemark() && ejb.getRemark()!=null){xml.setRemark(XmlRemarkFactory.build(ejb.getRemark()));}
		
		if(q.isSetCategory())
		{
			XmlCategoryFactory f = new XmlCategoryFactory(q.getCategory());
			xml.setCategory(f.build(ejb.getCategory()));
		}
		if(q.isSetStatus())
		{
			XmlStatusFactory f = new XmlStatusFactory(q.getStatus());
			xml.setStatus(f.build(ejb.getStatus()));
		}
		
		if(q.isSetSection())
		{
			XmlSectionFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> f = new XmlSectionFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(q.getSection().get(0));
			if(fSurvey!=null){f.lazyLoad(fSurvey,cSection);}
			
			for(SECTION section : ejb.getSections())
			{
				xml.getSection().add(f.build(section));
			}
		}
		
		return xml;
	}
	
	public static Template id()
	{
		Template xml = new Template();
		xml.setId(0);
		return xml;
	}
}