package org.jeesl.factory.ejb.io.cms;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.io.cms.JeeslWithCms;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoCmsFactory <L extends JeeslLang,D extends JeeslDescription,
								CAT extends JeeslStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<V,S,EC,ET,C,?>,
								EC extends JeeslStatus<EC,L,D>,
								ET extends JeeslStatus<ET,L,D>,
								C extends JeeslIoCmsContent<V,E,MT>,
								MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
								LOC extends JeeslLocale<L,D,LOC,?>>
{
	//TODO @tk reduce
	final static Logger logger = LoggerFactory.getLogger(EjbIoCmsFactory.class);
	
	private final Class<CMS> cCms;

	public EjbIoCmsFactory(final Class<CMS> cCms)
	{
        this.cCms = cCms;
	}
 
	public CMS build(CAT category, S root)
	{
		CMS ejb = null;
		try
		{
			ejb = cCms.newInstance();
			ejb.setCategory(category);
			ejb.setRoot(root);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public <W extends JeeslWithCms<CMS>> List<CMS> toCms(List<W> list)
	{
		List<CMS> result = new ArrayList<CMS>();
		for(W w : list) {result.add(w.getCms());}
		return result;
	}
}