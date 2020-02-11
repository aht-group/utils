package org.jeesl.factory.ejb.system.io.revision;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbRevisionMappingViewFactory<L extends JeeslLang,D extends JeeslDescription,
									RC extends JeeslRevisionCategory<L,D,RC,?>,
									RV extends JeeslRevisionView<L,D,RVM>,
									RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
									RS extends JeeslRevisionScope<L,D,RC,RA>,
									RST extends JeeslStatus<RST,L,D>,
									RE extends JeeslRevisionEntity<L,D,RC,REM,RA,?>,
									REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
									RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>, RER extends JeeslStatus<RER,L,D>,
									RAT extends JeeslStatus<RAT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionMappingViewFactory.class);
	
	final Class<RVM> cMapping;
    
	public EjbRevisionMappingViewFactory(final Class<RVM> cMapping)
	{       
        this.cMapping = cMapping;
	}
    
	public RVM build(RV view, RE entity, REM entityMapping)
	{
		RVM ejb = null;
		try
		{
			ejb = cMapping.newInstance();
			ejb.setPosition(1);
			ejb.setVisible(true);
			ejb.setView(view);
			ejb.setEntity(entity);
			ejb.setEntityMapping(entityMapping);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}