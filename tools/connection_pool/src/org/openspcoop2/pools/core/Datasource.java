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
 *         &lt;element ref="{http://www.openspcoop2.org/pools/core}pool-size" minOccurs="0"/>
 *         &lt;element ref="{http://www.openspcoop2.org/pools/core}validation" minOccurs="0"/>
 *         &lt;element ref="{http://www.openspcoop2.org/pools/core}when-exhausted" minOccurs="0"/>
 *         &lt;element ref="{http://www.openspcoop2.org/pools/core}idle-object-eviction" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="jndi-name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="connection-url" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="driver-class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="username" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="password" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="prepared-statement-pool" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="auto-commit" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="read-only" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="transaction-isolation" default="readCommitted">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="none"/>
 *             &lt;enumeration value="readCommitted"/>
 *             &lt;enumeration value="readUncommitted"/>
 *             &lt;enumeration value="repeatableRead"/>
 *             &lt;enumeration value="serializable"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
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
    "poolSize",
    "validation",
    "whenExhausted",
    "idleObjectEviction"
})
@XmlRootElement(name = "datasource", namespace = "http://www.openspcoop2.org/pools/core")
public class Datasource {

    @XmlElement(name = "pool-size", namespace = "http://www.openspcoop2.org/pools/core")
    protected PoolSize poolSize;
    @XmlElement(namespace = "http://www.openspcoop2.org/pools/core")
    protected Validation validation;
    @XmlElement(name = "when-exhausted", namespace = "http://www.openspcoop2.org/pools/core")
    protected WhenExhausted whenExhausted;
    @XmlElement(name = "idle-object-eviction", namespace = "http://www.openspcoop2.org/pools/core")
    protected IdleObjectEviction idleObjectEviction;
    @XmlAttribute(name = "jndi-name", required = true)
    protected String jndiName;
    @XmlAttribute(name = "connection-url", required = true)
    protected String connectionUrl;
    @XmlAttribute(name = "driver-class", required = true)
    protected String driverClass;
    @XmlAttribute(name = "username")
    protected String username;
    @XmlAttribute(name = "password")
    protected String password;
    @XmlAttribute(name = "prepared-statement-pool")
    protected Boolean preparedStatementPool;
    @XmlAttribute(name = "auto-commit")
    protected Boolean autoCommit;
    @XmlAttribute(name = "read-only")
    protected Boolean readOnly;
    @XmlAttribute(name = "transaction-isolation")
    protected String transactionIsolation;

    /**
     * Gets the value of the poolSize property.
     * 
     * @return
     *     possible object is
     *     {@link PoolSize }
     *     
     */
    public PoolSize getPoolSize() {
        return this.poolSize;
    }

    /**
     * Sets the value of the poolSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link PoolSize }
     *     
     */
    public void setPoolSize(PoolSize value) {
        this.poolSize = value;
    }

    /**
     * Gets the value of the validation property.
     * 
     * @return
     *     possible object is
     *     {@link Validation }
     *     
     */
    public Validation getValidation() {
        return this.validation;
    }

    /**
     * Sets the value of the validation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Validation }
     *     
     */
    public void setValidation(Validation value) {
        this.validation = value;
    }

    /**
     * Gets the value of the whenExhausted property.
     * 
     * @return
     *     possible object is
     *     {@link WhenExhausted }
     *     
     */
    public WhenExhausted getWhenExhausted() {
        return this.whenExhausted;
    }

    /**
     * Sets the value of the whenExhausted property.
     * 
     * @param value
     *     allowed object is
     *     {@link WhenExhausted }
     *     
     */
    public void setWhenExhausted(WhenExhausted value) {
        this.whenExhausted = value;
    }

    /**
     * Gets the value of the idleObjectEviction property.
     * 
     * @return
     *     possible object is
     *     {@link IdleObjectEviction }
     *     
     */
    public IdleObjectEviction getIdleObjectEviction() {
        return this.idleObjectEviction;
    }

    /**
     * Sets the value of the idleObjectEviction property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdleObjectEviction }
     *     
     */
    public void setIdleObjectEviction(IdleObjectEviction value) {
        this.idleObjectEviction = value;
    }

    /**
     * Gets the value of the jndiName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndiName() {
        return this.jndiName;
    }

    /**
     * Sets the value of the jndiName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndiName(String value) {
        this.jndiName = value;
    }

    /**
     * Gets the value of the connectionUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionUrl() {
        return this.connectionUrl;
    }

    /**
     * Sets the value of the connectionUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectionUrl(String value) {
        this.connectionUrl = value;
    }

    /**
     * Gets the value of the driverClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverClass() {
        return this.driverClass;
    }

    /**
     * Sets the value of the driverClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverClass(String value) {
        this.driverClass = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the preparedStatementPool property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPreparedStatementPool() {
        if (this.preparedStatementPool == null) {
            return false;
        } else {
            return this.preparedStatementPool;
        }
    }

    /**
     * Sets the value of the preparedStatementPool property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreparedStatementPool(Boolean value) {
        this.preparedStatementPool = value;
    }

    /**
     * Gets the value of the autoCommit property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isAutoCommit() {
        if (this.autoCommit == null) {
            return false;
        } else {
            return this.autoCommit;
        }
    }

    /**
     * Sets the value of the autoCommit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoCommit(Boolean value) {
        this.autoCommit = value;
    }

    /**
     * Gets the value of the readOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReadOnly() {
        if (this.readOnly == null) {
            return false;
        } else {
            return this.readOnly;
        }
    }

    /**
     * Sets the value of the readOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReadOnly(Boolean value) {
        this.readOnly = value;
    }

    /**
     * Gets the value of the transactionIsolation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionIsolation() {
        if (this.transactionIsolation == null) {
            return "readCommitted";
        } else {
            return this.transactionIsolation;
        }
    }

    /**
     * Sets the value of the transactionIsolation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionIsolation(String value) {
        this.transactionIsolation = value;
    }

}
