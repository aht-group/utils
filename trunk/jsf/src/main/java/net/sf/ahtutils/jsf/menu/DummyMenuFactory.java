package net.sf.ahtutils.jsf.menu;

import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyMenuFactory
{
	final static Logger logger = LoggerFactory.getLogger(DummyMenuFactory.class);
	
	public static Menu create()
	{
		MenuItem m1 = createItem("Top1","hr-t1",true);
		
		MenuItem sub11 = createItem("Sub-1.1");
		sub11.getMenuItem().add(createItem("Sub-1.1.1","hr-1.1.1"));
		sub11.getMenuItem().add(createItem("Sub-1.1.2","hr-1.1.2"));
		m1.getMenuItem().add(sub11);
		
		MenuItem sub12 = createItem("Sub-1.2","hr-1.2",true);
		sub12.getMenuItem().add(createItem("Sub-1.2.1","hr-1.2.1"));
		sub12.getMenuItem().add(createItem("Sub-1.2.2","hr-1.2.2",true));
		m1.getMenuItem().add(sub12);
		
		MenuItem m2 = createItem("Top2","hr-t2",false);
		m2.getMenuItem().add(createItem("Sub-2.1","hr-2.1"));
		
		MenuItem sub22 = createItem("Sub-2.2","hr-2.2");
		sub22.getMenuItem().add(createItem("Sub-2.2.1","hr-2.2.1"));
		m2.getMenuItem().add(sub22);
		
		Menu menu = new Menu();
		menu.getMenuItem().add(m1);
		menu.getMenuItem().add(m2);
		
		return menu;
	}
	
	private static MenuItem createItem(String name){return createItem(name,null,false);}
	private static MenuItem createItem(String name, String href){return createItem(name,href,false);}
	private static MenuItem createItem(String name, String href, boolean active)
	{
		MenuItem item = new MenuItem();
		item.setActive(active);
		item.setName(name);
		item.setHref(href);
		return item;
	}
}
