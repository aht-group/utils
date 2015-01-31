package net.sf.ahtutils.factory.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.ahtutils.model.data.UtilsMonth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractUtilsMonthFactory
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsMonthFactory.class);
		
	public static List<UtilsMonth> sorted(Collection<UtilsMonth> months)
	{
		List<UtilsMonth> list = new ArrayList<UtilsMonth>(months);
		Collections.sort(list);
		return list;
	}
}