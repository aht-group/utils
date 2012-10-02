package net.sf.ahtutils.model.event;

import java.io.Serializable;

public abstract class GlobalCdiEvent implements Serializable
{
	public static final String httpSessionAttributeName= "HttpSessionControllerEvent";
	
	private static final long serialVersionUID = 1L;
	public static enum Type {ADD,MOD,DEL}
	
	protected Type type;

	public Type getType() {return type;}
	public void setType(Type type) {this.type = type;}
	
}
