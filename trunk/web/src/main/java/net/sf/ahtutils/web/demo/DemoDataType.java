package net.sf.ahtutils.web.demo;

public class DemoDataType {
	
	private String label;
	private String value;
	
	public DemoDataType(String value)
	{
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
