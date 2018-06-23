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
package org.openspcoop2.protocol.information_missing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.protocol.information_missing.constants.FruitoreReplaceType;
import org.openspcoop2.protocol.information_missing.constants.StatoType;
import java.io.Serializable;


/** <p>Java class for Fruitore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Fruitore">
 * 		&lt;sequence>
 * 			&lt;element name="replace-match" type="{http://www.openspcoop2.org/protocol/information_missing}replaceMatchType" minOccurs="1" maxOccurs="1"/>
 * 		&lt;/sequence>
 * 		&lt;attribute name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" use="required"/>
 * 		&lt;attribute name="tipo" type="{http://www.openspcoop2.org/protocol/information_missing}FruitoreReplaceType" use="required"/>
 * 		&lt;attribute name="stato" type="{http://www.openspcoop2.org/protocol/information_missing}statoType" use="optional"/>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fruitore", 
  propOrder = {
  	"replaceMatch"
  }
)

@XmlRootElement(name = "Fruitore")

public class Fruitore extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Fruitore() {
  }

  public ReplaceMatchType getReplaceMatch() {
    return this.replaceMatch;
  }

  public void setReplaceMatch(ReplaceMatchType replaceMatch) {
    this.replaceMatch = replaceMatch;
  }

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  public void set_value_tipo(String value) {
    this.tipo = (FruitoreReplaceType) FruitoreReplaceType.toEnumConstantFromString(value);
  }

  public String get_value_tipo() {
    if(this.tipo == null){
    	return null;
    }else{
    	return this.tipo.toString();
    }
  }

  public org.openspcoop2.protocol.information_missing.constants.FruitoreReplaceType getTipo() {
    return this.tipo;
  }

  public void setTipo(org.openspcoop2.protocol.information_missing.constants.FruitoreReplaceType tipo) {
    this.tipo = tipo;
  }

  public void set_value_stato(String value) {
    this.stato = (StatoType) StatoType.toEnumConstantFromString(value);
  }

  public String get_value_stato() {
    if(this.stato == null){
    	return null;
    }else{
    	return this.stato.toString();
    }
  }

  public org.openspcoop2.protocol.information_missing.constants.StatoType getStato() {
    return this.stato;
  }

  public void setStato(org.openspcoop2.protocol.information_missing.constants.StatoType stato) {
    this.stato = stato;
  }

  private static final long serialVersionUID = 1L;



  @XmlElement(name="replace-match",required=true,nillable=false)
  protected ReplaceMatchType replaceMatch;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlAttribute(name="descrizione",required=true)
  protected java.lang.String descrizione;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.String _value_tipo;

  @XmlAttribute(name="tipo",required=true)
  protected FruitoreReplaceType tipo;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.String _value_stato;

  @XmlAttribute(name="stato",required=false)
  protected StatoType stato;

}
