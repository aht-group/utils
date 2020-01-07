package org.jeesl.factory.ejb.system.io.ssi.data;

import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiMapping;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.exlp.util.io.JsonUtil;

public class EjbIoSsiDataFactory <MAPPING extends JeeslIoSsiMapping<?,?>,
									DATA extends JeeslIoSsiData<MAPPING,LINK>,
									LINK extends UtilsStatus<LINK,?,?>>
{
	private final Class<DATA> cData;

	public EjbIoSsiDataFactory(final Class<DATA> cData)
	{
        this.cData = cData;
	}
	
	public DATA build(MAPPING mapping, String code, LINK link, Object json)
	{
		DATA ejb = null;
		try
		{
			ejb = cData.newInstance();
			ejb.setMapping(mapping);
			ejb.setCode(code);
			ejb.setLink(link);
			if(json!=null) {ejb.setJson(JsonUtil.toString(json));}
	       
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		catch (JsonProcessingException e) {e.printStackTrace();}
		return ejb;
	}
}