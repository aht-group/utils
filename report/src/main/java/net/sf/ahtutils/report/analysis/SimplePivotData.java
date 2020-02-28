package net.sf.ahtutils.report.analysis;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePivotData <T extends EjbWithId, P extends EjbWithId>
{
    final static Logger logger = LoggerFactory.getLogger(SimplePivotData.class);

    private double value;
    private T t;
    private P p;
    
	public SimplePivotData(T t, P p, double value)
    {
    	this.t=t;
    	this.p=p;
    	this.value=value;
    }
    
    public T getT() {return t;}
	public void setT(T t) {this.t = t;}

	public P getP() {return p;}
	public void setP(P p) {this.p = p;}

	public double getValue() {return value;}
	public void setValue(double value) {this.value = value;}
}