package net.sf.ahtutils.db;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsPropertyDbUpdater <P extends UtilsProperty>
{
	final static Logger logger = LoggerFactory.getLogger(UtilsPropertyDbUpdater.class);
	
    private final Class<P> cProperty;
    
    private UtilsFacade fUtils;
    
    public UtilsPropertyDbUpdater(UtilsFacade fUtils, final Class<P> cProperty)
	{       
        this.fUtils=fUtils;
        this.cProperty = cProperty;
	}
	
	public static <P extends UtilsProperty> UtilsPropertyDbUpdater<P> factory(UtilsFacade fUtils, final Class<P> cProperty)
	{
		return new UtilsPropertyDbUpdater<P>(fUtils,cProperty);
	}

	public DataUpdate iuProperties(Utils utils) throws UtilsConfigurationException
	{
//		DataUpdateTracker dut = new DataUpdateTracker(true);
//		dut.setType(XmlTypeFactory.build(cProperty.getName(),UtilsProperty.class.getSimpleName()"-DB Import"));
		
//		AhtDbEjbUpdater<P> updateLayer = AhtDbEjbUpdater.createFactory(cProperty);		
//		updateLayer.dbEjbs(fUtils.all(cLayer));

		/*
		for(Layer layer : layers.getLayer())
		{
			updateLayer.actualAdd(layer.getCode());
			
			SERVICE service;			
			try
			{
				service = fUtils.fByCode(cService, layer.getService().getCode());
			}
			catch (UtilsNotFoundException e1) {throw new UtilsConfigurationException(e1.getMessage());}
			
			
			LAYER ejb;
			try
			{
				ejb = fUtils.fByCode(cLayer,layer.getCode());
				ejbLangFactory.rmLang(fUtils,ejb);
				ejbDescriptionFactory.rmDescription(fUtils,ejb);
			}
			catch (UtilsNotFoundException e)
			{
				try
				{
					ejb = ejbLayerFactory.build(layer.getCode(), service, langKeys);					
					ejb = (LAYER)fUtils.persist(ejb);
				}

				catch (UtilsContraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
				catch (UtilsIntegrityException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			}
			
			try
			{
				ejb.setName(ejbLangFactory.getLangMap(layer.getLangs()));
				ejb.setDescription(ejbDescriptionFactory.create(layer.getDescriptions()));
				
				ejb.setTemporalLayer(layer.isTemporal());
				ejb.setService(fUtils.fByCode(cService, layer.getService().getCode()));
				
				ejb=(LAYER)fUtils.update(ejb);
			}
			catch (UtilsContraintViolationException e) {logger.error("",e);}
			catch (InstantiationException e) {logger.error("",e);}
			catch (IllegalAccessException e) {logger.error("",e);}
			catch (UtilsIntegrityException e) {logger.error("",e);}
			catch (UtilsLockingException e) {logger.error("",e);}
			catch (UtilsNotFoundException e) {logger.error("",e);}
		}
		
		updateLayer.remove(fUtils);
		logger.trace("initUpdateUsecaseCategories finished");
		*/
		return new DataUpdate();
	}
}