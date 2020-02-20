package org.jeesl.factory.json.module.ts;

import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.model.json.module.ts.JsonTsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonTsDataFactory<DATA extends JeeslTsData<?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonTsDataFactory.class);
	
	private final JsonTsData q;
	
	public JsonTsDataFactory(JsonTsData q)
	{
		this.q=q;
	}
		
	public JsonTsData build(DATA ejb)
	{
		JsonTsData json = new JsonTsData();
	
		if(q.isSetId()){json.setId(ejb.getId());}
		if(q.isSetRecord()){json.setRecord(ejb.getRecord());}
		
	
		return json;
	}
}