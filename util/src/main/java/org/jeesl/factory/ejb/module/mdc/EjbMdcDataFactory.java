package org.jeesl.factory.ejb.module.mdc;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.mdc.collection.JeeslMdcCollection;
import org.jeesl.interfaces.model.module.mdc.collection.JeeslMdcData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMdcDataFactory<COLLECTION extends JeeslMdcCollection<?,?,?,?>,
								CDATA extends JeeslMdcData<COLLECTION,ACON>,
								ACON extends JeeslAttributeContainer<?,?>>
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
	
	public List<ACON> toCollectionContainer(List<CDATA> list)
	{
		List<ACON> result = new ArrayList<>();
		for(CDATA d : list) {result.add(d.getCollectionContainer());}
		return result;
	}
}