
package org.jeesl.model.json.module.mdc;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="enrolment")
public class JsonMdcEnrolment implements Serializable
{
    private final static long serialVersionUID = 1L;
 
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("token")
	private String token;
	public String getToken() {return token;}
	public void setToken(String token) {this.token = token;}
}