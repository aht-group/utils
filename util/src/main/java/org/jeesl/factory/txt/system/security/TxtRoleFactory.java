package org.jeesl.factory.txt.system.security;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtRoleFactory <L extends JeeslLang,
										 D extends JeeslDescription,
										 R extends JeeslSecurityRole<L,D,?,?,?,?,?>
										 >
{
	final static Logger logger = LoggerFactory.getLogger(TxtRoleFactory.class);
    
}