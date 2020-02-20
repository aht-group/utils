package org.jeesl.util.query.json;

import org.jeesl.model.json.module.ts.JsonTsScope;

public class JsonTsQueryProvider
{
	public static JsonTsScope scope()
	{				
		JsonTsScope json = new JsonTsScope();
		json.setCode("");
		return json;
	}
}