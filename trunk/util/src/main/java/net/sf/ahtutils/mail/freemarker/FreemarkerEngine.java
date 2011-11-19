package net.sf.ahtutils.mail.freemarker;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Mails;
import net.sf.ahtutils.xml.xpath.MailXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.output.Format;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerEngine
{
	static Log logger = LogFactory.getLog(FreemarkerEngine.class);

	private Mails mails;
	private Template ftl;
	private Configuration freemarkerConfiguration;
	
	public FreemarkerEngine(Mails mails)
	{
		this.mails=mails;
		freemarkerConfiguration = new Configuration();
		freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/");
	}
	
	public void get(Mail mailCfg, String resourceName) throws IOException
	{
		logger.warn("name: " +resourceName);
		
		
		ftl = freemarkerConfiguration.getTemplate(resourceName);
		initTemplate(mailCfg);
	}
	
	private void initTemplate(Mail mailCfg) throws IOException
	{
		try
		{
			Mail mail = MailXpath.getMail(mails, mailCfg.getId());
			net.sf.ahtutils.xml.mail.Template utilsTemplate = MailXpath.getTemplate(mail, mail.getTemplate().get(0).getLang(), mail.getTemplate().get(0).getType());
			
			StringBuffer sb = new StringBuffer();
			sb.append(mails.getDir());
			sb.append("/").append(mail.getDir()).append("/");
			sb.append(mail.getId()).append("/");
			sb.append(utilsTemplate.getLang()).append("-");
			sb.append(utilsTemplate.getType()).append("-");
			sb.append(utilsTemplate.getFile());
			logger.warn("name: " +sb.toString());
			
			ftl = freemarkerConfiguration.getTemplate(sb.toString());
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
	}
 
	public void process(Mail mailCfg, String reName) throws SAXException, IOException, ParserConfigurationException, TemplateException
	{
		initTemplate(mailCfg);
		 Document jdom = JDomUtil.load(reName); 
		 jdom=JDomUtil.unsetNameSpace(jdom);
		 
		 Map root = new HashMap();
	     root.put("doc", freemarker.ext.dom.NodeModel.parse(new InputSource(JDomUtil.toInputStream(jdom, Format.getPrettyFormat()))));
	     
	     Writer out = new OutputStreamWriter(System.out);
	     ftl.process(root, out);
	     out.flush();
	}
	


}
