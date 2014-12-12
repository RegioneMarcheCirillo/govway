/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2014 Link.it srl (http://link.it). All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

package org.openspcoop2.protocol.sdk.constants;


/**
 * SubCodiceErrore
 *
 * @author Andrea Poli <apoli@link.it>
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class SubCodiceErrore {

	private Integer subCodice = null;
	
	public Integer getSubCodice() {
		return this.subCodice;
	}
	public void setSubCodice(Integer subCodice) {
		if(subCodice!=null && subCodice>=0){
			// non devo impostare un subcode minore di 0
			this.subCodice = subCodice;
		}
	}
	
	@Override
	public SubCodiceErrore clone(){
		SubCodiceErrore sub = new SubCodiceErrore();
		sub.setSubCodice(this.subCodice!=null ? new Integer(this.subCodice.intValue()) : null);
		return sub;
	}
}
