package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface JeeslDomainQuery<L extends JeeslLang, D extends JeeslDescription,
										DOMAIN extends JeeslDomain<L,?>,
										PATH extends JeeslDomainPath<?,?,?,?,?>
										>
			extends Serializable,EjbWithId,
					EjbSaveable,EjbRemoveable,
					EjbWithPositionParent,EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{domain}
	
	DOMAIN getDomain();
	void setDomain(DOMAIN domain);

	List<PATH> getPaths();
	void setPaths(List<PATH> paths);
}