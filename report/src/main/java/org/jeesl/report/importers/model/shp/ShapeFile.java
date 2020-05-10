package org.jeesl.report.importers.model.shp;

import java.util.List;
import java.util.Map;

/**
 * Represents important information on ESRI ArcGIS Shape-Files
 */
public class ShapeFile {
    
    private List<String>        propertyNames;  
    private Map<String, String> correlationMap;
    private Class               ejbEntityClass;
    private List<ShapeFeature>       data;
    
    /**
     * @return List of property names in the shape file, e.g. NAME, CODE, GEOM
     */
    public List<String> getPropertyNames() {return propertyNames;}
    public void setPropertyNames(List<String> propertyNames) {this.propertyNames = propertyNames;}  
    
    /**
     * @return Correlation of Shape File property names and EJB entity bean property names e.g. CODE_2016 and code
     */
    public Map<String, String> getCorrelationMap() {return correlationMap;}
    public void setCorrelationMap(Map<String, String> correlationMap) {this.correlationMap = correlationMap;}

    /**
     * @return The Java Class of the EJB entities that are target of the import of the Shape File data
     */
    public Class getEjbEntityClass() {return ejbEntityClass;}
    public void setEjbEntityClass(Class ejbEntityClass) {this.ejbEntityClass = ejbEntityClass;} 

    /**
     * @return The data of the Shape file, the ShapeFeature properties
    */
    public List<ShapeFeature> getData() {return data;}
    public void setData(List<ShapeFeature> data) {this.data = data;}
}
