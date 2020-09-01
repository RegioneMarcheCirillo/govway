/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it).
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
package it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2;

import it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.RitenutaType;
import it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.TipoCessionePrestazioneType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for DettaglioLineeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioLineeType"&gt;
 * 		&lt;sequence&gt;
 * 			&lt;element name="NumeroLinea" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}integer" minOccurs="1" maxOccurs="1"/&gt;
 * 			&lt;element name="TipoCessionePrestazione" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}TipoCessionePrestazioneType" minOccurs="0" maxOccurs="1"/&gt;
 * 			&lt;element name="CodiceArticolo" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}CodiceArticoloType" minOccurs="0" maxOccurs="unbounded"/&gt;
 * 			&lt;element name="Descrizione" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}normalizedString" minOccurs="1" maxOccurs="1"/&gt;
 * 			&lt;element name="Quantita" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}decimal" minOccurs="0" maxOccurs="1"/&gt;
 * 			&lt;element name="UnitaMisura" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}normalizedString" minOccurs="0" maxOccurs="1"/&gt;
 * 			&lt;element name="DataInizioPeriodo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0" maxOccurs="1"/&gt;
 * 			&lt;element name="DataFinePeriodo" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0" maxOccurs="1"/&gt;
 * 			&lt;element name="PrezzoUnitario" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}decimal" minOccurs="1" maxOccurs="1"/&gt;
 * 			&lt;element name="ScontoMaggiorazione" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}ScontoMaggiorazioneType" minOccurs="0" maxOccurs="unbounded"/&gt;
 * 			&lt;element name="PrezzoTotale" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}decimal" minOccurs="1" maxOccurs="1"/&gt;
 * 			&lt;element name="AliquotaIVA" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}decimal" minOccurs="1" maxOccurs="1"/&gt;
 * 			&lt;element name="Ritenuta" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}RitenutaType" minOccurs="0" maxOccurs="1"/&gt;
 * 			&lt;element name="Natura" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}string" minOccurs="0" maxOccurs="1"/&gt;
 * 			&lt;element name="RiferimentoAmministrazione" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}normalizedString" minOccurs="0" maxOccurs="1"/&gt;
 * 			&lt;element name="AltriDatiGestionali" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.2}AltriDatiGestionaliType" minOccurs="0" maxOccurs="unbounded"/&gt;
 * 		&lt;/sequence&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioLineeType", 
  propOrder = {
  	"numeroLinea",
  	"tipoCessionePrestazione",
  	"codiceArticolo",
  	"descrizione",
  	"_decimalWrapper_quantita",
  	"unitaMisura",
  	"dataInizioPeriodo",
  	"dataFinePeriodo",
  	"_decimalWrapper_prezzoUnitario",
  	"scontoMaggiorazione",
  	"_decimalWrapper_prezzoTotale",
  	"_decimalWrapper_aliquotaIVA",
  	"ritenuta",
  	"natura",
  	"riferimentoAmministrazione",
  	"altriDatiGestionali"
  }
)

@XmlRootElement(name = "DettaglioLineeType")

public class DettaglioLineeType extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public DettaglioLineeType() {
  }

  public java.math.BigInteger getNumeroLinea() {
    return this.numeroLinea;
  }

  public void setNumeroLinea(java.math.BigInteger numeroLinea) {
    this.numeroLinea = numeroLinea;
  }

  public void set_value_tipoCessionePrestazione(String value) {
    this.tipoCessionePrestazione = (TipoCessionePrestazioneType) TipoCessionePrestazioneType.toEnumConstantFromString(value);
  }

  public String get_value_tipoCessionePrestazione() {
    if(this.tipoCessionePrestazione == null){
    	return null;
    }else{
    	return this.tipoCessionePrestazione.toString();
    }
  }

  public it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.TipoCessionePrestazioneType getTipoCessionePrestazione() {
    return this.tipoCessionePrestazione;
  }

  public void setTipoCessionePrestazione(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.TipoCessionePrestazioneType tipoCessionePrestazione) {
    this.tipoCessionePrestazione = tipoCessionePrestazione;
  }

  public void addCodiceArticolo(CodiceArticoloType codiceArticolo) {
    this.codiceArticolo.add(codiceArticolo);
  }

  public CodiceArticoloType getCodiceArticolo(int index) {
    return this.codiceArticolo.get( index );
  }

  public CodiceArticoloType removeCodiceArticolo(int index) {
    return this.codiceArticolo.remove( index );
  }

  public List<CodiceArticoloType> getCodiceArticoloList() {
    return this.codiceArticolo;
  }

  public void setCodiceArticoloList(List<CodiceArticoloType> codiceArticolo) {
    this.codiceArticolo=codiceArticolo;
  }

  public int sizeCodiceArticoloList() {
    return this.codiceArticolo.size();
  }

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public java.math.BigDecimal getQuantita() {
    if(this._decimalWrapper_quantita!=null){
		return (java.math.BigDecimal) this._decimalWrapper_quantita.getObject(java.math.BigDecimal.class);
	}else{
		return this.quantita;
	}
  }

  public void setQuantita(java.math.BigDecimal quantita) {
    if(quantita!=null){
		this._decimalWrapper_quantita = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,12,2,8,quantita);
	}
  }

  public java.lang.String getUnitaMisura() {
    return this.unitaMisura;
  }

  public void setUnitaMisura(java.lang.String unitaMisura) {
    this.unitaMisura = unitaMisura;
  }

  public java.util.Date getDataInizioPeriodo() {
    return this.dataInizioPeriodo;
  }

  public void setDataInizioPeriodo(java.util.Date dataInizioPeriodo) {
    this.dataInizioPeriodo = dataInizioPeriodo;
  }

  public java.util.Date getDataFinePeriodo() {
    return this.dataFinePeriodo;
  }

  public void setDataFinePeriodo(java.util.Date dataFinePeriodo) {
    this.dataFinePeriodo = dataFinePeriodo;
  }

  public java.math.BigDecimal getPrezzoUnitario() {
    if(this._decimalWrapper_prezzoUnitario!=null){
		return (java.math.BigDecimal) this._decimalWrapper_prezzoUnitario.getObject(java.math.BigDecimal.class);
	}else{
		return this.prezzoUnitario;
	}
  }

  public void setPrezzoUnitario(java.math.BigDecimal prezzoUnitario) {
    if(prezzoUnitario!=null){
		this._decimalWrapper_prezzoUnitario = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,11,2,8,prezzoUnitario);
	}
  }

  public void addScontoMaggiorazione(ScontoMaggiorazioneType scontoMaggiorazione) {
    this.scontoMaggiorazione.add(scontoMaggiorazione);
  }

  public ScontoMaggiorazioneType getScontoMaggiorazione(int index) {
    return this.scontoMaggiorazione.get( index );
  }

  public ScontoMaggiorazioneType removeScontoMaggiorazione(int index) {
    return this.scontoMaggiorazione.remove( index );
  }

  public List<ScontoMaggiorazioneType> getScontoMaggiorazioneList() {
    return this.scontoMaggiorazione;
  }

  public void setScontoMaggiorazioneList(List<ScontoMaggiorazioneType> scontoMaggiorazione) {
    this.scontoMaggiorazione=scontoMaggiorazione;
  }

  public int sizeScontoMaggiorazioneList() {
    return this.scontoMaggiorazione.size();
  }

  public java.math.BigDecimal getPrezzoTotale() {
    if(this._decimalWrapper_prezzoTotale!=null){
		return (java.math.BigDecimal) this._decimalWrapper_prezzoTotale.getObject(java.math.BigDecimal.class);
	}else{
		return this.prezzoTotale;
	}
  }

  public void setPrezzoTotale(java.math.BigDecimal prezzoTotale) {
    if(prezzoTotale!=null){
		this._decimalWrapper_prezzoTotale = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,11,2,8,prezzoTotale);
	}
  }

  public java.math.BigDecimal getAliquotaIVA() {
    if(this._decimalWrapper_aliquotaIVA!=null){
		return (java.math.BigDecimal) this._decimalWrapper_aliquotaIVA.getObject(java.math.BigDecimal.class);
	}else{
		return this.aliquotaIVA;
	}
  }

  public void setAliquotaIVA(java.math.BigDecimal aliquotaIVA) {
    if(aliquotaIVA!=null){
		this._decimalWrapper_aliquotaIVA = new org.openspcoop2.utils.jaxb.DecimalWrapper(1,3,2,2,aliquotaIVA);
	}
  }

  public void set_value_ritenuta(String value) {
    this.ritenuta = (RitenutaType) RitenutaType.toEnumConstantFromString(value);
  }

  public String get_value_ritenuta() {
    if(this.ritenuta == null){
    	return null;
    }else{
    	return this.ritenuta.toString();
    }
  }

  public it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.RitenutaType getRitenuta() {
    return this.ritenuta;
  }

  public void setRitenuta(it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.constants.RitenutaType ritenuta) {
    this.ritenuta = ritenuta;
  }

  public java.lang.String getNatura() {
    return this.natura;
  }

  public void setNatura(java.lang.String natura) {
    this.natura = natura;
  }

  public java.lang.String getRiferimentoAmministrazione() {
    return this.riferimentoAmministrazione;
  }

  public void setRiferimentoAmministrazione(java.lang.String riferimentoAmministrazione) {
    this.riferimentoAmministrazione = riferimentoAmministrazione;
  }

  public void addAltriDatiGestionali(AltriDatiGestionaliType altriDatiGestionali) {
    this.altriDatiGestionali.add(altriDatiGestionali);
  }

  public AltriDatiGestionaliType getAltriDatiGestionali(int index) {
    return this.altriDatiGestionali.get( index );
  }

  public AltriDatiGestionaliType removeAltriDatiGestionali(int index) {
    return this.altriDatiGestionali.remove( index );
  }

  public List<AltriDatiGestionaliType> getAltriDatiGestionaliList() {
    return this.altriDatiGestionali;
  }

  public void setAltriDatiGestionaliList(List<AltriDatiGestionaliType> altriDatiGestionali) {
    this.altriDatiGestionali=altriDatiGestionali;
  }

  public int sizeAltriDatiGestionaliList() {
    return this.altriDatiGestionali.size();
  }

  private static final long serialVersionUID = 1L;



  @javax.xml.bind.annotation.XmlSchemaType(name="integer")
  @XmlElement(name="NumeroLinea",required=true,nillable=false)
  protected java.math.BigInteger numeroLinea;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.String _value_tipoCessionePrestazione;

  @XmlElement(name="TipoCessionePrestazione",required=false,nillable=false)
  protected TipoCessionePrestazioneType tipoCessionePrestazione;

  @XmlElement(name="CodiceArticolo",required=true,nillable=false)
  protected List<CodiceArticoloType> codiceArticolo = new ArrayList<CodiceArticoloType>();

  /**
   * @deprecated Use method getCodiceArticoloList
   * @return List&lt;CodiceArticoloType&gt;
  */
  @Deprecated
  public List<CodiceArticoloType> getCodiceArticolo() {
  	return this.codiceArticolo;
  }

  /**
   * @deprecated Use method setCodiceArticoloList
   * @param codiceArticolo List&lt;CodiceArticoloType&gt;
  */
  @Deprecated
  public void setCodiceArticolo(List<CodiceArticoloType> codiceArticolo) {
  	this.codiceArticolo=codiceArticolo;
  }

  /**
   * @deprecated Use method sizeCodiceArticoloList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeCodiceArticolo() {
  	return this.codiceArticolo.size();
  }

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="Descrizione",required=true,nillable=false)
  protected java.lang.String descrizione;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="Quantita",required=false,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_quantita = null;

  @javax.xml.bind.annotation.XmlTransient
  protected java.math.BigDecimal quantita;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="UnitaMisura",required=false,nillable=false)
  protected java.lang.String unitaMisura;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Date2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="date")
  @XmlElement(name="DataInizioPeriodo",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataInizioPeriodo;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Date2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="date")
  @XmlElement(name="DataFinePeriodo",required=false,nillable=false,type=java.lang.String.class)
  protected java.util.Date dataFinePeriodo;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="PrezzoUnitario",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_prezzoUnitario = null;

  @javax.xml.bind.annotation.XmlTransient
  protected java.math.BigDecimal prezzoUnitario;

  @XmlElement(name="ScontoMaggiorazione",required=true,nillable=false)
  protected List<ScontoMaggiorazioneType> scontoMaggiorazione = new ArrayList<ScontoMaggiorazioneType>();

  /**
   * @deprecated Use method getScontoMaggiorazioneList
   * @return List&lt;ScontoMaggiorazioneType&gt;
  */
  @Deprecated
  public List<ScontoMaggiorazioneType> getScontoMaggiorazione() {
  	return this.scontoMaggiorazione;
  }

  /**
   * @deprecated Use method setScontoMaggiorazioneList
   * @param scontoMaggiorazione List&lt;ScontoMaggiorazioneType&gt;
  */
  @Deprecated
  public void setScontoMaggiorazione(List<ScontoMaggiorazioneType> scontoMaggiorazione) {
  	this.scontoMaggiorazione=scontoMaggiorazione;
  }

  /**
   * @deprecated Use method sizeScontoMaggiorazioneList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeScontoMaggiorazione() {
  	return this.scontoMaggiorazione.size();
  }

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="PrezzoTotale",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_prezzoTotale = null;

  @javax.xml.bind.annotation.XmlTransient
  protected java.math.BigDecimal prezzoTotale;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(org.openspcoop2.utils.jaxb.Decimal2String.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="decimal")
  @XmlElement(name="AliquotaIVA",required=true,nillable=false)
  org.openspcoop2.utils.jaxb.DecimalWrapper _decimalWrapper_aliquotaIVA = null;

  @javax.xml.bind.annotation.XmlTransient
  protected java.math.BigDecimal aliquotaIVA;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.String _value_ritenuta;

  @XmlElement(name="Ritenuta",required=false,nillable=false)
  protected RitenutaType ritenuta;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="Natura",required=false,nillable=false)
  protected java.lang.String natura;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.NormalizedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="normalizedString")
  @XmlElement(name="RiferimentoAmministrazione",required=false,nillable=false)
  protected java.lang.String riferimentoAmministrazione;

  @XmlElement(name="AltriDatiGestionali",required=true,nillable=false)
  protected List<AltriDatiGestionaliType> altriDatiGestionali = new ArrayList<AltriDatiGestionaliType>();

  /**
   * @deprecated Use method getAltriDatiGestionaliList
   * @return List&lt;AltriDatiGestionaliType&gt;
  */
  @Deprecated
  public List<AltriDatiGestionaliType> getAltriDatiGestionali() {
  	return this.altriDatiGestionali;
  }

  /**
   * @deprecated Use method setAltriDatiGestionaliList
   * @param altriDatiGestionali List&lt;AltriDatiGestionaliType&gt;
  */
  @Deprecated
  public void setAltriDatiGestionali(List<AltriDatiGestionaliType> altriDatiGestionali) {
  	this.altriDatiGestionali=altriDatiGestionali;
  }

  /**
   * @deprecated Use method sizeAltriDatiGestionaliList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeAltriDatiGestionali() {
  	return this.altriDatiGestionali.size();
  }

}
