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

package org.openspcoop2.protocol.engine.archive;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openspcoop2.core.commons.DBMappingUtils;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaDelegata;
import org.openspcoop2.core.config.constants.PortaApplicativaAzioneIdentificazione;
import org.openspcoop2.core.config.constants.PortaDelegataAzioneIdentificazione;
import org.openspcoop2.core.config.driver.DriverConfigurazioneNotFound;
import org.openspcoop2.core.config.driver.FiltroRicercaPorteApplicative;
import org.openspcoop2.core.config.driver.FiltroRicercaPorteDelegate;
import org.openspcoop2.core.config.driver.db.DriverConfigurazioneDB;
import org.openspcoop2.core.constants.Costanti;
import org.openspcoop2.core.id.IDPortaApplicativa;
import org.openspcoop2.core.id.IDPortaDelegata;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.constants.PddTipologia;
import org.openspcoop2.core.registry.driver.DriverRegistroServiziNotFound;
import org.openspcoop2.core.registry.driver.FiltroRicerca;
import org.openspcoop2.core.registry.driver.FiltroRicercaServizi;
import org.openspcoop2.core.registry.driver.FiltroRicercaSoggetti;
import org.openspcoop2.core.registry.driver.db.DriverRegistroServiziDB;
import org.openspcoop2.protocol.sdk.ProtocolException;
import org.slf4j.Logger;


/**
 *  UtilitiesMappingFruizionePD
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class UtilitiesMappingFruizioneErogazione  {

	private DriverConfigurazioneDB driverConfigurazione;
	private DriverRegistroServiziDB driverRegistroServizi;
	private Logger log;
	
	public UtilitiesMappingFruizioneErogazione(DriverConfigurazioneDB driverConfigurazione,
			DriverRegistroServiziDB driverRegistroServizi,
			Logger log){
		this.driverConfigurazione = driverConfigurazione;
		this.driverRegistroServizi = driverRegistroServizi;
		this.log = log;
	}
	
	public void initMappingFruizione() throws ProtocolException {
		this._initMapping(false);
	}
	public void initMappingErogazione() throws ProtocolException {
		this._initMapping(true);
	}
	private void _initMapping(boolean erogazione) throws ProtocolException {
		
		try {

			List<String> tipiPdd = new ArrayList<String>();
			tipiPdd.add(PddTipologia.OPERATIVO.toString());
			tipiPdd.add(PddTipologia.NONOPERATIVO.toString());
			
			for (String tipoPdd : tipiPdd) {
				
				this.log.debug("Pdd (tipo:"+tipoPdd+") ricerca in corso...");
				FiltroRicerca filtroPdd = new FiltroRicerca();
				filtroPdd.setTipo(tipoPdd);
				List<String> listPdD = null;
				try{
					listPdD = this.driverRegistroServizi.getAllIdPorteDominio(filtroPdd);
				}catch(DriverRegistroServiziNotFound notFound){}
				if(listPdD==null){
					listPdD = new ArrayList<>();
				}
				this.log.debug("Pdd (tipo:"+tipoPdd+") trovate: "+listPdD.size());
				
				if(listPdD.size()>0){
					
					for (String nomePdd : listPdD) {
						
						this.log.debug("Soggetti (pdd:"+nomePdd+") ricerca in corso...");
						FiltroRicercaSoggetti filtroSoggetti = new FiltroRicercaSoggetti();
						filtroSoggetti.setNomePdd(nomePdd);
						List<IDSoggetto> listSoggetti = null;
						try{
							listSoggetti = this.driverRegistroServizi.getAllIdSoggetti(filtroSoggetti);
						}catch(DriverRegistroServiziNotFound notFound){}
						if(listSoggetti==null){
							listSoggetti = new ArrayList<>();
						}
						this.log.debug("Soggetti (pdd:"+nomePdd+") trovati: "+listSoggetti.size());
						
						if(listSoggetti.size()>0){
						
							for (IDSoggetto idSoggetto : listSoggetti) {
								
								if(erogazione){
								
									// erogazione
									
									this.log.debug("Servizi (soggetto:"+idSoggetto+") ricerca in corso...");
									FiltroRicercaServizi filtroServizi = new FiltroRicercaServizi();
									filtroServizi.setTipoSoggettoErogatore(idSoggetto.getTipo());
									filtroServizi.setNomeSoggettoErogatore(idSoggetto.getNome());
									List<IDServizio> listServizi = null;
									try{
										listServizi = this.driverRegistroServizi.getAllIdServizi(filtroServizi);
									}catch(DriverRegistroServiziNotFound notFound){}
									if(listServizi==null){
										listServizi = new ArrayList<>();
									}
									this.log.debug("Servizi (soggetto:"+idSoggetto+") trovati: "+listServizi.size());
									
									if(listServizi.size()>0){
										
										for (IDServizio idServizio : listServizi) {
												
											// Devo controllare il mapping per ogni porta applicativa
//											Connection con = null;
//											try{
//												con = this.driverRegistroServizi.getConnection("UtilitiesMappingFruizioneErogazione.existsIDPortaApplicativaAssociata");
//												if(DBMappingUtils.existsIDPortaApplicativaAssociata(idServizio, con, this.driverRegistroServizi.getTipoDB())){
//													this.log.debug("PortaApplicativa (soggetto:"+idSoggetto+" servizio:"+
//															idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+") mapping già esistente");
//													continue;
//												}
//											}finally{
//												try{
//													this.driverRegistroServizi.releaseConnection(con);
//												}catch(Throwable t){}
//											}
											
											this.log.debug("PorteApplicative (soggetto:"+idSoggetto+" servizio:"+
													idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+") ricerca in corso...");
											FiltroRicercaPorteApplicative filtroPA = new FiltroRicercaPorteApplicative();
											filtroPA.setTipoServizio(idServizio.getTipo());
											filtroPA.setNomeServizio(idServizio.getNome());
											filtroPA.setTipoSoggetto(idSoggetto.getTipo());
											filtroPA.setNomeSoggetto(idSoggetto.getNome());
											List<IDPortaApplicativa> listPA = null;
											try{
												listPA = this.driverConfigurazione.getAllIdPorteApplicative(filtroPA);
											}catch(DriverConfigurazioneNotFound notFound){}
											if(listPA==null){
												listPA = new ArrayList<>();
											}
											this.log.debug("PorteApplicative (soggetto:"+idSoggetto+" servizio:"+
													idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+") trovate: "+listPA.size());
											
											String nomePortaDefault = null;
											HashMap<String, IDPortaApplicativa> mapPorte = new HashMap<>(); 
											HashMap<IDPortaApplicativa, PortaApplicativa> listPAModificare = new  HashMap<>(); 
											for (IDPortaApplicativa idPortaApplicativa : listPA) {
												
												Connection con = null;
												try{
													con = this.driverRegistroServizi.getConnection("UtilitiesMappingFruizioneErogazione.existsIDPortaApplicativaAssociata");
													if(DBMappingUtils.existsMappingErogazione(idServizio, idPortaApplicativa, con, this.driverRegistroServizi.getTipoDB())){
														this.log.debug("PortaApplicativa '"+idPortaApplicativa.getNome()+"' (soggetto:"+idSoggetto+" servizio:"+
																idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+") mapping già esistente");
														continue;
													}
												}finally{
													try{
														this.driverRegistroServizi.releaseConnection(con);
													}catch(Throwable t){}
												}
												
												String nomeMapping = null;
												boolean isDefault = false;
												PortaApplicativa pa = this.driverConfigurazione.getPortaApplicativa(idPortaApplicativa);
												if( (pa.getAzione()==null) || 
														( 
																!PortaApplicativaAzioneIdentificazione.DELEGATED_BY.equals(pa.getAzione().getIdentificazione()) 
																&&
																(pa.getAzione().getNome()==null || "".equals(pa.getAzione().getNome()))
														)
													){
													nomeMapping = Costanti.MAPPING_EROGAZIONE_PA_NOME_DEFAULT;
													isDefault = true;
													if(nomePortaDefault!=null) {
														// già trovato una pa di default, questa non la considero
														this.log.debug("PortaApplicativa '"+idPortaApplicativa.getNome()+"' ignorata (soggetto:"+idSoggetto+" servizio:"+
																idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+"): mapping di default già esistente nella pa con nome ["+nomePortaDefault+"]");
														continue;
													}
													else {
														nomePortaDefault = idPortaApplicativa.getNome();
													}
												}
												else {
													if(PortaApplicativaAzioneIdentificazione.DELEGATED_BY.equals(pa.getAzione().getIdentificazione())) {
														// gia' nel nuovo formato devo solo creare il mapping che per qualche motivo si è perso
														if(pa.getAzione().sizeAzioneDelegataList()<=0) {
															this.log.error("Trovata porta applicativa "+pa.getNome()+"] con un'identificazione dell'azione delegata senza pero' possedere azioni delegate");
															continue;
														}
														else {
															String nomeAzione = pa.getAzione().getAzioneDelegata(0); // utilizzo un'azione a caso.
															nomeMapping = nomeAzione;
														}
													}
													else {
														String nomeAzione = pa.getAzione().getNome();
														if(mapPorte.containsKey(nomeAzione)) {
															// già trovato una pa che gestisce l'azione
															this.log.debug("PortaApplicativa '"+idPortaApplicativa.getNome()+"' ignorata (soggetto:"+idSoggetto+" servizio:"+
																	idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+"): mapping per l'azione '"+nomeAzione+"' già esistente nella pa con nome ["+mapPorte.get(nomeAzione)+"]");
															continue;
														}
														else {
															mapPorte.put(nomeAzione, idPortaApplicativa);
														}
														nomeMapping = nomeAzione;
														
														// modifico porta applicativa adeguandola alla nuova specifica
														pa.getAzione().setIdentificazione(PortaApplicativaAzioneIdentificazione.DELEGATED_BY); 
														pa.getAzione().addAzioneDelegata(nomeAzione);
														pa.getAzione().setNome(null);
														
														listPAModificare.put(idPortaApplicativa, pa);
													}
												}
												this.log.debug("Creazione Mapping Erogazione (default:"+isDefault+" nome:"+nomeMapping+") soggetto:"+idSoggetto+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+" con pa:"+idPortaApplicativa.getNome()+" in corso...");
												con = null;
												try{
													con = this.driverRegistroServizi.getConnection("UtilitiesMappingFruizioneErogazione.createMappingErogazione");
													
													DBMappingUtils.createMappingErogazione(nomeMapping, isDefault, idServizio, idPortaApplicativa, con, this.driverRegistroServizi.getTipoDB());
												}finally{
													try{
														if(this.driverRegistroServizi.isAtomica()) {
															con.commit();
														}
													}catch(Throwable t){}
													try{
														this.driverRegistroServizi.releaseConnection(con);
													}catch(Throwable t){}
												}
												this.log.debug("Creazione Mapping Erogazione (default:"+isDefault+" nome:"+nomeMapping+") soggetto:"+idSoggetto+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+" con pa:"+idPortaApplicativa.getNome()+" effettuata con successo");
												
											}
											
											if(listPAModificare.size()>0) {
												for (IDPortaApplicativa idPortaApplicativa : listPAModificare.keySet()) {
													PortaApplicativa pa = listPAModificare.get(idPortaApplicativa);
													
													if(nomePortaDefault==null) {
														// Creo una porta applicativa automaticamente simile a quella delegatedBy
														IDPortaApplicativa idPAclone = new IDPortaApplicativa();
														String nameDefault = idServizio.getSoggettoErogatore().getTipo()+idServizio.getSoggettoErogatore().getNome()+
																"/"+
																idServizio.getTipo()+idServizio.getNome();
														idPAclone.setNome(nameDefault);
														int index = 2;
														while(this.driverConfigurazione.existsPortaApplicativa(idPAclone) && index<100) {
															idPAclone.setNome(nameDefault+"_"+index);
															index++;
														}
														if(index<100) {
															
															PortaApplicativa paClone = (PortaApplicativa) pa.clone();
															paClone.setId(null);
															paClone.setNome(idPAclone.getNome());
															paClone.setAzione(null);
															nomePortaDefault = idPAclone.getNome();
															
															// creo porta applicativa standard
															this.log.info("Porta applicativa ["+pa.getNome()+"] del Soggetto "+pa.getTipoSoggettoProprietario()+"/"+pa.getNomeSoggettoProprietario()+" creazione delegante in corso...");
															this.driverConfigurazione.createPortaApplicativa(paClone);
															this.log.info("Porta applicativa ["+pa.getNome()+"] del Soggetto "+pa.getTipoSoggettoProprietario()+"/"+pa.getNomeSoggettoProprietario()+" creazione delegante.");
															
															Connection con = null;
															try{
																con = this.driverConfigurazione.getConnection("UtilitiesMappingFruizioneErogazione.createMappingErogazione");
																String nomeMapping = Costanti.MAPPING_EROGAZIONE_PA_NOME_DEFAULT;
																boolean isDefault = true;
																this.log.info("Creazione mapping di erogazione di default (nome:"+nomeMapping+" default:"+isDefault+") tra Porta delegata ["+paClone.getNome()+"] e servizio ["+idServizio+"] creazione delegante in corso...");
																DBMappingUtils.createMappingErogazione(nomeMapping, isDefault, idServizio, idPAclone, con, this.driverConfigurazione.getTipoDB());
																this.log.info("Creazione mapping di erogazione di default (nome:"+nomeMapping+" default:"+isDefault+") tra Porta delegata ["+paClone.getNome()+"] e servizio ["+idServizio+"] creato delegante.");
															}finally{
																try{
																	if(this.driverConfigurazione.isAtomica()) {
																		con.commit();
																	}
																}catch(Throwable t){}
																try{
																	this.driverRegistroServizi.releaseConnection(con);
																}catch(Throwable t){}
															}
														}
													}
													
													pa.getAzione().setNomePortaDelegante(nomePortaDefault);
													
													// comunque devo aggiornare i dati per delegatedBy (anche se nomePortaDefault is null)
													
													this.log.debug("Aggiornamento Porta Applicativa '"+idPortaApplicativa+"' per  Mapping Erogazione soggetto:"+idSoggetto+" servizio:"+
															idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+" per impostare nome delegate '"+nomePortaDefault+"' in corso...");
													this.driverConfigurazione.updatePortaApplicativa(pa);
													this.log.debug("Aggiornamento Porta Applicativa '"+idPortaApplicativa+"' per  Mapping Erogazione soggetto:"+idSoggetto+" servizio:"+
															idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+" per impostare nome delegate '"+nomePortaDefault+"' effettuata con successo");
													
												}
											}
										}
										
									}
								}
								else{
									
									// fruizione
									
									this.log.debug("Servizi (fruitore:"+idSoggetto+") ricerca in corso...");
									FiltroRicercaServizi filtroServizi = new FiltroRicercaServizi();
									filtroServizi.setTipoSoggettoFruitore(idSoggetto.getTipo());
									filtroServizi.setNomeSoggettoFruitore(idSoggetto.getNome());
									List<IDServizio> listServizi = null;
									try{
										listServizi = this.driverRegistroServizi.getAllIdServizi(filtroServizi);
									}catch(DriverRegistroServiziNotFound notFound){}
									if(listServizi==null){
										listServizi = new ArrayList<>();
									}
									this.log.debug("Servizi (fruitore:"+idSoggetto+") trovati: "+listServizi.size());
									
									if(listServizi.size()>0){
										
										for (IDServizio idServizio : listServizi) {
										
											// Devo controllare il mapping per ogni porta delegata
//											Connection con = null;
//											try{
//												con = this.driverRegistroServizi.getConnection("UtilitiesMappingFruizioneErogazione.existsIDPortaDelegataAssociata");		
//												if(DBMappingUtils.existsIDPortaDelegataAssociata(idServizio, idSoggetto, con, this.driverRegistroServizi.getTipoDB())){
//													this.log.debug("PortaDelegata (soggetto-fruitore:"+idSoggetto+
//															" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
//															idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+") mapping già esistente");
//													continue;
//												}
//											}finally{
//												try{
//													this.driverRegistroServizi.releaseConnection(con);
//												}catch(Throwable t){}
//											}
											
											this.log.debug("PorteDelegate (soggetto-fruitore:"+idSoggetto+
														" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+") ricerca in corso...");
											FiltroRicercaPorteDelegate filtroPD = new FiltroRicercaPorteDelegate();
											filtroPD.setTipoSoggetto(idSoggetto.getTipo());
											filtroPD.setNomeSoggetto(idSoggetto.getNome());
											filtroPD.setTipoSoggettoErogatore(idServizio.getSoggettoErogatore().getTipo());
											filtroPD.setNomeSoggettoErogatore(idServizio.getSoggettoErogatore().getNome());
											filtroPD.setTipoServizio(idServizio.getTipo());
											filtroPD.setNomeServizio(idServizio.getNome());
											List<IDPortaDelegata> listPD = null;
											try{
												listPD = this.driverConfigurazione.getAllIdPorteDelegate(filtroPD);
											}catch(DriverConfigurazioneNotFound notFound){}
											if(listPD==null){
												listPD = new ArrayList<>();
											}
											this.log.debug("PorteDelegate (soggetto-fruitore:"+idSoggetto+
														" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+") trovate: "+listPD.size());
											
											String nomePortaDefault = null;
											HashMap<String, IDPortaDelegata> mapPorte = new HashMap<>(); 
											HashMap<IDPortaDelegata, PortaDelegata> listPDModificare = new  HashMap<>(); 
											for (IDPortaDelegata idPortaDelegata : listPD) {
												
												Connection con = null;
												try{
													con = this.driverRegistroServizi.getConnection("UtilitiesMappingFruizioneErogazione.existsIDPortaDelegataAssociata");
													if(DBMappingUtils.existsMappingFruizione(idServizio, idSoggetto, idPortaDelegata, con, this.driverRegistroServizi.getTipoDB())){
														this.log.debug("PortaDelegata '"+idPortaDelegata.getNome()+"' (soggetto-fruitore:"+idSoggetto+
														" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+") mapping già esistente");
														continue;
													}
												}finally{
													try{
														this.driverRegistroServizi.releaseConnection(con);
													}catch(Throwable t){}
												}
												
												String nomeMapping = null;
												boolean isDefault = false;
												PortaDelegata pd = this.driverConfigurazione.getPortaDelegata(idPortaDelegata);
												if( (pd.getAzione()==null) || 
														( 
																!PortaDelegataAzioneIdentificazione.DELEGATED_BY.equals(pd.getAzione().getIdentificazione()) 
																&&
																(pd.getAzione().getNome()==null || "".equals(pd.getAzione().getNome()))
														)
													){
													nomeMapping = Costanti.MAPPING_FRUIZIONE_PD_NOME_DEFAULT;
													isDefault = true;
													if(nomePortaDefault!=null) {
														// già trovato una pa di default, questa non la considero
														this.log.debug("PortaDelegata '"+idPortaDelegata.getNome()+"' ignorata (soggetto-fruitore:"+idSoggetto+
														" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+"): mapping di default già esistente nella pd con nome ["+nomePortaDefault+"]");
														continue;
													}
													else {
														nomePortaDefault = idPortaDelegata.getNome();
													}
												}
												else {
													if(PortaDelegataAzioneIdentificazione.DELEGATED_BY.equals(pd.getAzione().getIdentificazione())) {
														// gia' nel nuovo formato devo solo creare il mapping che per qualche motivo si è perso
														if(pd.getAzione().sizeAzioneDelegataList()<=0) {
															this.log.error("Trovata porta delegata "+pd.getNome()+"] con un'identificazione dell'azione delegata senza pero' possedere azioni delegate");
															continue;
														}
														else {
															String nomeAzione = pd.getAzione().getAzioneDelegata(0); // utilizzo un'azione a caso.
															nomeMapping = nomeAzione;
														}
													}
													else {
														String nomeAzione = pd.getAzione().getNome();
														if(mapPorte.containsKey(nomeAzione)) {
															// già trovato una pa che gestisce l'azione
															this.log.debug("PortaDelegata '"+idPortaDelegata.getNome()+"' ignorata (soggetto-fruitore:"+idSoggetto+
															" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
															idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+"): mapping per l'azione '"+nomeAzione+"' già esistente nella pa con nome ["+mapPorte.get(nomeAzione)+"]");
															continue;
														}
														else {
															mapPorte.put(nomeAzione, idPortaDelegata);
														}
														nomeMapping = nomeAzione;
														
														// modifico porta applicativa adeguandola alla nuova specifica
														pd.getAzione().setIdentificazione(PortaDelegataAzioneIdentificazione.DELEGATED_BY); 
														pd.getAzione().addAzioneDelegata(nomeAzione);
														pd.getAzione().setNome(null);
														
														listPDModificare.put(idPortaDelegata, pd);
													}
												}
												this.log.debug("Creazione Mapping Fruizione soggetto-fruitore:"+idSoggetto+
														" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+" con pd:"+idPortaDelegata.getNome()+" in corso...");
												con = null;
												try{
													con = this.driverRegistroServizi.getConnection("UtilitiesMappingFruizioneErogazione.createMappingFruizione");
													
													DBMappingUtils.createMappingFruizione(nomeMapping, isDefault, idServizio, idSoggetto, idPortaDelegata, con, this.driverRegistroServizi.getTipoDB());
												}finally{
													try{
														if(this.driverRegistroServizi.isAtomica()) {
															con.commit();
														}
													}catch(Throwable t){}
													try{
														this.driverRegistroServizi.releaseConnection(con);
													}catch(Throwable t){}
												}
												this.log.debug("Creazione Mapping Fruizione soggetto-fruitore:"+idSoggetto+
														" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+" con pd:"+idPortaDelegata.getNome()+" effettuata con successo");
											}
											
											if(listPDModificare.size()>0) {
												for (IDPortaDelegata idPortaDelegata : listPDModificare.keySet()) {
													PortaDelegata pd = listPDModificare.get(idPortaDelegata);
													
													if(nomePortaDefault==null) {
														// Creo una porta delegata automaticamente simile a quella delegatedBy
														IDPortaDelegata idPDclone = new IDPortaDelegata();
														String nameDefault = pd.getTipoSoggettoProprietario()+ pd.getNomeSoggettoProprietario()+
																"/"+
																idServizio.getSoggettoErogatore().getTipo()+idServizio.getSoggettoErogatore().getNome()+
																"/"+
																idServizio.getTipo()+idServizio.getNome();
														idPDclone.setNome(nameDefault);
														int index = 2;
														while(this.driverConfigurazione.existsPortaDelegata(idPDclone) && index<100) {
															idPDclone.setNome(nameDefault+"_"+index);
															index++;
														}
														if(index<100) {
															
															PortaDelegata pdClone = (PortaDelegata) pd.clone();
															pdClone.setId(null);
															pdClone.setNome(idPDclone.getNome());
															pdClone.setAzione(null);
															nomePortaDefault = idPDclone.getNome();
															
															// creo porta delegata standard
															this.log.info("Porta delegata ["+pd.getNome()+"] del Soggetto "+pd.getTipoSoggettoProprietario()+"/"+pd.getNomeSoggettoProprietario()+" creazione delegante in corso...");
															this.driverConfigurazione.createPortaDelegata(pdClone);
															this.log.info("Porta delegata ["+pd.getNome()+"] del Soggetto "+pd.getTipoSoggettoProprietario()+"/"+pd.getNomeSoggettoProprietario()+" creazione delegante.");
															
															Connection con = null;
															try{
																con = this.driverConfigurazione.getConnection("UtilitiesMappingFruizioneErogazione.createMappingFruizione");
																String nomeMapping = Costanti.MAPPING_FRUIZIONE_PD_NOME_DEFAULT;
																boolean isDefault = true;
																IDSoggetto idFruitore = new IDSoggetto(pd.getTipoSoggettoProprietario(), pd.getNomeSoggettoProprietario());
																this.log.info("Creazione mapping di fruizione di default (nome:"+nomeMapping+" default:"+isDefault+") tra Porta delegata ["+pdClone.getNome()+"], fruitore ["+idFruitore+"] e servizio ["+idServizio+"] creazione delegante in corso...");
																DBMappingUtils.createMappingFruizione(nomeMapping, isDefault, idServizio, idFruitore, idPDclone, con, this.driverConfigurazione.getTipoDB());
																this.log.info("Creazione mapping di fruizione di default (nome:"+nomeMapping+" default:"+isDefault+") tra Porta delegata ["+pdClone.getNome()+"], fruitore ["+idFruitore+"] e servizio ["+idServizio+"] creato delegante.");
															}finally{
																try{
																	if(this.driverConfigurazione.isAtomica()) {
																		con.commit();
																	}
																}catch(Throwable t){}
																try{
																	this.driverRegistroServizi.releaseConnection(con);
																}catch(Throwable t){}
															}
														}
													}
													
													pd.getAzione().setNomePortaDelegante(nomePortaDefault);
													
													// comunque devo aggiornare i dati per delegatedBy (anche se nomePortaDefault is null)
													
													this.log.debug("Aggiornamento Porta Delegata '"+idPortaDelegata+"' per  Mapping Fruizione soggetto-fruitore:"+idSoggetto+
														" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+" per impostare nome delegate '"+nomePortaDefault+"' in corso...");
													this.driverConfigurazione.updatePortaDelegata(pd);
													this.log.debug("Aggiornamento Porta Delegata '"+idPortaDelegata+"' per  Mapping Fruizione soggetto-fruitore:"+idSoggetto+
														" soggetto-erogatore:"+idServizio.getSoggettoErogatore()+" servizio:"+
														idServizio.getTipo()+"/"+idServizio.getNome()+" v"+idServizio.getVersione()+" per impostare nome delegate '"+nomePortaDefault+"' effettuata con successo");
													
												}
											}
										}
									}
									
								}
							}
							
						}
					}
					
				}
	
			}			
			

		} catch (Throwable se) {
			throw new ProtocolException(se.getMessage(),se);
		} 
		
	}
	
}
