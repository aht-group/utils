package org.jeesl.util.comparator.ejb;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.with.primitive.date.EjbWithRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordComparator<T extends EjbWithRecord> implements Comparator<T>
{
	final static Logger logger = LoggerFactory.getLogger(RecordComparator.class);

	public int compare(T a, T b)
    {
		  CompareToBuilder ctb = new CompareToBuilder();
		  ctb.append(a.getRecord(), b.getRecord());
		  ctb.append(a.getId(), b.getId());
		  return ctb.toComparison();
    }
}