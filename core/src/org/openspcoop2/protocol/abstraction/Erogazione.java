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
package org.openspcoop2.protocol.abstraction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.protocol.abstraction.constants.Tipologia;
import java.io.Serializable;


/** <p>Java class for erogazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="erogazione">
 * 		&lt;sequence>
 * 			&lt;element name="accordo-servizio-parte-comune" type="{http://www.openspcoop2.org/protocol/abstraction}RiferimentoAccordoServizioParteComune" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="soggetto-erogatore" type="{http://www.openspcoop2.org/protocol/abstraction}RiferimentoSoggetto" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="servizio" type="{http://www.openspcoop2.org/protocol/abstraction}DatiServizio" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="servizio-applicativo" type="{http://www.openspcoop2.org/protocol/abstraction}RiferimentoServizioApplicativoErogatore" minOccurs="0" maxOccurs="1"/>
 * 		&lt;/sequence>
 * 		&lt;attribute name="tipologia" type="{http://www.openspcoop2.org/protocol/abstraction}Tipologia" use="required"/>
 * 		&lt;attribute name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" use="optional"/>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "erogazione", 
  propOrder = {
  	"accordoServizioParteComune",
  	"soggettoErogatore",
  	"servizio",
  	"servizioApplicativo"
  }
)

@XmlRootElement(name = "erogazione")

public class Erogazione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Erogazione() {
  }

  public RiferimentoAccordoServizioParteComune getAccordoServizioParteComune() {
    return this.accordoServizioParteComune;
  }

  public void setAccordoServizioParteComune(RiferimentoAccordoServizioParteComune accordoServizioParteComune) {
    this.accordoServizioParteComune = accordoServizioParteComune;
  }

  public RiferimentoSoggetto getSoggettoErogatore() {
    return this.soggettoErogatore;
  }

  public void setSoggettoErogatore(RiferimentoSoggetto soggettoErogatore) {
    this.soggettoErogatore = soggettoErogatore;
  }

  public DatiServizio getServizio() {
    return this.servizio;
  }

  public void setServizio(DatiServizio servizio) {
    this.servizio = servizio;
  }

  public RiferimentoServizioApplicativoErogatore getServizioApplicativo() {
    return this.servizioApplicativo;
  }

  public void setServizioApplicativo(RiferimentoServizioApplicativoErogatore servizioApplicativo) {
    this.servizioApplicativo = servizioApplicativo;
  }

  public void set_value_tipologia(String value) {
    this.tipologia = (Tipologia) Tipologia.toEnumConstantFromString(value);
  }

  public String get_value_tipologia() {
    if(this.tipologia == null){
    	return null;
    }else{
    	return this.tipologia.toString();
    }
  }

  public org.openspcoop2.protocol.abstraction.constants.Tipologia getTipologia() {
    return this.tipologia;
  }

  public void setTipologia(org.openspcoop2.protocol.abstraction.constants.Tipologia tipologia) {
    this.tipologia = tipologia;
  }

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  private static final long serialVersionUID = 1L;

  private static org.openspcoop2.protocol.abstraction.model.ErogazioneModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(org.openspcoop2.protocol.abstraction.Erogazione.modelStaticInstance==null){
  			org.openspcoop2.protocol.abstraction.Erogazione.modelStaticInstance = new org.openspcoop2.protocol.abstraction.model.ErogazioneModel();
	  }
  }
  public static org.openspcoop2.protocol.abstraction.model.ErogazioneModel model(){
	  if(org.openspcoop2.protocol.abstraction.Erogazione.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return org.openspcoop2.protocol.abstraction.Erogazione.modelStaticInstance;
  }


  @XmlElement(name="accordo-servizio-parte-comune",required=true,nillable=false)
  protected RiferimentoAccordoServizioParteComune accordoServizioParteComune;

  @XmlElement(name="soggetto-erogatore",required=true,nillable=false)
  protected RiferimentoSoggetto soggettoErogatore;

  @XmlElement(name="servizio",required=false,nillable=false)
  protected DatiServizio servizio;

  @XmlElement(name="servizio-applicativo",required=false,nillable=false)
  protected RiferimentoServizioApplicativoErogatore servizioApplicativo;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.String _value_tipologia;

  @XmlAttribute(name="tipologia",required=true)
  protected Tipologia tipologia;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlAttribute(name="descrizione",required=false)
  protected java.lang.String descrizione;

}
