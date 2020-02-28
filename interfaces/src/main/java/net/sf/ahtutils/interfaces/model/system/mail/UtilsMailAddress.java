package net.sf.ahtutils.interfaces.model.system.mail;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface UtilsMailAddress extends EjbWithId,EjbWithName
{
	String getEmail();
	void setEmail(String email);
}