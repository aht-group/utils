package net.sf.ahtutils.jsf.menu;

import junit.framework.Assert;
import net.sf.ahtutils.controller.factory.xml.acl.XmlViewFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangFactory;
import net.sf.ahtutils.test.AbstractAhtUtilsJsfTest;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.ahtutils.xml.status.Langs;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMenuFactory extends AbstractAhtUtilsJsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMenuFactory.class);
	
	private Access access;
	
	private Menu menu;
	private View v1;
	private MenuItem mWithLangs,mWithView;
	
	private final String lang = "de";
	private final String viewCode = "testView";
	
	private MenuFactory mf;
	
	@Before
	public void init()
	{
		mf = new MenuFactory(lang);
		initAccess();
		initMenu();
	}
	
	private void initAccess()
	{
		v1 = XmlViewFactory.create(viewCode);
		v1.setLangs(new Langs());
		v1.getLangs().getLang().add(XmlLangFactory.create(lang, "viewTranslation"));
		v1.getLangs().getLang().add(XmlLangFactory.create("en", "dummyTranslation"));
		
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
		mWithLangs.setLangs(new Langs());
		mWithLangs.getLangs().getLang().add(XmlLangFactory.create(lang, "myTranslation"));
		mWithLangs.getLangs().getLang().add(XmlLangFactory.create("en", "dummyTranslation"));
		menu.getMenuItem().add(mWithLangs);
		
		mWithView = new MenuItem();
		mWithView.setView(XmlViewFactory.create(viewCode));
		menu.getMenuItem().add(mWithView);
	}
	
	@Test
	public void testWithLangs()
	{
		Menu actualMenu = mf.create(menu);
		Assert.assertEquals(menu.getMenuItem().size(), actualMenu.getMenuItem().size());
		MenuItem actual = actualMenu.getMenuItem().get(0);
		Assert.assertTrue(actual.isSetName());
		Assert.assertEquals(mWithLangs.getLangs().getLang().get(0).getTranslation(), actual.getName());
	}
	
	@Test
	public void testWithView()
	{
		Menu actualMenu = mf.create(menu);
		MenuItem actual = actualMenu.getMenuItem().get(1);
		Assert.assertTrue(actual.isSetName());
//		Assert.assertEquals(mWithLangs.getLangs().getLang().get(0).getTranslation(), actual.getName());
	}
}