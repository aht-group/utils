package org.jeesl.factory.ejb.module.survey;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyAnalysisToolFactory<L extends JeeslLang, D extends JeeslDescription,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,?,?>,
				TOOL extends JeeslSurveyAnalysisTool<L,D,?,?,?,AQ,ATT>,
				ATT extends JeeslStatus<ATT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyAnalysisToolFactory.class);
	
	private final Class<TOOL> cAt;
    
	public EjbSurveyAnalysisToolFactory(final Class<TOOL> cAt)
	{       
        this.cAt = cAt;
	}
    
	public TOOL build(AQ analysisQuestion, ATT type, List<TOOL> list)
	{
		TOOL ejb = null;
		try
		{
			ejb = cAt.newInstance();
			ejb.setAnalysisQuestion(analysisQuestion);
			ejb.setType(type);
			if(list==null) {ejb.setPosition(1);}
			else {ejb.setPosition(list.size()+1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public boolean withDomainQuery(TOOL tool)
	{
		return tool!=null && tool.getQuery()!=null;
	}
}