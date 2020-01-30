package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;

import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.xml.io.File;
import net.sf.exlp.xml.io.Files;

public interface JeeslIoJcrFacade <L extends UtilsLang,D extends UtilsDescription> extends JeeslFacade
{
	List<File> jcrFiles(EjbWithId ejb);
	File jcrFile(EjbWithId ejb, String fileName) throws JeeslNotFoundException;
	Files jcrUpload(EjbWithId ejb, File file) throws UtilsProcessingException;
}