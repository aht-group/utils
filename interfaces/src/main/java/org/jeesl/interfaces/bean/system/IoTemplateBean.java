package org.jeesl.interfaces.bean.system;

import java.io.IOException;

import org.jeesl.exception.processing.UtilsProcessingException;

import freemarker.template.TemplateException;

public interface IoTemplateBean
{
	void sendIoMailsFromTemplateHandler() throws UtilsProcessingException, IOException, TemplateException;
}