package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslHdFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.HdFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEventType;
import org.jeesl.interfaces.model.module.hd.resolution.JeeslHdResolutionLevel;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketCategory;
import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicketStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.util.query.module.EjbHelpdeskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslHelpdeskFacadeBean<L extends JeeslLang,D extends JeeslDescription,
										R extends JeeslMcsRealm<L,D,R,?>,
										TICKET extends JeeslHdTicket<R,EVENT,M>,
										CAT extends JeeslHdTicketCategory<?,?,R,CAT,?>,
										STATUS extends JeeslHdTicketStatus<?,?,R,STATUS,?>,
										EVENT extends JeeslHdEvent<TICKET,CAT,STATUS,TYPE,LEVEL,USER>,
										TYPE extends JeeslHdEventType<L,D,TYPE,?>,
										LEVEL extends JeeslHdResolutionLevel<L,D,R,LEVEL,?>,
										M extends JeeslMarkup<MT>,
										MT extends JeeslIoCmsMarkupType<?,?,MT,?>,
										USER extends JeeslSimpleUser>
					extends JeeslFacadeBean
					implements JeeslHdFacade<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,M,MT,USER>
{	
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslHelpdeskFacadeBean.class);
	
	private final HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,M,MT,USER> fbHd;
	
	public JeeslHelpdeskFacadeBean(EntityManager em, final HdFactoryBuilder<L,D,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,M,MT,USER> fbHd)
	{
		super(em);
		this.fbHd=fbHd;
	}

	@Override public TICKET saveHdTicket(TICKET ticket, EVENT event) throws JeeslConstraintViolationException, JeeslLockingException
	{
		boolean unsaved = EjbIdFactory.isUnSaved(ticket);
		ticket = this.save(ticket);
		
		if(unsaved)
		{
			TYPE type = this.fByEnum(fbHd.getClassType(),JeeslHdEventType.Code.create);
			event.setType(type);
			event = this.save(event);
			ticket.setFirstEvent(event);
			ticket.setLastEvent(event);
			ticket = this.save(ticket);
		}
		
		return ticket;
	}

	@Override public <RREF extends EjbWithId> List<TICKET> fHdTickets(EjbHelpdeskQuery<R,RREF,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,USER> query)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TICKET> cQ = cB.createQuery(fbHd.getClassTicket());
		Root<TICKET> root = cQ.from(fbHd.getClassTicket());
		
//		Expression<Date> dStart = root.get(JeeslCalendarItem.Attributes.startDate.toString());
//		Expression<Date> dEnd   = root.get(JeeslCalendarItem.Attributes.endDate.toString());
//		
//		//After
//	    Predicate startAfterFrom = cB.greaterThanOrEqualTo(dStart, from);
//	    Predicate endAfterFrom = cB.greaterThanOrEqualTo(dEnd, from);
//	    Predicate endAfterTo = cB.greaterThanOrEqualTo(dEnd, to);
//	    
//	    //Before
//	    Predicate startBeforeTo = cB.lessThan(dStart, to);
//	    Predicate startBeforeFrom = cB.lessThan(dStart, from);
//	    Predicate endBeforeTo = cB.lessThan(dEnd, to);
//		
//		Predicate pOnlyStartAndStartInRange = cB.and(cB.isNull(dEnd),startAfterFrom,startBeforeTo);
//		Predicate pStartAndEndInRange = cB.and(cB.isNotNull(dEnd),startAfterFrom,endBeforeTo);
//		Predicate pStartOutsideEndInRange = cB.and(cB.isNotNull(dEnd),startBeforeFrom,endAfterFrom,endBeforeTo);
//		Predicate pStartInRangeEndOutside = cB.and(cB.isNotNull(dEnd),startAfterFrom,startBeforeTo,endAfterTo);
//		Predicate pStartBeforeRangeEndAfterRange = cB.and(cB.isNotNull(dEnd),startBeforeFrom,endAfterTo);
//		predicates.add(cB.or(pOnlyStartAndStartInRange,pStartAndEndInRange,pStartOutsideEndInRange,pStartInRangeEndOutside,pStartBeforeRangeEndAfterRange));
//		
//		Join<ITEM,CALENDAR> jCalendar = root.join(JeeslCalendarItem.Attributes.calendar.toString());
//		predicates.add(cB.isTrue(jCalendar.in(calendars)));
				    
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root);
//		cQ.orderBy(cB.asc(dStart));
		
		return em.createQuery(cQ).getResultList();
	}
}