package org.jeesl.util.filter.ejb.module.survey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyAnswerFilter <SECTION extends JeeslSurveySection<?,?,?,SECTION,QUESTION>,
									QUESTION extends JeeslSurveyQuestion<?,?,SECTION,?,?,?,?,?,?,?,?>,
									ANSWER extends JeeslSurveyAnswer<?,?,QUESTION,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyAnswerFilter.class);
	
	public List<ANSWER> toSectionAnswers(SECTION section, Map<QUESTION,ANSWER> map)
	{
		List<ANSWER> list = new ArrayList<ANSWER>();
		
		for(QUESTION q : map.keySet())
		{
			if(q.isVisible() && q.getSection().equals(section))
			{
				list.add(map.get(q));
			}
		}
		return list;
	}
}