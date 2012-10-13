package net.sf.ahtutils.web.demo;

import net.sf.ahtutils.model.interfaces.UtilsProperty;

public class AhtUtilsProperty implements UtilsProperty {
	
	private long id;
	private String key;
	private String value;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
