/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it).
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
package org.openspcoop2.web.monitor.core.constants;


/****
 * 
 * Classe per gestire l'enumerazione dei ruoli utente possibili
 * 
 * 
 * @author Pintori Giuliano (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 *
 */
public enum ModalitaRicercaTransazioni {

	ANDAMENTO_TEMPORALE ("intervalloTemporale"),
	RICERCA_LIBERA ("ricercaLibera"),
	MITTENTE_TOKEN_INFO ("mittenteTokenInfo"),
	MITTENTE_SOGGETTO ("mittenteSoggetto"),
	MITTENTE_APPLICATIVO ("mittenteApplicativo"),
	MITTENTE_IDENTIFICATIVO_AUTENTICATO ("mittenteIdentificativoAutenticato"),
	MITTENTE_INDIRIZZO_IP ("mittenteIndirizzoIP"),
	ID_APPLICATIVO ("idApplicativo"), 
	ID_MESSAGGIO ("idMessaggio"),
	ID_TRANSAZIONE ("idTransazione");
	
	private String value;
	ModalitaRicercaTransazioni(String ruolo) {
		this.value = ruolo;
	}
	
	@Override
	public String toString(){
		return this.value;
	}
	
	public String getValue() {
		return this.value;
	}

	public static ModalitaRicercaTransazioni getFromString(String v){
		ModalitaRicercaTransazioni res = null;
		for (ModalitaRicercaTransazioni tmp : values()) {
			if(tmp.getValue().equals(v)){
				res = tmp;
				break;
			}
		}
		return res;
	}
	
}
