//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.22 at 06:22:47 PM ICT 
//


package RestController;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;


/**
 * <p>Java class for subscriptionDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subscriptionDetails"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="sites" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="numberOfYears" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subscriptionDetails", propOrder = {

})
public class SubscriptionDetails {

    @XmlElement(required = true)
    protected BigInteger sites;
    @XmlElement(required = true)
    protected BigInteger numberOfYears;

    /**
     * Gets the value of the sites property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSites() {
        return sites;
    }

    /**
     * Sets the value of the sites property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSites(BigInteger value) {
        this.sites = value;
    }

    /**
     * Gets the value of the numberOfYears property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfYears() {
        return numberOfYears;
    }

    /**
     * Sets the value of the numberOfYears property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfYears(BigInteger value) {
        this.numberOfYears = value;
    }

}