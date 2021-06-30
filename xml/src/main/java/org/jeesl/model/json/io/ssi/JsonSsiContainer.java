package org.jeesl.model.json.io.ssi;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="ssi")
public class JsonSsiContainer implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("versions")
	private List<JsonSsiVersion> versions;
	public List<JsonSsiVersion> getVersions() {return versions;}
	public void setVersions(List<JsonSsiVersion> versions) {this.versions = versions;}

	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}