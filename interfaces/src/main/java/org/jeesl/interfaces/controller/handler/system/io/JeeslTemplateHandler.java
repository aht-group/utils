package org.jeesl.interfaces.controller.handler.system.io;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.controller.JeeslMail;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;

public interface JeeslTemplateHandler <L extends JeeslLang,D extends JeeslDescription,LOC extends JeeslLocale<L,D,LOC,?>,
										CATEGORY extends JeeslStatus<CATEGORY,L,D>,
										CHANNEL extends JeeslTemplateChannel<L,D,CHANNEL,?>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
										SCOPE extends JeeslStatus<SCOPE,L,D>,
										DEFINITION extends JeeslIoTemplateDefinition<D,CHANNEL,TEMPLATE>,
										TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
										TOKENTYPE extends JeeslStatus<TOKENTYPE,L,D>> extends Serializable
{
	
	JeeslMail<TEMPLATE> getMail();
	
	String getRecipients();
	void setRecipients(String recipients);
	
	List<LOC> getLocales();
	List<DEFINITION> getDefinitons();
	List<TOKEN> getTokens();
	
	String toHeader(DEFINITION definition, LOC locale);
	String toBody(DEFINITION definition, LOC locale);
	
	String getPreviewHeader();
	String getPreviewBody();
}