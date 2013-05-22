
package net.sf.ahtutils.report;

import java.io.IOException;

import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.test.AbstractAhtUtilsReportTest;

import org.jdom.JDOMException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFontUtil extends AbstractAhtUtilsReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestFontUtil.class);
    		
	@Test
	public void fontUtil() throws ReportException, JDOMException, IOException
	{
		ReportUtilFonts fontUtil = new ReportUtilFonts(reports);
		fontUtil.replaceAll("SansSerif", "Arial", false);
	}
	
}