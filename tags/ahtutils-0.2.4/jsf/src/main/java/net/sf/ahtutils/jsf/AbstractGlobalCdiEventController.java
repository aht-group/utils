package net.sf.ahtutils.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.ahtutils.model.event.GlobalCdiEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractGlobalCdiEventController
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGlobalCdiEventController.class);

	private final List<HttpSession> httpSessions = new ArrayList<HttpSession>();

	public void addSession(final HttpSession httpSession)
	{
		logger.trace("addSession");
		httpSessions.add(httpSession);
	}

	public void removeSession(final HttpSession httpSession)
	{
		logger.trace("removeSession");
		httpSessions.remove(httpSession);
	}

	public void fireEvent(final GlobalCdiEvent eventObject)
	{
		if(logger.isTraceEnabled()){logger.trace("fire: "+eventObject);}
		for (final HttpSession session : httpSessions)
		{
			fireEventIntoSession(session, eventObject);
		}
	}

	private void fireEventIntoSession(final HttpSession session, final GlobalCdiEvent eventObject)
	{
		if(logger.isTraceEnabled()){logger.trace("fireEventIntoSession: "+eventObject);}
		try
		{
			final List<GlobalCdiEvent> globalEvents = getGlobalEvents(session);
			globalEvents.add(eventObject);
		}
		catch (final Exception e)
		{
			throw new IllegalStateException("fireEvent", e);
		}
	}

	@SuppressWarnings("unchecked")
	private synchronized List<GlobalCdiEvent> getGlobalEvents(final HttpSession session)
	{
		List<GlobalCdiEvent> globalEvents = (List<GlobalCdiEvent>) session.getAttribute(GlobalCdiEvent.httpSessionAttributeName);

		if (globalEvents == null)
		{
			globalEvents = new ArrayList<GlobalCdiEvent>();
			session.setAttribute(GlobalCdiEvent.httpSessionAttributeName, globalEvents);
		}
		return globalEvents;
	}
}