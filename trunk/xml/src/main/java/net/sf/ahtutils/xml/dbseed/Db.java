
package net.sf.ahtutils.xml.dbseed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{http://ahtutils.aht-group.com/dbseed}seed" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="pathIde" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="pathExport" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "seed"
})
@XmlRootElement(name = "db")
public class Db
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Seed> seed;
    @XmlAttribute(name = "pathIde")
    protected String pathIde;
    @XmlAttribute(name = "pathExport")
    protected String pathExport;

    /**
     * Gets the value of the seed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the seed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Seed }
     * 
     * 
     */
    public List<Seed> getSeed() {
        if (seed == null) {
            seed = new ArrayList<Seed>();
        }
        return this.seed;
    }

    public boolean isSetSeed() {
        return ((this.seed!= null)&&(!this.seed.isEmpty()));
    }

    public void unsetSeed() {
        this.seed = null;
    }

    /**
     * Gets the value of the pathIde property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathIde() {
        return pathIde;
    }

    /**
     * Sets the value of the pathIde property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathIde(String value) {
        this.pathIde = value;
    }

    public boolean isSetPathIde() {
        return (this.pathIde!= null);
    }

    /**
     * Gets the value of the pathExport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathExport() {
        return pathExport;
    }

    /**
     * Sets the value of the pathExport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathExport(String value) {
        this.pathExport = value;
    }

    public boolean isSetPathExport() {
        return (this.pathExport!= null);
    }

}
