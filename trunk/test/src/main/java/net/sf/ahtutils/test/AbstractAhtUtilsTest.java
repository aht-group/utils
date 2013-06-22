package net.sf.ahtutils.test;

import java.io.File;
import java.util.Date;
import java.util.Random;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilsTest.class);
	
	protected static File fTarget;
	protected static Random rnd;
	
	@BeforeClass
	public static void initTargetDirectory()
	{
		if(fTarget==null)
		{
			String dirTarget = System.getProperty("targetDir");
			if(dirTarget==null){dirTarget="target";}
			setfTarget(new File(dirTarget));
			if(LoggerInit.isLog4jInited())
			{
				logger.debug("Using targeDir "+fTarget.getAbsolutePath());
			}
		}
	}
	
	protected static void initLogger()
	{
		System.err.println("Must be overridden!!");
		System.exit(-1);
	}
	
	protected long rndL()
	{
		if(rnd==null){rnd = new Random();}
		return rnd.nextLong();
	}
	protected int rndI()
	{
		if(rnd==null){rnd = new Random();}
		return rnd.nextInt();
	}
	protected int rndI(int max)
	{
		if(rnd==null){rnd = new Random();}
		return rnd.nextInt(max);
	}
	
	protected String getSizedString(int size)
    {
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<size;i++)
    	{
    		sb.append("a");
    	}
    	return sb.toString();
    }
	
	protected void assertJaxbEquals(Object ref, Object test)
	{
		Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
	}
	
	protected static Date getDefaultDate()
	{
		return DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
	}
	
	protected static void setfTarget(File myTarget) {fTarget=myTarget;}
}