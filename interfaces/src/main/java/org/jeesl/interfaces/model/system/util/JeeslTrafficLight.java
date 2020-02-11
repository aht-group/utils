package org.jeesl.interfaces.model.system.util;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.interfaces.model.with.status.JeeslWithScope;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTrafficLight<L extends JeeslLang, D extends JeeslDescription,
								SCOPE extends JeeslStatus<SCOPE,L,D>>
			extends EjbWithId,
					EjbSaveable,EjbRemoveable,
					JeeslWithScope<SCOPE>,
					EjbWithLangDescription<L,D>
{
	double getThreshold();
	void setThreshold(double threshold);
	
	String getColorBackground();
	void setColorBackground(String colorBackground);
	
	String getColorText();
	void setColorText(String colorText);
}