package net.sf.ahtutils.interfaces.model.util;

import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.interfaces.model.with.status.UtilsWithScope;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsTrafficLight<L extends UtilsLang,
							D extends UtilsDescription,
							SCOPE extends UtilsStatus<SCOPE,L,D>>
			extends EjbWithId,
					UtilsWithScope<L,D,SCOPE>,
					EjbWithLangDescription<L,D>
{
	double getThreshold();
	void setThreshold(double threshold);
	
	String getColorBackground();
	void setColorBackground(String colorBackground);
	
	void getColorText();
	String setColorText(String colorText);
}