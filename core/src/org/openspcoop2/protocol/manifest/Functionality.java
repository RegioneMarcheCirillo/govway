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
package org.openspcoop2.protocol.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for Functionality complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Functionality">
 * 		&lt;attribute name="duplicateFilter" type="{http://www.w3.org/2001/XMLSchema}boolean" use="optional" default="false"/>
 * 		&lt;attribute name="acknowledgement" type="{http://www.w3.org/2001/XMLSchema}boolean" use="optional" default="false"/>
 * 		&lt;attribute name="conversationIdentifier" type="{http://www.w3.org/2001/XMLSchema}boolean" use="optional" default="false"/>
 * 		&lt;attribute name="referenceToRequestIdentifier" type="{http://www.w3.org/2001/XMLSchema}boolean" use="optional" default="false"/>
 * 		&lt;attribute name="deliveryOrder" type="{http://www.w3.org/2001/XMLSchema}boolean" use="optional" default="false"/>
 * 		&lt;attribute name="expiration" type="{http://www.w3.org/2001/XMLSchema}boolean" use="optional" default="false"/>
 * 		&lt;attribute name="manifestAttachments" type="{http://www.w3.org/2001/XMLSchema}boolean" use="optional" default="false"/>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Functionality")

@XmlRootElement(name = "Functionality")

public class Functionality extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Functionality() {
  }

  public boolean isDuplicateFilter() {
    return this.duplicateFilter;
  }

  public boolean getDuplicateFilter() {
    return this.duplicateFilter;
  }

  public void setDuplicateFilter(boolean duplicateFilter) {
    this.duplicateFilter = duplicateFilter;
  }

  public boolean isAcknowledgement() {
    return this.acknowledgement;
  }

  public boolean getAcknowledgement() {
    return this.acknowledgement;
  }

  public void setAcknowledgement(boolean acknowledgement) {
    this.acknowledgement = acknowledgement;
  }

  public boolean isConversationIdentifier() {
    return this.conversationIdentifier;
  }

  public boolean getConversationIdentifier() {
    return this.conversationIdentifier;
  }

  public void setConversationIdentifier(boolean conversationIdentifier) {
    this.conversationIdentifier = conversationIdentifier;
  }

  public boolean isReferenceToRequestIdentifier() {
    return this.referenceToRequestIdentifier;
  }

  public boolean getReferenceToRequestIdentifier() {
    return this.referenceToRequestIdentifier;
  }

  public void setReferenceToRequestIdentifier(boolean referenceToRequestIdentifier) {
    this.referenceToRequestIdentifier = referenceToRequestIdentifier;
  }

  public boolean isDeliveryOrder() {
    return this.deliveryOrder;
  }

  public boolean getDeliveryOrder() {
    return this.deliveryOrder;
  }

  public void setDeliveryOrder(boolean deliveryOrder) {
    this.deliveryOrder = deliveryOrder;
  }

  public boolean isExpiration() {
    return this.expiration;
  }

  public boolean getExpiration() {
    return this.expiration;
  }

  public void setExpiration(boolean expiration) {
    this.expiration = expiration;
  }

  public boolean isManifestAttachments() {
    return this.manifestAttachments;
  }

  public boolean getManifestAttachments() {
    return this.manifestAttachments;
  }

  public void setManifestAttachments(boolean manifestAttachments) {
    this.manifestAttachments = manifestAttachments;
  }

  private static final long serialVersionUID = 1L;



  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlAttribute(name="duplicateFilter",required=false)
  protected boolean duplicateFilter = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlAttribute(name="acknowledgement",required=false)
  protected boolean acknowledgement = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlAttribute(name="conversationIdentifier",required=false)
  protected boolean conversationIdentifier = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlAttribute(name="referenceToRequestIdentifier",required=false)
  protected boolean referenceToRequestIdentifier = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlAttribute(name="deliveryOrder",required=false)
  protected boolean deliveryOrder = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlAttribute(name="expiration",required=false)
  protected boolean expiration = false;

  @javax.xml.bind.annotation.XmlSchemaType(name="boolean")
  @XmlAttribute(name="manifestAttachments",required=false)
  protected boolean manifestAttachments = false;

}
