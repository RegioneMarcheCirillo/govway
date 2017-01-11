/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it).
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
package org.openspcoop2.core.eccezione.router_details;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for dettaglio-routing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dettaglio-routing">
 * 		&lt;sequence>
 * 			&lt;element name="dominio" type="{http://www.openspcoop2.org/core/eccezione/router_details}dominio" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="ora-registrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="dettaglio" type="{http://www.openspcoop2.org/core/eccezione/router_details}dettaglio" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "dettaglio-routing", 
  propOrder = {
  	"dominio",
  	"oraRegistrazione",
  	"dettaglio"
  }
)

@XmlRootElement(name = "dettaglio-routing")

public class DettaglioRouting extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public DettaglioRouting() {
  }

  public Dominio getDominio() {
    return this.dominio;
  }

  public void setDominio(Dominio dominio) {
    this.dominio = dominio;
  }

  public java.util.Date getOraRegistrazione() {
    return this.oraRegistrazione;
  }

  public void setOraRegistrazione(java.util.Date oraRegistrazione) {
    this.oraRegistrazione = oraRegistrazione;
  }

  public Dettaglio getDettaglio() {
    return this.dettaglio;
  }

  public void setDettaglio(Dettaglio dettaglio) {
    this.dettaglio = dettaglio;
  }

  private static final long serialVersionUID = 1L;

  private static org.openspcoop2.core.eccezione.router_details.model.DettaglioRoutingModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.openspcoop2.core.eccezione.router_details.DettaglioRouting.modelStaticInstance==null){
  			org.openspcoop2.core.eccezione.router_details.DettaglioRouting.modelStaticInstance = new org.openspcoop2.core.eccezione.router_details.model.DettaglioRoutingModel();
	  }
  }
  public static org.openspcoop2.core.eccezione.router_details.model.DettaglioRoutingModel model(){
	  if(org.openspcoop2.core.eccezione.router_details.DettaglioRouting.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.openspcoop2.core.eccezione.router_details.DettaglioRouting.modelStaticInstance;
  }


  @XmlElement(name="dominio",required=true,nillable=false)
  protected Dominio dominio;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.DateTime2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="dateTime")
  @XmlElement(name="ora-registrazione",required=true,nillable=false,type=java.lang.String.class)
  protected java.util.Date oraRegistrazione;

  @XmlElement(name="dettaglio",required=true,nillable=false)
  protected Dettaglio dettaglio;

}
