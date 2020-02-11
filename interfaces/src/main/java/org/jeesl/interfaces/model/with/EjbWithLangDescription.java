package org.jeesl.interfaces.model.with;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface EjbWithLangDescription<L extends JeeslLang, D extends JeeslDescription>
					extends EjbWithId, EjbWithLang<L>, EjbWithDescription<D>
{

}