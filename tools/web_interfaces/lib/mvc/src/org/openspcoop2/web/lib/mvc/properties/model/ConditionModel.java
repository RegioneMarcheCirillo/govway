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
package org.openspcoop2.web.lib.mvc.properties.model;

import org.openspcoop2.web.lib.mvc.properties.Condition;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model Condition 
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ConditionModel extends AbstractModel<Condition> {

	public ConditionModel(){
	
		super();
	
		this.SELECTED = new org.openspcoop2.web.lib.mvc.properties.model.SelectedModel(new Field("selected",org.openspcoop2.web.lib.mvc.properties.Selected.class,"condition",Condition.class));
		this.EQUALS = new org.openspcoop2.web.lib.mvc.properties.model.EqualsModel(new Field("equals",org.openspcoop2.web.lib.mvc.properties.Equals.class,"condition",Condition.class));
		this.DEFINED = new org.openspcoop2.web.lib.mvc.properties.model.DefinedModel(new Field("defined",org.openspcoop2.web.lib.mvc.properties.Defined.class,"condition",Condition.class));
		this.AND = new Field("and",boolean.class,"condition",Condition.class);
		this.NOT = new Field("not",boolean.class,"condition",Condition.class);
	
	}
	
	public ConditionModel(IField father){
	
		super(father);
	
		this.SELECTED = new org.openspcoop2.web.lib.mvc.properties.model.SelectedModel(new ComplexField(father,"selected",org.openspcoop2.web.lib.mvc.properties.Selected.class,"condition",Condition.class));
		this.EQUALS = new org.openspcoop2.web.lib.mvc.properties.model.EqualsModel(new ComplexField(father,"equals",org.openspcoop2.web.lib.mvc.properties.Equals.class,"condition",Condition.class));
		this.DEFINED = new org.openspcoop2.web.lib.mvc.properties.model.DefinedModel(new ComplexField(father,"defined",org.openspcoop2.web.lib.mvc.properties.Defined.class,"condition",Condition.class));
		this.AND = new ComplexField(father,"and",boolean.class,"condition",Condition.class);
		this.NOT = new ComplexField(father,"not",boolean.class,"condition",Condition.class);
	
	}
	
	

	public org.openspcoop2.web.lib.mvc.properties.model.SelectedModel SELECTED = null;
	 
	public org.openspcoop2.web.lib.mvc.properties.model.EqualsModel EQUALS = null;
	 
	public org.openspcoop2.web.lib.mvc.properties.model.DefinedModel DEFINED = null;
	 
	public IField AND = null;
	 
	public IField NOT = null;
	 

	@Override
	public Class<Condition> getModeledClass(){
		return Condition.class;
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