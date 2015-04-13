package net.sf.ahtutils.jsf.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimpleArithmetic
{
	final static Logger logger = LoggerFactory.getLogger(SimpleArithmetic.class);
	
    private SimpleArithmetic() {}
    
    public static Integer add(Integer a, Integer b)
    {
    	if((a!=null) && (b!=null)){return a+b;}
    	else if((a==null) && (b!=null)){return b;}
    	else if((a!=null) && (b==null)){return a;}
    	return 0;
    }

}
