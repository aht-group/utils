package org.jeesl.interfaces.model.with.system.locale;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithLangDescription<L extends JeeslLang, D extends JeeslDescription>
					extends EjbWithId, EjbWithLang<L>, EjbWithDescription<D>
{

}