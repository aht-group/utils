package org.jeesl.factory.ejb.module.hd;

import java.util.List;

import org.jeesl.factory.builder.module.HdFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFaq;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbHdFgaFactory<FAQ extends JeeslHdFaq<?,?,?,?,?>,
							FGA extends JeeslHdFga<FAQ,SEC>,
							SEC extends JeeslIoCmsSection<?,SEC>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbHdFgaFactory.class);
	
	private final HdFactoryBuilder<?,?,?,?,?,?,?,?,?,?,?,?,FAQ,?,FGA,SEC,?> fbHd;

    public EjbHdFgaFactory(HdFactoryBuilder<?,?,?,?,?,?,?,?,?,?,?,?,FAQ,?,FGA,SEC,?> fbHd)
    {
        this.fbHd = fbHd;
    }
	
	public FGA build(FAQ faq, List<FGA> list)
	{
		try
		{
			FGA ejb = fbHd.getClassFga().newInstance();
			ejb.setFaq(faq);
			EjbPositionFactory.next(ejb, list);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void converter(JeeslFacade facade, FGA fga)
	{
		if(fga.getSection()!=null) {fga.setSection(facade.find(fbHd.getClassSection(),fga.getSection()));}
	}
}