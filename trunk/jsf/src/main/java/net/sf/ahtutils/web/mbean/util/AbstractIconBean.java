package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.controller.interfaces.UtilsFacade;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractIconBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractIconBean.class);
	private static final long serialVersionUID = 1L;
	
	private UtilsFacade fUtils;
	
	private Map<Long,String> mapImages;
	
	//******* Methods *******************************

    public void initFacade(UtilsFacade fUtils)
    {
		this.fUtils=fUtils;
		mapImages = new Hashtable<Long,String>();
    }

    @SuppressWarnings("rawtypes")
	public String url(int size,  UtilsStatus status)
	{
    	if(!mapImages.containsKey(status.getId())){generate(size, status);}
		return mapImages.get(status.getId());
	}
    
    @SuppressWarnings("rawtypes")
    private void generate(int size,  UtilsStatus status)
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("/images/").append(size).append("/");
    	if(status.getImage()!=null){sb.append(status.getImage());}
    	else{sb.append("noImage.png");}
    	mapImages.put(status.getId(), sb.toString());
    }
}
