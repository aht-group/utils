package org.jeesl.interfaces.model.io.ssi.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLangDescription;

public interface JeeslIoSsiHost <L extends JeeslLang, D extends JeeslDescription>
							extends Serializable,EjbSaveable,
									EjbWithId,EjbWithCode,
									EjbWithLangDescription<L,D>
{	
	
}