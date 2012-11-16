package net.sf.ahtutils.jsf.menu;

import java.util.List;

import junit.framework.Assert;
import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMenuFactoryBreadcrumb extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestMenuFactoryBreadcrumb.class);
		
	private MenuFactory mf;
	
	
	@Before
	public void init()
	{
		TestMenuFactory tMf = TestMenuFactory.factory();
		mf = tMf.getMf();
	}
	
	@Test
	public void withRoutRootLevel1()
	{
		List<MenuItem> actual = mf.breadcrumb(false,TestMenuFactory.code1);
		Assert.assertEquals(1, actual.size());
	}
	
	@Test
	public void name()
	{
		mf.build(TestMenuFactory.code2);
		List<MenuItem> actual = mf.breadcrumb(false,TestMenuFactory.code2);
		MenuItem mi = actual.get(0);
		Assert.assertNotNull(mi.getName());
	}
	
	@Test
	public void withRootLevel1()
	{
		List<MenuItem> actual = mf.breadcrumb(true,TestMenuFactory.code1);
		Assert.assertEquals(2, actual.size());
	}
	
	@Test
	public void withoutRootLevel2()
	{
		List<MenuItem> actual = mf.breadcrumb(false,TestMenuFactory.code2);
		Assert.assertEquals(2, actual.size());
	}
	
	@Test
	public void withRootLevel2()
	{
		List<MenuItem> actual = mf.breadcrumb(true,TestMenuFactory.code2);
		Assert.assertEquals(3, actual.size());
	}
}