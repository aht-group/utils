package net.sf.ahtutils.prototype.web.mbean;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import net.sf.ahtutils.jsf.menu.MenuFactory;
import net.sf.ahtutils.web.mbean.util.AbstractMenuBean;
import net.sf.ahtutils.xml.access.Access;
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
    public void init() throws FileNotFoundException
    {
		logger.debug("@PostConstruct");
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
	
	@Override protected void buildViewAllowedMap() {}
	
	protected String getLang(){return "en";}
	
	public Menu build() {return super.menu(mfMain, rootMain);}
	public Menu build(String code){return super.menu(mfMain, code);}
	public List<MenuItem> breadcrumb(String code){return super.breadcrumb(mfMain, code);}
	public MenuItem sub(String code){return super.sub(mfMain, code);}
}