package org.jeesl.factory.json.system.status;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.json.system.status.JsonWorkspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonWorkspaceFactory<L extends JeeslLang, D extends JeeslDescription,S extends JeeslStatus<S,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonWorkspaceFactory.class);
	
	private final String localeCode;
	private final JsonWorkspace q;
	
	public JsonWorkspaceFactory(JsonWorkspace q) {this(null,q);}
	public JsonWorkspaceFactory(String localeCode, JsonWorkspace q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
		
	public JsonWorkspace build(S ejb)
	{
		JsonWorkspace json = new JsonWorkspace();
	
		if(q.isSetId()){json.setId(ejb.getId());}
		if(q.isSetCode()){json.setCode(ejb.getCode());}
		if(q.isSetLabel() && ejb.getName().containsKey(localeCode)){json.setLabel(ejb.getName().get(localeCode).getLang());}
		if(q.isSetDescription() && ejb.getDescription().containsKey(localeCode)){json.setDescription(ejb.getDescription().get(localeCode).getLang());}
	
		return json;
	}
}