package net.sf.ahtutils.mail.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
	
	public void createTemplate(String id, String lang, String type)
	{
		try
		{
			FreemarkerConfigBuilder fcb = new FreemarkerConfigBuilder(mails);
			initTemplate(fcb.build(id, lang, type));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
			
			ftl = freemarkerConfiguration.getTemplate(sb.toString(),"UTF-8");
			ftl.setEncoding("UTF-8"); 
		}
		catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
		catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
	}
 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String process(Object xml) throws SAXException, IOException, ParserConfigurationException, TemplateException
	{
		if(ftl==null){throw new AhtUtilsDeveloperException("You forgot to init the template");}
		Document jdom = JaxbUtil.toDocument(xml);
		jdom=JDomUtil.unsetNameSpace(jdom);
		 
		Map root = new HashMap();
		root.put("doc", freemarker.ext.dom.NodeModel.parse(new InputSource(JDomUtil.toInputStream(jdom, Format.getPrettyFormat()))));
	     
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     
		Writer out = new OutputStreamWriter(baos);
		ftl.process(root, out);
		out.flush();
		
		String result = new String(baos.toByteArray(),"UTF-8");
	     
		return result;
	}
}