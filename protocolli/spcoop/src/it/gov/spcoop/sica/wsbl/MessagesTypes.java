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
package it.gov.spcoop.sica.wsbl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for messagesTypes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messagesTypes">
 * 		&lt;sequence>
 * 			&lt;element name="message" type="{http://spcoop.gov.it/sica/wsbl}message" minOccurs="1" maxOccurs="unbounded"/>
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
@XmlType(name = "messagesTypes", 
  propOrder = {
  	"message"
  }
)

@XmlRootElement(name = "messagesTypes")

public class MessagesTypes extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public MessagesTypes() {
  }

  public void addMessage(Message message) {
    this.message.add(message);
  }

  public Message getMessage(int index) {
    return this.message.get( index );
  }

  public Message removeMessage(int index) {
    return this.message.remove( index );
  }

  public List<Message> getMessageList() {
    return this.message;
  }

  public void setMessageList(List<Message> message) {
    this.message=message;
  }

  public int sizeMessageList() {
    return this.message.size();
  }

  private static final long serialVersionUID = 1L;



  @XmlElement(name="message",required=true,nillable=false)
  protected List<Message> message = new ArrayList<Message>();

  /**
   * @deprecated Use method getMessageList
   * @return List<Message>
  */
  @Deprecated
  public List<Message> getMessage() {
  	return this.message;
  }

  /**
   * @deprecated Use method setMessageList
   * @param message List<Message>
  */
  @Deprecated
  public void setMessage(List<Message> message) {
  	this.message=message;
  }

  /**
   * @deprecated Use method sizeMessageList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeMessage() {
  	return this.message.size();
  }

}
