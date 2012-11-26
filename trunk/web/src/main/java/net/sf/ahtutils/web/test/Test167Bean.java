package net.sf.ahtutils.web.test;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.ejb.status.AhtUtilsStatus;
import net.sf.ahtutils.model.primefaces.PrimefacesEjbIdDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @ViewScoped
public class Test167Bean implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(Test167Bean.class);
	
	private PrimefacesEjbIdDataModel<AhtUtilsStatus> dmStatus;

	private int counter;public int getCounter() {return counter;}
	private String test;public String getTest() {return test;}

	@PostConstruct
	public void init() throws FileNotFoundException, InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		logger.info("@PostConstruct");
		counter=0;
		test = "Hello World "+Math.random();
	}
	
	public void button()
	{
		counter++;
		logger.info("Button "+counter);
	}
	
	public PrimefacesEjbIdDataModel<AhtUtilsStatus> getDmStatus() {return dmStatus;}
	
}
