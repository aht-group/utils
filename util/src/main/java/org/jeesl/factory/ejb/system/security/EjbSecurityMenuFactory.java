package org.jeesl.factory.ejb.system.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.util.comparator.ejb.PositionComparator;

public class EjbSecurityMenuFactory <L extends UtilsLang, D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 M extends JeeslSecurityMenu<L,D,C,R,V,U,A,AT,M,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
						extends AbstractEjbSecurityFactory<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityMenuFactory.class);
	
	private final Class<M> cM;
	private final Comparator<M> comparator;
    
    public EjbSecurityMenuFactory(final Class<L> cLang,final Class<D> cDescription, final Class<M> cM)
    {
    		super(cLang,cDescription);
        this.cM = cM;
        comparator = new PositionComparator<M>();
    } 
    
    public M create(V view)
    {
		M ejb = null;
    	
	    	try
	    	{
			ejb = cM.newInstance();
			ejb.setView(view);
		}
	    	catch (InstantiationException e) {e.printStackTrace();}
	    	catch (IllegalAccessException e) {e.printStackTrace();}
	    	
	    	return ejb;
    }
    
    public Map<V,M> toMapView(List<M> list)
    {
    		Map<V,M> map = new HashMap<V,M>();
    		for(M m : list) {map.put(m.getView(),m);}
    		return map;
    }
    
    public List<M> toListRoot(List<M> list)
    {
    		List<M> result = new ArrayList<M>();
    		for(M m : list) {if(m.getParent()==null) {result.add(m);}}
    		Collections.sort(result,comparator);
    		return result;
    }
    
    public Map<M,List<M>> toMapParent(List<M> list)
	{
    		Map<M,List<M>> map = new HashMap<M,List<M>>();
    		for(M m : list)
    		{
    			if(m.getParent()!=null)
    			{
    				if(!map.containsKey(m.getParent())) {map.put(m.getParent(), new ArrayList<M>());}
    				map.get(m.getParent()).add(m);
    			}
    		}
    		for(M m : map.keySet())
    		{
    			Collections.sort(map.get(m),comparator);
    		}
    		return map;
    }
}