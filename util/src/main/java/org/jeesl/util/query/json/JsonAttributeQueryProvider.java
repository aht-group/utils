package org.jeesl.util.query.json;

import java.util.ArrayList;

import org.jeesl.model.json.module.attribute.JsonAttributeCriteria;
import org.jeesl.model.json.module.attribute.JsonAttributeItem;
import org.jeesl.model.json.module.attribute.JsonAttributeOption;
import org.jeesl.model.json.module.attribute.JsonAttributeSet;

public class JsonAttributeQueryProvider
{
	public static JsonAttributeSet set()
	{				
		JsonAttributeSet json = new JsonAttributeSet();
		json.setId(0l);
		json.setItems(new ArrayList<>());
		json.getItems().add(item());
		return json;
	}
	
	private static JsonAttributeItem item()
	{
		JsonAttributeItem json = new JsonAttributeItem();
		json.setId(0l);
		json.setPosition(0);
		json.setVisible(true);
		json.setCriteria(criteria());
		return json;
	}

	private static JsonAttributeCriteria criteria()
	{
		JsonAttributeCriteria json = new JsonAttributeCriteria();
		json.setId(0l);
		json.setCode("");
		json.setVisible(true);
		json.setLabel("");
		json.setDescription("");
		json.setType(JsonStatusQueryProvider.typeCode());
		
		json.setOptions(new ArrayList<>());
		json.getOptions().add(option());
		return json;
	}
	
	public static JsonAttributeOption option()
	{				
		JsonAttributeOption json = new JsonAttributeOption();
		json.setId(0l);
		json.setCode("");
		json.setPosition(0);
		json.setLabel("");
		json.setDescription("");
		return json;
	}
}