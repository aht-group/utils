package org.jeesl.factory.txt.system.io.mail.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtIoTemplateTokenFactory <L extends JeeslLang,D extends JeeslDescription,
										CATEGORY extends JeeslStatus<CATEGORY,L,D>,
										CHANNEL extends JeeslTemplateChannel<L,D,CHANNEL,?>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
										SCOPE extends JeeslStatus<SCOPE,L,D>,
										DEFINITION extends JeeslIoTemplateDefinition<D,CHANNEL,TEMPLATE>,
										TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
										TOKENTYPE extends JeeslStatus<TOKENTYPE,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtIoTemplateTokenFactory.class);
		
	public Map<String,Object> buildModel(TEMPLATE template)
	{
		return buildModel(template.getTokens());
	}
	
	public Map<String,Object> buildModel(List<TOKEN> tokens)
	{
		Map<String,Object> model = new HashMap<String,Object>();
		for(TOKEN token : tokens)
		{
			model.put(token.getCode(), token.getExample());
		}
		return model;
	}
}