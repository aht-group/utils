
package org.jeesl.model.json.module.mdc;

import java.io.Serializable;

import org.jeesl.model.json.module.attribute.JsonAttributeContainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="collection")
public class JsonMdcData implements Serializable
{
    private final static long serialVersionUID = 1L;
 
	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("collection")
	private JsonMdcCollection collection;
	public JsonMdcCollection getCollection() {return collection;}
	public void setCollection(JsonMdcCollection collection) {this.collection = collection;}

	@JsonProperty("collectionContainer")
	private JsonAttributeContainer collectionContainer;
	public JsonAttributeContainer getCollectionContainer() {return collectionContainer;}
	public void setCollectionContainer(JsonAttributeContainer collectionContainer) {this.collectionContainer = collectionContainer;}
	
}