package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslIoTemplateFacade <L extends JeeslLang,D extends JeeslDescription,
										CATEGORY extends JeeslStatus<L,D,CATEGORY>,
										CHANNEL extends JeeslTemplateChannel<L,D,CHANNEL,?>,
										TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
										SCOPE extends JeeslStatus<L,D,SCOPE>,
										DEFINITION extends JeeslIoTemplateDefinition<D,CHANNEL,TEMPLATE>,
										TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
										TOKENTYPE extends JeeslStatus<L,D,TOKENTYPE>>
			extends JeeslFacade
{	
	TEMPLATE load(TEMPLATE template);
	
	DEFINITION fDefinition(CHANNEL type, String code) throws JeeslNotFoundException;
	
	<E extends Enum<E>> List<TEMPLATE> loadTemplates(E category);
	List<TEMPLATE> fTemplates(List<CATEGORY> categories, boolean showInvisibleEntities);
	List<TEMPLATE> fTemplates(CATEGORY category, SCOPE scope);
}