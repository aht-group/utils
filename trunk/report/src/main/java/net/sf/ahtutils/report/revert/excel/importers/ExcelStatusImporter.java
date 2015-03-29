package net.sf.ahtutils.report.revert.excel.importers;

import java.io.IOException;
import java.io.Serializable;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.report.revert.excel.AbstractExcelImporter;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelStatusImporter <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription, C extends Serializable, I extends ImportStrategy>
	extends AbstractExcelImporter<C,I> {
	
	final static Logger logger = LoggerFactory.getLogger(ExcelStatusImporter.class);
	
	final Class<S> cStatus;
    final Class<L> cLang;
    final Class<D> cDescription;
    final String[] defaultLanguages;
	
	public ExcelStatusImporter(Class<S> cStatus, Class<L> cLang,Class<D> cDescription,String[] defaultLanguages, String filename) throws IOException
	{
		// Initialize the Abstract class with the given filename
		super(filename);
		
		// Set the concrete classes to be used
		this.cStatus          = cStatus;
		this.cLang            = cLang;
		this.cDescription     = cDescription;
		this.defaultLanguages = defaultLanguages; 
	}
	
	/**
	* Because the ExcelStatusImporter is highly parameterized with classes to implement certain aspects, this method
	* can be used to get an instance that is configured correctly according to your use case
	*
	* @param cClass           Concrete class to be imported (must implements Serializable)
	* @param cLang            Concrete class that implements UtilsLang
	* @param cDescription     Concrete class that implements UtilsDescription
	* @param cStatus          Concrete class that implements UtilsStatus
	* @param defaultLanguages Array of language codes (en, fr, de, ...)
	* @param filename         Location of the Excel-Sheet
	*
	* @return New instance of ExcelStatusImporter
	*
	*/
	public static <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription, C extends Serializable, I extends ImportStrategy>
	ExcelStatusImporter<S,L,D,C,I> factory(final Class<S> cStatus, final Class<L> cLang, final Class<D> cDescription,String[] defaultLanguages, String filename) throws IOException
	{
		return new ExcelStatusImporter<S,L,D,C,I>(cStatus, cLang, cDescription, defaultLanguages, filename);
	}
	
	/**
	* Persist the imported object into database. The method will check if there is an entity with the given code already available and loads it if so.
	* Otherwise, a new instance is created using 
	*
	* @param pojo   Plain Old Java Object holding the code
	*
	* @return       Database Entity object with ID and (if not available in database already empty) translations
	*
	*/
	public S persistEntity(S pojo) throws UtilsConstraintViolationException
	{
		// The Plain Old Java Object (pojo) holding the code will be transformed into an Entity that has an ID and is to be found in the database
		S entity = null;
		
		try {
			
			// First, the database is searched for an existing entity that has the correct code
			logger.info("Trying to save " +pojo.getCode());
			entity = facade.fByCode(cStatus, pojo.getCode());
		}
		catch (UtilsNotFoundException e)
		{
			logger.warn("Entity not found by Code. Initializing a new one.");
			logger.warn("(" +e.getMessage() +")");
			
			// If the entity code is unknown, a new one is created with that code and 
			// the default languages to prevent NullPointerExceptions in UI when requesting translations
			EjbStatusFactory<S,L,D> factory = EjbStatusFactory.createFactory(cStatus, cLang, cDescription);
			entity = factory.create(pojo.getCode(), defaultLanguages);
			
			// The entity is saved and is returned with the new database ID assigned to it
			entity   = facade.persist(entity);
			this.enititesSaved++;
		}
		
		return entity;
	}
}
