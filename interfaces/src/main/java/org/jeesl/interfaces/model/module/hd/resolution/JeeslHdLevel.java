package org.jeesl.interfaces.model.module.hd.resolution;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslMcsStatus;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;

public interface JeeslHdLevel <L extends JeeslLang, D extends JeeslDescription,
									R extends JeeslMcsRealm<L,D,R,G>,
									S extends JeeslMcsStatus<L,D,R,S,G>,
									G extends JeeslGraphic<L,D,?,?,?>>
					extends JeeslMcsStatus<L,D,R,S,G>,JeeslOptionRestDownload
{	

}