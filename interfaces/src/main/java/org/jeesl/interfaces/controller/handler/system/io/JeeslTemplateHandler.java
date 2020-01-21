package org.jeesl.interfaces.controller.handler.system.io;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.controller.JeeslMail;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslTemplateHandler <L extends UtilsLang,D extends UtilsDescription,LOC extends JeeslLocale<L,D,LOC,?>,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CHANNEL extends JeeslTemplateChannel<L,D,CHANNEL,?>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
										SCOPE extends UtilsStatus<SCOPE,L,D>,
										DEFINITION extends JeeslIoTemplateDefinition<D,CHANNEL,TEMPLATE>,
										TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
										TOKENTYPE extends UtilsStatus<TOKENTYPE,L,D>> extends Serializable
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