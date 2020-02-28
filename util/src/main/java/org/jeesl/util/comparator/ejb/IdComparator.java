package org.jeesl.util.comparator.ejb;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdComparator<T extends EjbWithId> implements Comparator<T>
{
	final static Logger logger = LoggerFactory.getLogger(IdComparator.class);

	public int compare(T a, T b)
    {
		  CompareToBuilder ctb = new CompareToBuilder();
		  ctb.append(a.getId(), b.getId());
		  return ctb.toComparison();
    }
}