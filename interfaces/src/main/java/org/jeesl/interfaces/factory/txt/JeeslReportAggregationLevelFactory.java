package org.jeesl.interfaces.factory.txt;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslReportAggregationLevelFactory
{
	String buildTreeLevelName(String localeCode, EjbWithId ejb);
	String build(EjbWithId ejb);
}