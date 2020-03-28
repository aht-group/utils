package org.jeesl.util.filter.ejb.module.survey;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveySectionFilter
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveySectionFilter.class);
	
	public static <SECTION extends JeeslSurveySection<?,?,?,SECTION,?>> List<SECTION> filterVisible(List<SECTION> sections)
	{	
		List<SECTION> result = new ArrayList<>();
		for(SECTION section : sections)
		{
			
			if(section.isVisible()) {result.add(section);}
		}
		return result;
	}
}