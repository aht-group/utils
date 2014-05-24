package net.sf.ahtutils.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.security.Staff;

public class QaQuery
{
	public static enum Key {staff}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case staff: q.setStaff(staff());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Staff staff()
	{
		Staff xml = new Staff();
		xml.setId(0);
		xml.setRole(SecurityQuery.role());
		xml.setUser(SecurityQuery.user());
		return xml;
	}
}
