package net.sf.ahtutils.msgbundle;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.test.AbstractAhtUtilTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class TestTranslationMap extends AbstractAhtUtilTest
{
	static Log logger = LogFactory.getLog(TestTranslationMap.class);
	
	private TranslationMap tMap;
	
	private String[] t1,t2,t3;
	private List<String[]> translations;
	
	@Before
	public void init()
	{
		tMap = new TranslationMap();
		translations = new ArrayList<String[]>();
		
		t1 = new String[] {"de","key1","lang1"};translations.add(t1);
		t2 = new String[] {"de","key2","lang2"};translations.add(t2);
		t3 = new String[] {"en","key3","lang3"};translations.add(t3);
	}
	
	private void addAllTranslations()
	{
		for(String[] s : translations)
		{
			tMap.add(s);
		}
	}
	
	@Test
	public void sizeLanguages()
    {	
		tMap.add(t1);
		Assert.assertEquals(1, tMap.sizeLanguages());
		
		tMap.add(t2);
		Assert.assertEquals(1, tMap.sizeLanguages());
		
		tMap.add(t3);
		Assert.assertEquals(2, tMap.sizeLanguages());
    }
	
	@Test
	public void sizeKeys() throws AhtUtilsNotFoundException
	{
		addAllTranslations();
		Assert.assertEquals(2, tMap.sizeKeys("de"));
		Assert.assertEquals(1, tMap.sizeKeys("en"));
	}
	
	@Test(expected=AhtUtilsNotFoundException.class)
	public void sizeKeysUnknown() throws AhtUtilsNotFoundException
	{
		tMap.sizeKeys("-1");
	}
	
	@Test
	public void translate()
	{
		addAllTranslations();
		for(String[] s : translations)
		{
			Assert.assertEquals(s[2], tMap.translate(s[0], s[1]));
		}
	}
	
	@Test(expected=AhtUtilsNotFoundException.class)
	public void translateUnknowLang() throws AhtUtilsNotFoundException
	{
		addAllTranslations();
		tMap.translateWithException("-1","-1");
	}
	
	@Test(expected=AhtUtilsNotFoundException.class)
	public void translateUnknowKey() throws AhtUtilsNotFoundException
	{
		addAllTranslations();
		tMap.translateWithException("de","-1");
	}
}
