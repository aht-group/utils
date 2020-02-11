package org.jeesl.util.comparator.ejb.system.io.template;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoTemplateTokenComparator<L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								CHANNEL extends JeeslTemplateChannel<L,D,CHANNEL,?>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends JeeslStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<D,CHANNEL,TEMPLATE>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
								TOKENTYPE extends JeeslStatus<TOKENTYPE,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(IoTemplateTokenComparator.class);

    public enum Type {position};

    public IoTemplateTokenComparator()
    {
    	
    }
    
    public Comparator<TOKEN> factory(Type type)
    {
        Comparator<TOKEN> c = null;
        IoTemplateTokenComparator<L,D,CATEGORY,CHANNEL,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE> factory = new IoTemplateTokenComparator<>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<TOKEN>
    {
        public int compare(TOKEN a, TOKEN b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getPosition(), b.getPosition());
			  ctb.append(a.getCode(), b.getCode());
			  return ctb.toComparison();
        }
    }
}