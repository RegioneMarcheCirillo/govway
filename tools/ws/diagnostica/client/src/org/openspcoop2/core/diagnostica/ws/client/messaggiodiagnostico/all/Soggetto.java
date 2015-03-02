
package org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.all;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for soggetto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="soggetto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificativo" type="{http://www.openspcoop2.org/core/diagnostica/management}soggetto-identificativo" minOccurs="0"/>
 *         &lt;element name="identificativo-porta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soggetto", propOrder = {
    "identificativo",
    "identificativoPorta"
})
public class Soggetto {

    protected SoggettoIdentificativo identificativo;
    @XmlElement(name = "identificativo-porta")
    protected String identificativoPorta;

    /**
     * Gets the value of the identificativo property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoIdentificativo }
     *     
     */
    public SoggettoIdentificativo getIdentificativo() {
        return this.identificativo;
    }

    /**
     * Sets the value of the identificativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoIdentificativo }
     *     
     */
    public void setIdentificativo(SoggettoIdentificativo value) {
        this.identificativo = value;
    }

    /**
     * Gets the value of the identificativoPorta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPorta() {
        return this.identificativoPorta;
    }

    /**
     * Sets the value of the identificativoPorta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPorta(String value) {
        this.identificativoPorta = value;
    }

}
