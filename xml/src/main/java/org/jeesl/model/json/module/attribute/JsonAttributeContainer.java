
package org.jeesl.model.json.module.attribute;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="container")
public class JsonAttributeContainer implements Serializable
{
    private final static long serialVersionUID = 1L;
 
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
    
	@JsonProperty("datas")
	private List<JsonAttributeData> datas;
	public List<JsonAttributeData> getDatas() {return datas;}
	public void setDatas(List<JsonAttributeData> datas) {this.datas = datas;}
	
}