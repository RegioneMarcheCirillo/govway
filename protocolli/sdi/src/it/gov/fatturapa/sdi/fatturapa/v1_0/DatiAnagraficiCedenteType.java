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
package it.gov.fatturapa.sdi.fatturapa.v1_0;

import it.gov.fatturapa.sdi.fatturapa.v1_0.constants.RegimeFiscaleType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for DatiAnagraficiCedenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAnagraficiCedenteType">
 * 		&lt;sequence>
 * 			&lt;element name="IdFiscaleIVA" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}IdFiscaleType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="CodiceFiscale" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="Anagrafica" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}AnagraficaType" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="AlboProfessionale" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="ProvinciaAlbo" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="NumeroIscrizioneAlbo" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}normalizedString" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="DataIscrizioneAlbo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="RegimeFiscale" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}RegimeFiscaleType" minOccurs="1" maxOccurs="1"/>
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
@XmlType(name = "DatiAnagraficiCedenteType", 
  propOrder = {
  	"idFiscaleIVA",
  	"codiceFiscale",
  	"anagrafica",
  	"alboProfessionale",
  	"provinciaAlbo",
  	"numeroIscrizioneAlbo",
  	"dataIscrizioneAlbo",
  	"regimeFiscale"
  }
)

@XmlRootElement(name = "DatiAnagraficiCedenteType")

public class DatiAnagraficiCedenteType extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public DatiAnagraficiCedenteType() {
  }

  public IdFiscaleType getIdFiscaleIVA() {
    return this.idFiscaleIVA;
  }

  public void setIdFiscaleIVA(IdFiscaleType idFiscaleIVA) {
    this.idFiscaleIVA = idFiscaleIVA;
  }

  public java.lang.String getCodiceFiscale() {
    return this.codiceFiscale;
  }

  public void setCodiceFiscale(java.lang.String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public AnagraficaType getAnagrafica() {
    return this.anagrafica;
  }

  public void setAnagrafica(AnagraficaType anagrafica) {
    this.anagrafica = anagrafica;
  }

  public java.lang.String getAlboProfessionale() {
    return this.alboProfessionale;
  }

  public void setAlboProfessionale(java.lang.String alboProfessionale) {
    this.alboProfessionale = alboProfessionale;
  }

  public java.lang.String getProvinciaAlbo() {
    return this.provinciaAlbo;
  }

  public void setProvinciaAlbo(java.lang.String provinciaAlbo) {
    this.provinciaAlbo = provinciaAlbo;
  }

  public java.lang.String getNumeroIscrizioneAlbo() {
    return this.numeroIscrizioneAlbo;
  }

  public void setNumeroIscrizioneAlbo(java.lang.String numeroIscrizioneAlbo) {
    this.numeroIscrizioneAlbo = numeroIscrizioneAlbo;
  }

  public java.util.Date getDataIscrizioneAlbo() {
    return this.dataIscrizioneAlbo;
  }

  public void setDataIscrizioneAlbo(java.util.Date dataIscrizioneAlbo) {
    this.dataIscrizioneAlbo = dataIscrizioneAlbo;
  }

  public void set_value_regimeFiscale(String value) {
    this.regimeFiscale = (RegimeFiscaleType) RegimeFiscaleType.toEnumConstantFromString(value);
  }

  public String get_value_regimeFiscale() {
    if(this.regimeFiscale == null){
    	return null;
    }else{
    	return this.regimeFiscale.toString();
    }
  }

  public it.gov.fatturapa.sdi.fatturapa.v1_0.constants.RegimeFiscaleType getRegimeFiscale() {
    return this.regimeFiscale;
  }

  public void setRegimeFiscale(it.gov.fatturapa.sdi.fatturapa.v1_0.constants.RegimeFiscaleType regimeFiscale) {
    this.regimeFiscale = regimeFiscale;
  }

  private static final long serialVersionUID = 1L;



  @XmlElement(name="IdFiscaleIVA",required=true,nillable=false)
  protected IdFiscaleType idFiscaleIVA;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="CodiceFiscale",required=false,nillable=false)
  protected java.lang.String codiceFiscale;

  @XmlElement(name="Anagrafica",required=true,nillable=false)
  protected AnagraficaType anagrafica;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="AlboProfessionale",required=false,nillable=false)
  protected java.lang.String alboProfessionale;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="ProvinciaAlbo",required=false,nillable=false)
  protected java.lang.String provinciaAlbo;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="NumeroIscrizioneAlbo",required=false,nillable=false)
  protected java.lang.String numeroIscrizioneAlbo;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Date2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="date")
  @XmlElement(name="DataIscrizioneAlbo",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataIscrizioneAlbo;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.String _value_regimeFiscale;

  @XmlElement(name="RegimeFiscale",required=true,nillable=false)
  protected RegimeFiscaleType regimeFiscale;

}
