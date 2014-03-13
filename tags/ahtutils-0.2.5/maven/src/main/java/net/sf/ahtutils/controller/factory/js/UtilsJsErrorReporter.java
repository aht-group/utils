package net.sf.ahtutils.controller.factory.js;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsJsErrorReporter implements ErrorReporter
{
	final static Logger logger = LoggerFactory.getLogger(UtilsJsErrorReporter.class);
		
	public void warning(String message, String sourceName,
            int line, String lineSource, int lineOffset) {
        if (line < 0) {
            System.err.println("\n[WARNING] " + message);
        } else {
            System.err.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
        }
    }

    public void error(String message, String sourceName,
            int line, String lineSource, int lineOffset) {
        if (line < 0) {
            System.err.println("\n[ERROR] " + message);
        } else {
            System.err.println("\n[ERROR] " + line + ':' + lineOffset + ':' + message);
        }
    }

    public EvaluatorException runtimeError(String message, String sourceName,
            int line, String lineSource, int lineOffset) {
        error(message, sourceName, line, lineSource, lineOffset);
        return new EvaluatorException(message);
    }
}