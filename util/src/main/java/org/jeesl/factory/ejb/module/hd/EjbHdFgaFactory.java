package org.jeesl.factory.ejb.module.hd;

import java.util.List;

import org.jeesl.factory.builder.module.HdFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFaq;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbHdFgaFactory<FAQ extends JeeslHdFaq<?,?,?,?,?>,
							FGA extends JeeslHdFga<FAQ,DOC,SEC>,
							DOC extends JeeslIoCms<?,?,?,?,SEC>,
							SEC extends JeeslIoCmsSection<?,SEC>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbHdFgaFactory.class);
	
	private final HdFactoryBuilder<?,?,?,?,?,?,?,?,?,?,?,?,?,?,FAQ,?,FGA,DOC,SEC,?,?> fbHd;

    public EjbHdFgaFactory(HdFactoryBuilder<?,?,?,?,?,?,?,?,?,?,?,?,?,?,FAQ,?,FGA,DOC,SEC,?,?> fbHd)
    {
        this.fbHd = fbHd;
    }
	
	public FGA build(FAQ faq, DOC document, List<FGA> list)
	{
		try
		{
			FGA ejb = fbHd.getClassFga().newInstance();
			ejb.setFaq(faq);
			ejb.setDocument(document);
			EjbPositionFactory.next(ejb, list);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void converter(JeeslFacade facade, FGA answer)
	{
		if(answer.getDocument()!=null) {answer.setDocument(facade.find(fbHd.getClassDoc(),answer.getDocument()));}
		if(answer.getSection()!=null) {answer.setSection(facade.find(fbHd.getClassSection(),answer.getSection()));}
	}
}