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
package org.openspcoop2.core.registry.ws.server.wrapped;

/**
 * <p>Java class for DeleteAllAccordoServizioParteComuneResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteAllResponse">
 *     &lt;sequence>
 *         &lt;element name="deletedItems" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="1" />
 *     &lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
 
import java.io.Serializable;
 
import javax.xml.bind.annotation.XmlElement;

/**     
 * DeleteAllAccordoServizioParteComuneResponse
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(name = "deleteAllResponse", namespace="http://www.openspcoop2.org/core/registry/management", propOrder = {
    "deletedItems"
})
@javax.xml.bind.annotation.XmlRootElement(name = "deleteAllResponse")
public class DeleteAllAccordoServizioParteComuneResponse extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
	
	private static final long serialVersionUID = -1L;
	
	@javax.xml.bind.annotation.XmlSchemaType(name="long")
  @XmlElement(name="deletedItems",required=true,nillable=false)
	private Long deletedItems;
	
	public void setDeletedItems(Long deletedItems){
		this.deletedItems = deletedItems;
	}
	
	public Long getDeletedItems(){
		return this.deletedItems;
	}
	
	
	
	
}