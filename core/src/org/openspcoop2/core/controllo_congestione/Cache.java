/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
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
package org.openspcoop2.core.controllo_congestione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.core.controllo_congestione.constants.CacheAlgorithm;
import java.io.Serializable;


/** <p>Java class for cache complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cache">
 * 		&lt;sequence>
 * 			&lt;element name="cache" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="true"/>
 * 			&lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="algorithm" type="{http://www.openspcoop2.org/core/controllo_congestione}cache-algorithm" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="idle-time" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="life-time" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" minOccurs="0" maxOccurs="1"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cache", 
  propOrder = {
  	"cache",
  	"size",
  	"algorithm",
  	"idleTime",
  	"lifeTime"
  }
)

@XmlRootElement(name = "cache")

public class Cache extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Cache() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return new Long(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=new Long(-1);
  }

  public boolean isCache() {
    return this.cache;
  }

  public boolean getCache() {
    return this.cache;
  }

  public void setCache(boolean cache) {
    this.cache = cache;
  }

  public java.lang.Long getSize() {
    return this.size;
  }

  public void setSize(java.lang.Long size) {
    this.size = size;
  }

  public void set_value_algorithm(String value) {
    this.algorithm = (CacheAlgorithm) CacheAlgorithm.toEnumConstantFromString(value);
  }

  public String get_value_algorithm() {
    if(this.algorithm == null){
    	return null;
    }else{
    	return this.algorithm.toString();
    }
  }

  public org.openspcoop2.core.controllo_congestione.constants.CacheAlgorithm getAlgorithm() {
    return this.algorithm;
  }

  public void setAlgorithm(org.openspcoop2.core.controllo_congestione.constants.CacheAlgorithm algorithm) {
    this.algorithm = algorithm;
  }

  public java.lang.Long getIdleTime() {
    return this.idleTime;
  }

  public void setIdleTime(java.lang.Long idleTime) {
    this.idleTime = idleTime;
  }

  public java.lang.Long getLifeTime() {
    return this.lifeTime;
  }

  public void setLifeTime(java.lang.Long lifeTime) {
    this.lifeTime = lifeTime;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="cache",required=true,nillable=false,defaultValue="true")
  protected boolean cache = true;

  @javax.xml.bind.annotation.XmlSchemaType(name="unsignedLong")
  @XmlElement(name="size",required=false,nillable=false)
  protected java.lang.Long size;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.String _value_algorithm;

  @XmlElement(name="algorithm",required=false,nillable=false)
  protected CacheAlgorithm algorithm;

  @javax.xml.bind.annotation.XmlSchemaType(name="unsignedLong")
  @XmlElement(name="idle-time",required=false,nillable=false)
  protected java.lang.Long idleTime;

  @javax.xml.bind.annotation.XmlSchemaType(name="unsignedLong")
  @XmlElement(name="life-time",required=false,nillable=false)
  protected java.lang.Long lifeTime;

}
