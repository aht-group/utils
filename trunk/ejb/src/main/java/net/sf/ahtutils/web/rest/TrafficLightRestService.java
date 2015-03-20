package net.sf.ahtutils.web.rest;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.util.EjbTrafficLightFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.factory.xml.utils.XmlTrafficLightFactory;
import net.sf.ahtutils.factory.xml.utils.XmlTrafficLightsFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.util.UtilsTrafficLight;
import net.sf.ahtutils.interfaces.rest.util.traffic.UtilsTrafficLightRestExport;
import net.sf.ahtutils.interfaces.rest.util.traffic.UtilsTrafficLightRestImport;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.util.query.UtilsQuery;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.utils.TrafficLight;
import net.sf.ahtutils.xml.utils.TrafficLights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrafficLightRestService <L extends UtilsLang,D extends UtilsDescription,SCOPE extends UtilsStatus<SCOPE,L,D>,LIGHT extends UtilsTrafficLight<L,D,SCOPE>>
		implements UtilsTrafficLightRestExport,UtilsTrafficLightRestImport
{
	final static Logger logger = LoggerFactory.getLogger(TrafficLightRestService.class);
	
	private UtilsFacade fUtils;
	
	private final Class<SCOPE> cScope;
	private final Class<LIGHT> cLight;
	
	private XmlTrafficLightFactory<L,D,SCOPE,LIGHT> xfLight;
	private EjbTrafficLightFactory<L,D,SCOPE,LIGHT> efLight;
	
	private TrafficLightRestService(UtilsFacade fUtils,final Class<L> cLang,final Class<D> cDescription,final Class<SCOPE> cScope,final Class<LIGHT> cLight)
	{
		this.fUtils=fUtils;
		this.cScope=cScope;
		this.cLight=cLight;
		
		xfLight = new XmlTrafficLightFactory<L,D,SCOPE,LIGHT>(UtilsQuery.get(UtilsQuery.Key.exTrafficLight));
		efLight = EjbTrafficLightFactory.factory(cLang,cDescription,cLight);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,SCOPE extends UtilsStatus<SCOPE,L,D>,LIGHT extends UtilsTrafficLight<L,D,SCOPE>>
		TrafficLightRestService<L,D,SCOPE,LIGHT>
			factory(UtilsFacade fUtils,final Class<L> cL,final Class<D> cD,final Class<SCOPE> cScope,final Class<LIGHT> cLight)
	{
		return new TrafficLightRestService<L,D,SCOPE,LIGHT>(fUtils,cL,cD,cScope,cLight);
	}
	
	@Override
	public TrafficLights exportTrafficLights()
	{
		TrafficLights xml = XmlTrafficLightsFactory.build();
		for(LIGHT light : fUtils.all(cLight))
		{
			xml.getTrafficLight().add(xfLight.build(light));
		}
		return null;
	}

	@Override
	public DataUpdate importTrafficLights(TrafficLights lights)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cLight.getName(),"DB Import"));
		for(TrafficLight xLight : lights.getTrafficLight())
		{
			try
			{
				SCOPE scope = fUtils.fByCode(cScope,xLight.getScope().getCode());
				LIGHT eLight = efLight.build(scope,xLight);
				eLight = fUtils.persist(eLight);

				dut.success();
			}
			catch (UtilsNotFoundException e) {dut.fail(e,true);}
			catch (UtilsContraintViolationException e) {dut.fail(e,true);}
		}
		return dut.toDataUpdate();
	}
}