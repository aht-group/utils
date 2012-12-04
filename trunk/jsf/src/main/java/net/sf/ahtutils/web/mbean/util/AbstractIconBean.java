package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.with.EjbWithImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractIconBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractIconBean.class);
	private static final long serialVersionUID = 1L;
	
	private String imagePath;
	
	private Map<String,Map<Long,String>> mapImages;
	protected Map<String,String> mapStatic;
	
	//******* Methods *******************************

    public void initPath(String imagePath)
    {
		this.imagePath=imagePath;
		mapImages = new Hashtable<String,Map<Long,String>>();
		mapStatic = new Hashtable<String,String>();
    }

	public String url(Integer size,  EjbWithImage image)
	{
    	if(image==null){return "";}
    	String key = image.getClass().getSimpleName();
    	if(logger.isTraceEnabled()){logger.trace("size:"+size+" image:"+image);}
    	if(!mapImages.containsKey(key))
    	{
    		generate(size, image, key);
    	}
    	else
    	{
    		if(!mapImages.get(key).containsKey(image.getId())){generate(size, image, key);}
    	}
    	
		return mapImages.get(key).get(image.getId());
	}
    
    private void generate(int size,  EjbWithImage image, String key)
    {
    	if(!mapImages.containsKey(key))
    	{
    		mapImages.put(key, new Hashtable<Long,String>());
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append("/").append(imagePath);
    	sb.append("/").append(size);
    	sb.append("/");
    	if(image.getImage()!=null){sb.append(image.getImage());}
    	else{sb.append("noImage.png");}
    	mapImages.get(key).put(image.getId(), sb.toString());
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
