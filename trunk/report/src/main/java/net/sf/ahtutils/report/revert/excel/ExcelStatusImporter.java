package net.sf.ahtutils.report.revert.excel;

import java.io.IOException;
import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public class ExcelStatusImporter <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription, C extends Serializable, I extends ImportStrategy>
	extends AbstractExcelImporter<S,L,D,C,I> {
	
	public ExcelStatusImporter(String filename) throws IOException
	{
		super(filename);
	}

}
