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
	
	protected Map<String,Boolean> mapViewAllowed;

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
		mapViewAllowed = null;
	}
	
	protected void buildViewAllowedMap()
	{
		logger.error("This should never been called here. A @Override in extended class is required");
	}
		
	// ******************************************
	// Menu
	public Menu menu(MenuFactory mf, String code)
	{
		buildViewAllowedMap();
		synchronized(mf)
		{
			if(!mapMenu.containsKey(code))
			{
				mapMenu.put(code, mf.build(code));
			}
			return mapMenu.get(code);
		}
	}
	public Menu menu(MenuFactory mf, String code, boolean loggedIn)
	{
		buildViewAllowedMap();
		if(code.length()==0){code=rootMain;}
		if(!mapMenu.containsKey(code))
		{
			synchronized(mf)
			{
				mapMenu.put(code, mf.build(mapViewAllowed,code,loggedIn));
			}
		}
		return mapMenu.get(code);
	}
	
	/**
	 * Breadcrumb
	 */
	public List<MenuItem> breadcrumb(MenuFactory mf,String code){return breadcrumb(mf,false,code);}
	public List<MenuItem> breadcrumb(MenuFactory mf,boolean withRoot, String code)
	{
		synchronized(mf)
		{
			if(!mapBreadcrumb.containsKey(code))
			{
				if(!mapMenu.containsKey(code)){menu(mf,code);}
				mapBreadcrumb.put(code,mf.breadcrumb(withRoot,code));
			}
			return mapBreadcrumb.get(code);
		}
	}
	
	// ******************************************
	// SubMenu
	public MenuItem sub(MenuFactory mf, String code)
	{
		synchronized(mf)
		{
			if(!mapSub.containsKey(code))
			{
				if(!mapMenu.containsKey(code)){menu(mf,code);}
				Menu m = mapMenu.get(code);
				mapSub.put(code,mf.subMenu(m,code));
			}
			return mapSub.get(code);
		}
	}
}