package org.jeesl.interfaces.model.io.ssi.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLangDescription;

public interface JeeslIoSsiHost <L extends JeeslLang, D extends JeeslDescription,
									SYSTEM extends JeeslIoSsiSystem<?,?>>
							extends Serializable,EjbSaveable,
									EjbWithId,EjbWithCode,EjbWithParentAttributeResolver,
									EjbWithLangDescription<L,D>
{
	public enum Attributes{system}
	
	SYSTEM getSystem();
	void setSystem(SYSTEM system);
	
	String getFqdn();
	void setFqdn(String fqdn);
	
	String getIpAddr();
	void setIpAddr(String ipAddr);
}