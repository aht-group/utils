package org.jeesl.interfaces.model.system.io.ssi;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface JeeslIoSsiSystem<L extends JeeslLang, D extends JeeslDescription>
		extends Serializable,EjbSaveable,EjbRemoveable,
				EjbWithId,EjbWithCode
				,EjbWithLang<L>,EjbWithDescription<D>
{	

}