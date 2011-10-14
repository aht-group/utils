package net.sf.ahtutils.test.controller.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.ahtutils.controller.util.Ranking;
import net.sf.ahtutils.test.AbstractAhtUtilTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestRanking extends AbstractAhtUtilTest
{
	static Log logger = LogFactory.getLog(TestRanking.class);
	
	private Ranking ranking;
	private Random rnd;
	
	@Before
	public void init()
	{
		ranking = new Ranking();
		rnd = new Random();
	}
    
    @After
    public void close()
    {
    	ranking = null;
    	rnd = null;
    }
 
    @Test
    public void testSimple()
    {
    	List<Integer> points = new ArrayList<Integer>();points.add(1);points.add(2);points.add(3);
    	List<Integer> expected = new ArrayList<Integer>();expected.add(3);expected.add(2);expected.add(1);
    	List<Integer> actual = ranking.rank(points);
    	for(int i=0;i<actual.size();i++)
    	{
    		Assert.assertEquals(expected.get(i), actual.get(i));
    	}	
    }
    
    @Ignore ("Result is too high")
    @Test
    public void testRandom()
    {
    	int nr = 1000;
    	List<Integer> points = new ArrayList<Integer>();
    	
    	for(int i=0;i<nr;i++)
    	{
    		points.add(rnd.nextInt());
    	}
    	List<Integer> test = ranking.rank(points);
    	Assert.assertEquals(nr, points.size());
    	Assert.assertEquals(nr, test.size());
    	for(Integer i : test)
    	{
    		Assert.assertTrue("Rank must be > 0, but is "+i,i>0);
    		Assert.assertTrue("Rank must be <= "+nr+", but is "+i, +i<=1000);
    	}
    }
}