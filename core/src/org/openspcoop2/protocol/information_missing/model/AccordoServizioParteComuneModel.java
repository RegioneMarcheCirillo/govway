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
package org.openspcoop2.protocol.information_missing.model;

import org.openspcoop2.protocol.information_missing.AccordoServizioParteComune;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model AccordoServizioParteComune 
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class AccordoServizioParteComuneModel extends AbstractModel<AccordoServizioParteComune> {

	public AccordoServizioParteComuneModel(){
	
		super();
	
		this.REPLACE_MATCH = new org.openspcoop2.protocol.information_missing.model.ReplaceMatchTypeModel(new Field("replace-match",org.openspcoop2.protocol.information_missing.ReplaceMatchType.class,"accordo-servizio-parte-comune",AccordoServizioParteComune.class));
		this.DESCRIZIONE = new Field("descrizione",java.lang.String.class,"accordo-servizio-parte-comune",AccordoServizioParteComune.class);
		this.TIPO = new Field("tipo",java.lang.String.class,"accordo-servizio-parte-comune",AccordoServizioParteComune.class);
		this.STATO = new Field("stato",java.lang.String.class,"accordo-servizio-parte-comune",AccordoServizioParteComune.class);
	
	}
	
	public AccordoServizioParteComuneModel(IField father){
	
		super(father);
	
		this.REPLACE_MATCH = new org.openspcoop2.protocol.information_missing.model.ReplaceMatchTypeModel(new ComplexField(father,"replace-match",org.openspcoop2.protocol.information_missing.ReplaceMatchType.class,"accordo-servizio-parte-comune",AccordoServizioParteComune.class));
		this.DESCRIZIONE = new ComplexField(father,"descrizione",java.lang.String.class,"accordo-servizio-parte-comune",AccordoServizioParteComune.class);
		this.TIPO = new ComplexField(father,"tipo",java.lang.String.class,"accordo-servizio-parte-comune",AccordoServizioParteComune.class);
		this.STATO = new ComplexField(father,"stato",java.lang.String.class,"accordo-servizio-parte-comune",AccordoServizioParteComune.class);
	
	}
	
	

	public org.openspcoop2.protocol.information_missing.model.ReplaceMatchTypeModel REPLACE_MATCH = null;
	 
	public IField DESCRIZIONE = null;
	 
	public IField TIPO = null;
	 
	public IField STATO = null;
	 

	@Override
	public Class<AccordoServizioParteComune> getModeledClass(){
		return AccordoServizioParteComune.class;
	}
	
	@Override
	public String toString(){
		if(this.getModeledClass()!=null){
			return this.getModeledClass().getName();
		}else{
			return "N.D.";
		}
	}

}