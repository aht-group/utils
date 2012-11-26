package net.sf.ahtutils.web.util;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import net.sf.ahtutils.jsf.menu.MenuFactory;
import net.sf.ahtutils.web.mbean.util.AbstractMenuBean;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @SessionScoped
public class MenuBean extends AbstractMenuBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(MenuBean.class);
	private static final long serialVersionUID = 1L;
	
//	@Inject private Identity identity;
//	@Inject private String localeCode;

	protected Menu menu;
	protected MenuFactory mfMain;
	
	@PostConstruct
    public void init() throws FileNotFoundException
    {
		logger.info("@PostConstruct");
		super.initMaps();
		
		Menu xmlMenuMain = JaxbUtil.loadJAXB(this.getClass().getClassLoader(),"/menu.xml", Menu.class);
		
		if(logger.isTraceEnabled())
		{
			logger.info("main.root="+rootMain);
		}

		mfMain = new MenuFactory(xmlMenuMain,"en",rootMain);
		mfMain.setAlwaysUpToLevel(99);
		mfMain.setContextRoot("geojsf");
    }
	
	public Menu menu(String code) {return super.menu(mfMain, code);}
	public List<MenuItem> breadcrumb(String code){return super.breadcrumb(mfMain, code);}
	public MenuItem sub(String code){return super.sub(mfMain, code);}
}