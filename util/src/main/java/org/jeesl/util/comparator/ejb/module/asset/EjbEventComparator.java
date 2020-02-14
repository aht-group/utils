package org.jeesl.util.comparator.ejb.module.asset;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbEventComparator<EVENT extends JeeslAomEvent<?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbEventComparator.class);

    public enum Type {record};
    
    public Comparator<EVENT> factory(Type type)
    {
        Comparator<EVENT> c = null;
        EjbEventComparator<EVENT> factory = new EjbEventComparator<EVENT>();
        switch (type)
        {
            case record: c = factory.new PositionComparator();break;
        }

        return c;
    }

    private class PositionComparator implements Comparator<EVENT>
    {
    	@Override public int compare(EVENT a, EVENT b)
        {
        	CompareToBuilder ctb = new CompareToBuilder();
        	ctb.append(b.getRecord(),a.getRecord());
        	ctb.append(a.getId(),b.getId());
        	return ctb.toComparison();
        }
    }
}