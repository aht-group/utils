package org.jeesl.interfaces.model.module.ts.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;

public interface JeeslTsWorkspace <L extends UtilsLang, D extends UtilsDescription,
								S extends UtilsStatus<S,L,D>, 
								G extends JeeslGraphic<L,D,?,?,?>>
					extends Serializable,EjbPersistable,
								EjbWithCode,UtilsStatusFixedCode,UtilsWithSymbol,
//								JeeslOptionRestDownload,EjbWithCodeGraphic<G>,
								UtilsStatus<S,L,D>
{	
	public enum Code{live}
}