package net.sf.ahtutils.report;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliReport
{
	final static Logger logger = LoggerFactory.getLogger(CliReport.class);

    public static String cliDebug = "report.cli.debug";
    public static String cliObfuscate = "report.cli.obfuscate";
    public static String cliSave = "report.cli.save";
    public static String cliLang = "report.cli.lang";

    public static void debugCliConfig(Configuration config)
    {
        logger.warn(cliDebug+" "+config.getBoolean(cliDebug));
        logger.warn(cliObfuscate+" "+config.getBoolean(cliObfuscate));
        logger.warn(cliSave+" "+config.getBoolean(cliSave));
        logger.warn(cliLang+" "+config.getString(cliLang));
    }
}
