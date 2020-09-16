
package org.jeesl.model.json.module.attribute;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="item")
public class JsonAttributeItem implements Serializable
{
    private final static long serialVersionUID = 1L;
 
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("position")
	private Integer position;
	public Integer getPosition() {return position;}
	public void setPosition(Integer position) {this.position = position;}
	
	@JsonProperty("visible")
	private Boolean visible;
	public Boolean getVisible() {return visible;}
	public void setVisible(Boolean visible) {this.visible = visible;}
	
	@JsonProperty("criteria")
	private JsonAttributeCriteria criteria;
	public JsonAttributeCriteria getCriteria() {return criteria;}
	public void setCriteria(JsonAttributeCriteria criteria) {this.criteria = criteria;}
	
}