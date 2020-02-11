package org.jeesl.interfaces.model.module.survey.correlation;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyCorrelation<DATA extends JeeslSurveyData<?,?,?,?,?>>
			extends Serializable,EjbWithId,EjbSaveable
{
	public enum Attributes{survey}
//	DATA getData();
//	void setData(DATA data);
}