package org.jeesl.controller.handler.system;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranslationHandler<L extends JeeslLang,D extends JeeslDescription,
								RE extends JeeslRevisionEntity<L,D,?,?,RA,?>,
								RA extends JeeslRevisionAttribute<L,D,RE,?,?>>
	implements Serializable,JeeslLabelBean<RE>
{
	final static Logger logger = LoggerFactory.getLogger(TranslationHandler.class);
	private static final long serialVersionUID = 1L;

	private final Class<RE> cRE;

	private final JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?,?> fRevision;

	private final Map<String,Map<String,L>> entities; public Map<String,Map<String,L>> getEntities() {return entities;}
	private final Map<String,Map<String,Map<String,L>>> labels; public Map<String, Map<String, Map<String,L>>> getLabels() {return labels;}
	private final Map<String,Map<String,Map<String,D>>> descriptions;public Map<String, Map<String, Map<String,D>>> getDescriptions() {return descriptions;}

	public final Map<String,RE> mapEntities; public Map<String,RE> getMapEntities() {return mapEntities;}

	public TranslationHandler(JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?,?> fRevision, final Class<RE> cRE)
	{
		this.cRE = cRE;
		this.fRevision=fRevision;

        entities = new HashMap<String,Map<String,L>>();
        labels = new HashMap<String,Map<String,Map<String,L>>>();
        descriptions = new HashMap<String,Map<String,Map<String,D>>>();

        mapEntities = new HashMap<String,RE>();

        loadAll();
	}

	private void loadAll()
	{
		List<RE> list = fRevision.all(cRE);
        logger.info("building "+list.size());

		for(RE re : list)
		{
			load(re);
		}
	}

	@Override public void reload(RE re)
	{
		try
		{
			Class<?> c = Class.forName(re.getCode());
			if(mapEntities.containsKey(c.getSimpleName())) {mapEntities.remove(c.getSimpleName());}
			if(entities.containsKey(c.getSimpleName())) {entities.remove(c.getSimpleName());}
			if(labels.containsKey(c.getSimpleName())) {labels.remove(c.getSimpleName());}
			if(descriptions.containsKey(c.getSimpleName())) {descriptions.remove(c.getSimpleName());}
			load(re);
		}
		catch (ClassNotFoundException e) {logger.warn("CNFE: "+re.getCode());}
	}

	public void load(RE re)
	{
		try
		{
			Class<?> c = Class.forName(re.getCode());

			re = fRevision.load(cRE, re);
			if(entities.containsKey(c.getSimpleName())){logger.warn("Duplicate classs in Revisions "+re.getCode());}

			entities.put(c.getSimpleName(), re.getName());


			labels.put(c.getSimpleName(), new Hashtable<String,Map<String,L>>());
			descriptions.put(c.getSimpleName(), new Hashtable<String,Map<String,D>>());

			//Prepare a list of attributes from "Attributes" or "Labels" Enum
			List<Class> classes = new ArrayList<>();
			List<Field> fields = new ArrayList<>();
			try {
				classes.add(c);
				Class[] interfaces = c.getInterfaces();
				for (Class i : interfaces) {
					classes.add(i);
				}
				for (Class cls : classes) {
					for (Class enumClass : cls.getDeclaredClasses()) {
						if (enumClass.getName().contains("Attributes") || enumClass.getName().contains("Labels")) {
							Field[] allFields = enumClass.getFields();
							for (Field f : allFields) {
								fields.add(f);
								//System.out.println(f.getName());
							}
						}
					}

				}
			} catch (SecurityException e) {
				logger.warn("SecurityException: "+e.getMessage());
			}

			//Write log waring when enum attributes are not saved revision attributes in database
			for (Field f : fields) {
				boolean foundField = false;
				for(RA attribute : re.getAttributes())
				{
					if(attribute.getCode().equals(f.getName()))
					{
						foundField= true;
					}
				}
				if(!foundField) {
					logger.warn("Revision Attribute Not Saved for : Class ->"+c.getName() + " -> Field: " + f.getName());
				}
			}
			//mapEntities.put(c.getSimpleName(),re);
			for(RA attribute : re.getAttributes())
			{
				if(attribute.getCode()!=null && attribute.getCode().trim().length()>0)
				{
					labels.get(c.getSimpleName()).put(attribute.getCode(), attribute.getName());
					descriptions.get(c.getSimpleName()).put(attribute.getCode(), attribute.getDescription());
				}

			}
		}
		catch (ClassNotFoundException e) {logger.warn("CNFE: "+re.getCode());}
	}

	@Override public List<RE> allEntities() {return new ArrayList<RE>(mapEntities.values());}
}