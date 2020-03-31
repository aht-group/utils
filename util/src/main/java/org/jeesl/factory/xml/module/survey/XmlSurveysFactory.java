package org.jeesl.factory.xml.module.survey;

import org.jeesl.model.xml.module.survey.Survey;
import org.jeesl.model.xml.module.survey.Surveys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSurveysFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSurveysFactory.class);
	
	public static Surveys build(Survey survey)
	{
		Surveys xml = new Surveys();
		xml.getSurvey().add(survey);
		return xml;
	}
}