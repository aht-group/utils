package org.jeesl.factory.ejb.system.io.ssi.data;

import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.sf.exlp.util.io.JsonUtil;

public class EjbIoSsiDataFactory <MAPPING extends JeeslIoSsiMapping<?,?>,
									DATA extends JeeslIoSsiData<MAPPING,LINK>,
									LINK extends JeeslStatus<LINK,?,?>>
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
			this.updateJson(ejb,json);
	       
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
	
	public void updateJson(DATA data, Object json)
	{
		if(json!=null)
		{
			try{data.setJson(JsonUtil.toString(json));}
			catch (JsonProcessingException e) {e.printStackTrace();}
		}
	}
}