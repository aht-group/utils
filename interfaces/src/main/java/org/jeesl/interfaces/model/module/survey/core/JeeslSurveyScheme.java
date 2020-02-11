package org.jeesl.interfaces.model.module.survey.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyScheme<L extends JeeslLang, D extends JeeslDescription,
									TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>,
									SCORE extends JeeslSurveyScore<L,D,?,?>>
			extends Serializable,EjbWithId,EjbWithNonUniqueCode,EjbWithPosition,EjbSaveable,
					EjbWithLang<L>,EjbWithDescription<D>
{
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
}