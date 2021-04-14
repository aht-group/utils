
package org.jeesl.model.json.module.attribute;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="attributeDate")
public class JsonAttributeData implements Serializable
{
    private final static long serialVersionUID = 1L;
 
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
		
	@JsonProperty("criteria")
	private JsonAttributeCriteria criteria;
	public JsonAttributeCriteria getCriteria() {return criteria;}
	public void setCriteria(JsonAttributeCriteria criteria) {this.criteria = criteria;}
	
	
	@JsonProperty("valueDate")
	private Date valueDate;
	public Date getValueDate() {return valueDate;}
	public void setValueDate(Date valueDate) {this.valueDate = valueDate;}
	
	@JsonProperty("valueString")
	private String valueString;
	public String getValueString() {return valueString;}
	public void setValueString(String valueString) {this.valueString = valueString;}
	
	@JsonProperty("valueBoolean")
	private Boolean valueBoolean;
	public Boolean getValueBoolean() {return valueBoolean;}
	public void setValueBoolean(Boolean valueBoolean) {this.valueBoolean = valueBoolean;}
	
	@JsonProperty("valueInteger")
	private Integer valueInteger;
	public Integer getValueInteger() {return valueInteger;}
	public void setValueInteger(Integer valueInteger) {this.valueInteger = valueInteger;}
	
	@JsonProperty("valueDouble")
	private Double valueDouble;
	public Double getValueDouble() {return valueDouble;}
	public void setValueDouble(Double valueDouble) {this.valueDouble = valueDouble;}
	
	
	@JsonProperty("valueOption")
	private JsonAttributeOption valueOption;
	public JsonAttributeOption getValueOption() {return valueOption;}
	public void setValueOption(JsonAttributeOption valueOption) {this.valueOption = valueOption;}
	
	@JsonProperty("valueOptions")
	private List<JsonAttributeOption> valueOptions;
	public List<JsonAttributeOption> getValueOptions() {return valueOptions;}
	public void setValueOptions(List<JsonAttributeOption> valueOptions) {this.valueOptions = valueOptions;}
}