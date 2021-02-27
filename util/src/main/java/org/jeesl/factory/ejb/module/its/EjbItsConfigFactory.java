package org.jeesl.factory.ejb.module.its;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.its.config.JeeslItsConfig;
import org.jeesl.interfaces.model.module.its.config.JeeslItsConfigOption;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbItsConfigFactory<R extends JeeslTenantRealm<?,?,R,?>,
								C extends JeeslItsConfig<?,?,R,O>,
								O extends JeeslItsConfigOption<?,?,O,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbItsConfigFactory.class);
	
	private final Class<C> cConfig;
	
    public EjbItsConfigFactory(final Class<C> cConfig)
    {
        this.cConfig = cConfig;
    }
	
	public <RREF extends EjbWithId> C build(R realm, RREF rref, O option)
	{
		try
		{
			C ejb = cConfig.newInstance();
			ejb.setRealm(realm);
			ejb.setRref(rref.getId());
			ejb.setOption(option);
			ejb.setVisible(false);
			ejb.setOverrideLabel(false);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public Map<O,C> toMapOption(List<C> list)
	{
		Map<O,C> map = new HashMap<>();
		for(C c : list) {map.put(c.getOption(), c);}
		return map;
	}
}