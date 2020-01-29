package org.jeesl.controller.mail;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoMailFacade;
import org.jeesl.api.facade.io.JeeslIoTemplateFacade;
import org.jeesl.controller.mail.freemarker.FreemarkerIoTemplateEngine;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoMailFactoryBuilder;
import org.jeesl.factory.builder.io.IoTemplateFactoryBuilder;
import org.jeesl.factory.xml.system.io.mail.XmlMailFactory;
import org.jeesl.factory.xml.system.io.mail.XmlMailsFactory;
import org.jeesl.interfaces.controller.JeeslMail;
import org.jeesl.interfaces.controller.handler.system.io.JeeslTemplateHandler;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslIoMail;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslMailRetention;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslMailStatus;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.model.xml.system.io.mail.EmailAddress;
import org.jeesl.model.xml.system.io.mail.Mail;
import org.jeesl.model.xml.system.io.mail.Mails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractJeeslMail<L extends UtilsLang,D extends UtilsDescription,LOC extends JeeslLocale<L,D,LOC,?>,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								CHANNEL extends JeeslTemplateChannel<L,D,CHANNEL,?>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<D,CHANNEL,TEMPLATE>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
								TOKENTYPE extends UtilsStatus<TOKENTYPE,L,D>,
								MAILCAT extends UtilsStatus<MAILCAT,L,D>,
								MAIL extends JeeslIoMail<L,D,MAILCAT,STATUS,RETENTION,FRC>,
								STATUS extends JeeslMailStatus<L,D,STATUS,?>,
								RETENTION extends JeeslMailRetention<L,D,RETENTION,?>,
								FRC extends JeeslFileContainer<?,?>>
							implements JeeslMail<TEMPLATE>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslMail.class);
	
	private final IoTemplateFactoryBuilder<L,D,CATEGORY,CHANNEL,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> fbTemplate;
	private final IoMailFactoryBuilder<L,D,MAILCAT,MAIL,STATUS,RETENTION,FRC> fbMail;
	
	protected final JeeslIoTemplateFacade<L,D,CATEGORY,CHANNEL,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> fTemplate;
	protected final JeeslIoMailFacade<L,D,MAILCAT,MAIL,STATUS,RETENTION,FRC> fMail;
	
	protected final FreemarkerIoTemplateEngine<L,D,CATEGORY,CHANNEL,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> fmEngine;

	protected final Map<String,Template> mapTemplateHeader;
	protected final Map<String,Template> mapTemplateBody;

	protected TEMPLATE template; @Override public TEMPLATE getTemplate() {return template;}

	protected MAILCAT categoryMail;
	protected RETENTION retention;
	protected EmailAddress mailFrom;
	
	protected final Mails mails;
	
	protected String subjectPreifx;
//	private final String dummyLocaleCode = "xx";
	
	public AbstractJeeslMail(IoTemplateFactoryBuilder<L,D,CATEGORY,CHANNEL,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> fbTemplate,
							IoMailFactoryBuilder<L,D,MAILCAT,MAIL,STATUS,RETENTION,FRC> fbMail,
							JeeslIoTemplateFacade<L,D,CATEGORY,CHANNEL,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> fTemplate,
							JeeslIoMailFacade<L,D,MAILCAT,MAIL,STATUS,RETENTION,FRC> fMail)
	{
		this.fbTemplate=fbTemplate;
		this.fbMail=fbMail;
		this.fTemplate=fTemplate;
		this.fMail=fMail;
		
		fmEngine = new FreemarkerIoTemplateEngine<>(fbTemplate);
		
		mapTemplateHeader = new HashMap<String,Template>();
		mapTemplateBody = new HashMap<String,Template>();
		
		subjectPreifx = "";
		mails = XmlMailsFactory.build();
	}
	
	protected <E extends Enum<E>> void initIo(Class<?> c, E code)
	{
		try
		{
			categoryMail = fMail.fByCode(fbMail.getClassCategory(), code);
			
			template = fTemplate.fByCode(fbTemplate.getClassTemplate(), c.getSimpleName());
			template = fTemplate.load(template);
			fmEngine.addTemplate(template);
		}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
	}
	
	public List<DEFINITION> toDefinitions(CHANNEL channel)
	{
		List<DEFINITION> result = new ArrayList<>();
		for(DEFINITION d : template.getDefinitions())
		{
			if(d.getType().equals(channel)) {result.add(d);}
		}
		return result;
	}
	
//	protected void compile(String header, String body) throws IOException {compile(dummyLocaleCode,header,body);}
	protected void compile(JeeslTemplateHandler<L,D,LOC,CATEGORY,CHANNEL,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> handler) throws IOException
	{
		for(DEFINITION def : handler.getDefinitons())
		{
			for(LOC loc : handler.getLocales())
			{
				compile(loc.getCode(),handler.toHeader(def,loc),handler.toBody(def,loc));
			}
		}
		
	}
	protected void compile(String localeCode, String header, String body) throws IOException
	{
		mapTemplateHeader.put(localeCode, AbstractJeeslMail.compile(header));
		mapTemplateBody.put(localeCode, AbstractJeeslMail.compile(body));
	}
	
	public static Template compile(String text) throws IOException
	{
		return new Template("name", new StringReader(text),new Configuration());
	}
	
//	protected String processHeader(Map<String,Object> model) throws TemplateException, IOException {return processHeader(dummyLocaleCode,model);}
	protected String processHeader(String localeCode, Map<String,Object> model) throws TemplateException, IOException
	{
		StringWriter swHeader = new StringWriter();
		mapTemplateHeader.get(localeCode).process(model,swHeader);
		swHeader.flush();
		return swHeader.toString();
	}
	
//	protected String processBody(Map<String,Object> model) throws TemplateException, IOException {return processBody(dummyLocaleCode,model);}
	protected String processBody(String localeCode, Map<String,Object> model) throws TemplateException, IOException
	{
		StringWriter swBody = new StringWriter();
		mapTemplateBody.get(localeCode).process(model,swBody);
		swBody.flush();
		return swBody.toString();
	}
	
	public static String preview(Map<String,Object> model, Template template)
	{
		String result;
		
		try
		{
			StringWriter swHeader = new StringWriter();
			template.process(model,swHeader);
			swHeader.flush();
			result = swHeader.toString();
		}
    	catch (IOException e) {result="Error: "+e.getMessage();}
    	catch (TemplateException e) {result="Error: "+e.getMessage();}
		
		return result;
	}
	
	public void spool(Mail mail) throws JeeslConstraintViolationException, JeeslNotFoundException
	{
		fMail.queueMail(categoryMail,null,mail);
		logger.info("Spooled");
	}
	
	@Override public void overrideRecipients(EmailAddress email)
	{
		for(Mail mail : mails.getMail())
		{
			XmlMailFactory.overwriteRecipients(mail,email);
		}
	}
	
	@Override public void spool()
	{
		for(Mail mail : mails.getMail())
		{
			try {fMail.queueMail(categoryMail,retention,mail);}
			catch (JeeslConstraintViolationException | JeeslNotFoundException e) {e.printStackTrace();}
		}
	}
}