package net.sf.ahtutils.jsf.menu;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.xml.navigation.Breadcrumb;
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
	
	public void add(String label,String href)
	{
		MenuItem mi = new MenuItem();
		mi.setName(label);
		mi.setHref(href);
		list.add(mi);
	}
	
	public Breadcrumb build()
	{
		Breadcrumb breadcrumb = new Breadcrumb();
		breadcrumb.getMenuItem().addAll(list);
		return breadcrumb;
	}

}
