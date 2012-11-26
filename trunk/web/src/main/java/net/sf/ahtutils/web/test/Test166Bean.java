package net.sf.ahtutils.web.test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import net.sf.ahtutils.controller.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.ejb.status.AhtUtilsStatus;
import net.sf.ahtutils.model.primefaces.PrimefacesEjbIdDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @ViewScoped
public class Test166Bean
{
	final static Logger logger = LoggerFactory.getLogger(Test166Bean.class);
	
	private PrimefacesEjbIdDataModel<AhtUtilsStatus> dmStatus;

	private String test ="hello";
	
	public String getTest() {
		return test;
	}

	@PostConstruct
	public void init() throws FileNotFoundException, InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		logger.info("@PostConstruct");
		
		EjbStatusFactory<AhtUtilsStatus,AhtUtilsLang,AhtUtilsDescription> f = EjbStatusFactory.createFactory(AhtUtilsStatus.class, AhtUtilsLang.class, AhtUtilsDescription.class);
		
		List<AhtUtilsStatus> list = new ArrayList<AhtUtilsStatus>();
		
		AhtUtilsStatus s1 = f.create("alpha");s1.setId(1);
		AhtUtilsStatus s2 = f.create("beta");s2.setId(2);
		
		list.add(s1);
		list.add(s2);
		dmStatus = new PrimefacesEjbIdDataModel<AhtUtilsStatus>(list);
	}
	
	public PrimefacesEjbIdDataModel<AhtUtilsStatus> getDmStatus() {return dmStatus;}
	
}
