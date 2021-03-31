package org.jeesl.factory.ejb.module.mdc;

import org.jeesl.interfaces.model.module.mdc.JeeslMdcActivity;
import org.jeesl.interfaces.model.module.mdc.JeeslMdcData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMdcDataFactory<COLLECTION extends JeeslMdcActivity<?,?,?,?>,
								CDATA extends JeeslMdcData<COLLECTION,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbMdcDataFactory.class);

	private final Class<CDATA> cData;

	public EjbMdcDataFactory(final Class<CDATA> cData)
	{
		this.cData=cData;
	}

	public CDATA build(COLLECTION collection)
	{
		CDATA ejb = null;
		try
		{
			ejb = cData.newInstance();
			ejb.setActivity(collection);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}