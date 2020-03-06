package org.jeesl.interfaces.model.system.io.ssi;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLangDescription;

public interface JeeslIoSsiHost <L extends JeeslLang, D extends JeeslDescription>
							extends Serializable,EjbWithId,EjbWithLangDescription<L,D>
{	
	
}