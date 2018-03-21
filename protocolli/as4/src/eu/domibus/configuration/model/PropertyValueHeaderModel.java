/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
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
package eu.domibus.configuration.model;

import eu.domibus.configuration.PropertyValueHeader;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model PropertyValueHeader 
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PropertyValueHeaderModel extends AbstractModel<PropertyValueHeader> {

	public PropertyValueHeaderModel(){
	
		super();
	
		this.NAME = new Field("name",java.lang.String.class,"PropertyValueHeader",PropertyValueHeader.class);
		this.PATTERN = new Field("pattern",java.lang.String.class,"PropertyValueHeader",PropertyValueHeader.class);
	
	}
	
	public PropertyValueHeaderModel(IField father){
	
		super(father);
	
		this.NAME = new ComplexField(father,"name",java.lang.String.class,"PropertyValueHeader",PropertyValueHeader.class);
		this.PATTERN = new ComplexField(father,"pattern",java.lang.String.class,"PropertyValueHeader",PropertyValueHeader.class);
	
	}
	
	

	public IField NAME = null;
	 
	public IField PATTERN = null;
	 

	@Override
	public Class<PropertyValueHeader> getModeledClass(){
		return PropertyValueHeader.class;
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