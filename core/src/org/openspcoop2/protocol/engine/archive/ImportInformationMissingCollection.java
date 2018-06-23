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

package org.openspcoop2.protocol.engine.archive;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import org.openspcoop2.protocol.sdk.ProtocolException;

/**
 *  ImportInformationMissingCollection
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ImportInformationMissingCollection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String,ImportInformationMissing> importInformationMissing = new Hashtable<String, ImportInformationMissing>();


	public Hashtable<String, ImportInformationMissing> getImportInformationMissing() {
		return this.importInformationMissing;
	}
	public void setImportInformationMissing(
			Hashtable<String, ImportInformationMissing> importInformationMissing) {
		this.importInformationMissing = importInformationMissing;
	}
	
	public void add(String objectId,ImportInformationMissing importInformationMissing) throws ProtocolException{
		if(this.importInformationMissing.containsKey(objectId)){
			throw new ProtocolException("ImportInformationMissing with id ["+objectId+"] already exists");
		}
		this.importInformationMissing.put(objectId,importInformationMissing);
	}
	public ImportInformationMissing get(String objectId){
		return this.importInformationMissing.get(objectId);
	}
	public ImportInformationMissing remove(String objectId){
		return this.importInformationMissing.remove(objectId);
	}
	public boolean exists(String objectId){
		return this.importInformationMissing.containsKey(objectId);
	}
	public int size(){
		return this.importInformationMissing.size();
	}
	public Enumeration<String> keys(){
		return this.importInformationMissing.keys();
	}
}
