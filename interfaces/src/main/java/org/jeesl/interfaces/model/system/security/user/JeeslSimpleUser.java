package org.jeesl.interfaces.model.system.security.user;

import org.jeesl.interfaces.model.with.text.EjbWithEmail;

public interface JeeslSimpleUser extends EjbWithEmail
{
	String getFirstName();
	void setFirstName(String firstName);
	
	String getLastName();
	void setLastName(String lastName);
}