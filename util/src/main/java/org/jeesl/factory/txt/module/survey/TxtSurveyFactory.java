package org.jeesl.factory.txt.module.survey;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtSurveyFactory <L extends JeeslLang, D extends JeeslDescription,
										SURVEY extends JeeslSurvey<L,D,?,TEMPLATE,?>,
										
										TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>
										>
{
	final static Logger logger = LoggerFactory.getLogger(TxtSurveyFactory.class);
		
	private final String localeCode;
	
	public TxtSurveyFactory(final String localeCode)
	{
		this.localeCode=localeCode;
	}
	
	public String debug(SURVEY survey)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(survey.getName().get(localeCode).getLang());
		sb.append(" (").append(survey.getTemplate().getName()).append(")");
		return sb.toString();
	}
}