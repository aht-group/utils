package net.sf.ahtutils.r.engine;

import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RLoggingChannel  implements RMainLoopCallbacks{
	
	final static Logger logger = LoggerFactory.getLogger(RLoggingChannel.class);

	@Override
	public void rBusy(Rengine arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String rChooseFile(Rengine arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rFlushConsole(Rengine arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rLoadHistory(Rengine arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String rReadConsole(Rengine arg0, String arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rSaveHistory(Rengine arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rShowMessage(Rengine arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rWriteConsole(Rengine arg0, String arg1, int arg2) {
		logger.debug(arg1);
		
	}

}
