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
	
	private Map<Long,String> mapImages;
	protected Map<String,String> mapStatic;
	
	//******* Methods *******************************

    public void initPath(String imagePath)
    {
		this.imagePath=imagePath;
		mapImages = new Hashtable<Long,String>();
		mapStatic = new Hashtable<String,String>();
    }

	public String url(int size,  EjbWithImage image)
	{
    	if(image==null){return "";}
    	if(logger.isTraceEnabled()){logger.trace("size:"+size+" image:"+image);}
    	if(!mapImages.containsKey(image.getId())){generate(size, image);}
		return mapImages.get(image.getId());
	}
    
    private void generate(int size,  EjbWithImage image)
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("/").append(imagePath);
    	sb.append("/").append(size);
    	sb.append("/");
    	if(image.getImage()!=null){sb.append(image.getImage());}
    	else{sb.append("noImage.png");}
    	mapImages.put(image.getId(), sb.toString());
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
