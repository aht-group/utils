
package org.jeesl.model.json.module.attribute;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="set")
public class JsonAttributeSet implements Serializable
{
    private final static long serialVersionUID = 1L;
 
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
    
	@JsonProperty("items")
	private List<JsonAttributeItem> items;
	public List<JsonAttributeItem> getItems() {return items;}
	public void setItems(List<JsonAttributeItem> items) {this.items = items;}
	

}