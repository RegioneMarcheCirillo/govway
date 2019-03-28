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
package org.openspcoop2.core.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for trasformazioni complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="trasformazioni">
 * 		&lt;sequence>
 * 			&lt;element name="regola" type="{http://www.openspcoop2.org/core/config}trasformazione-regola" minOccurs="1" maxOccurs="unbounded"/>
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
@XmlType(name = "trasformazioni", 
  propOrder = {
  	"regola"
  }
)

@XmlRootElement(name = "trasformazioni")

public class Trasformazioni extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Trasformazioni() {
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

  public void addRegola(TrasformazioneRegola regola) {
    this.regola.add(regola);
  }

  public TrasformazioneRegola getRegola(int index) {
    return this.regola.get( index );
  }

  public TrasformazioneRegola removeRegola(int index) {
    return this.regola.remove( index );
  }

  public List<TrasformazioneRegola> getRegolaList() {
    return this.regola;
  }

  public void setRegolaList(List<TrasformazioneRegola> regola) {
    this.regola=regola;
  }

  public int sizeRegolaList() {
    return this.regola.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @XmlElement(name="regola",required=true,nillable=false)
  protected List<TrasformazioneRegola> regola = new ArrayList<TrasformazioneRegola>();

  /**
   * @deprecated Use method getRegolaList
   * @return List<TrasformazioneRegola>
  */
  @Deprecated
  public List<TrasformazioneRegola> getRegola() {
  	return this.regola;
  }

  /**
   * @deprecated Use method setRegolaList
   * @param regola List<TrasformazioneRegola>
  */
  @Deprecated
  public void setRegola(List<TrasformazioneRegola> regola) {
  	this.regola=regola;
  }

  /**
   * @deprecated Use method sizeRegolaList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeRegola() {
  	return this.regola.size();
  }

}
