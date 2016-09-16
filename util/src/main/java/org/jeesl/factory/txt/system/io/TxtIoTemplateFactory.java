package org.jeesl.factory.txt.system.io;

import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateEnvelope;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtIoTemplateFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtIoTemplateFactory.class);
		
	public static <L extends UtilsLang,D extends UtilsDescription, CATEGORY extends UtilsStatus<CATEGORY,L,D>, TYPE extends UtilsStatus<TYPE,L,D>, TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>, DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>, TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
		String buildCode(TEMPLATE template, DEFINITION definition, String localeCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(template.getCode());
		sb.append("-").append(definition.getType().getCode());
		sb.append("-").append(localeCode);
		return sb.toString();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription, CATEGORY extends UtilsStatus<CATEGORY,L,D>, TYPE extends UtilsStatus<TYPE,L,D>, TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>, DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>, TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
		String buildCode(TEMPLATE template, TYPE type, String localeCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(template.getCode());
		sb.append("-").append(type.getCode());
		sb.append("-").append(localeCode);
		return sb.toString();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription, CATEGORY extends UtilsStatus<CATEGORY,L,D>, TYPE extends UtilsStatus<TYPE,L,D>, TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>, DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>, TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
		String buildCode(JeeslIoTemplateEnvelope<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> envelope)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(envelope.getTemplate().getCode());
		sb.append("-").append(envelope.getType().getCode());
		sb.append("-").append(envelope.getLocaleCode());
		return sb.toString();
	}
	
}