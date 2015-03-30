package net.sf.ahtutils.factory.ejb.survey;

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

public class EjbSurveyTemplateFactory<L extends UtilsLang,
										D extends UtilsDescription,
										SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										SS extends UtilsStatus<SS,L,D>,
										TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										TS extends UtilsStatus<TS,L,D>,
										TC extends UtilsStatus<TC,L,D>,
										SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										CORRELATION extends UtilsSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyTemplateFactory.class);
	
	final Class<TEMPLATE> cTemplate;
    
	public EjbSurveyTemplateFactory(final Class<TEMPLATE> cTemplate)
	{       
        this.cTemplate = cTemplate;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,SS extends UtilsStatus<SS,L,D>,TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,CORRELATION extends UtilsSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
	EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> factory(final Class<TEMPLATE> cTemplate)
	{
		return new EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(cTemplate);
	}
    
	public TEMPLATE build(TC category,TS status, Template xTemplate)
	{
		return build(category,status,xTemplate.getDescription().getValue());
	}
	
	public TEMPLATE build(TC category,TS status, String name)
	{
		TEMPLATE ejb = null;
		try
		{
			ejb = cTemplate.newInstance();
			ejb.setName(name);
			ejb.setCategory(category);
			ejb.setStatus(status);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}