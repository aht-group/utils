package net.sf.ahtutils.mail.freemarker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFreemarkerEngine // extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestFreemarkerEngine.class);
/*	
	private FreemarkerEngine fme;
	
	private Mails mails;
	
	@Before
	public void init()
	{	
		mails = new Mails();
		
		Mail mail = new Mail();
		mail.setId("id");
		
		Template template = new Template();
		template.setLang("de");
		template.setType("html");
		
		mail.getTemplate().add(template);
		mails.getMail().add(mail);
		
		fme = new FreemarkerEngine(mails);
	}
	
	@After
	public void close()
	{
		fme = null;
	}
    
    @Test(expected=UtilsDeveloperException.class)
    public void devException() throws SAXException, IOException, ParserConfigurationException, TemplateException
    {
    	fme.processXml("test");
    }
    
    @Test
    public void isAvailable() throws SAXException, IOException, ParserConfigurationException, TemplateException
    {
    	Assert.assertFalse(fme.isAvailable("null", "de", "txt"));
    	Assert.assertFalse(fme.isAvailable("id", "de", "txt"));
    	Assert.assertTrue(fme.isAvailable("id", "de", "html"));
    }
	*/
}