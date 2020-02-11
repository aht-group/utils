package org.jeesl.util.comparator.ejb.system.io.revision;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
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

public class RevisionEntityComparator<L extends JeeslLang,D extends JeeslDescription,
										RC extends JeeslRevisionCategory<L,D,RC,?>,	
										RV extends JeeslRevisionView<L,D,RVM>,
										RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
										RS extends JeeslRevisionScope<L,D,RC,RA>,
										RST extends JeeslStatus<RST,L,D>,
										RE extends JeeslRevisionEntity<L,D,RC,REM,RA,?>,
										REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
										RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
										RER extends JeeslStatus<RER,L,D>,
										RAT extends JeeslStatus<RAT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(RevisionEntityComparator.class);

    public enum Type {position};

    public RevisionEntityComparator()
    {
    	
    }
    
    public Comparator<RE> factory(Type type)
    {
        Comparator<RE> c = null;
        RevisionEntityComparator<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> factory = new RevisionEntityComparator<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<RE>
    {
        public int compare(RE a, RE b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());
			  ctb.append(a.getPosition(), b.getPosition());
			  return ctb.toComparison();
        }
    }
}