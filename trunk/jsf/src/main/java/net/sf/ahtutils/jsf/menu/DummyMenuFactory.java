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
		MenuItem rootLevel = createItem("Top","hr-t1",true);
		
		MenuItem sublevel1 = createItem("SubLevel1");
		sublevel1.getMenuItem().add(createItem("ItemOfSublevel1-a","hr-1.1.1"));
		sublevel1.getMenuItem().add(createItem("ItemOfSublevel1-b","hr-1.1.2"));
		
		MenuItem sublevel2 = createItem("SubLevel2","hr-1.2",true);
		sublevel2.getMenuItem().add(createItem("ItemOfSublevel2-a","hr-1.2.1"));
		sublevel2.getMenuItem().add(createItem("ItemOfSublevel2-b","hr-1.2.2",true));
		
		MenuItem sublevel3 = createItem("SubLevel3","hr-5.2",true);
		sublevel3.getMenuItem().add(createItem("ItemOfSublevel3-a","hr-5.2.5"));
		sublevel3.getMenuItem().add(createItem("ItemOfSublevel3-b","hr-5.2.2",true));
		
		sublevel2.getMenuItem().add(sublevel3);
		sublevel1.getMenuItem().add(sublevel2);
		rootLevel.getMenuItem().add(sublevel1);
		
		Menu menu = new Menu();
		menu.getMenuItem().add(rootLevel);
		
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
