package net.sf.ahtutils.interfaces.facade;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurvey;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyAnswer;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyCorrelation;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyData;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyOption;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyQuestion;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveySection;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyTemplate;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public interface UtilsSurveyFacade <L extends UtilsLang,
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
	extends UtilsFacade
{	
	TEMPLATE load(Class<TEMPLATE> cTemplate, TEMPLATE template);
	SECTION load(Class<SECTION> cSection, SECTION section);
	SURVEY load(Class<SURVEY> cSurvey, SURVEY survey);
	DATA load(Class<DATA> cData, DATA data);
	
	TEMPLATE fcSurveyTemplate(Class<TEMPLATE> cTemplate, Class<TS> cTS, TC category);
	List<ANSWER> fcAnswers(Class<DATA> cData, Class<ANSWER> cAnswer, Class<TEMPLATE> cTemplate, DATA data);
	
	DATA saveData(Class<DATA> cData, Class<CORRELATION> cCorrelation, DATA data) throws UtilsContraintViolationException, UtilsLockingException;
}
