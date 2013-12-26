package net.sf.ahtutils.jsf.menu;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.xml.navigation.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreadcrumbFactory
{
	final static Logger logger = LoggerFactory.getLogger(BreadcrumbFactory.class);
	
	private List<MenuItem> list;
	
	public BreadcrumbFactory()
	{
		list = new ArrayList<MenuItem>();
	}
	
	public void add(String label)
	{
		MenuItem mi = new MenuItem();
		mi.setName(label);
		list.add(mi);
	}
	
	public List<MenuItem> build()
	{
		return list;
	}

}
