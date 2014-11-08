package net.sf.ahtutils.doc.latex.builder;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsLatexAdminDocumentationBuilder extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexAdminDocumentationBuilder.class);
	
	private static enum Code {accessIntroduction};
							 
	public static enum MaintenanceCode {mLoggingIntroduction}
	
	public static enum BackupCode {bPostgres}
	
	public static enum SecurityCode {sConceptIntroduction}
							 
	public static enum InstallationCode {instIntroduction,instDebian,instJava,instJboss,instPostGis,instMySql,instMaven}
	public static enum InstallationArchitecture {debian,debianWheezy,debianSqueeze,debianRaspberry,devJava7FX}
	
	public static enum JBossClassifier {as7,eap6,mysql,postgis}
	
	public static enum RequirementsCode {reqIntroduction,reqHardware,reqAdmin,reqDeveloper}
	public static enum RequirementsClassifier {reqHardware,reqAdmin,reqDeveloper,reqNetwork}
		
	public UtilsLatexAdminDocumentationBuilder(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(config,translations,langs,cmm,dsm);
	}
	
	@Override protected void applyBaseLatexDir()
	{
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
	}
	
	@Override protected void applyConfigCodes()
	{
		//Security
		addConfig(SecurityCode.sConceptIntroduction.toString(),"ofx.aht-utils/administration/security/introduction.xml","admin/security/introduction");
				
		//Maintenance
		addConfig(MaintenanceCode.mLoggingIntroduction.toString(),"ofx.aht-utils/administration/logging/introduction.xml","admin/system/logging/introduction");
		
		//Backup
		addConfig(BackupCode.bPostgres.toString(),"ofx.aht-utils/administration/db/postgres.xml","admin/db/postgres");
		
		//Installation
		addConfig(InstallationCode.instIntroduction.toString(),"ofx.aht-utils/installation/introduction.xml","admin/installation/introduction");
		addConfig(InstallationCode.instDebian.toString(),"ofx.aht-utils/installation/debian.xml","admin/installation/debian");
		addConfig(InstallationCode.instJava.toString(),"ofx.aht-utils/installation/java.xml","admin/installation/java");
		addConfig(InstallationCode.instJboss.toString(),"ofx.aht-utils/installation/jboss.xml","admin/installation/jboss");
		addConfig(InstallationCode.instPostGis.toString(),"ofx.aht-utils/installation/postgres.xml","admin/installation/postgres");
		addConfig(InstallationCode.instMySql.toString(),"ofx.aht-utils/installation/mysql.xml","admin/installation/mysql");
		addConfig(InstallationCode.instMaven.toString(),"ofx.aht-utils/installation/maven.xml","admin/installation/maven");
//		addConfig(InstallationCode.instGeoserver.toString(),"ofx.aht-utils/installation/geoserver.xml","admin/installation/geoserver");
		
		//Requirements
		addConfig(RequirementsCode.reqIntroduction.toString(),"ofx.aht-utils/requirements/introduction.xml","admin/requirements/introduction");
		addConfig(RequirementsCode.reqHardware.toString(),"ofx.aht-utils/requirements/hardware.xml","admin/requirements/hardware");
		addConfig(RequirementsCode.reqAdmin.toString(),"ofx.aht-utils/requirements/administrator.xml","admin/requirements/administrator");
		addConfig(RequirementsCode.reqDeveloper.toString(),"ofx.aht-utils/requirements/developer.xml","admin/requirements/developer");
	}
	
	@Deprecated
	public void buildDoc() throws UtilsConfigurationException
	{
		logger.info("buildDoc");
		render(Code.accessIntroduction.toString());
	}
	
	public void render(SecurityCode code) throws UtilsConfigurationException{render(code.toString());}

	public void render(MaintenanceCode code) throws UtilsConfigurationException{render(code.toString());}
	public void render(BackupCode code) throws UtilsConfigurationException{render(code.toString());}
	
	public void render(InstallationCode code) throws UtilsConfigurationException{render(code.toString());}
	public void render(InstallationCode code, InstallationArchitecture... architectures) throws UtilsConfigurationException
	{
		String[] classifier = new String[architectures.length];
		for(int i=0;i<architectures.length;i++){classifier[i]=architectures[i].toString();}
		render(code.toString(),classifier);
	}
	public void render(InstallationCode code, JBossClassifier... versions) throws UtilsConfigurationException
	{
		String[] classifier = new String[versions.length];
		for(int i=0;i<versions.length;i++){classifier[i]=versions[i].toString();}
		render(code.toString(),classifier);
	}
	
	public void render(RequirementsCode code) throws UtilsConfigurationException{render(code.toString());}
	public void render(RequirementsCode code, RequirementsClassifier... reqClassifier) throws UtilsConfigurationException
	{
		String[] classifier = new String[reqClassifier.length];
		for(int i=0;i<reqClassifier.length;i++){classifier[i]=reqClassifier[i].toString();}
		render(code.toString(),classifier);
	}
	
}