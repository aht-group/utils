package net.sf.ahtutils.db.excel;

import java.io.IOException;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class ExcelStatusImporter <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription, C extends EjbWithId>
	extends AbstractExcelImporter<S,L,D,C> {
	
	public ExcelStatusImporter(String filename) throws IOException
	{
		super(filename);
	}

}
