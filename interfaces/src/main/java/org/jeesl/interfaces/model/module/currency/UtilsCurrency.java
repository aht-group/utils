package org.jeesl.interfaces.model.module.currency;

import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsCurrency<L extends JeeslLang>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>
{
	public String getSymbol();
	public void setSymbol(String code);
}