package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

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

    @SuppressWarnings("rawtypes")
	public String url(int size,  UtilsStatus status)
	{
    	if(logger.isTraceEnabled()){logger.trace("size:"+size+" status:"+status);}
    	if(!mapImages.containsKey(status.getId())){generate(size, status);}
		return mapImages.get(status.getId());
	}
    
    @SuppressWarnings("rawtypes")
    private void generate(int size,  UtilsStatus status)
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("/").append(imagePath);
    	sb.append("/").append(size);
    	sb.append("/");
    	if(status.getImage()!=null){sb.append(status.getImage());}
    	else{sb.append("noImage.png");}
    	mapImages.put(status.getId(), sb.toString());
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
