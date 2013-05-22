package net.sf.ahtutils.test.controller.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.ahtutils.controller.util.Ranking;
import net.sf.ahtutils.test.AbstractAhtUtilTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRankComparator extends AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestRankComparator.class);
	
	@Before
	public void init()
	{
		
	}
 
    @Test
    public void testSimple()
    {	
    	Ranking r = new Ranking();
    	List<Ranking.Rank> list = new ArrayList<Ranking.Rank>();
    	Ranking.Rank a = r.new Rank(1,4);list.add(a);
    	Ranking.Rank b = r.new Rank(2,5);list.add(b);
    	
    	Collections.sort(list, r.new Rank());
    	Assert.assertEquals("b.points = " + b.getPoints() + " b2 = " + list.get(0).getPoints(), b, list.get(0));
    	Assert.assertEquals(a, list.get(1));
    }
}