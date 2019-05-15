package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;

public interface JeeslFileStorageEngine <S extends UtilsStatus<S,L,D>,
										L extends UtilsLang,
										D extends UtilsDescription,
										G extends JeeslGraphic<L,D,G,?,?,?>>
							extends Serializable,EjbPersistable,JeeslOptionRestDownload,
									EjbWithCodeGraphic<G>,UtilsStatusFixedCode,
									UtilsStatus<S,L,D>
{	
	public enum Code{fs,oak,gridFS,amazonS3,postgres}
}