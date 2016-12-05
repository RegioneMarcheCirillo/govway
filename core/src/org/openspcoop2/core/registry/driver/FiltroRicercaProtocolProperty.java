/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it). 
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



package org.openspcoop2.core.registry.driver;

import java.io.Serializable;

/**
 * Permette il filtro di ricerca attraverso i driver che implementano l'interfaccia 'get'
 * 
 * 
 * @author Poli Andrea (apoli@link.it)
 * @author Nardi Lorenzo (nardi@link.it)
 * @author $Author: apoli $
 * @version $Rev: 12237 $, $Date: 2016-10-04 11:41:45 +0200 (Tue, 04 Oct 2016) $
 */

public class FiltroRicercaProtocolProperty implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Nome */
	private String nome;
	
	/** Nome */
	private String valore;
	
	/** Nome */
	private Long valoreNumerico;
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValore() {
		return this.valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public Long getValoreNumerico() {
		return this.valoreNumerico;
	}
	public void setValoreNumerico(Long valoreNumerico) {
		this.valoreNumerico = valoreNumerico;
	}
	
	@Override
	public String toString(){
		return this.toString(true);
	}
	public String toString(boolean checkEmpty){
		StringBuffer bf = new StringBuffer();
		bf.append("FiltroProtocolProperty: ");
		this.addDetails(bf);
		if(checkEmpty){
			if(bf.length()=="FiltroProtocolProperty: ".length()){
				bf.append(" nessun filtro presente");
			}
		}
		return bf.toString();
	}
	public void addDetails(StringBuffer bf){
		if(this.nome!=null)
			bf.append(" [nome:"+this.nome+"]");
		if(this.valore!=null)
			bf.append(" [valore:"+this.valore+"]");
		if(this.valoreNumerico!=null)
			bf.append(" [valore-numerico:"+this.valoreNumerico+"]");
	}
}
