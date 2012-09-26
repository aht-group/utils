package net.sf.ahtutils.controller.factory.ejb.finance;

import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.finance.UtilsCurrency;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbCurrencyFactory<C extends UtilsCurrency<L>, L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(EjbCurrencyFactory.class);
	
	final Class<C> clCurrency;
    final Class<L> langClass;
    
    private EjbLangFactory<L> ejbLangFactory;
	
    public EjbCurrencyFactory(final Class<C> clCurrency, final Class<L> langClass)
    {
        this.clCurrency = clCurrency;
        this.langClass = langClass;
        
        ejbLangFactory = EjbLangFactory.createFactory(langClass);
    } 
    
    public static <C extends UtilsCurrency<L>, L extends UtilsLang> EjbCurrencyFactory<C, L>
    		factory(final Class<C> clCurrency, final Class<L> langClass)
    {
        return new EjbCurrencyFactory<C, L>(clCurrency, langClass);
    }
	
	public C create(String code, String symbol) throws InstantiationException, IllegalAccessException
	{
        C c = clCurrency.newInstance();
        c.setCode(code);
        c.setSymbol(symbol);
        return c;
    }
}