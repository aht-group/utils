package org.jeesl.interfaces.model.module.currency;

import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface UtilsCurrency<L extends JeeslLang>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>
{
	public String getSymbol();
	public void setSymbol(String code);
}