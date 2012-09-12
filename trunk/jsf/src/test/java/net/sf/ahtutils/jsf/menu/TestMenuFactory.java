package net.sf.ahtutils.jsf.menu;

import java.util.Hashtable;
import java.util.Map;

import junit.framework.Assert;
import net.sf.ahtutils.controller.factory.xml.acl.XmlViewFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangFactory;
import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;
import net.sf.ahtutils.test.IgnoreOtherRule;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.ahtutils.xml.navigation.Navigation;
import net.sf.ahtutils.xml.navigation.UrlMapping;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.process.ProcessClock;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMenuFactory extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestMenuFactory.class);
	
	private Access access;
	private Map<String,Boolean> mapViewAllowed;
	
	private Menu menu;
	private View v1;
	private MenuItem mWithLangs,mWithView,mWithHref;
	
	private final String lang = "de";
	private final String viewCode = "testView";
	
	private MenuFactory mf;
	
	@Before
	public void init()
	{
		initAccess();
		initMenu();
		mf = new MenuFactory(access,mapViewAllowed,lang);
	}
	
	private void initAccess()
	{
		mapViewAllowed = new Hashtable<String,Boolean>();
		
		v1 = XmlViewFactory.create(viewCode);
		v1.setPublic(false);
		v1.setLangs(new Langs());
		v1.getLangs().getLang().add(XmlLangFactory.create(lang, "viewTranslation"));
		v1.getLangs().getLang().add(XmlLangFactory.create("en", "dummyTranslation"));
		v1.setNavigation(new Navigation());
		v1.getNavigation().setUrlMapping(new UrlMapping());
		v1.getNavigation().getUrlMapping().setValue("myViewUrlHref");
		mapViewAllowed.put(viewCode, true);
				
		Category c1 = new Category();
		c1.setViews(new Views());
		c1.getViews().getView().add(v1);
		
		access = new Access();
		access.getCategory().add(c1);
	}
	
	private void initMenu()
	{
		menu = new Menu();
		
		mWithLangs = new MenuItem();
		mWithLangs.setCode("mWithLangs");
		mWithLangs.setLangs(new Langs());
		mWithLangs.getLangs().getLang().add(XmlLangFactory.create(lang, "myTranslation"));
		mWithLangs.getLangs().getLang().add(XmlLangFactory.create("en", "dummyTranslation"));
		menu.getMenuItem().add(mWithLangs);
		
		mWithView = new MenuItem();
		mWithView.setCode("mWithView");
		mWithView.setView(XmlViewFactory.create(viewCode));
		menu.getMenuItem().add(mWithView);
		
		mWithHref = new MenuItem();
		mWithHref.setCode("mWithHref");
		mWithHref.setHref("myHref");
		menu.getMenuItem().add(mWithHref);
	}
	
	@Test
	public void testWithLangs()
	{
		Menu actualMenu = mf.create(menu,"test");
		Assert.assertEquals(menu.getMenuItem().size(), actualMenu.getMenuItem().size());
		MenuItem actual = actualMenu.getMenuItem().get(0);
		Assert.assertTrue(actual.isSetName());
		Assert.assertEquals(mWithLangs.getLangs().getLang().get(0).getTranslation(), actual.getName());
	}

	@Test
	public void testWithView()
	{
		Menu actualMenu = mf.create(menu,"test");
		MenuItem actual = actualMenu.getMenuItem().get(1);
		Assert.assertTrue(actual.isSetName());
		Assert.assertEquals(v1.getLangs().getLang().get(0).getTranslation(), actual.getName());
	}
	
	@Test
	public void testWithViewDenied()
	{
		mapViewAllowed.put(viewCode, false);
		mf = new MenuFactory(access,mapViewAllowed,lang);
		Menu actualMenu = mf.create(menu,"test");
		Assert.assertEquals(menu.getMenuItem().size()-1, actualMenu.getMenuItem().size());
	}
	
	@Test
	public void testHrefDirect()
	{
		Menu actualMenu = mf.create(menu,"test");
		Assert.assertEquals("#", actualMenu.getMenuItem().get(0).getHref());
		
		MenuItem actual = actualMenu.getMenuItem().get(2);
		Assert.assertTrue("href not set",actual.isSetHref());
		Assert.assertEquals(mWithHref.getHref(), actual.getHref());
	}
	
	@Test
	public void testHrefInView()
	{
		Menu actualMenu = mf.create(menu,"test");
		
		MenuItem actual = actualMenu.getMenuItem().get(1);
		Assert.assertTrue("href not set",actual.isSetHref());
		Assert.assertEquals(v1.getNavigation().getUrlMapping().getValue(), actual.getHref());
	}
	
	@Rule public IgnoreOtherRule test = new IgnoreOtherRule("speed");
	@Test
	public void speed()
	{
		ProcessClock pc = new ProcessClock();	
		pc.add("start");
		mf.create(menu,"test");
		pc.add("stop");
		pc.info(logger);
	}
}