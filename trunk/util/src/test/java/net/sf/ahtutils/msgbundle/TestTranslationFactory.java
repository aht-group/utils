package net.sf.ahtutils.msgbundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.test.AbstractAhtUtilTest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

public class TestTranslationFactory extends AbstractAhtUtilTest
{
	static Log logger = LogFactory.getLog(TestTranslationFactory.class);
	
	private static final String targetDir = "target/msg-bundle.test";
	private TranslationFactory tFactory;
	private File fTarget,dirBundle;
	private String bundleName = "msg";
	private String bundlePackage = "net.sf";
	
	@Before
	public void init() throws IOException
	{
		tFactory = new TranslationFactory();
		tFactory.setInEncoding("UTF-8");
		tFactory.setOutEncoding("UTF-8");
		tFactory.add("src/test/resources/data/xml/msgBundle/translation1.xml");
		
		fTarget = new File(targetDir);
		if(fTarget.exists())
		{
			FileUtils.deleteDirectory(fTarget);
		}
		fTarget.mkdir();
		dirBundle = new File(fTarget,bundlePackage.replaceAll("\\.", "/"));
	}
	
	@Test
	public void targetCreation() throws FileNotFoundException, AhtUtilsNotFoundException
    {	
		tFactory.writeMessageResourceBundles(bundleName,bundlePackage,fTarget.getAbsolutePath());
		Assert.assertTrue(fTarget.exists());
		Assert.assertTrue(fTarget.isDirectory());
		
		
		Assert.assertTrue("Directory ("+bundlePackage+") does not exist: "+dirBundle.getAbsolutePath(),dirBundle.exists());
		Assert.assertTrue("("+bundlePackage+") not a directory: "+dirBundle.getAbsolutePath(),dirBundle.isDirectory());
    }
	
	@Test
	public void sizeLanguages() throws FileNotFoundException, AhtUtilsNotFoundException
    {	
		tFactory.writeMessageResourceBundles(bundleName,bundlePackage,fTarget.getAbsolutePath());
		TranslationMap tMap = tFactory.gettMap();
		for(String s : tMap.getLangKeys())
		{
			File f = new File(dirBundle,bundleName+"_"+s+"."+TranslationFactory.msgBundleSuffix);
			Assert.assertTrue("Should exist: "+f.getAbsolutePath(),f.exists());
			Assert.assertTrue(f.isFile());
		}
    }
}
