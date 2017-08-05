package org.jeesl.factory.ejb.system.io.cms;

import java.util.List;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoCmsElementFactory <L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M>,
								V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M>,
								S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M>,
								E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M>,
								T extends UtilsStatus<T,L,D>,
								C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M>,
								M extends UtilsStatus<M,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoCmsElementFactory.class);
	
	private final Class<E> cElement;

	public EjbIoCmsElementFactory(final Class<E> cElement)
	{
        this.cElement = cElement;
	}
 
	public E build(S section, List<E> list)
	{
		E ejb = null;
		try
		{
			ejb = cElement.newInstance();
			ejb.setSection(section);
			
			if(list!=null) {ejb.setPosition(list.size()+1);}
			else {ejb.setPosition(1);}
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void update(S src, S dst)
	{
		dst.setSection(src.getSection());
		dst.setPosition(src.getPosition());
		dst.setName(src.getName());
	}
}