package org.openspcoop2.web.monitor.statistiche.dao;

import org.openspcoop2.web.monitor.core.dao.IService;
import org.openspcoop2.web.monitor.core.datamodel.Res;
import org.openspcoop2.web.monitor.core.datamodel.ResBase;
import org.openspcoop2.web.monitor.core.datamodel.ResDistribuzione;
import org.openspcoop2.web.monitor.core.report.ILiveReport;

import java.util.List;
import java.util.Map;

import org.openspcoop2.generic_project.exception.ServiceException;

public interface IStatisticheGiornaliere extends IService<ResBase, Integer>,ILiveReport {

	//Andamento Temporale
	public List<Res> findAllAndamentoTemporale() throws ServiceException;
	public List<Res> findAllAndamentoTemporale(int start, int limit) throws ServiceException;
	public int countAllAndamentoTemporale() throws ServiceException;
	
	//Distribuzione Per Soggetto
	public List<ResDistribuzione> findAllDistribuzioneSoggetto() throws ServiceException;
	public List<ResDistribuzione> findAllDistribuzioneSoggetto(int start, int limit) throws ServiceException;
	public int countAllDistribuzioneSoggetto() throws ServiceException;
	
	//Distribuzione Per Servizio
	public List<ResDistribuzione> findAllDistribuzioneServizio() throws ServiceException;
	public List<ResDistribuzione> findAllDistribuzioneServizio(int start, int limit) throws ServiceException;
	public int countAllDistribuzioneServizio() throws ServiceException;
		
	//Distribuzione Per Azione
	public List<ResDistribuzione> findAllDistribuzioneAzione() throws ServiceException;
	public List<ResDistribuzione> findAllDistribuzioneAzione(int start, int limit) throws ServiceException;
	public int countAllDistribuzioneAzione() throws ServiceException;
	
	//Distribuzione per Servizio Applicativo
	public List<ResDistribuzione> findAllDistribuzioneServizioApplicativo() throws ServiceException;
	public List<ResDistribuzione> findAllDistribuzioneServizioApplicativo(int start, int limit) throws ServiceException;
	public int countAllDistribuzioneServizioApplicativo() throws ServiceException;
	
	//statistiche personalizzate
	public int countAllDistribuzionePersonalizzata() throws ServiceException;
	public List<ResDistribuzione> findAllDistribuzionePersonalizzata() throws ServiceException;
	public List<ResDistribuzione> findAllDistribuzionePersonalizzata(int start, int limit) throws ServiceException;
	public Map<String, List<Res>> findAllAndamentoTemporalePersonalizzato() throws ServiceException;
	public Map<String, List<Res>> findAllAndamentoTemporalePersonalizzato(int start, int limit) throws ServiceException;
	
	//distribuzione personalizzata
	public List<String> getValoriRisorse() throws ServiceException;
	

}