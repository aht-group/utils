package org.jeesl.factory.ejb.module.lf;

import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLfLogFrameFactory<LF extends JeeslLfLogframe<?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLfLogFrameFactory.class);

	private final Class<LF> cLogFrame;

	public EjbLfLogFrameFactory(final Class<LF> cLogFrame)
	{
		this.cLogFrame =  cLogFrame;
	}

	public LF build()
	{
		LF ejb = null;
		try
		{
			ejb = cLogFrame.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}