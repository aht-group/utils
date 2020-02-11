package org.jeesl.factory.json.system.status;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.json.system.status.JsonStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonStatusFactory<S extends JeeslStatus<S,L,D>,L extends JeeslLang, D extends JeeslDescription>
{
	final static Logger logger = LoggerFactory.getLogger(JsonStatusFactory.class);
	
	private final String localeCode;
	private final JsonStatus q;
	
	public JsonStatusFactory(String localeCode, JsonStatus q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
		
	public JsonStatus build(S ejb)
	{
		JsonStatus json = new JsonStatus();
	
		if(q.getId()!=null){json.setId(ejb.getId());}
		if(q.isSetCode()){json.setCode(ejb.getCode());}
		if(q.isSetLabel() && ejb.getName().containsKey(localeCode)){json.setLabel(ejb.getName().get(localeCode).getLang());}
		if(q.isSetDescription() && ejb.getDescription().containsKey(localeCode)){json.setDescription(ejb.getDescription().get(localeCode).getLang());}
	
		return json;
	}
}