package org.jeesl.factory.ftl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.controller.processor.FtlW3cProcessor;
import org.jeesl.interfaces.model.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.io.mail.template.JeeslTemplateTokenType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtlIoTemplateTokenFactory <L extends JeeslLang,D extends JeeslDescription,
										CATEGORY extends JeeslStatus<CATEGORY,L,D>,
										CHANNEL extends JeeslTemplateChannel<L,D,CHANNEL,?>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
										SCOPE extends JeeslStatus<SCOPE,L,D>,
										DEFINITION extends JeeslIoTemplateDefinition<D,CHANNEL,TEMPLATE>,
										TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
										TOKENTYPE extends JeeslTemplateTokenType<L,D,TOKENTYPE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(FtlIoTemplateTokenFactory.class);
		
	public Map<String,Object> buildModel(TEMPLATE template)
	{
		return buildModel(template.getTokens());
	}
	
	public Map<String,Object> buildModel(List<TOKEN> tokens)
	{
		Map<String,Object> model = new HashMap<String,Object>();
		for(TOKEN token : tokens)
		{
			if(token.getType().getCode().equals(JeeslTemplateTokenType.Code.text.toString()))
			{
				model.put(token.getCode(), token.getExample());
			}
			else if(token.getType().getCode().equals(JeeslTemplateTokenType.Code.xml.toString()))
			{
				model.put(token.getCode(),FtlW3cProcessor.text2W3c(token.getExample()));
			}
		}
		return model;
	}
}