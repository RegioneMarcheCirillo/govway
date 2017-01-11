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
package org.openspcoop2.core.tracciamento.model;

import org.openspcoop2.core.tracciamento.DominioSoggetto;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model DominioSoggetto 
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DominioSoggettoModel extends AbstractModel<DominioSoggetto> {

	public DominioSoggettoModel(){
	
		super();
	
		this.BASE = new Field("base",java.lang.String.class,"dominio-soggetto",DominioSoggetto.class);
		this.TIPO = new Field("tipo",java.lang.String.class,"dominio-soggetto",DominioSoggetto.class);
	
	}
	
	public DominioSoggettoModel(IField father){
	
		super(father);
	
		this.BASE = new ComplexField(father,"base",java.lang.String.class,"dominio-soggetto",DominioSoggetto.class);
		this.TIPO = new ComplexField(father,"tipo",java.lang.String.class,"dominio-soggetto",DominioSoggetto.class);
	
	}
	
	

	public IField BASE = null;
	 
	public IField TIPO = null;
	 

	@Override
	public Class<DominioSoggetto> getModeledClass(){
		return DominioSoggetto.class;
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