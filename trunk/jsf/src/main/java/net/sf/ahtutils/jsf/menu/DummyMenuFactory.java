package net.sf.ahtutils.jsf.menu;

import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;

public class DummyMenuFactory
{
	public static Menu create()
	{
		MenuItem m1 = createItem("Top1");
		
		MenuItem sub11 = createItem("Sub-1.1");
		sub11.getMenuItem().add(createItem("Sub-1.1.1"));
		sub11.getMenuItem().add(createItem("Sub-1.1.2"));
		m1.getMenuItem().add(sub11);
		
		MenuItem sub12 = createItem("Sub-1.2");
		sub12.getMenuItem().add(createItem("Sub-1.2.1"));
		sub12.getMenuItem().add(createItem("Sub-1.2.2"));
		m1.getMenuItem().add(sub12);
		
		MenuItem m2 = createItem("Top2");
		m2.getMenuItem().add(createItem("Sub-2.1"));
		m2.getMenuItem().add(createItem("Sub-2.2"));
		
		Menu menu = new Menu();
		menu.getMenuItem().add(m1);
		menu.getMenuItem().add(m2);
		
		return menu;
	}
	
	private static MenuItem createItem(String name)
	{
		MenuItem item = new MenuItem();
		item.setName(name);
		return item;
	}
}
