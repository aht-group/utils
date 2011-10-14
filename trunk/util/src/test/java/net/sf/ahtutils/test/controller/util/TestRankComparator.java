package net.sf.ahtutils.test.controller.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;
import net.sf.ahtutils.controller.util.Rank;
import net.sf.ahtutils.test.AbstractAhtUtilTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class TestRankComparator extends AbstractAhtUtilTest
{
	static Log logger = LogFactory.getLog(TestRankComparator.class);
	
	@Before
	public void init()
	{
		
	}
 
    @Test
    public void testSimple()
    {	
    	List<Rank> list = new ArrayList<Rank>();
    	Rank a = new Rank(1,4);list.add(a);
    	Rank b = new Rank(2,5);list.add(b);
    	
    	Collections.sort(list, new Rank());
    	Assert.assertEquals("b.points = " + b.getPoints() + " b2 = " + list.get(0).getPoints(), b, list.get(0));
    	Assert.assertEquals(a, list.get(1));
    }
}