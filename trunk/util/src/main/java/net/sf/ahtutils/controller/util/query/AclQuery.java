package net.sf.ahtutils.controller.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.xml.access.Role;
import net.sf.ahtutils.xml.aht.Query;

public class AclQuery
{
	public static enum Key {ProjectRole}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			switch(key)
			{
				case ProjectRole: mQueries.put(key, createProjectRole());break;
			}
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Query createProjectRole()
	{
		Role r = new Role();
		r.setCode("");
		r.setName("");
    	
		Query q = new Query();
    	q.setRole(r);
    	return q;
	}
}
