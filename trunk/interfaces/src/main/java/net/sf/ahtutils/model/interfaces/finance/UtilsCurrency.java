package net.sf.ahtutils.model.interfaces.finance;

import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithLang;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsCurrency<L extends UtilsLang>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>
{
	public String getSymbol();
	public void setSymbol(String code);
}