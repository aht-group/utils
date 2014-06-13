package net.sf.ahtutils.db;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.util.EjbPropertyFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.utils.Property;
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

	public DataUpdate iuProperties(Utils utils)
	{
		EjbPropertyFactory<P> f = EjbPropertyFactory.factory(cProperty);
		
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cProperty.getName(),UtilsProperty.class.getSimpleName()+"-DB Import"));
		
//		AhtDbEjbUpdater<P> updateLayer = AhtDbEjbUpdater.createFactory(cProperty);		
//		updateLayer.dbEjbs(fUtils.all(cLayer));

		
		for(Property property : utils.getProperty())
		{
//			updateLayer.actualAdd(layer.getCode());
			
			P ejb;			
			try
			{
				fUtils.valueStringForKey(cProperty, property.getKey(),null);
			}
			catch (UtilsNotFoundException e1)
			{
				ejb = f.build(property);
				dut.success();
				try
				{
					ejb = (P)fUtils.persist(ejb);
				}
				catch (UtilsContraintViolationException e) {dut.fail(e, true);}
			}
		}

		return dut.toDataUpdate();
	}
}