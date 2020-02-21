package org.jeesl.model.json.module.ts;

import java.io.Serializable;
import java.util.List;

import org.jeesl.model.json.system.status.JsonCategory;
import org.jeesl.model.json.system.status.JsonInterval;
import org.jeesl.model.json.system.status.JsonWorkspace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="series")
public class JsonTsSeries implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("category")
	private JsonCategory category;
	public JsonCategory getCategory() {return category;}
	public void setCategory(JsonCategory category) {this.category = category;}

	@JsonProperty("scope")
	private JsonTsScope scope;
	public JsonTsScope getScope() {return scope;}
	public void setScope(JsonTsScope scope) {this.scope = scope;}
	
	@JsonProperty("interval")
	private JsonInterval interval;
	public JsonInterval getInterval() {return interval;}
	public void setInterval(JsonInterval interval) {this.interval = interval;}
	
	@JsonProperty("workspace")
	private JsonWorkspace workspace;
	public JsonWorkspace getWorkspace() {return workspace;}
	public void setWorkspace(JsonWorkspace workspace) {this.workspace = workspace;}

	@JsonProperty("size")
	private Integer size;
	public Integer getSize() {return size;}
	public void setSize(Integer size) {this.size = size;}
	
	@JsonProperty("datas")
	private List<JsonTsData> datas;
	public List<JsonTsData> getDatas() {return datas;}
	public void setDatas(List<JsonTsData> datas) {this.datas = datas;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}