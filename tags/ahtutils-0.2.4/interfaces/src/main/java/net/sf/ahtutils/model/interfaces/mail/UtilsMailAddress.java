package net.sf.ahtutils.model.interfaces.mail;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsMailAddress extends EjbWithId,EjbWithName
{
	String getEmail();
	void setEmail(String email);
}