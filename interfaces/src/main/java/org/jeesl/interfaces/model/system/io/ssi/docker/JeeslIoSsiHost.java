package org.jeesl.interfaces.model.system.io.ssi.docker;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiHost <L extends UtilsLang, D extends UtilsDescription>
							extends Serializable,EjbWithId,EjbWithLangDescription<L,D>
{	
	
}