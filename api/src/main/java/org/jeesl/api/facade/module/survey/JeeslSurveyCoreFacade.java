package org.jeesl.api.facade.module.survey;

import java.util.Date;
import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurveyType;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateCategory;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateStatus;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyStatus;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestionElement;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestionUnit;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.system.status.JeeslWithType;

public interface JeeslSurveyCoreFacade <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslStatus<LOC,L,D>,
									SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
									SS extends JeeslSurveyStatus<L,D,SS,?>,
									SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
									TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
									VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
									TS extends JeeslSurveyTemplateStatus<L,D,TS,?>,
									TC extends JeeslSurveyTemplateCategory<L,D,TC,?>,
									SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
									QUESTION extends JeeslSurveyQuestion<L,D,SECTION,?,?,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
									QE extends JeeslSurveyQuestionElement<L,D,QE,?>,
									SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
									UNIT extends JeeslSurveyQuestionUnit<L,D,UNIT,?>,
									ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
									MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
									DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
									OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
									OPTION extends JeeslSurveyOption<L,D>,
									CORRELATION extends JeeslSurveyCorrelation<DATA>>
	extends JeeslFacade
{	
//	@Deprecated //Use fTemplate
//	TEMPLATE load(TEMPLATE template, boolean withQuestions, boolean withOptions);
	
	SECTION load(SECTION section);
	SURVEY load(SURVEY survey);
	QUESTION load(QUESTION question);
	ANSWER load(ANSWER answer);
	DATA load(DATA data);
	OPTIONS load(OPTIONS optionSet);
	
	List<SURVEY> fSurveysForCategories(List<TC> categories);
	
	OPTION saveOption(QUESTION question, OPTION option) throws JeeslConstraintViolationException, JeeslLockingException;
	OPTION saveOption(OPTIONS set, OPTION option) throws JeeslConstraintViolationException, JeeslLockingException;
	
	void rmVersion(VERSION version) throws JeeslConstraintViolationException;
	void rmOption(QUESTION question, OPTION option) throws JeeslConstraintViolationException, JeeslLockingException;
	void rmOption(OPTIONS set, OPTION option) throws JeeslConstraintViolationException, JeeslLockingException;
	
	SURVEY fSurvey(CORRELATION correlation) throws JeeslNotFoundException;
	void deleteSurvey(SURVEY survey) throws JeeslConstraintViolationException, JeeslLockingException;
	
	List<SURVEY> fSurveys(TC category, SS status, Date date);
	List<SURVEY> fSurveys(List<TC> categories, SS status, Date date);
	<W extends JeeslWithSurvey<SURVEY>> List<W> fSurveys(Class<W> c, List<SS> status, Date date);
	<TYPE extends JeeslStatus<TYPE,L,D>, WT extends JeeslWithType<TYPE>, W extends JeeslWithSurveyType<SURVEY,WT,TYPE>> List<W> fWithSurveys(Class<W> c, List<SS> status, TYPE type, Date date);
	<W extends JeeslWithSurvey<SURVEY>> W fWithSurvey(Class<W> c, long id) throws JeeslNotFoundException;
	List<VERSION> fVersions(TC category, Long refId);
	
	List<ANSWER> fcAnswers(DATA data);
	List<ANSWER> fAnswers(SURVEY survey);
	List<ANSWER> fAnswers(SURVEY survey, QUESTION question);
	List<ANSWER> fAnswers(DATA data, Boolean visible, List<SECTION> sections);
	List<ANSWER> fAnswers(List<DATA> datas);
	List<MATRIX> fCells(List<ANSWER> answers);
	
	DATA fData(CORRELATION correlation) throws JeeslNotFoundException;
	List<DATA> fDatas(List<CORRELATION> correlations);
	DATA saveData(DATA data) throws JeeslConstraintViolationException, JeeslLockingException;
	
	ANSWER saveAnswer(ANSWER answer) throws JeeslConstraintViolationException, JeeslLockingException;
	void rmAnswer(ANSWER answer) throws JeeslConstraintViolationException;
}