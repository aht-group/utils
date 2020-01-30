package org.jeesl.controller.handler.sb.decade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.controller.handler.tree.cache.JeeslTree2Cache;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroDecade;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class SbDecadeTreeCache<HD extends JeeslHydroDecade, HY extends JeeslHydroYear>  implements Serializable,JeeslTree2Cache<HD,HY>
{
	final static Logger logger = LoggerFactory.getLogger(SbDecadeTreeCache.class);
	private static final long serialVersionUID = 1L;

	private final JeeslFacade fUtils;
	private final Class<HD> cDecade; public Class<HD> getClassDecade(){return cDecade;}
	private final Class<HY> cYear; public Class<HY> getClassYear(){return cYear;}

	public SbDecadeTreeCache(JeeslFacade fUtils, final Class<HD> cDecade, final Class<HY> cYear)
	{
		this.fUtils=fUtils;
		this.cDecade = cDecade;
		this.cYear = cYear;
	}

	@Override
	public List<HD> getCachedL1()
	{
		return fUtils.all(getClassDecade());
	}

	@Override public List<HY> getCachedChildsForL1(HD ejb)
	{
		List<HY> result = new ArrayList<HY>();
		result = fUtils.allForParent(getClassYear(), ejb);
		logger.info("getCachedChildsForL1 "+ejb.toString()+": "+result.size());
		return result;
	}

}