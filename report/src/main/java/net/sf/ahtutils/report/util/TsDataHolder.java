package net.sf.ahtutils.report.util;

import java.io.Serializable;
import java.util.Date;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;

/**
 * Class that is used to hold information on TimeSeries when using the Excel importer
 */
public class TsDataHolder implements Serializable {
    
    private JeeslTsBridge   bridge;
    private JeeslTimeSeries timeSeries;
    private Date            record;
    private Double          value;

    public JeeslTsBridge getBridge() {return bridge;} public void setBridge(JeeslTsBridge bridge) {this.bridge = bridge;}
    public JeeslTimeSeries getTimeSeries() {return timeSeries;} public void setTimeSeries(JeeslTimeSeries timeSeries) {this.timeSeries = timeSeries;}
    public Date getRecord() {return record;} public void setRecord(Date record) {this.record = record;}
    public Double getValue() {return value;} public void setValue(Double value) {this.value = value;}
}
