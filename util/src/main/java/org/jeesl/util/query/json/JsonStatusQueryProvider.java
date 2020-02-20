package org.jeesl.util.query.json;

import org.jeesl.model.json.system.status.JsonInterval;
import org.jeesl.model.json.system.status.JsonStatus;
import org.jeesl.model.json.system.status.JsonWorkspace;

public class JsonStatusQueryProvider
{
	public static JsonStatus statusExport()
	{				
		JsonStatus xml = new JsonStatus();
		xml.setId(Long.valueOf(0));
		xml.setCode("");
		return xml;
	}
	
	public static JsonStatus statusLabel()
	{				
		JsonStatus xml = new JsonStatus();
//		xml.setId(Long.valueOf(0));
		xml.setCode("");
		xml.setLabel("");
		return xml;
	}
	
	public static JsonInterval intervalCode()
	{				
		JsonInterval xml = new JsonInterval();
		xml.setCode("");
		return xml;
	}
	
	public static JsonWorkspace workspaceCode()
	{				
		JsonWorkspace json = new JsonWorkspace();
		json.setCode("");
		return json;
	}
}