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

package org.openspcoop2.web.ctrlstat.servlet.soggetti;

import java.util.HashMap;
import java.util.List;

import org.openspcoop2.core.commons.ErrorsHandlerCostant;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.Soggetto;
import org.openspcoop2.protocol.engine.utils.DBOggettiInUsoUtils;
import org.openspcoop2.web.ctrlstat.dao.SoggettoCtrlStat;
import org.openspcoop2.web.ctrlstat.servlet.pdd.PddCore;
import org.openspcoop2.web.ctrlstat.servlet.utenti.UtentiCore;

/**
 * SoggettiUtilities
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class SoggettiUtilities {

	public static List<Object> getOggettiDaAggiornare(SoggettiCore soggettiCore,
			String oldnomeprov,String nomeprov,
			String oldtipoprov,String tipoprov,
			SoggettoCtrlStat sog) throws Exception{
		
		// Oggetti da modificare (per riflettere la modifica sul connettore)
		SoggettoUpdateUtilities soggettoUpdateUtilities = 
				new SoggettoUpdateUtilities(soggettiCore, oldnomeprov, nomeprov, oldtipoprov, tipoprov, sog);

		// Soggetto
		// aggiungo il soggetto da aggiornare
		soggettoUpdateUtilities.addSoggetto();

		// Servizi Applicativi
		// Se e' cambiato il tipo o il nome del soggetto devo effettuare la modifica dei servizi applicativi
		// poiche il cambio si riflette sul nome dei connettori del servizio applicativo
		soggettoUpdateUtilities.checkServiziApplicativi();

		// Accordi di Cooperazione
		// Se e' cambiato il tipo o il nome del soggetto devo effettuare la modifica degli accordi di cooperazione:
		// - soggetto referente
		// - soggetti partecipanti
		soggettoUpdateUtilities.checkAccordiCooperazione();

		// Accordi di Servizio Parte Comune
		// Se e' cambiato il tipo o il nome del soggetto devo effettuare la modifica degli accordi di servizio 
		// poiche il cambio si riflette sul soggetto gestore
		soggettoUpdateUtilities.checkAccordiServizioParteComune();

		// Accordi di Servizio Parte Specifica
		// Se e' cambiato il tipo o il nome del soggetto devo effettuare la modifica dei servizi 
		// poiche il cambio si riflette sul nome dei connettori del servizio 
		soggettoUpdateUtilities.checkAccordiServizioParteSpecifica();

		// Porte Delegate
		// Se e' cambiato il tipo o il nome del soggetto devo effettuare la modifica delle porte delegate
		// poiche il cambio si riflette sul nome della porta delegata
		soggettoUpdateUtilities.checkPorteDelegate();

		// Porte Applicative
		// Se e' cambiato il tipo o il nome del soggetto virtuale devo effettuare la modifica delle porte applicative
		// poiche il cambio si riflette all'interno delle informazioni delle porte applicative
		soggettoUpdateUtilities.checkPorteApplicative();	

		// Fruitori nei servizi 
		soggettoUpdateUtilities.checkFruitori();
		
		return soggettoUpdateUtilities.getOggettiDaAggiornare();
	}
	
	public static boolean deleteSoggetto(Soggetto soggettoRegistro, org.openspcoop2.core.config.Soggetto soggettoConfig, String userLogin, 
			SoggettiCore soggettiCore, SoggettiHelper soggettiHelper, StringBuffer inUsoMessage, String newLine) throws Exception {
		
		PddCore pddCore = new PddCore(soggettiCore);
		UtentiCore utentiCore = new UtentiCore(soggettiCore);
		
		boolean deleteOperativo = false;
		
		IDSoggetto idSoggetto = null;
		if(soggettiCore.isRegistroServiziLocale()){
			idSoggetto = new IDSoggetto(soggettoRegistro.getTipo(), soggettoRegistro.getNome());
			soggettoConfig = soggettiCore.getSoggetto(idSoggetto);
		}
		else{
			idSoggetto = new IDSoggetto(soggettoConfig.getTipo(), soggettoConfig.getNome());
		}
		boolean soggettoInUso = false;
		boolean normalizeObjectIds = !soggettiHelper.isModalitaCompleta();
		HashMap<ErrorsHandlerCostant, List<String>> whereIsInUso = new HashMap<ErrorsHandlerCostant, List<String>>();
		if(soggettiCore.isRegistroServiziLocale()){
			soggettoInUso = soggettiCore.isSoggettoInUso(soggettoRegistro, whereIsInUso, normalizeObjectIds);
		}else{
			soggettoInUso = soggettiCore.isSoggettoInUso(soggettoConfig, whereIsInUso, normalizeObjectIds);
		}


		if (soggettoInUso) {
			inUsoMessage.append(DBOggettiInUsoUtils.toString(idSoggetto, whereIsInUso, true, newLine, normalizeObjectIds));
			inUsoMessage.append(newLine);

		} 
		else if(soggettoConfig.isDominioDefault()) {
			String protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(idSoggetto.getTipo());
			inUsoMessage.append("Il Soggetto '"+soggettiHelper.getLabelNomeSoggetto(protocollo, idSoggetto)+"',  essendo il soggeto predefinito per il profilo '"+
					soggettiHelper.getLabelProtocollo(protocollo)+"', non è eliminabile");
			inUsoMessage.append(newLine);
			inUsoMessage.append(newLine);
		}
		else {
			SoggettoCtrlStat sog = new SoggettoCtrlStat(soggettoRegistro, soggettoConfig);
			soggettiCore.performDeleteOperation(userLogin, soggettiHelper.smista(), sog);
			if(soggettoRegistro!=null && !pddCore.isPddEsterna(soggettoRegistro.getPortaDominio())) {
				
				// sistemo utenze dopo l'aggiornamento
				IDSoggetto idSoggettoSelezionato = new IDSoggetto(soggettoRegistro.getTipo(), soggettoRegistro.getNome());
				utentiCore.modificaSoggettoUtilizzatoConsole(idSoggettoSelezionato.toString(), null); // annullo selezione
				
				deleteOperativo = true;
			}
		}
		
		return deleteOperativo;
		
	}
}
