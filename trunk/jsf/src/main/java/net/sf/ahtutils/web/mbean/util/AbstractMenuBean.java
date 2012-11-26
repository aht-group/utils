package net.sf.ahtutils.web.mbean.util;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.jsf.menu.MenuFactory;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractMenuBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractMenuBean.class);
	private static final long serialVersionUID = 1L;
	
	protected static final String rootMain = "root";
	protected Map<String,Menu> mapMenu;
	protected Map<String,MenuItem> mapSub;
	protected Map<String,List<MenuItem>> mapBreadcrumb;

    public void initMaps() throws FileNotFoundException
    {
		mapMenu = new Hashtable<String,Menu>();
		mapSub = new Hashtable<String,MenuItem>();
		mapBreadcrumb = new Hashtable<String,List<MenuItem>>();
    }
    
	public void clear()
	{
		mapMenu.clear();
		mapSub.clear();
		mapBreadcrumb.clear();
	}
	
	protected Menu createMenu(String code,MenuFactory mf)
	{
		logger.trace("createMenu for "+code);
		return mf.build(code);	
	}
	
	public Menu menu(MenuFactory mf, String code)
	{
		synchronized(mf)
		{
			if(!mapMenu.containsKey(code))
			{
				mapMenu.put(code, createMenu(code,mf));
			}
			return mapMenu.get(code);
		}
	}
	
	public List<MenuItem> breadcrumb(MenuFactory mf,String code)
	{
		synchronized(mf)
		{
			if(!mapBreadcrumb.containsKey(code))
			{
				if(!mapMenu.containsKey(code)){menu(mf,code);}
				mapBreadcrumb.put(code,mf.breadcrumb(code));
			}
			return mapBreadcrumb.get(code);
		}
	}
	
	public MenuItem sub(MenuFactory mf, String code)
	{
		synchronized(mf)
		{
			if(!mapSub.containsKey(code))
			{
				if(!mapMenu.containsKey(code)){menu(mf,code);}
				mapSub.put(code,mf.subMenu(mapMenu.get(code),code));
			}
			return mapSub.get(code);
		}
	}
}