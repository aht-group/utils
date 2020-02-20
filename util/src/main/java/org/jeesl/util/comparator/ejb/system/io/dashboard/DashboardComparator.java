package org.jeesl.util.comparator.ejb.system.io.dashboard;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.system.io.dash.JeeslIoDashboard;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class DashboardComparator<L extends JeeslLang,D extends JeeslDescription,
DBR extends JeeslStatus<DBR,L,D>,
DB extends JeeslIoDashboard<L,D,DBR,DB>>
{
    final static Logger logger = LoggerFactory.getLogger(DashboardComparator.class);

    public enum Type {code};

    public Comparator<DB> factory(Type type)
    {
        Comparator<DB> c = null;
        DashboardComparator factory = new DashboardComparator();
        switch (type)
        {
            case code: c = factory.new CodeComparator();break;
        }

        return c;
    }

    private class CodeComparator implements Comparator<DB>
    {
    	@Override
		public int compare(DB a, DB b)
        {
            CompareToBuilder ctb = new CompareToBuilder();
            ctb.append(a.getCode(),b.getCode());
            return ctb.toComparison();
        }
    }

}
