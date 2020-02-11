package org.jeesl.interfaces.model.module.currency;

import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.finance.EjbWithValue;

public interface UtilsValueCurrency<C extends UtilsCurrency<L>, L extends JeeslLang> extends EjbWithValue
{
	C getCurrency();
	void setCurrency(C currency);
}