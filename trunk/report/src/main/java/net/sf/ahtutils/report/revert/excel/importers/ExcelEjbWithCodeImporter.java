package net.sf.ahtutils.report.revert.excel.importers;

import java.io.IOException;
import java.io.Serializable;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.report.revert.excel.AbstractExcelImporter;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelEjbWithCodeImporter <E extends EjbWithCode, I extends ImportStrategy>
	extends AbstractExcelImporter<E,I> {
	
	final static Logger logger = LoggerFactory.getLogger(ExcelEjbWithCodeImporter.class);
	
	final Class<E> cEjbWithCode;
	
	public ExcelEjbWithCodeImporter(Class<E> cEjbWithCode, String filename) throws IOException
	{
		// Initialize the Abstract class with the given filename
		super(filename);
		
		// Initialize class parameter
		this.cEjbWithCode = cEjbWithCode;
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
	public static <E extends EjbWithCode, C extends Serializable, I extends ImportStrategy>
	ExcelEjbWithCodeImporter<E,I> factory(final Class<E> cEjbWithCode, String filename) throws IOException
	{
		return new ExcelEjbWithCodeImporter<E,I>(cEjbWithCode, filename);
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
	public E persistEntity(E pojo) throws UtilsConstraintViolationException
	{
		// The Plain Old Java Object (pojo) holding the code will be transformed into an Entity that has an ID and is to be found in the database
		E entity = null;
		
		try {
			
			// First, the database is searched for an existing entity that has the correct code
			logger.trace("Trying to save " +pojo.getCode());
			entity = facade.fByCode(cEjbWithCode, pojo.getCode());
		}
		catch (UtilsNotFoundException e)
		{
			logger.warn("Entity not found by Code. Initializing a new one.");
			logger.warn("(" +e.getMessage() +")");
			
			// The entity is saved and is returned with the new database ID assigned to it
			entity   = facade.persist(pojo);
			this.enititesSaved++;
		}
		
		return entity;
	}
}
