package org.jeesl.factory.ejb.module.bb;

import org.jeesl.interfaces.model.module.bb.JeeslBbBoard;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbBbBoardFactory<L extends JeeslLang,D extends JeeslDescription,
								SCOPE extends JeeslStatus<SCOPE,L,D>,
								BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,?>,
								PUB extends JeeslStatus<PUB,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbBbBoardFactory.class);
	
	private final Class<BB> cBb;
	
    public EjbBbBoardFactory(final Class<BB> cBb)
    {
        this.cBb = cBb;
    }
	
	public BB build(BB parent, SCOPE scope, long refId, PUB publishing)
	{
		try
		{
			BB ejb = cBb.newInstance();
			ejb.setParent(parent);
			ejb.setScope(scope);
			ejb.setRefId(refId);
			ejb.setPosition(0);
			ejb.setPublishing(publishing);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void update(BB src, BB dst)
	{
		dst.setParent(src.getParent());
		dst.setPosition(src.getPosition());
//		dst.setName(src.getName());
	}
}