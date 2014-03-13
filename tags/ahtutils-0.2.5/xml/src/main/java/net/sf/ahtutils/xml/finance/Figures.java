
package net.sf.ahtutils.xml.finance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://ahtutils.aht-group.com/finance}finance" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/finance}time" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "finance",
    "time"
})
@XmlRootElement(name = "figures")
public class Figures
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Finance> finance;
    @XmlElement(required = true)
    protected List<Time> time;

    /**
     * Gets the value of the finance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the finance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFinance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Finance }
     * 
     * 
     */
    public List<Finance> getFinance() {
        if (finance == null) {
            finance = new ArrayList<Finance>();
        }
        return this.finance;
    }

    public boolean isSetFinance() {
        return ((this.finance!= null)&&(!this.finance.isEmpty()));
    }

    public void unsetFinance() {
        this.finance = null;
    }

    /**
     * Gets the value of the time property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the time property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Time }
     * 
     * 
     */
    public List<Time> getTime() {
        if (time == null) {
            time = new ArrayList<Time>();
        }
        return this.time;
    }

    public boolean isSetTime() {
        return ((this.time!= null)&&(!this.time.isEmpty()));
    }

    public void unsetTime() {
        this.time = null;
    }

}
