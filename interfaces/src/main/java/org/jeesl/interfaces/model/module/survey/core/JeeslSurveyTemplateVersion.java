package org.jeesl.interfaces.model.module.survey.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.number.EjbWithRefId;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslSurveyTemplateVersion<L extends JeeslLang, D extends JeeslDescription,
											TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>>
			extends Serializable,EjbSaveable,EjbRemoveable,
					EjbWithRecord,EjbWithRefId,
					EjbWithLang<L>,EjbWithDescription<D>
{
	enum Attributes {template,record,refId}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
}