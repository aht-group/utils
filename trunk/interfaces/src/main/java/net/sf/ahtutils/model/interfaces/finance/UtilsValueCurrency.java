package net.sf.ahtutils.model.interfaces.finance;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsValueCurrency<C extends UtilsCurrency<L>, L extends UtilsLang> extends UtilsFinance
{
	C getCurrency();
	void setCurrency(C currency);
}