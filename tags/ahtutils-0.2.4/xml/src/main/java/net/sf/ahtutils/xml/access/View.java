
package net.sf.ahtutils.xml.access;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.ahtutils.xml.navigation.Navigation;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;


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
 *         &lt;element ref="{http://ahtutils.aht-group.com/status}descriptions"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/status}langs"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/access}actions"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/navigation}navigation"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="index" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="public" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="onlyLoginRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="urlParameter" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "descriptions",
    "langs",
    "actions",
    "navigation"
})
@XmlRootElement(name = "view")
public class View
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/status", required = true)
    protected Descriptions descriptions;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/status", required = true)
    protected Langs langs;
    @XmlElement(required = true)
    protected Actions actions;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/navigation", required = true)
    protected Navigation navigation;
    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "index")
    protected Integer index;
    @XmlAttribute(name = "public")
    protected Boolean _public;
    @XmlAttribute(name = "onlyLoginRequired")
    protected Boolean onlyLoginRequired;
    @XmlAttribute(name = "urlParameter")
    protected String urlParameter;
    @XmlAttribute(name = "label")
    protected String label;

    /**
     * Gets the value of the descriptions property.
     * 
     * @return
     *     possible object is
     *     {@link Descriptions }
     *     
     */
    public Descriptions getDescriptions() {
        return descriptions;
    }

    /**
     * Sets the value of the descriptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Descriptions }
     *     
     */
    public void setDescriptions(Descriptions value) {
        this.descriptions = value;
    }

    public boolean isSetDescriptions() {
        return (this.descriptions!= null);
    }

    /**
     * Gets the value of the langs property.
     * 
     * @return
     *     possible object is
     *     {@link Langs }
     *     
     */
    public Langs getLangs() {
        return langs;
    }

    /**
     * Sets the value of the langs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Langs }
     *     
     */
    public void setLangs(Langs value) {
        this.langs = value;
    }

    public boolean isSetLangs() {
        return (this.langs!= null);
    }

    /**
     * Gets the value of the actions property.
     * 
     * @return
     *     possible object is
     *     {@link Actions }
     *     
     */
    public Actions getActions() {
        return actions;
    }

    /**
     * Sets the value of the actions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Actions }
     *     
     */
    public void setActions(Actions value) {
        this.actions = value;
    }

    public boolean isSetActions() {
        return (this.actions!= null);
    }

    /**
     * Gets the value of the navigation property.
     * 
     * @return
     *     possible object is
     *     {@link Navigation }
     *     
     */
    public Navigation getNavigation() {
        return navigation;
    }

    /**
     * Sets the value of the navigation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Navigation }
     *     
     */
    public void setNavigation(Navigation value) {
        this.navigation = value;
    }

    public boolean isSetNavigation() {
        return (this.navigation!= null);
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    public boolean isSetCode() {
        return (this.code!= null);
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndex(int value) {
        this.index = value;
    }

    public boolean isSetIndex() {
        return (this.index!= null);
    }

    public void unsetIndex() {
        this.index = null;
    }

    /**
     * Gets the value of the public property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPublic() {
        return _public;
    }

    /**
     * Sets the value of the public property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPublic(boolean value) {
        this._public = value;
    }

    public boolean isSetPublic() {
        return (this._public!= null);
    }

    public void unsetPublic() {
        this._public = null;
    }

    /**
     * Gets the value of the onlyLoginRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isOnlyLoginRequired() {
        return onlyLoginRequired;
    }

    /**
     * Sets the value of the onlyLoginRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnlyLoginRequired(boolean value) {
        this.onlyLoginRequired = value;
    }

    public boolean isSetOnlyLoginRequired() {
        return (this.onlyLoginRequired!= null);
    }

    public void unsetOnlyLoginRequired() {
        this.onlyLoginRequired = null;
    }

    /**
     * Gets the value of the urlParameter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlParameter() {
        return urlParameter;
    }

    /**
     * Sets the value of the urlParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlParameter(String value) {
        this.urlParameter = value;
    }

    public boolean isSetUrlParameter() {
        return (this.urlParameter!= null);
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    public boolean isSetLabel() {
        return (this.label!= null);
    }

}
