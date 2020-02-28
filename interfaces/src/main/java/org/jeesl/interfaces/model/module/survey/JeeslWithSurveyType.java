package org.jeesl.interfaces.model.module.survey;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.status.JeeslWithType;

public interface JeeslWithSurveyType<SURVEY extends JeeslSurvey<?,?,?,?,?>,
										W extends JeeslWithType<TYPE>,
										TYPE extends JeeslStatus<TYPE,?,?>>
			extends EjbWithId, JeeslWithSurvey<SURVEY>
{
	public enum Attributes{survey}

}