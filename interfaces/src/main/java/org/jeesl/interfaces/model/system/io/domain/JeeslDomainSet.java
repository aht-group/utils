package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslDomainSet <L extends JeeslLang, D extends JeeslDescription,
								DOMAIN extends JeeslDomain<L,?>
>
		extends Serializable,EjbSaveable,EjbRemoveable,
				EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{domain,position}

	DOMAIN getDomain();
	void setDomain(DOMAIN domain);
}