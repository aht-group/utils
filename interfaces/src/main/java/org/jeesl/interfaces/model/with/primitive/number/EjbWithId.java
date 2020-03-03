package org.jeesl.interfaces.model.with.primitive.number;

import java.io.Serializable;

public interface EjbWithId extends Serializable
{
	public enum Attribute{id}
	String attribute = "id";
	
	long getId();
	void setId(long id);
}
