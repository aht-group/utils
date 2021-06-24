
package org.jeesl.model.json.module.mdc;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.jeesl.model.json.module.attribute.JsonAttributeSet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="collection")
public class JsonMdcCollection implements Serializable
{
    private final static long serialVersionUID = 1L;
 
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("name")
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	@JsonProperty("validFrom")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date validFrom;
	public Date getValidFrom() {return validFrom;}
	public void setValidFrom(Date validFrom) {this.validFrom = validFrom;}
	
	@JsonProperty("validUntil")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date validUntil;
	public Date getValidUntil() {return validUntil;}
	public void setValidUntil(Date validUntil) {this.validUntil = validUntil;}
	
//	@JsonProperty("test")
//	private Date test1;
//	public Date getTest1() {return test1;}
//	public void setTest1(Date test1) {this.test1 = test1;}
//	
//	@JsonProperty("test2")
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//	private Date test2;
//	public Date getTest2() {return test2;}
//	public void setTest2(Date test2) {this.test2 = test2;}
//	
//	@JsonProperty("test3")
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//	private Date test3;
//	public Date getTest3() {return test3;}
//	public void setTest3(Date test3) {this.test3 = test3;}

	@JsonProperty("collectionSet")
	private JsonAttributeSet collectionSet;
	public JsonAttributeSet getCollectionSet() {return collectionSet;}
	public void setCollectionSet(JsonAttributeSet collectionSet) {this.collectionSet = collectionSet;}
	
	
	
}