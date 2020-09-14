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
package it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for FatturaElettronicaBodyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FatturaElettronicaBodyType"&gt;
 * 		&lt;sequence&gt;
 * 			&lt;element name="DatiGenerali" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.0}DatiGeneraliType" minOccurs="1" maxOccurs="1"/&gt;
 * 			&lt;element name="DatiBeniServizi" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.0}DatiBeniServiziType" minOccurs="1" maxOccurs="unbounded"/&gt;
 * 			&lt;element name="Allegati" type="{http://ivaservizi.agenziaentrate.gov.it/docs/xsd/fatture/v1.0}AllegatiType" minOccurs="0" maxOccurs="unbounded"/&gt;
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
@XmlType(name = "FatturaElettronicaBodyType", 
  propOrder = {
  	"datiGenerali",
  	"datiBeniServizi",
  	"allegati"
  }
)

@XmlRootElement(name = "FatturaElettronicaBodyType")

public class FatturaElettronicaBodyType extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public FatturaElettronicaBodyType() {
  }

  public DatiGeneraliType getDatiGenerali() {
    return this.datiGenerali;
  }

  public void setDatiGenerali(DatiGeneraliType datiGenerali) {
    this.datiGenerali = datiGenerali;
  }

  public void addDatiBeniServizi(DatiBeniServiziType datiBeniServizi) {
    this.datiBeniServizi.add(datiBeniServizi);
  }

  public DatiBeniServiziType getDatiBeniServizi(int index) {
    return this.datiBeniServizi.get( index );
  }

  public DatiBeniServiziType removeDatiBeniServizi(int index) {
    return this.datiBeniServizi.remove( index );
  }

  public List<DatiBeniServiziType> getDatiBeniServiziList() {
    return this.datiBeniServizi;
  }

  public void setDatiBeniServiziList(List<DatiBeniServiziType> datiBeniServizi) {
    this.datiBeniServizi=datiBeniServizi;
  }

  public int sizeDatiBeniServiziList() {
    return this.datiBeniServizi.size();
  }

  public void addAllegati(AllegatiType allegati) {
    this.allegati.add(allegati);
  }

  public AllegatiType getAllegati(int index) {
    return this.allegati.get( index );
  }

  public AllegatiType removeAllegati(int index) {
    return this.allegati.remove( index );
  }

  public List<AllegatiType> getAllegatiList() {
    return this.allegati;
  }

  public void setAllegatiList(List<AllegatiType> allegati) {
    this.allegati=allegati;
  }

  public int sizeAllegatiList() {
    return this.allegati.size();
  }

  private static final long serialVersionUID = 1L;



  @XmlElement(name="DatiGenerali",required=true,nillable=false)
  protected DatiGeneraliType datiGenerali;

  @XmlElement(name="DatiBeniServizi",required=true,nillable=false)
  protected List<DatiBeniServiziType> datiBeniServizi = new ArrayList<DatiBeniServiziType>();

  /**
   * @deprecated Use method getDatiBeniServiziList
   * @return List&lt;DatiBeniServiziType&gt;
  */
  @Deprecated
  public List<DatiBeniServiziType> getDatiBeniServizi() {
  	return this.datiBeniServizi;
  }

  /**
   * @deprecated Use method setDatiBeniServiziList
   * @param datiBeniServizi List&lt;DatiBeniServiziType&gt;
  */
  @Deprecated
  public void setDatiBeniServizi(List<DatiBeniServiziType> datiBeniServizi) {
  	this.datiBeniServizi=datiBeniServizi;
  }

  /**
   * @deprecated Use method sizeDatiBeniServiziList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeDatiBeniServizi() {
  	return this.datiBeniServizi.size();
  }

  @XmlElement(name="Allegati",required=true,nillable=false)
  protected List<AllegatiType> allegati = new ArrayList<AllegatiType>();

  /**
   * @deprecated Use method getAllegatiList
   * @return List&lt;AllegatiType&gt;
  */
  @Deprecated
  public List<AllegatiType> getAllegati() {
  	return this.allegati;
  }

  /**
   * @deprecated Use method setAllegatiList
   * @param allegati List&lt;AllegatiType&gt;
  */
  @Deprecated
  public void setAllegati(List<AllegatiType> allegati) {
  	this.allegati=allegati;
  }

  /**
   * @deprecated Use method sizeAllegatiList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeAllegati() {
  	return this.allegati.size();
  }

}