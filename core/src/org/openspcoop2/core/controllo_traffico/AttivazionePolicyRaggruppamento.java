/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it).
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
package org.openspcoop2.core.controllo_traffico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for attivazione-policy-raggruppamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attivazione-policy-raggruppamento">
 * 		&lt;sequence>
 * 			&lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="ruolo-porta" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="protocollo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="fruitore" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="servizio-applicativo-fruitore" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="identificativo-autenticato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="erogatore" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="servizio-applicativo-erogatore" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="servizio" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="azione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="informazione-applicativa-enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="1" maxOccurs="1" default="false"/>
 * 			&lt;element name="informazione-applicativa-tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="informazione-applicativa-nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
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
@XmlType(name = "attivazione-policy-raggruppamento", 
  propOrder = {
  	"enabled",
  	"ruoloPorta",
  	"protocollo",
  	"fruitore",
  	"servizioApplicativoFruitore",
  	"identificativoAutenticato",
  	"token",
  	"erogatore",
  	"servizioApplicativoErogatore",
  	"servizio",
  	"azione",
  	"informazioneApplicativaEnabled",
  	"informazioneApplicativaTipo",
  	"informazioneApplicativaNome"
  }
)

@XmlRootElement(name = "attivazione-policy-raggruppamento")

public class AttivazionePolicyRaggruppamento extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public AttivazionePolicyRaggruppamento() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return Long.valueOf(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=Long.valueOf(-1);
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public boolean getEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isRuoloPorta() {
    return this.ruoloPorta;
  }

  public boolean getRuoloPorta() {
    return this.ruoloPorta;
  }

  public void setRuoloPorta(boolean ruoloPorta) {
    this.ruoloPorta = ruoloPorta;
  }

  public boolean isProtocollo() {
    return this.protocollo;
  }

  public boolean getProtocollo() {
    return this.protocollo;
  }

  public void setProtocollo(boolean protocollo) {
    this.protocollo = protocollo;
  }

  public boolean isFruitore() {
    return this.fruitore;
  }

  public boolean getFruitore() {
    return this.fruitore;
  }

  public void setFruitore(boolean fruitore) {
    this.fruitore = fruitore;
  }

  public boolean isServizioApplicativoFruitore() {
    return this.servizioApplicativoFruitore;
  }

  public boolean getServizioApplicativoFruitore() {
    return this.servizioApplicativoFruitore;
  }

  public void setServizioApplicativoFruitore(boolean servizioApplicativoFruitore) {
    this.servizioApplicativoFruitore = servizioApplicativoFruitore;
  }

  public boolean isIdentificativoAutenticato() {
    return this.identificativoAutenticato;
  }

  public boolean getIdentificativoAutenticato() {
    return this.identificativoAutenticato;
  }

  public void setIdentificativoAutenticato(boolean identificativoAutenticato) {
    this.identificativoAutenticato = identificativoAutenticato;
  }

  public java.lang.String getToken() {
    return this.token;
  }

  public void setToken(java.lang.String token) {
    this.token = token;
  }

  public boolean isErogatore() {
    return this.erogatore;
  }

  public boolean getErogatore() {
    return this.erogatore;
  }

  public void setErogatore(boolean erogatore) {
    this.erogatore = erogatore;
  }

  public boolean isServizioApplicativoErogatore() {
    return this.servizioApplicativoErogatore;
  }

  public boolean getServizioApplicativoErogatore() {
    return this.servizioApplicativoErogatore;
  }

  public void setServizioApplicativoErogatore(boolean servizioApplicativoErogatore) {
    this.servizioApplicativoErogatore = servizioApplicativoErogatore;
  }

  public boolean isServizio() {
    return this.servizio;
  }

  public boolean getServizio() {
    return this.servizio;
  }

  public void setServizio(boolean servizio) {
    this.servizio = servizio;
  }

  public boolean isAzione() {
    return this.azione;
  }

  public boolean getAzione() {
    return this.azione;
  }

  public void setAzione(boolean azione) {
    this.azione = azione;
  }

  public boolean isInformazioneApplicativaEnabled() {
    return this.informazioneApplicativaEnabled;
  }

  public boolean getInformazioneApplicativaEnabled() {
    return this.informazioneApplicativaEnabled;
  }

  public void setInformazioneApplicativaEnabled(boolean informazioneApplicativaEnabled) {
    this.informazioneApplicativaEnabled = informazioneApplicativaEnabled;
  }

  public java.lang.String getInformazioneApplicativaTipo() {
    return this.informazioneApplicativaTipo;
  }

  public void setInformazioneApplicativaTipo(java.lang.String informazioneApplicativaTipo) {
    this.informazioneApplicativaTipo = informazioneApplicativaTipo;
  }

  public java.lang.String getInformazioneApplicativaNome() {
    return this.informazioneApplicativaNome;
  }

  public void setInformazioneApplicativaNome(java.lang.String informazioneApplicativaNome) {
    this.informazioneApplicativaNome = informazioneApplicativaNome;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="enabled",required=true,nillable=false,defaultValue="false")
  protected boolean enabled = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="ruolo-porta",required=true,nillable=false,defaultValue="false")
  protected boolean ruoloPorta = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="protocollo",required=true,nillable=false,defaultValue="false")
  protected boolean protocollo = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="fruitore",required=true,nillable=false,defaultValue="false")
  protected boolean fruitore = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="servizio-applicativo-fruitore",required=true,nillable=false,defaultValue="false")
  protected boolean servizioApplicativoFruitore = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="identificativo-autenticato",required=true,nillable=false,defaultValue="false")
  protected boolean identificativoAutenticato = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="token",required=false,nillable=false)
  protected java.lang.String token;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="erogatore",required=true,nillable=false,defaultValue="false")
  protected boolean erogatore = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="servizio-applicativo-erogatore",required=true,nillable=false,defaultValue="false")
  protected boolean servizioApplicativoErogatore = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="servizio",required=true,nillable=false,defaultValue="false")
  protected boolean servizio = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="azione",required=true,nillable=false,defaultValue="false")
  protected boolean azione = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlElement(name="informazione-applicativa-enabled",required=true,nillable=false,defaultValue="false")
  protected boolean informazioneApplicativaEnabled = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="informazione-applicativa-tipo",required=false,nillable=false)
  protected java.lang.String informazioneApplicativaTipo;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="informazione-applicativa-nome",required=false,nillable=false)
  protected java.lang.String informazioneApplicativaNome;

}
