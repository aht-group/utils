package net.sf.ahtutils.msgbundle;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TranslationMap
{
	static Log logger = LogFactory.getLog(TranslationMap.class);
	
	private Map<String,Map<String,String>> tMap;
	
	public TranslationMap()
	{
		tMap = new Hashtable<String,Map<String,String>>();
	}

	public String translate(String langKey, String key)
	{
		String result;
		try {result = translateWithException(langKey, key);}
		catch (AhtUtilsNotFoundException e)
		{
			result = "??? "+e.getMessage()+" ???";
		}

		return result;
	}
	
	public String translateWithException(String langKey, String key) throws AhtUtilsNotFoundException
	{
		if(tMap.containsKey(langKey))
		{
			Map<String,String> mapLang = tMap.get(langKey);
			if(mapLang.containsKey(key))
			{
				return mapLang.get(key);
			}
			else
			{
				throw new AhtUtilsNotFoundException("No Translation for lang="+langKey+" and key="+key);
			}
		}
		else
		{
			throw new AhtUtilsNotFoundException("No Translations for lang="+langKey);
		}
	}
	
	protected void add(String[] translation)
	{
		add(translation[0], translation[1], translation[2]);
	}
	public void add(String langKey, String translationKey, String translationValue)
	{
		logger.trace("Add "+langKey+" "+translationKey+" "+translationValue);
		getLangMap(langKey).put(translationKey, translationValue);
	}
	
	private Map<String,String> getLangMap(String langKey)
	{
		if(!tMap.containsKey(langKey))
		{
			Map<String,String> m = new Hashtable<String,String>();
			tMap.put(langKey, m);
		}
		return tMap.get(langKey);
	}
	
	public int sizeLanguages()
	{
		return tMap.size();
	}
	
	public int sizeKeys(String langKey) throws AhtUtilsNotFoundException
	{
		if(tMap.containsKey(langKey))
		{
			return getLangMap(langKey).size();
		}
		else
		{
			throw new AhtUtilsNotFoundException("No Translations for lang="+langKey);
		}
	}
	
	public List<String> getLangKeys()
	{
		List<String> result = new ArrayList<String>();
		for(String s : tMap.keySet())
		{
			result.add(s);
		}
		return result;
	}
	
	public List<String> getTranslationKeys(String langKey) throws AhtUtilsNotFoundException
	{
		if(tMap.containsKey(langKey))
		{
			List<String> result = new ArrayList<String>();
			for(String s : tMap.get(langKey).keySet())
			{
				result.add(s);
			}
			return result;
		}
		else
		{
			throw new AhtUtilsNotFoundException("No Translations for lang="+langKey);
		}
	}
}