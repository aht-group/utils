package org.jeesl.factory.json.module.ts;

import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.model.json.module.ts.JsonTsScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonTsScopeFactory<SCOPE extends JeeslTsScope<?,?,?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonTsScopeFactory.class);
	
	private final JsonTsScope q;
	
	public JsonTsScopeFactory(JsonTsScope q)
	{
		this.q=q;
	}
	
	public static JsonTsScope build(){return new JsonTsScope();}
	
	public JsonTsScope build(SCOPE ejb)
	{
		JsonTsScope json = build();
		if(q.isSetCode()) {json.setCode(ejb.getCode());}
		return json;
	}
}