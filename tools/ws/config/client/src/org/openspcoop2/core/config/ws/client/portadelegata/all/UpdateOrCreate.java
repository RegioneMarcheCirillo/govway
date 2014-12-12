/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2014 Link.it srl (http://link.it). All rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openspcoop2.core.config.ws.client.portadelegata.all;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.core.config.IdPortaDelegata;
import org.openspcoop2.core.config.PortaDelegata;


/**
 * <p>Java class for updateOrCreate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateOrCreate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oldIdPortaDelegata" type="{http://www.openspcoop2.org/core/config}id-porta-delegata"/>
 *         &lt;element name="portaDelegata" type="{http://www.openspcoop2.org/core/config}porta-delegata"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOrCreate", propOrder = {
    "oldIdPortaDelegata",
    "portaDelegata"
})
public class UpdateOrCreate {

    @XmlElement(required = true)
    protected IdPortaDelegata oldIdPortaDelegata;
    @XmlElement(required = true)
    protected PortaDelegata portaDelegata;

    /**
     * Gets the value of the oldIdPortaDelegata property.
     * 
     * @return
     *     possible object is
     *     {@link IdPortaDelegata }
     *     
     */
    public IdPortaDelegata getOldIdPortaDelegata() {
        return this.oldIdPortaDelegata;
    }

    /**
     * Sets the value of the oldIdPortaDelegata property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdPortaDelegata }
     *     
     */
    public void setOldIdPortaDelegata(IdPortaDelegata value) {
        this.oldIdPortaDelegata = value;
    }

    /**
     * Gets the value of the portaDelegata property.
     * 
     * @return
     *     possible object is
     *     {@link PortaDelegata }
     *     
     */
    public PortaDelegata getPortaDelegata() {
        return this.portaDelegata;
    }

    /**
     * Sets the value of the portaDelegata property.
     * 
     * @param value
     *     allowed object is
     *     {@link PortaDelegata }
     *     
     */
    public void setPortaDelegata(PortaDelegata value) {
        this.portaDelegata = value;
    }

}
