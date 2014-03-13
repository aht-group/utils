package net.sf.ahtutils.jsf.components;

import java.util.UUID;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent(value="net.sf.ahtutils.jsf.components.CodeEditor")
public class CodeEditor extends UINamingContainer
{
	
	private String codeId="ace" +UUID.randomUUID().toString().replaceAll("-","");
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
}