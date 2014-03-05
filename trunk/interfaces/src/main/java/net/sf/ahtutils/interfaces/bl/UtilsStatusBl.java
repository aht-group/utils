package net.sf.ahtutils.interfaces.bl;

import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsStatusBl
{	
	<T extends EjbWithLangDescription<L,D>,L extends UtilsLang,D extends UtilsDescription> T verifiyLangs(Class<T> cl,Class<D> clD,T t, String[] langs);
}