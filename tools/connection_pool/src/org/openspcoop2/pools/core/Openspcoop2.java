/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 * 
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.11 at 09:00:13 AM CEST 
//


package org.openspcoop2.pools.core;

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
 *         &lt;element ref="{http://www.openspcoop2.org/pools/core}jndi"/>
 *         &lt;element ref="{http://www.openspcoop2.org/pools/core}datasource" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.openspcoop2.org/pools/core}connection-factory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "jndi",
    "datasource",
    "connectionFactory"
})
@XmlRootElement(name = "openspcoop2", namespace = "http://www.openspcoop2.org/pools/core")
public class Openspcoop2 {

    @XmlElement(namespace = "http://www.openspcoop2.org/pools/core", required = true)
    protected Jndi jndi;
    @XmlElement(namespace = "http://www.openspcoop2.org/pools/core")
    protected List<Datasource> datasource;
    @XmlElement(name = "connection-factory", namespace = "http://www.openspcoop2.org/pools/core")
    protected List<ConnectionFactory> connectionFactory;

    /**
     * Gets the value of the jndi property.
     * 
     * @return
     *     possible object is
     *     {@link Jndi }
     *     
     */
    public Jndi getJndi() {
        return this.jndi;
    }

    /**
     * Sets the value of the jndi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Jndi }
     *     
     */
    public void setJndi(Jndi value) {
        this.jndi = value;
    }

    /**
     * Gets the value of the datasource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datasource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatasource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Datasource }
     * 
     * 
     */
    public List<Datasource> getDatasource() {
        if (this.datasource == null) {
            this.datasource = new ArrayList<Datasource>();
        }
        return this.datasource;
    }

    /**
     * Gets the value of the connectionFactory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the connectionFactory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConnectionFactory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConnectionFactory }
     * 
     * 
     */
    public List<ConnectionFactory> getConnectionFactory() {
        if (this.connectionFactory == null) {
            this.connectionFactory = new ArrayList<ConnectionFactory>();
        }
        return this.connectionFactory;
    }

}
