package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.jsf.filter.UtilsStatusFilter;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractIconBean <L extends UtilsLang, D extends UtilsDescription, S extends UtilsStatus<L,D>>	 implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractIconBean.class);
	private static final long serialVersionUID = 1L;
	
	private String imagePath;
	
	private Map<String,Map<Long,String>> mapImages;
	private Map<String,Map<Long,String>> mapImagesAlt;
	protected Map<String,String> mapStatic;
	
	//******* Methods *******************************

    public void initPath(String imagePath)
    {
		this.imagePath=imagePath;
		mapImages = new Hashtable<String,Map<Long,String>>();
		mapImagesAlt = new Hashtable<String,Map<Long,String>>(); 
		mapStatic = new Hashtable<String,String>();
    }

    public String urlFilter(Integer size, UtilsStatusFilter<L,D,S> filter)
    {
//    	logger.info("URL for "+filter.getValue().getCode()+" active="+filter.isActive());
    	if(filter.isActive()){return url(size,filter.getValue());}
    	else
    	{
    		String key = filter.getValue().getClass().getSimpleName();
        	if(!mapImagesAlt.containsKey(key)){generate(mapImagesAlt,size, filter.getValue().getId(), filter.getValue().getImageAlt(), key);}
        	else
        	{
        		if(!mapImagesAlt.get(key).containsKey(filter.getValue().getId())){generate(mapImagesAlt,size, filter.getValue().getId(), filter.getValue().getImageAlt(), key);}
        	}
        	
    		return mapImagesAlt.get(key).get(filter.getValue().getId());
    	}
    }
    
	public String url(Integer size, EjbWithImage image)
	{
    	String key = image.getClass().getSimpleName();
    	if(!mapImages.containsKey(key)){generate(mapImages, size, image.getId(), image.getImage(), key);}
    	else
    	{
    		if(!mapImages.get(key).containsKey(image.getId())){generate(mapImages, size, image.getId(), image.getImage(), key);}
    	}
    	
		return mapImages.get(key).get(image.getId());
	}
    
    private void generate(Map<String,Map<Long,String>> map, int size, long id, String image, String key)
    {
    	if(!map.containsKey(key))
    	{
    		map.put(key, new Hashtable<Long,String>());
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append("/").append(imagePath);
    	sb.append("/").append(size);
    	sb.append("/");
    	if(image!=null){sb.append(image);}
    	else{sb.append("noImage.png");}
    	map.get(key).put(id, sb.toString());
    }
    
	public String icon(int size,  String key)
	{
		StringBuffer sb = new StringBuffer();
    	sb.append("/").append(imagePath);
    	sb.append("/").append(size);
    	sb.append("/").append(mapStatic.get(key));
		return sb.toString();
	}
}
