package org.jeesl.interfaces.model.system.security.doc;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface JeeslSecurityHelp<L extends JeeslLang, D extends JeeslDescription,
									V extends JeeslSecurityView<L,D,?,?,?,?>>
			extends Serializable,EjbSaveable,
					EjbWithPositionVisible,
					EjbWithLang<L>,EjbWithDescription<D>
{	
	V getView();
	void setView(V view);
}