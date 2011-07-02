package net.sf.ahtutils.test.xml.xpath.report;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TestReportMediaXpath extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(TestReportMediaXpath.class);
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	
    	
    }
    
    @Test(expected=ExlpXpathNotFoundException.class)
    public void testAuthNoServerId() throws ExlpXpathNotFoundException
    {
    	
    }
}