/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it). 
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
package org.openspcoop2.pdd.core.behaviour.built_in.multi_deliver;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.core.commons.CoreException;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaApplicativaServizioApplicativo;
import org.openspcoop2.core.config.Proprieta;
import org.slf4j.Logger;

/**
 * MultiDeliverUtils
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class MultiDeliverUtils  {
	
	
	public static ConfigurazioneMultiDeliver read(PortaApplicativa pa, Logger log) throws CoreException {
		ConfigurazioneMultiDeliver config = new ConfigurazioneMultiDeliver();
		if(pa.getBehaviour()==null) {
			throw new CoreException("Configurazione non disponibile");
		}
		
		if(pa.getBehaviour().sizeProprietaList()>0) {
			for (Proprieta p : pa.getBehaviour().getProprietaList()) {
				
				String nome = p.getNome();
				String valore = p.getValore().trim();
				
				try {
					if(Costanti.MULTI_DELIVER_CONNETTORE_API.equals(nome)) {
						config.setTransazioneSincrona_nomeConnettore(valore);
					}
					
					else if(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO.equals(nome)) {
						config.setNotificheByEsito("true".equals(valore));
					}
					
					else if(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_OK.equals(nome)) {
						config.setNotificheByEsito_ok("true".equals(valore));
					}
					else if(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_FAULT.equals(nome)) {
						config.setNotificheByEsito_fault("true".equals(valore));
					}
					else if(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_ERRORI_CONSEGNA.equals(nome)) {
						config.setNotificheByEsito_erroriConsegna("true".equals(valore));
					}
					else if(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_RICHIESTA_SCARTATE.equals(nome)) {
						config.setNotificheByEsito_richiesteScartate("true".equals(valore));
					}
					else if(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_ERRORI_PROCESSAMENTO.equals(nome)) {
						config.setNotificheByEsito_erroriProcessamento("true".equals(valore));
					}
					
				}catch(Exception e) {
					throw new CoreException("Configurazione condizionale non corretta (proprietà:"+p.getNome()+" valore:'"+p.getValore()+"'): "+e.getMessage(),e);
				}
			}
		}	

		return config;
	}
	

	public static void save(PortaApplicativa pa, ConfigurazioneMultiDeliver configurazione) throws CoreException {
		
		if(pa.getBehaviour()==null) {
			throw new CoreException("Configurazione behaviour non abilitata");
		}
		if(configurazione==null) {
			throw new CoreException("Configurazione condizionale non fornita");
		}
		
		if(StringUtils.isNotEmpty(configurazione.getTransazioneSincrona_nomeConnettore())) {
			pa.getBehaviour().addProprieta(newP(Costanti.MULTI_DELIVER_CONNETTORE_API, configurazione.getTransazioneSincrona_nomeConnettore()));
		}
		
		pa.getBehaviour().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO, configurazione.isNotificheByEsito()+""));
				
		pa.getBehaviour().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_OK, configurazione.isNotificheByEsito_ok()+""));
		pa.getBehaviour().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_FAULT, configurazione.isNotificheByEsito_fault()+""));
		pa.getBehaviour().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_ERRORI_CONSEGNA, configurazione.isNotificheByEsito_erroriConsegna()+""));
		pa.getBehaviour().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_RICHIESTA_SCARTATE, configurazione.isNotificheByEsito_richiesteScartate()+""));
		pa.getBehaviour().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_BY_ESITO_ERRORI_PROCESSAMENTO, configurazione.isNotificheByEsito_erroriProcessamento()+""));
	}
	
	
	
	public static ConfigurazioneGestioneConsegnaNotifiche read(PortaApplicativaServizioApplicativo pasa, Logger log) throws CoreException {
		ConfigurazioneGestioneConsegnaNotifiche config = new ConfigurazioneGestioneConsegnaNotifiche();
		if(pasa==null || pasa.getDatiConnettore()==null || pasa.getDatiConnettore().sizeProprietaList()==0) {
	//		throw new CoreException("Configurazione non disponibile");
			return GestioneConsegnaNotificheUtils.getGestioneDefault();
		}
		
		for (Proprieta p : pasa.getDatiConnettore().getProprietaList()) {
			
			String nome = p.getNome();
			String valore = p.getValore().trim();
			
			try {
				if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_CADENZA.equals(nome)) {
					config.setCadenzaRispedizione(Integer.valueOf(valore));
				}
				
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_2XX.equals(nome)) {
					config.setGestioneTrasporto2xx(TipoGestioneNotificaTrasporto.toEnumConstant(valore, true));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_2XX_CODE_LIST.equals(nome)) {
					if(valore.contains(",")) {
						String [] tmp = valore.split(",");
						for (String t : tmp) {
							config.getGestioneTrasporto2xx_codes().add(Integer.valueOf(t.trim()));
						}
					}
					else {
						config.getGestioneTrasporto2xx_codes().add(Integer.valueOf(valore));
					}
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_2XX_LEFT_INTERVAL.equals(nome)) {
					config.setGestioneTrasporto2xx_leftInterval(Integer.valueOf(valore));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_2XX_RIGHT_INTERVAL.equals(nome)) {
					config.setGestioneTrasporto2xx_rightInterval(Integer.valueOf(valore));
				}
				
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_3XX.equals(nome)) {
					config.setGestioneTrasporto3xx(TipoGestioneNotificaTrasporto.toEnumConstant(valore, true));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_3XX_CODE_LIST.equals(nome)) {
					if(valore.contains(",")) {
						String [] tmp = valore.split(",");
						for (String t : tmp) {
							config.getGestioneTrasporto3xx_codes().add(Integer.valueOf(t.trim()));
						}
					}
					else {
						config.getGestioneTrasporto3xx_codes().add(Integer.valueOf(valore));
					}
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_3XX_LEFT_INTERVAL.equals(nome)) {
					config.setGestioneTrasporto3xx_leftInterval(Integer.valueOf(valore));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_3XX_RIGHT_INTERVAL.equals(nome)) {
					config.setGestioneTrasporto3xx_rightInterval(Integer.valueOf(valore));
				}
				
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_4XX.equals(nome)) {
					config.setGestioneTrasporto4xx(TipoGestioneNotificaTrasporto.toEnumConstant(valore, true));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_4XX_CODE_LIST.equals(nome)) {
					if(valore.contains(",")) {
						String [] tmp = valore.split(",");
						for (String t : tmp) {
							config.getGestioneTrasporto4xx_codes().add(Integer.valueOf(t.trim()));
						}
					}
					else {
						config.getGestioneTrasporto4xx_codes().add(Integer.valueOf(valore));
					}
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_4XX_LEFT_INTERVAL.equals(nome)) {
					config.setGestioneTrasporto4xx_leftInterval(Integer.valueOf(valore));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_4XX_RIGHT_INTERVAL.equals(nome)) {
					config.setGestioneTrasporto4xx_rightInterval(Integer.valueOf(valore));
				}
				
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_5XX.equals(nome)) {
					config.setGestioneTrasporto5xx(TipoGestioneNotificaTrasporto.toEnumConstant(valore, true));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_5XX_CODE_LIST.equals(nome)) {
					if(valore.contains(",")) {
						String [] tmp = valore.split(",");
						for (String t : tmp) {
							config.getGestioneTrasporto5xx_codes().add(Integer.valueOf(t.trim()));
						}
					}
					else {
						config.getGestioneTrasporto5xx_codes().add(Integer.valueOf(valore));
					}
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_5XX_LEFT_INTERVAL.equals(nome)) {
					config.setGestioneTrasporto5xx_leftInterval(Integer.valueOf(valore));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_5XX_RIGHT_INTERVAL.equals(nome)) {
					config.setGestioneTrasporto5xx_rightInterval(Integer.valueOf(valore));
				}
				
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_FAULT.equals(nome)) {
					config.setFault(TipoGestioneNotificaFault.toEnumConstant(valore, true));
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_FAULT_CODE.equals(nome)) {
					config.setFaultCode(valore);
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_FAULT_ACTOR.equals(nome)) {
					config.setFaultActor(valore);
				}
				else if(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_FAULT_MESSAGE.equals(nome)) {
					config.setFaultMessage(valore);
				}
				
			}catch(Exception e) {
				throw new CoreException("Configurazione condizionale non corretta (proprietà:"+p.getNome()+" valore:'"+p.getValore()+"'): "+e.getMessage(),e);
			}
		}	

		return config;
	}
	

	public static void save(PortaApplicativaServizioApplicativo pasa, ConfigurazioneGestioneConsegnaNotifiche configurazione) throws CoreException {
		
		if(pasa==null || pasa.getDatiConnettore()==null) {
			throw new CoreException("Configurazione behaviour non disponibile");
		}
		if(configurazione==null) {
			throw new CoreException("Configurazione condizionale non fornita");
		}
		
		if(configurazione.getCadenzaRispedizione()!=null) {
			pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_CADENZA, configurazione.getCadenzaRispedizione().intValue()+""));
		}
		
		if(configurazione.getGestioneTrasporto2xx()!=null) {
			pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_2XX, configurazione.getGestioneTrasporto2xx().getValue()));
			switch (configurazione.getGestioneTrasporto2xx()) {
			case CONSEGNA_COMPLETATA:
			case CONSEGNA_FALLITA:
				break;
			case INTERVALLO_CONSEGNA_COMPLETATA:
				if(configurazione.getGestioneTrasporto2xx_leftInterval()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_2XX_LEFT_INTERVAL, configurazione.getGestioneTrasporto2xx_leftInterval().intValue()+""));
				}
				else {
					throw new CoreException("[2xx] Left Interval undefined");
				}
				if(configurazione.getGestioneTrasporto2xx_rightInterval()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_2XX_RIGHT_INTERVAL, configurazione.getGestioneTrasporto2xx_rightInterval().intValue()+""));
				}
				else {
					throw new CoreException("[2xx] Right Interval undefined");
				}
				break;
			case CODICI_CONSEGNA_COMPLETATA:
				if(configurazione.getGestioneTrasporto2xx_codes()!=null && !configurazione.getGestioneTrasporto2xx_codes().isEmpty()) {
					StringBuffer bf = new StringBuffer();
					for (Integer code : configurazione.getGestioneTrasporto2xx_codes()) {
						if(bf.length()>0) {
							bf.append(",");
						}
						bf.append(code);
					}
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_2XX_CODE_LIST, bf.toString()));
				}
				else {
					throw new CoreException("[2xx] Code undefined");
				}
				break;
			}
		}
		
		if(configurazione.getGestioneTrasporto3xx()!=null) {
			pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_3XX, configurazione.getGestioneTrasporto3xx().getValue()));
			switch (configurazione.getGestioneTrasporto3xx()) {
			case CONSEGNA_COMPLETATA:
			case CONSEGNA_FALLITA:
				break;
			case INTERVALLO_CONSEGNA_COMPLETATA:
				if(configurazione.getGestioneTrasporto3xx_leftInterval()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_3XX_LEFT_INTERVAL, configurazione.getGestioneTrasporto3xx_leftInterval().intValue()+""));
				}
				else {
					throw new CoreException("[3xx] Left Interval undefined");
				}
				if(configurazione.getGestioneTrasporto3xx_rightInterval()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_3XX_RIGHT_INTERVAL, configurazione.getGestioneTrasporto3xx_rightInterval().intValue()+""));
				}
				else {
					throw new CoreException("[3xx] Right Interval undefined");
				}
				break;
			case CODICI_CONSEGNA_COMPLETATA:
				if(configurazione.getGestioneTrasporto3xx_codes()!=null && !configurazione.getGestioneTrasporto3xx_codes().isEmpty()) {
					StringBuffer bf = new StringBuffer();
					for (Integer code : configurazione.getGestioneTrasporto3xx_codes()) {
						if(bf.length()>0) {
							bf.append(",");
						}
						bf.append(code);
					}
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_3XX_CODE_LIST, bf.toString()));
				}
				else {
					throw new CoreException("[3xx] Code undefined");
				}
				break;
			}
		}
		
		if(configurazione.getGestioneTrasporto4xx()!=null) {
			pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_4XX, configurazione.getGestioneTrasporto4xx().getValue()));
			switch (configurazione.getGestioneTrasporto4xx()) {
			case CONSEGNA_COMPLETATA:
			case CONSEGNA_FALLITA:
				break;
			case INTERVALLO_CONSEGNA_COMPLETATA:
				if(configurazione.getGestioneTrasporto4xx_leftInterval()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_4XX_LEFT_INTERVAL, configurazione.getGestioneTrasporto4xx_leftInterval().intValue()+""));
				}
				else {
					throw new CoreException("[4xx] Left Interval undefined");
				}
				if(configurazione.getGestioneTrasporto4xx_rightInterval()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_4XX_RIGHT_INTERVAL, configurazione.getGestioneTrasporto4xx_rightInterval().intValue()+""));
				}
				else {
					throw new CoreException("[4xx] Right Interval undefined");
				}
				break;
			case CODICI_CONSEGNA_COMPLETATA:
				if(configurazione.getGestioneTrasporto4xx_codes()!=null && !configurazione.getGestioneTrasporto4xx_codes().isEmpty()) {
					StringBuffer bf = new StringBuffer();
					for (Integer code : configurazione.getGestioneTrasporto4xx_codes()) {
						if(bf.length()>0) {
							bf.append(",");
						}
						bf.append(code);
					}
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_4XX_CODE_LIST, bf.toString()));
				}
				else {
					throw new CoreException("[4xx] Code undefined");
				}
				break;
			}
		}
		
		if(configurazione.getGestioneTrasporto5xx()!=null) {
			pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_5XX, configurazione.getGestioneTrasporto5xx().getValue()));
			switch (configurazione.getGestioneTrasporto5xx()) {
			case CONSEGNA_COMPLETATA:
			case CONSEGNA_FALLITA:
				break;
			case INTERVALLO_CONSEGNA_COMPLETATA:
				if(configurazione.getGestioneTrasporto5xx_leftInterval()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_5XX_LEFT_INTERVAL, configurazione.getGestioneTrasporto5xx_leftInterval().intValue()+""));
				}
				else {
					throw new CoreException("[5xx] Left Interval undefined");
				}
				if(configurazione.getGestioneTrasporto5xx_rightInterval()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_5XX_RIGHT_INTERVAL, configurazione.getGestioneTrasporto5xx_rightInterval().intValue()+""));
				}
				else {
					throw new CoreException("[5xx] Right Interval undefined");
				}
				break;
			case CODICI_CONSEGNA_COMPLETATA:
				if(configurazione.getGestioneTrasporto5xx_codes()!=null && !configurazione.getGestioneTrasporto5xx_codes().isEmpty()) {
					StringBuffer bf = new StringBuffer();
					for (Integer code : configurazione.getGestioneTrasporto5xx_codes()) {
						if(bf.length()>0) {
							bf.append(",");
						}
						bf.append(code);
					}
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_TRASPORTO_5XX_CODE_LIST, bf.toString()));
				}
				else {
					throw new CoreException("[5xx] Code undefined");
				}
				break;
			}
		}
		
		if(configurazione.getFault()!=null) {
			pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_FAULT, configurazione.getFault().getValue()));
			switch (configurazione.getFault()) {
			case CONSEGNA_COMPLETATA:
			case CONSEGNA_FALLITA:
				break;
			case CUSTOM:
				if(configurazione.getFaultCode()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_FAULT_CODE, configurazione.getFaultCode()));
				}
				if(configurazione.getFaultActor()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_FAULT_ACTOR, configurazione.getFaultActor()));
				}
				if(configurazione.getFaultMessage()!=null) {
					pasa.getDatiConnettore().addProprieta(newP(Costanti.MULTI_DELIVER_NOTIFICHE_GESTIONE_ERRORE_TIPO_GESTIONE_FAULT_MESSAGE, configurazione.getFaultMessage()));
				}
				break;
			}
		}
	}
	
	
	
	
	
	private static Proprieta newP(String nome, String valore) {
		Proprieta p = new Proprieta();
		p.setNome(nome);
		p.setValore(valore);
		return p;
	}
}
