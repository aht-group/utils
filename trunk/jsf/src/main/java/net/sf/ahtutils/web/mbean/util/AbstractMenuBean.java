package net.sf.ahtutils.web.mbean.util;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.jsf.menu.MenuFactory;
import net.sf.ahtutils.xml.navigation.Breadcrumb;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMenuBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractMenuBean.class);
	private static final long serialVersionUID = 1L;
	
	protected static final String rootMain = "root";
	protected Map<String,Menu> mapMenu;
	protected Map<String,MenuItem> mapSub;
	protected Map<String,Breadcrumb> mapBreadcrumb;
	
	protected Map<String,Boolean> mapViewAllowed;

    public void initMaps() throws FileNotFoundException
    {
		mapMenu = new Hashtable<String,Menu>();
		mapSub = new Hashtable<String,MenuItem>();
		mapBreadcrumb = new Hashtable<String,Breadcrumb>();
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
		logger.warn("This should never been called here. A @Override in extended class is required");
	}
		
	// ******************************************
	// Menu
	protected Menu menu(MenuFactory mf, String code) {return menu(mf,code,false);}
	protected Menu menu(MenuFactory mf, String code, boolean loggedIn)
	{
		buildViewAllowedMap();
		if(code==null || code.length()==0){code=rootMain;}
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
	public Breadcrumb breadcrumb(MenuFactory mf,String code){return breadcrumb(mf,false,code,false,false);}
	public Breadcrumb breadcrumb(MenuFactory mf,boolean withRoot, String code, boolean withFirst, boolean withChilds)
	{
		if(!mapBreadcrumb.containsKey(code))
		{
			synchronized(mf)
			{
				if(!mapMenu.containsKey(code)){menu(mf,code);}
				Breadcrumb bOrig = mf.breadcrumb(withRoot,code);
				Breadcrumb bClone = new Breadcrumb();
				int startIndex=0;
				if(bOrig.getMenuItem().size()>1 && !withFirst){startIndex=1;}
				for(int i=startIndex;i<bOrig.getMenuItem().size();i++)
				{
					MenuItem miOrig = bOrig.getMenuItem().get(i);
					MenuItem miClone = new MenuItem();
					miClone.setName(miOrig.getName());
					miClone.setHref(miOrig.getHref());
					miClone.setCode(miOrig.getCode());
					bClone.getMenuItem().add(miClone);
				}
				//TODO WITHFIRST
		/*		if(b.getMenuItem().size()>1 && !withFirst)
				{
					b.getMenuItem().remove(0);
				}
				*/
				if(withChilds)
				{
					for(MenuItem mi : bClone.getMenuItem())
					{
						String parentCode = mf.getParent(mi.getCode());
						for(MenuItem subOrig : sub(mf, parentCode).getMenuItem())
						{
							MenuItem subClone = new MenuItem();
							subClone.setName(subOrig.getName());
							subClone.setHref(subOrig.getHref());
							mi.getMenuItem().add(subClone);
						}
					}
				}
				mapBreadcrumb.put(code,bClone);
				JaxbUtil.info(mapBreadcrumb.get(code));
			}
		}
		return mapBreadcrumb.get(code);
	}
	
	// ******************************************
	// SubMenu
	public MenuItem sub(MenuFactory mf, String code)
	{
		if(!mapSub.containsKey(code))
		{
			synchronized(mf)
			{
				if(!mapMenu.containsKey(code)){menu(mf,code);}
				Menu m = mapMenu.get(code);
				mapSub.put(code,mf.subMenu(m,code));
			}
		}
		return mapSub.get(code);
	}
}