package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveyOptionSet<L extends JeeslLang, D extends JeeslDescription,
										TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>,
										OPTION extends JeeslSurveyOption<L,D>>
			extends Serializable,EjbSaveable,EjbRemoveable,EjbWithCode,EjbWithPosition,EjbWithVisible,
					EjbWithLang<L>
{
	public enum Attributes{template}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	List<OPTION> getOptions();
	void setOptions(List<OPTION> options);
}