package net.sf.ahtutils.report.revert.excel.configuration;

/**
 * This class holds ENUMs to be used in the strategies to import
 * data from Excel or other table-based structures.
 */
public class JeeslDataImporterConstants {
    
    public static enum timeSeriesVariables  {	TsScope,		    // The domain specific object implementing JeeslTsScope and holding the scope for the time series
						TsWorkspace,		    // The domain specific object implementing JeeslTsWorkspace and holding the workspace for the time series (e.g. live or staging)
						TsDomainModelBridgeClass,   // The class in the project that implements JeeslTsBridge
						TsData,			    // The class in the project that implements JeeslTsData
						TsBridgeEntitiyClass,	    // The domain specific object that is used as the reference for the time series (e.g. a Station, a Province, ..)
						TsDomainModelEntityClass,   // The class in the project that implements JeeslTsEntityClass
						TsInterval,		    // The domain specific object implementing JeeslTsInterval and holding the interval for the time series (e.g. yearly or daily)
						TsStatistic		    // The domain specific object implementing JeeslTsStatistic and holding the type of statistics for the time series (e.g. raw)
					    }; 
    
    public static enum controlParamteres    {	createEntityForUnknown,	    // If set to true, new entities are created if none are found on the database
						lookup,			    // If set to true, e.g. loadByCode is searching the database for a matching entry instead of creating a plain new object and only set the code
						dontGenerateIds		    // If set to true, no IDs will be set for the created entites
					    };
    
}
