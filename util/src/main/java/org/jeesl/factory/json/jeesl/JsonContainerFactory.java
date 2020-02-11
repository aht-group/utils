package org.jeesl.factory.json.jeesl;

import java.util.List;

import org.jeesl.factory.json.system.status.JsonStatusFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.json.system.status.JsonContainer;
import org.jeesl.model.json.system.status.JsonStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class JsonContainerFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonContainerFactory.class);
	
	private final JsonStatusFactory jfStatus;
	
	public JsonContainerFactory(String localeCode, JsonStatus query)
	{
		jfStatus = new JsonStatusFactory(localeCode,query);
	}
	
	@SuppressWarnings("unchecked")
	public <S extends JeeslStatus<S,L,D>,L extends JeeslLang, D extends JeeslDescription> JsonContainer build(List<S> list)
	{
		JsonContainer xml = build();
		for(S s : list){xml.getStatus().add(jfStatus.build(s));}
		return xml;
	}
		
    public static JsonContainer build()
    {
    	JsonContainer xml = new JsonContainer();
    	return xml;
    }
}