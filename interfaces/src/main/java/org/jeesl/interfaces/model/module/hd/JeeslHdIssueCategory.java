package org.jeesl.interfaces.model.module.hd;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslMcsStatus;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;

public interface JeeslHdIssueCategory <L extends JeeslLang, D extends JeeslDescription,
							REALM extends JeeslMcsRealm<L,D,REALM,?>,
							G extends JeeslGraphic<L,D,?,?,?>>
			extends JeeslMcsStatus<L,D,REALM,G>
					
{
	public enum Attributes{realm,rref}
}