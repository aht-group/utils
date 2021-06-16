
package org.jeesl.model.json.module.mdc;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="enrolment")
public class JsonMdcContainer implements Serializable
{
    private final static long serialVersionUID = 1L;
 
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("enrolment")
	private JsonMdcEnrolment enrolment;
	public JsonMdcEnrolment getEnrolment() {return enrolment;}
	public void setEnrolment(JsonMdcEnrolment enrolment) {this.enrolment = enrolment;}

	@JsonProperty("collection")
	private JsonMdcCollection collection;
	public JsonMdcCollection getCollection() {return collection;}
	public void setCollection(JsonMdcCollection collection) {this.collection = collection;}
}