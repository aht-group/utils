package org.jeesl.interfaces.model.system.io.importer;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslImportHandler<L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								IMPORTER extends JeeslImport<L,D,CATEGORY,IMPORTER,VALIDATOR,HANDLER>,
								VALIDATOR extends JeeslImportValidator<L,D,CATEGORY,IMPORTER,VALIDATOR,HANDLER>,
								HANDLER extends JeeslImportHandler<L,D,CATEGORY,IMPORTER,VALIDATOR,HANDLER>
								>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
}