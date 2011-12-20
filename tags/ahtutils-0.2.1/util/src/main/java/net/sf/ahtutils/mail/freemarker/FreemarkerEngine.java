package net.sf.ahtutils.mail.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.ahtutils.controller.exception.AhtUtilsDeveloperException;
import net.sf.ahtutils.xml.mail.Mail;
import net.sf.ahtutils.xml.mail.Mails;
import net.sf.ahtutils.xml.xpath.MailXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jdom.Document;
import org.jdom.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerEngine
{
	final static Logger logger = LoggerFactory.getLogger(FreemarkerEngine.class);

	private Mails mails;
	private Template ftl;
	private Configuration freemarkerConfiguration;
	private FreemarkerConfigBuilder fcb;
	
	public FreemarkerEngine(Mails mails)
	{
		this.mails=mails;
		freemarkerConfiguration = new Configuration();
		freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/");
		fcb = new FreemarkerConfigBuilder(mails);
	}
	
	public void createTemplate(String id, String lang, String type)
	{
		try
		{
			
			initTemplate(fcb.build(id, lang, type));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void initTemplate(Mail cfgMail) throws IOException
	{
		try
		{
			Mail mail = MailXpath.getMail(mails, cfgMail.getId());

			net.sf.ahtutils.xml.mail.Template cfgTemplate =  cfgMail.getTemplate().get(0);
			net.sf.ahtutils.xml.mail.Template utilsTemplate = MailXpath.getTemplate(mail, cfgTemplate.getLang(), cfgTemplate.getType());
			
			StringBuffer sb = new StringBuffer();
			sb.append(mails.getDir());
			sb.append("/").append(mail.getDir()).append("/");
			sb.append(mail.getId()).append("/");
			sb.append(utilsTemplate.getLang()).append("-");
			sb.append(utilsTemplate.getType()).append("-");
			sb.append(utilsTemplate.getFile());
			
			ftl = freemarkerConfiguration.getTemplate(sb.toString(),"UTF-8");
			ftl.setEncoding("UTF-8");
		}
		catch (ExlpXpathNotFoundException e) {logger.error("Mail.id="+cfgMail.getId()+" "+e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {logger.error("Mail.id="+cfgMail.getId()+" "+e.getMessage());}
	}
 
	public String processXml(Object xml) throws SAXException, IOException, ParserConfigurationException, TemplateException
	{
		Document jdom = JaxbUtil.toDocument(xml);
		return process(jdom);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String process(Document jdom) throws SAXException, IOException, ParserConfigurationException, TemplateException
	{
		if(ftl==null){throw new AhtUtilsDeveloperException("You forgot to init the template");}
		jdom=JDomUtil.unsetNameSpace(jdom);
		 
//		JDomUtil.debug(jdom);
		Map root = new HashMap();
		root.put("doc", freemarker.ext.dom.NodeModel.parse(new InputSource(JDomUtil.toInputStream(jdom, Format.getPrettyFormat()))));
	     
		StringWriter sw = new StringWriter();
		ftl.process(root, sw);
		sw.flush();
	     
		return sw.toString();
	}
	
	public boolean isAvailable(String id, String lang, String type)
	{
		try
		{
			Mail mail = MailXpath.getMail(mails, id);
			MailXpath.getTemplate(mail, lang, type);
			return true;
		}
		catch (ExlpXpathNotFoundException e) {}
		catch (ExlpXpathNotUniqueException e) {}
		return false;
	}
}