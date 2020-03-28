package org.jeesl.util.filter.ejb.module.survey;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyQuestionFilter <QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyQuestionFilter.class);
	
	public List<QUESTION> filterVisible(List<QUESTION> questions)
	{	
		List<QUESTION> result = new ArrayList<>();
		for(QUESTION section : questions)
		{
			
			if(section.isVisible()) {result.add(section);}
		}
		return result;
	}
}