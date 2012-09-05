package net.sf.ahtutils.test;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilsTest.class);
	
	protected static Random rnd;
	
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
}