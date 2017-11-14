package org.jeesl.factory.xml.module.survey;

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
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlUnitFactory;
import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Question;

public class XmlQuestionFactory<L extends UtilsLang,D extends UtilsDescription,SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
				SS extends UtilsStatus<SS,L,D>,
				SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
				TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,ANALYSIS>,VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,TS extends UtilsStatus<TS,L,D>,TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>, QE extends UtilsStatus<QE,L,D>, SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>, MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>, OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,OPTION extends JeeslSurveyOption<L,D>,CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>, DOMAIN extends JeeslSurveyDomain<L,D,PATH,DENTITY>, PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY>, DENTITY extends JeeslRevisionEntity<L,D,?,?,?>, ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>, AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>, AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>, ATT extends UtilsStatus<ATT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlQuestionFactory.class);
		
	private String lang;
	private Question q;
	
	private XmlScoreFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> xfScore;
	
	
	//TODO tk: remove this constructor
	public XmlQuestionFactory(Question q){this(null,q);}
	
	public XmlQuestionFactory(String lang, Question q)
	{
		this.lang=lang;
		this.q=q;
		if(q.isSetScore()){xfScore = new XmlScoreFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(q.getScore());}
	}
	
	public Question build(QUESTION ejb)
	{
		Question xml = new Question();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetTopic()){xml.setTopic(ejb.getTopic());}
		
		if(q.isSetQuestion() && ejb.getQuestion()!=null){xml.setQuestion(net.sf.ahtutils.factory.xml.text.XmlQuestionFactory.build(ejb.getQuestion()));}
		if(q.isSetRemark() && ejb.getRemark()!=null){xml.setRemark(XmlRemarkFactory.build(ejb.getRemark()));}
		
		if(q.isSetUnit() && ejb.getUnit()!=null)
		{
			XmlUnitFactory f = new XmlUnitFactory(lang,q.getUnit());
			xml.setUnit(f.build(ejb.getUnit()));
		}
		
		if(q.isSetShowBoolean()){if(ejb.getShowBoolean()!=null){xml.setShowBoolean(ejb.getShowBoolean());}else{xml.setShowBoolean(false);}}
		if(q.isSetShowInteger()){if(ejb.getShowInteger()!=null){xml.setShowInteger(ejb.getShowInteger());}else{xml.setShowInteger(false);}}
		if(q.isSetShowDouble()){if(ejb.getShowDouble()!=null){xml.setShowDouble(ejb.getShowDouble());}else{xml.setShowDouble(false);}}
		if(q.isSetShowText()){if(ejb.getShowText()!=null){xml.setShowText(ejb.getShowText());}else{xml.setShowText(false);}}
		if(q.isSetShowScore()){if(ejb.getShowScore()!=null){xml.setShowScore(ejb.getShowScore());}else{xml.setShowScore(false);}}
		if(q.isSetShowRemark()){if(ejb.getShowRemark()!=null){xml.setShowRemark(ejb.getShowRemark());}else{xml.setShowRemark(false);}}
		if(q.isSetShowSelectOne()){if(ejb.getShowSelectOne()!=null){xml.setShowSelectOne(ejb.getShowSelectOne());}else{xml.setShowSelectOne(false);}}
		
		if(q.isSetScore()){xml.setScore(xfScore.build(ejb));}
		
		return xml;
	}
	
	public static Question id()
	{
		Question xml = new Question();
		xml.setId(0);
		return xml;
	}
}