package net.sf.ahtutils.r.server;

public class RServer {
	/*
	 RConnection connection;
	//RConnection c = new RConnection("192.168.1.20", 6311);
	public RServer(String serverIp) throws RserveException {
		connection = new RConnection(serverIp, 6311);
	}

	public void eval(String eval) throws RserveException {
		connection.eval(eval);
	}

	public void assign(String var, String value) throws RserveException
	{
		connection.eval(var + "<- " + value);
	}

	public void assign(String var, Integer value) throws RserveException
	{
		assign(var,String.valueOf(value));
	}

	public void assign(String var, Double value) throws RserveException
	{
		assign(var,String.valueOf(value));
	}

	public void assign(String var, Boolean value) throws RserveException
	{
		assign(var,String.valueOf(value));
	}
	public void assignVector(String var, String[] values) throws RserveException
	{
		this.assignVector(var,Arrays.toString(values));
	}

	public void assignVector(String var, Integer[] values) throws RserveException
	{
		this.assignVector(var,Arrays.toString(values));
	}

	public void assignVector(String var, Double[] values) throws RserveException
	{
		this.assignVector(var,Arrays.toString(values));
	}

	public String[] readVectorVarString(String var) throws RserveException, REXPMismatchException {
		org.rosuda.REngine.REXP rExp = connection.eval(var);
	    return rExp.asStrings();
	}

	public int[] readVectorVarInteger(String var) throws RserveException, REXPMismatchException {
		org.rosuda.REngine.REXP rExp = connection.eval(var);
	    return rExp.asIntegers();
	}

	public double[] readVectorVarDouble(String var) throws RserveException, REXPMismatchException {
		org.rosuda.REngine.REXP rExp = connection.eval(var);
	    return rExp.asDoubles();
	}

	public String readVarString(String var) throws RserveException, REXPMismatchException {
		org.rosuda.REngine.REXP rExp = connection.eval(var);
	    return rExp.asString();
	}

	public Integer readVarInteger(String var) throws RserveException, REXPMismatchException {
		org.rosuda.REngine.REXP rExp = connection.eval(var);
	    return rExp.asInteger();
	}

	public Double readVarDouble(String var) throws RserveException, REXPMismatchException {
		org.rosuda.REngine.REXP rExp = connection.eval(var);
	    return rExp.asDouble();
	}


	private void assignVector(String var, String vectorValue) throws RserveException
	{
		connection.eval(var + "<- (" + vectorValue.substring(1, vectorValue.length()-1) +")");
	}

*/

}
