//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.2-hudson-jaxb-ri-2.2-63- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.28 at 02:32:48 PM MESZ 
//


package net.sf.ahtutils.xml.report;

import java.io.Serializable;
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
 *         &lt;element ref="{http://ahtutils.aht-group.com/report}mediaPdf"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/report}mediaXls"/>
 *         &lt;element name="example">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dir" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mediaPdf",
    "mediaXls",
    "example"
})
@XmlRootElement(name = "report")
public class Report
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected MediaPdf mediaPdf;
    @XmlElement(required = true)
    protected MediaXls mediaXls;
    @XmlElement(namespace = "", required = true)
    protected String example;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "dir")
    protected String dir;

    /**
     * Gets the value of the mediaPdf property.
     * 
     * @return
     *     possible object is
     *     {@link MediaPdf }
     *     
     */
    public MediaPdf getMediaPdf() {
        return mediaPdf;
    }

    /**
     * Sets the value of the mediaPdf property.
     * 
     * @param value
     *     allowed object is
     *     {@link MediaPdf }
     *     
     */
    public void setMediaPdf(MediaPdf value) {
        this.mediaPdf = value;
    }

    public boolean isSetMediaPdf() {
        return (this.mediaPdf!= null);
    }

    /**
     * Gets the value of the mediaXls property.
     * 
     * @return
     *     possible object is
     *     {@link MediaXls }
     *     
     */
    public MediaXls getMediaXls() {
        return mediaXls;
    }

    /**
     * Sets the value of the mediaXls property.
     * 
     * @param value
     *     allowed object is
     *     {@link MediaXls }
     *     
     */
    public void setMediaXls(MediaXls value) {
        this.mediaXls = value;
    }

    public boolean isSetMediaXls() {
        return (this.mediaXls!= null);
    }

    /**
     * Gets the value of the example property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExample() {
        return example;
    }

    /**
     * Sets the value of the example property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExample(String value) {
        this.example = value;
    }

    public boolean isSetExample() {
        return (this.example!= null);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    /**
     * Gets the value of the dir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDir() {
        return dir;
    }

    /**
     * Sets the value of the dir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDir(String value) {
        this.dir = value;
    }

    public boolean isSetDir() {
        return (this.dir!= null);
    }

}
