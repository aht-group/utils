package net.sf.ahtutils.report.revert.excel.importers;

import java.io.IOException;
import java.io.Serializable;

import net.sf.ahtutils.report.revert.excel.AbstractExcelImporter;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelSimpleSerializableImporter <S extends Serializable, I extends ImportStrategy>
	extends AbstractExcelImporter<S,I> {
	
	final static Logger logger = LoggerFactory.getLogger(ExcelSimpleSerializableImporter.class);
	
	final Class<S> cSerializable;
	
	public ExcelSimpleSerializableImporter(Class<S> cSerializable, String filename) throws IOException
	{
		// Initialize the Abstract class with the given filename
		super(filename);
		
		// Initialize class parameter
		this.cSerializable = cSerializable;
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
	public static <S extends Serializable, C extends Serializable, I extends ImportStrategy>
	ExcelSimpleSerializableImporter<S,I> factory(final Class<S> cSerializable, String filename) throws IOException
	{
		return new ExcelSimpleSerializableImporter<S,I>(cSerializable, filename);
	}
}
