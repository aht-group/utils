package org.jeesl.interfaces.model.system.io.ssi.docker;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiHost <L extends JeeslLang, D extends JeeslDescription>
							extends Serializable,EjbWithId,EjbWithLangDescription<L,D>
{	
	
}