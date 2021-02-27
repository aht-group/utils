package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.hd.EjbHdEventFactory;
import org.jeesl.factory.ejb.module.hd.EjbHdFaqFactory;
import org.jeesl.factory.ejb.module.hd.EjbHdFgaFactory;
import org.jeesl.factory.ejb.module.hd.EjbHdMessageFactory;
import org.jeesl.factory.ejb.module.hd.EjbHdTicketFactory;
import org.jeesl.factory.ftl.module.hd.FtlHdTicketFactory;
import org.jeesl.interfaces.model.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFaq;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdFga;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdLevel;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdMessage;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdPriority;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdScope;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LfFactoryBuilder<L extends JeeslLang,D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
							R extends JeeslTenantRealm<L,D,R,?>,
							TTG extends JeeslLfTargetTimeGroup<TTI>,
							TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
							TTE extends JeeslLfTargetTimeElement<TTG>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LfFactoryBuilder.class);
	


	public LfFactoryBuilder(final Class<L> cL,final Class<D> cD)
	{       
		super(cL,cD);
		
	}


}