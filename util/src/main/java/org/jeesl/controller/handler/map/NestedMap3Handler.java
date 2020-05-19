package org.jeesl.controller.handler.map;

import java.util.HashMap;
import java.util.Map;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NestedMap3Handler <L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, VALUE extends EjbWithId>
{
    final static Logger logger = LoggerFactory.getLogger(NestedMap3Handler.class);

    

	public NestedMap3Handler()
    {
		
    }
	
	public void clear()
	{
		
		
	}
	
	public void put(Map<L1,Map<L2,Map<L3,VALUE>>> map, L1 l1, L2 l2, L3 l3, VALUE value)
	{
		if(!map.containsKey(l1)){map.put(l1,new HashMap<>());}
		if(!map.get(l1).containsKey(l2)) {map.get(l1).put(l2,new HashMap<>());}
		map.get(l1).get(l2).put(l3,value);
	}
	
	public int size(Map<L1,Map<L2,Map<L3,VALUE>>> map)
	{
		int size=0;
		for(Map<L2,Map<L3,VALUE>> m2 : map.values())
		{
			for(Map<L3,VALUE> m3 : m2.values())
			{
				size=size+m3.size();
			}
		}
		return size;
	}
	
//	public void putIfEmpty(L1 l1, L2 l2, L3 l3, VALUE value)
//	{
//		if(!this.containsKey(l1,l2,l3))
//		{
//			this.put(l1,l2,l3,value);
//		}
//	}
//	
	public boolean containsKey(Map<L1,Map<L2,Map<L3,VALUE>>> map, L1 l1, L2 l2, L3 l3)
    {
    	return (map.containsKey(l1) && map.get(l1).containsKey(l2) && map.get(l1).get(l2).containsKey(l3));
    }
	public VALUE get(Map<L1,Map<L2,Map<L3,VALUE>>> map, L1 l1, L2 l2, L3 l3)
    {
		if(!containsKey(map,l1,l2,l3)) {return null;}
    	return map.get(l1).get(l2).get(l3);
    }
//	
//	public List<VALUE> values(L1 l1)
//	{
//		List<VALUE> list = new ArrayList<VALUE>();
//		
//		if(m.containsKey(l1))
//		{
//			Nested2Map<L2,L3,VALUE> n2 = m.get(l1);
//			list.addAll(n2.values());
//		}
//		
//		return list;
//	}
}