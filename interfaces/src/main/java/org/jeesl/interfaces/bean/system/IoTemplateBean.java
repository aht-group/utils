package org.jeesl.interfaces.bean.system;

import java.io.IOException;

import freemarker.template.TemplateException;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;

public interface IoTemplateBean
{
	void sendIoMailsFromTemplateHandler() throws UtilsProcessingException, IOException, TemplateException;
}