package org.jeesl.interfaces.model.module.hd;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslMcsStatus;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;

public interface JeeslHdLevel <L extends JeeslLang, D extends JeeslDescription,
									REALM extends JeeslMcsRealm<L,D,REALM,?>,
									S extends JeeslMcsStatus<L,D,REALM,S,G>,
									G extends JeeslGraphic<L,D,?,?,?>>
					extends JeeslMcsStatus<L,D,REALM,S,G>
{	

}