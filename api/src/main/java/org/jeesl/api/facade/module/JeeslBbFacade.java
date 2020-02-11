package org.jeesl.api.facade.module;

import java.util.List;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.bb.JeeslBbBoard;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbPost;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbThread;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;

public interface JeeslBbFacade <L extends JeeslLang,D extends JeeslDescription,
								SCOPE extends JeeslStatus<SCOPE,L,D>,
								BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,USER>,
								PUB extends JeeslStatus<PUB,L,D>,
								THREAD extends JeeslBbThread<BB>,
								POST extends JeeslBbPost<THREAD,M,MT,USER>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
								USER extends EjbWithEmail>
			extends JeeslFacade
{	
	List<BB> fBulletinBoards(SCOPE scope, long refId);
}