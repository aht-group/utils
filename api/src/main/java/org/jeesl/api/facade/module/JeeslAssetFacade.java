package org.jeesl.api.facade.module;

import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslAssetFacade <L extends UtilsLang, D extends UtilsDescription,
									ASSET extends JeeslAsset,
									MANU extends JeeslAssetManufacturer,
									AS extends JeeslAssetStatus<L,D,AS,?>>
			extends UtilsFacade
{	
	
}