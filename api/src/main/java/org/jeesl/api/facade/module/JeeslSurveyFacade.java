package org.jeesl.api.facade.module;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.JeeslWithSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurveyType;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.with.status.JeeslWithType;
import org.jeesl.model.json.JsonFlatFigures;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslSurveyFacade <L extends UtilsLang, D extends UtilsDescription,
									SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									SS extends UtilsStatus<SS,L,D>,
									SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									TS extends UtilsStatus<TS,L,D>,
									TC extends UtilsStatus<TC,L,D>,
									SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>, QE extends UtilsStatus<QE,L,D>,
									SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									UNIT extends UtilsStatus<UNIT,L,D>,
									ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									OPTIONS extends JeeslSurveyOptionSet<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>,
									CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>, DOMAIN extends JeeslSurveyDomain<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>, ANALYSIS extends JeeslSurveyAnalysis<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>, AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>, AT extends JeeslSurveyAnalysisTool<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>, ATT extends UtilsStatus<ATT,L,D>>
	extends UtilsFacade
{	
	TEMPLATE load(TEMPLATE template, boolean withQuestions, boolean withOptions);
	SECTION load(SECTION section);
	SURVEY load(SURVEY survey);
	QUESTION load(QUESTION question);
	ANSWER load(ANSWER answer);
	DATA load(DATA data);
	OPTIONS load(OPTIONS optionSet);
	
	List<SURVEY> fSurveysForCategories(List<TC> categories);
	
	OPTION saveOption(QUESTION question, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException;
	OPTION saveOption(OPTIONS set, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException;
	
	void rmVersion(VERSION version) throws UtilsConstraintViolationException;
	void rmOption(QUESTION question, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException;
	void rmOption(OPTIONS set, OPTION option) throws UtilsConstraintViolationException, UtilsLockingException;
	
	TEMPLATE fcSurveyTemplate(TC category, TS status);
	TEMPLATE fcSurveyTemplate(TC category, VERSION version, TS status, VERSION nestedVersion);
	
	SURVEY fSurvey(CORRELATION correlation) throws UtilsNotFoundException;
	void deleteSurvey(SURVEY survey) throws UtilsConstraintViolationException, UtilsLockingException;
	
	List<SURVEY> fSurveys(TC category, SS status, Date date);
	List<SURVEY> fSurveys(List<TC> categories, SS status, Date date);
	<W extends JeeslWithSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>> List<W> fSurveys(Class<W> c, List<SS> status, Date date);
	<TYPE extends UtilsStatus<TYPE,L,D>, WT extends JeeslWithType<L,D,TYPE>, W extends JeeslWithSurveyType<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT,WT,TYPE>> List<W> fWithSurveys(Class<W> c, List<SS> status, TYPE type, Date date);
	<W extends JeeslWithSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,ANALYSIS,AQ,AT,ATT>> W fWithSurvey(Class<W> c, long id) throws UtilsNotFoundException;
	List<VERSION> fVersions(TC category);
	
	List<ANSWER> fcAnswers(DATA data);
	List<ANSWER> fAnswers(SURVEY survey);
	List<ANSWER> fAnswers(SURVEY survey, QUESTION question);
	List<ANSWER> fAnswers(DATA data, Boolean visible, List<SECTION> sections);
	List<ANSWER> fAnswers(List<DATA> datas);
	List<MATRIX> fCells(List<ANSWER> answers);
	
	DATA fData(CORRELATION correlation) throws UtilsNotFoundException;
	List<DATA> fDatas(List<CORRELATION> correlations);
	DATA saveData(DATA data) throws UtilsConstraintViolationException, UtilsLockingException;
	
	ANSWER saveAnswer(ANSWER answer) throws UtilsConstraintViolationException, UtilsLockingException;
	void rmAnswer(ANSWER answer) throws UtilsConstraintViolationException;
	
	AQ fAnalysis(ANALYSIS analysis, QUESTION question) throws UtilsNotFoundException;
	
	JsonFlatFigures surveyStatisticOption(QUESTION question, SURVEY survey);
	JsonFlatFigures surveyStatisticBoolean(QUESTION question, SURVEY survey);
	
	JsonFlatFigures surveyCountOption(List<QUESTION> questions, SURVEY survey, List<CORRELATION> correlations);
	JsonFlatFigures surveyCountAnswer(List<QUESTION> questions, SURVEY survey, List<CORRELATION> correlations);
}