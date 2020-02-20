package org.jeesl.factory.json.system.status;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.json.system.status.JsonInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonIntervalFactory<L extends JeeslLang, D extends JeeslDescription,S extends JeeslStatus<S,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonIntervalFactory.class);
	
	private final String localeCode;
	private final JsonInterval q;
	
	public JsonIntervalFactory(JsonInterval q) {this(null,q);}
	public JsonIntervalFactory(String localeCode, JsonInterval q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
		
	public JsonInterval build(S ejb)
	{
		JsonInterval json = new JsonInterval();
	
		if(q.isSetId()){json.setId(ejb.getId());}
		if(q.isSetCode()){json.setCode(ejb.getCode());}
		if(q.isSetLabel() && ejb.getName().containsKey(localeCode)){json.setLabel(ejb.getName().get(localeCode).getLang());}
		if(q.isSetDescription() && ejb.getDescription().containsKey(localeCode)){json.setDescription(ejb.getDescription().get(localeCode).getLang());}
	
		return json;
	}
}