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
    public void testOrderedAsc()
    {
    	int[] points = {1,2,3};
    	int[] expected = {3,2,1};
    	int[] actual = ranking.rank(points);
    	for(int i=0;i<actual.length;i++)
    	{
    		Assert.assertEquals(expected[i], actual[i]);
    	}	
    }
    
    @Test
    public void testOrderedRnd()
    {
    	int[] points = {1,5,6};
    	int[] expected = {3,2,1};
    	int[] actual = ranking.rank(points);
    	for(int i=0;i<actual.length;i++)
    	{
    		Assert.assertEquals(expected[i], actual[i]);
    	}	
    }
    
    @Test
    public void testUnOrdered()
    {
    	int[] points = {5,1,4};
    	int[] expected = {1,3,2};
    	int[] actual = ranking.rank(points);
    	for(int i=0;i<actual.length;i++)
    	{
    		Assert.assertEquals(expected[i], actual[i]);
    	}	
    }
    
    @Test
    public void testDoubleLow()
    {
    	int[] points = {3,5,3};
    	int[] expected = {2,1,2};
    	int[] actual = ranking.rank(points);
    	for(int i=0;i<actual.length;i++)
    	{
    		Assert.assertEquals(expected[i], actual[i]);
    	}	
    }
    @Ignore
    @Test
    public void testDoubleHigh()
    {
    	int[] points = {7,5,7};
    	int[] expected = {1,3,1};
    	int[] actual = ranking.rank(points);
    	for(int i=0;i<actual.length;i++)
    	{
    		Assert.assertEquals(expected[i], actual[i]);
    	}	
    }
    
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