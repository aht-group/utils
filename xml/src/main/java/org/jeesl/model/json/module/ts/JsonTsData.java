package org.jeesl.model.json.module.ts;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="data")
public class JsonTsData implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	@JsonIgnore public boolean isSetId() {return id!=null;}
	
	@JsonProperty("record")
	private Date record;
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	@JsonIgnore public boolean isSetRecord() {return record!=null;}
	
	@JsonProperty("points")
	private List<JsonTsPoint> points;
	public List<JsonTsPoint> getPoints() {return points;}
	public void setPoints(List<JsonTsPoint> points) {this.points = points;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}