package net.sf.ahtutils.model.interfaces.finance;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsValueCurrency<L extends UtilsLang, C extends UtilsCurrency<L>> extends UtilsFinance
{
	C getCurrency();
	void setCurrency(C currency);
}