package net.sf.ahtutils.model.interfaces.finance;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsCurrency<L extends UtilsLang>
			extends EjbWithId,EjbWithCode,EjbWithLang<L>
{
	public String getSymbol();
	public void setSymbol(String code);
}