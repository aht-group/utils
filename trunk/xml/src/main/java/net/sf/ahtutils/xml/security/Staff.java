
package net.sf.ahtutils.xml.security;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.ahtutils.xml.status.Responsible;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Type;


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
 *         &lt;element ref="{http://ahtutils.aht-group.com/security}role"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/security}user"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/status}status"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/status}type"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/status}responsible"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "role",
    "user",
    "status",
    "type",
    "responsible"
})
@XmlRootElement(name = "staff")
public class Staff
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Role role;
    @XmlElement(required = true)
    protected User user;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/status", required = true)
    protected Status status;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/status", required = true)
    protected Type type;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/status", required = true)
    protected Responsible responsible;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link Role }
     *     
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link Role }
     *     
     */
    public void setRole(Role value) {
        this.role = value;
    }

    public boolean isSetRole() {
        return (this.role!= null);
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

    public boolean isSetUser() {
        return (this.user!= null);
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    public boolean isSetStatus() {
        return (this.status!= null);
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link Type }
     *     
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link Type }
     *     
     */
    public void setType(Type value) {
        this.type = value;
    }

    public boolean isSetType() {
        return (this.type!= null);
    }

    /**
     * Gets the value of the responsible property.
     * 
     * @return
     *     possible object is
     *     {@link Responsible }
     *     
     */
    public Responsible getResponsible() {
        return responsible;
    }

    /**
     * Sets the value of the responsible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Responsible }
     *     
     */
    public void setResponsible(Responsible value) {
        this.responsible = value;
    }

    public boolean isSetResponsible() {
        return (this.responsible!= null);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(long value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    public void unsetId() {
        this.id = null;
    }

}
