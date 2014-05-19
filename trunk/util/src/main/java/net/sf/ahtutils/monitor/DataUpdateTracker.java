package net.sf.ahtutils.monitor;

import java.util.Date;

import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.factory.xml.sync.XmlExceptionFactory;
import net.sf.ahtutils.factory.xml.sync.XmlExceptionsFactory;
import net.sf.ahtutils.xml.status.Type;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.sync.Result;
import net.sf.exlp.util.DateUtil;

public class DataUpdateTracker
{
	public static enum Code {success,fail,partial}
	
	private DataUpdate update;
	
	public DataUpdateTracker()
	{
		this(false);
	}
	public DataUpdateTracker(boolean autoStart)
	{
		update = new DataUpdate();
		
		update.setResult(new Result());
		update.getResult().setSuccess(0);
		update.getResult().setFail(0);
		update.getResult().setTotal(0);
		
		if(autoStart){start();}
	}
	
	public void start()
	{
		update.setBegin(DateUtil.getXmlGc4D(new Date(), true));
	}
	
	public void stop()
	{
		update.setFinished(DateUtil.getXmlGc4D(new Date(), true));
		update.getResult().setTotal(update.getResult().getSuccess()+update.getResult().getFail());
	}
	
	public void success()
	{
		update.getResult().setSuccess(update.getResult().getSuccess()+1);
	}
	
	public void fail(Throwable t, boolean printStackTrace)
	{
		if(printStackTrace){t.printStackTrace();}
		update.getResult().setFail(update.getResult().getFail()+1);
		if(!update.isSetExceptions()){update.setExceptions(XmlExceptionsFactory.build());}
		update.getExceptions().getException().add(XmlExceptionFactory.build(t));
	}
	
	public void add(DataUpdate dataUpdate)
	{
		update.getResult().setFail(update.getResult().getFail()+dataUpdate.getResult().getFail());
		update.getResult().setSuccess(update.getResult().getSuccess()+dataUpdate.getResult().getSuccess());
	}
	
	public void setType(Type type)
	{
		update.setType(type);
	}
	
	public DataUpdate getUpdate() {return update;}
	
	public DataUpdate toDataUpdate()
	{
		if(!update.isSetFinished()){stop();}
		
		if(update.getResult().getSuccess()==update.getResult().getTotal()){update.getResult().setStatus(XmlStatusFactory.create(Code.success.toString()));}
		else if(update.getResult().getFail()==update.getResult().getTotal()){update.getResult().setStatus(XmlStatusFactory.create(Code.fail.toString()));}
		else if(update.getResult().getFail()!=0){update.getResult().setStatus(XmlStatusFactory.create(Code.partial.toString()));}
		
		return update;
	}
}
