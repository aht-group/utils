package org.jeesl.controller.handler.system.io;

import java.io.OutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.factory.builder.io.IoLogFactoryBuilder;
import org.jeesl.factory.ejb.io.log.EjbIoLogMilestoneFactory;
import org.jeesl.interfaces.model.io.logging.JeeslIoLog;
import org.jeesl.interfaces.model.io.logging.JeeslIoLogMilestone;
import org.jeesl.interfaces.model.io.logging.JeeslIoLogRetention;
import org.jeesl.interfaces.model.io.logging.JeeslIoLogStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.openfuxml.factory.xml.table.XmlTableFactory;
import org.openfuxml.renderer.text.OfxTextSilentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslLogger<L extends JeeslLang, D extends JeeslDescription,
							LOG extends JeeslIoLog<L,D,STATUS,RETENTION,USER>,
							STATUS extends JeeslIoLogStatus<L,D,STATUS,?>,
							RETENTION extends JeeslIoLogRetention<L,D,RETENTION,?>,
							MILESTONE extends JeeslIoLogMilestone<LOG>,
							USER extends JeeslSimpleUser>
				implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(JeeslLogger.class);
	
	private final IoLogFactoryBuilder<L,D,LOG,MILESTONE> fbLog;
	private final EjbIoLogMilestoneFactory<LOG,MILESTONE> efMilestone;
	
	private Instant timeStart;
	private Instant timeMilestone;
	
	private final List<MILESTONE> milestones;
	private final Map<String,Integer> mapLoopCount;
	private final Map<String,Integer> mapLoopElements;
	
	private final Class<?> c;
	private LOG log;
	
	public JeeslLogger(IoLogFactoryBuilder<L,D,LOG,MILESTONE> fbLog,
					   Class<?> c)
	{
		this.fbLog=fbLog;
		this.c=c;
		efMilestone = fbLog.ejbMilestone();
		
		mapLoopCount = new HashMap<>();
		mapLoopElements = new HashMap<>();
		milestones = new ArrayList<>();
	}
	
	private void reset()
	{
		milestones.clear();
		mapLoopCount.clear();
		mapLoopElements.clear();
	}
	
	public String start(String log, USER user)
	{
		reset();
		timeStart = Instant.now();
		timeMilestone = Instant.now();
		StringBuilder sb = new StringBuilder();
		sb.append("Starting ");
		sb.append(c.getSimpleName());
		if(user!=null) {sb.append(" by ").append(user.getEmail());}
		return sb.toString();
	}
	
	public String milestone(String milestone) {return milestone(milestone,null,null);}
	public String milestone(String milestone, String message){return milestone(milestone,message,null);}
	public String milestone(String milestone, Integer elements) {return milestone(milestone,null,elements);}
	public String milestone(String milestone, String message, Integer elements)
	{
		Instant timeNow = Instant.now();
		MILESTONE ejb = efMilestone.build(log);
		ejb.setRecord(Date.from(timeNow));
		ejb.setMilliTotal(ChronoUnit.MILLIS.between(timeStart,timeNow));
		ejb.setMilliStep(ChronoUnit.MILLIS.between(timeMilestone,timeNow));
		
		if(elements!=null && elements.intValue()!=0) {ejb.setMilliRelative(ejb.getMilliStep()/elements);}
		else {ejb.setMilliRelative(ejb.getMilliStep());}
		
		ejb.setName(milestone);
		ejb.setMessage(message);
		ejb.setElements(elements);
		
		milestones.add(ejb);
	
		StringBuilder sb = new StringBuilder();
		sb.append("Milestone ");
		sb.append(milestone);
		if(message!=null) {sb.append(": ").append(message);}
		
		timeMilestone = timeNow;
		return sb.toString();
	}
	
	public String loopStart(String loop)
	{
		return "";
	}
	public String loopEnd(String loop, Integer elements)
	{
		if(!mapLoopCount.containsKey(loop)) {mapLoopCount.put(loop,0);}
		if(elements!=null && !mapLoopElements.containsKey(loop)) {mapLoopElements.put(loop,0);}
		
		mapLoopCount.put(loop,1+mapLoopCount.get(loop));
		if(elements!=null) {mapLoopElements.put(loop,elements+mapLoopElements.get(loop));}
		
		return "";
	}
	
	public void ofxMilestones(OutputStream os)
	{
		List<String> header = new ArrayList<>();
		header.add("Time");
		
		header.add("Total");
		header.add("Step");
		header.add("Elements");
		header.add("Relative");
		
		header.add("Milestone");
		header.add("Message");
		
		List<Object[]> data = new ArrayList<>();
		
		for(MILESTONE ejb : milestones)
		{
			String[] cell = new String[7];
			cell[0] = ejb.getRecord().toString();
			
			cell[1] = Long.valueOf(ejb.getMilliTotal()).toString();
			cell[2] = Long.valueOf(ejb.getMilliStep()).toString();
			if(ejb.getElements()!=null) {cell[3] = ejb.getElements().toString();}else {cell[3]="";}
			cell[4] = Long.valueOf(ejb.getMilliRelative()).toString();
			
			cell[5] = ejb.getName();
			cell[6] = ejb.getMessage();
			data.add(cell);
		}
		
		OfxTextSilentRenderer.table(XmlTableFactory.build(c.getSimpleName(),header,data),os);
	}
	
	public void ofxLoops(OutputStream os)
	{
		List<String> header = new ArrayList<>();
		header.add("Count");
		header.add("Elements");
		header.add("Loop");
		
		List<Object[]> data = new ArrayList<>();
		
		for(String loop : mapLoopCount.keySet())
		{
			String[] cell = new String[3];
			
			cell[0] = mapLoopCount.get(loop).toString();
			if(mapLoopElements.containsKey(loop)) {cell[1] = mapLoopElements.get(loop).toString();} else {cell[1] ="-";}
			cell[2] = loop;
			
			data.add(cell);
		}
		
		OfxTextSilentRenderer.table(XmlTableFactory.build(c.getSimpleName(),header,data),os);
	}
}