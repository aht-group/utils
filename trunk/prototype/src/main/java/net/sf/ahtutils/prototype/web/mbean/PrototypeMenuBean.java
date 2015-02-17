package net.sf.ahtutils.prototype.web.mbean;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.annotation.PostConstruct;

import net.sf.ahtutils.jsf.menu.MenuFactory;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.ahtutils.web.mbean.util.AbstractMenuBean;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.navigation.Breadcrumb;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrototypeMenuBean extends AbstractMenuBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(PrototypeMenuBean.class);
	private static final long serialVersionUID = 1L;
	
	protected Menu menu;
	protected MenuFactory mfMain;
	
	@PostConstruct
    public void init()
    {
		logger.info(AbstractLogMessage.postConstruct());
		
		// GEO-75 Remove Exception for Compatibility
		try
		{
			super.initMaps();
			Access xmlAccess = JaxbUtil.loadJAXB(this.getClass().getClassLoader(),"/views.xml", Access.class);
			Menu xmlMenuMain = JaxbUtil.loadJAXB(this.getClass().getClassLoader(),"/menu.xml", Menu.class);
			
			if(logger.isTraceEnabled())
			{
				logger.info("main.root="+rootMain);
			}

			mfMain = new MenuFactory(xmlMenuMain,xmlAccess,getLang(),rootMain);
			mfMain.setAlwaysUpToLevel(99);
		}
		catch (FileNotFoundException e)
		{
			throw new IllegalStateException("Class: " +e.getClass().getName() +" Message: " +e.getMessage());
		}
		
		
    }
	
	@Override protected void buildViewAllowedMap() {}
	
	public void clearAndRemoveChilds(String key, String dynKey)
	{
		mfMain.removeChilds(key,dynKey);
		mapMenu.remove(key);
		mapSub.remove(key);
		mapBreadcrumb.remove(key);
	}
	
	protected String getLang(){return "en";}
	
	public Menu build() {return super.menu(mfMain, rootMain);}
	public Menu build(String code){return super.menu(mfMain, code);}
	
	public Breadcrumb breadcrumb(String code){return super.breadcrumb(mfMain, false, code, true, true);}
	public Breadcrumb breadcrumbDyn(String code,Menu dynamic){return super.breadcrumb(mfMain, false, code, true, true, dynamic);}
	
	public MenuItem sub(String code){return super.sub(mfMain, code);}
	public MenuItem subDyn(String code,Menu dynamic){return super.subDyn(mfMain,code,dynamic);}
}