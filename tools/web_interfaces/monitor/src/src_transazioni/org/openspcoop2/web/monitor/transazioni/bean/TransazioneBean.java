package org.openspcoop2.web.monitor.transazioni.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.core.transazioni.Transazione;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.protocol.engine.ProtocolFactoryManager;
import org.openspcoop2.protocol.engine.utils.NamingUtils;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.sdk.constants.EsitoTransazioneName;
import org.openspcoop2.protocol.utils.EsitiProperties;
import org.openspcoop2.protocol.utils.PorteNamingUtils;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.json.JSONUtils;
import org.openspcoop2.web.monitor.core.core.Utils;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;
import org.openspcoop2.web.monitor.core.utils.BeanUtils;
import org.openspcoop2.web.monitor.core.utils.BlackListElement;

public class TransazioneBean extends Transazione{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long latenzaTotale = null;
	private Long latenzaServizio = null;
	private Long latenzaPorta = null;

	public TransazioneBean() {
		super();
	}
	
	public TransazioneBean(Transazione transazione){
		List<BlackListElement> metodiEsclusi = new ArrayList<BlackListElement>(
				0);
		metodiEsclusi.add(new BlackListElement("setLatenzaTotale",
				Long.class));
		metodiEsclusi.add(new BlackListElement("setLatenzaServizio",
				Long.class));
		metodiEsclusi.add(new BlackListElement("setLatenzaPorta",
				Long.class));
		
		BeanUtils.copy(this, transazione, metodiEsclusi);
	}

	public Long getLatenzaTotale() {
		if(this.latenzaTotale == null){
			if(this.dataUscitaRisposta != null && this.dataIngressoRichiesta != null){
				this.latenzaTotale = this.dataUscitaRisposta.getTime() - this.dataIngressoRichiesta.getTime();
			}
		}

		if(this.latenzaTotale == null)
			return -1L;
		
		return this.latenzaTotale;
	}

	public void setLatenzaTotale(Long latenzaTotale) {
		this.latenzaTotale = latenzaTotale;
	}

	public Long getLatenzaServizio() {
		if(this.latenzaServizio == null){
			if(this.dataUscitaRichiesta != null && this.dataIngressoRisposta != null){
				this.latenzaServizio = this.dataIngressoRisposta.getTime() - this.dataUscitaRichiesta.getTime();
			}
		}
		
		if(this.latenzaServizio == null)
			return -1L;

		return this.latenzaServizio;
	}

	public void setLatenzaServizio(Long latenzaServizio) {
		this.latenzaServizio = latenzaServizio;
	}

	public Long getLatenzaPorta() {
		if(this.latenzaPorta == null){
			if(this.getLatenzaTotale() != null && this.getLatenzaTotale()>=0)
				if(this.getLatenzaServizio() != null && this.getLatenzaServizio()>=0)
					this.latenzaPorta = this.getLatenzaTotale().longValue() - this.getLatenzaServizio().longValue();
				else 
					this.latenzaPorta = this.getLatenzaTotale();

		}
		
		if(this.latenzaPorta == null)
			return -1L;

		return this.latenzaPorta;
	}

	public void setLatenzaPorta(Long latenzaPorta) {
		this.latenzaPorta = latenzaPorta;
	}

	public String getEsitoStyleClass(){
		
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger());
			String name = esitiProperties.getEsitoName(this.getEsito());
			EsitoTransazioneName esitoName = EsitoTransazioneName.convertoTo(name);
			boolean casoSuccesso = esitiProperties.getEsitiCodeOk().contains(this.getEsito());
			if(EsitoTransazioneName.ERRORE_APPLICATIVO.equals(esitoName)){
				//return "icon-alert-orange";
				return "icon-alert-yellow";
			}
			else if(casoSuccesso){
				return "icon-verified-green";
			}
			else{
				return "icon-alert-red";
			}
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo del layout dell'esito ["+this.getEsito()+"]: "+e.getMessage(),e);
			return "icon-ko";
		}
		
	}
	
	public boolean isEsitoOk(){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger());
			boolean res = esitiProperties.getEsitiCodeOk_senzaFaultApplicativo().contains(this.getEsito());
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+this.getEsito()+")");
			return res;
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo del layout dell'esito ["+this.getEsito()+"]: "+e.getMessage(),e);
			return false;
		}
	}
	public boolean isEsitoFaultApplicativo(){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger());
			boolean res = esitiProperties.getEsitiCodeFaultApplicativo().contains(this.getEsito());
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+this.getEsito()+")");
			return res;
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo del layout dell'esito ["+this.getEsito()+"]: "+e.getMessage(),e);
			return false;
		}
	}
	public boolean isEsitoKo(){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger());
			boolean res = esitiProperties.getEsitiCodeKo_senzaFaultApplicativo().contains(this.getEsito());
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+this.getEsito()+")");
			return res;
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo del layout dell'esito ["+this.getEsito()+"]: "+e.getMessage(),e);
			return false;
		}
	}
	
	public boolean isShowContesto(){
		try{
			return EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger()).getEsitiTransactionContextCode().size()>1;
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo dei contesti: "+e.getMessage(),e);
			return false;
		}
	}
	
	public String getFaultCooperazionePretty(){
		String f = super.getFaultCooperazione();
		String toRet = null;
		if(f !=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
			if(errore!= null)
				return "";

			MessageType messageType= MessageType.XML;
//			if(StringUtils.isNotEmpty(super.getFormatoFaultCooperazione())) {
//			messageType = MessageType.valueOf(super.getFormatoFaultCooperazione());
//		}

			switch (messageType) {
			case BINARY:
			case MIME_MULTIPART:
				// questi due casi dovrebbero essere gestiti sopra 
				break;	
			case JSON:
				JSONUtils jsonUtils = JSONUtils.getInstance(true);
				try {
					toRet = jsonUtils.toString(jsonUtils.getAsNode(f));
				} catch (UtilsException e) {
				}
				break;
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = Utils.prettifyXml(f);
				break;
			}
		}

		if(toRet == null)
			toRet = f != null ? f : "";

			return toRet;
	}
	
	public boolean isVisualizzaFaultCooperazione(){
		boolean visualizzaMessaggio = true;
		String f = super.getFaultCooperazione();
		
		if(f == null)
			return false;
		
		StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
		String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
		if(errore!= null)
			return false;

		//			MessageType messageType= MessageType.XML;
		//			if(StringUtils.isNotEmpty(this.dumpMessaggio.getFormatoMessaggio())) {
		//				messageType = MessageType.valueOf(this.dumpMessaggio.getFormatoMessaggio());
		//			}

		//			switch (messageType) {
		//			case BINARY:
		//			case MIME_MULTIPART:
		//				// questi due casi dovrebbero essere gestiti sopra 
		//				break;	
		//			case JSON:
		//				JSONUtils jsonUtils = JSONUtils.getInstance(true);
		//				try {
		//					toRet = jsonUtils.toString(jsonUtils.getAsNode(this.dumpMessaggio.getBody()));
		//				} catch (UtilsException e) {
		//				}
		//				break;
		//			case SOAP_11:
		//			case SOAP_12:
		//			case XML:
		//			default:
		//				toRet = Utils.prettifyXml(this.dumpMessaggio.getBody());
		//				break;
		//			}

		return visualizzaMessaggio;
	}

	public String getBrushFaultCooperazione() {
		String toRet = null;
		String f = super.getFaultCooperazione();
		if(f!=null) {
			MessageType messageType= MessageType.XML;
//			if(StringUtils.isNotEmpty(super.getFormatoFaultCooperazione())) {
//				messageType = MessageType.valueOf(super.getFormatoFaultCooperazione());
//			}

			switch (messageType) {
			case JSON:
				toRet = "json";
				break;
			case BINARY:
			case MIME_MULTIPART:
				// per ora restituisco il default
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = "xml";
				break;
			}
		}

		return toRet;
	}

	public String getErroreVisualizzaFaultCooperazione(){
		String f = super.getFaultCooperazione();
		if(f!=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
			return errore;
		}

		return null;
	}
	
	public String getFaultIntegrazionePretty(){
		String f = super.getFaultIntegrazione();
		String toRet = null;
		if(f !=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
			if(errore!= null)
				return "";

			MessageType messageType= MessageType.XML;
//			if(StringUtils.isNotEmpty(super.getFormatoFaultCooperazione())) {
//			messageType = MessageType.valueOf(super.getFormatoFaultCooperazione());
//		}

			switch (messageType) {
			case BINARY:
			case MIME_MULTIPART:
				// questi due casi dovrebbero essere gestiti sopra 
				break;	
			case JSON:
				JSONUtils jsonUtils = JSONUtils.getInstance(true);
				try {
					toRet = jsonUtils.toString(jsonUtils.getAsNode(f));
				} catch (UtilsException e) {
				}
				break;
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = Utils.prettifyXml(f);
				break;
			}
		}

		if(toRet == null)
			toRet = f != null ? f : "";

			return toRet;
	}
	
	public boolean isVisualizzaFaultIntegrazione(){
		boolean visualizzaMessaggio = true;
		String f = super.getFaultIntegrazione();
		
		if(f == null)
			return false;
		
		StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
		String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
		if(errore!= null)
			return false;

		//			MessageType messageType= MessageType.XML;
		//			if(StringUtils.isNotEmpty(this.dumpMessaggio.getFormatoMessaggio())) {
		//				messageType = MessageType.valueOf(this.dumpMessaggio.getFormatoMessaggio());
		//			}

		//			switch (messageType) {
		//			case BINARY:
		//			case MIME_MULTIPART:
		//				// questi due casi dovrebbero essere gestiti sopra 
		//				break;	
		//			case JSON:
		//				JSONUtils jsonUtils = JSONUtils.getInstance(true);
		//				try {
		//					toRet = jsonUtils.toString(jsonUtils.getAsNode(this.dumpMessaggio.getBody()));
		//				} catch (UtilsException e) {
		//				}
		//				break;
		//			case SOAP_11:
		//			case SOAP_12:
		//			case XML:
		//			default:
		//				toRet = Utils.prettifyXml(this.dumpMessaggio.getBody());
		//				break;
		//			}

		return visualizzaMessaggio;
	}

	public String getBrushFaultIntegrazione() {
		String toRet = null;
		String f = super.getFaultIntegrazione();
		if(f!=null) {
			MessageType messageType= MessageType.XML;
//			if(StringUtils.isNotEmpty(super.getFormatoFaultCooperazione())) {
//				messageType = MessageType.valueOf(super.getFormatoFaultCooperazione());
//			}

			switch (messageType) {
			case JSON:
				toRet = "json";
				break;
			case BINARY:
			case MIME_MULTIPART:
				// per ora restituisco il default
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = "xml";
				break;
			}
		}

		return toRet;
	}

	public String getErroreVisualizzaFaultIntegrazione(){
		String f = super.getFaultIntegrazione();
		if(f!=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
			return errore;
		}

		return null;
	}
	
	public String getEventiGestioneHTML(){
		String tmp = this.getEventiGestione();
		if(tmp!=null){
			tmp = tmp.trim();
			if(tmp.contains(",")){
				String [] split = tmp.split(",");
				if(split!=null && split.length>0){
					StringBuffer bf = new StringBuffer();
					for (int i = 0; i < split.length; i++) {
						if(bf.length()>0){
							bf.append("<BR/>");
						}
						bf.append(split[i].trim());
					}
					return bf.toString();
				}
				else{
					return tmp;
				}
			}
			else{
				return tmp;
			}
		}
		else{
			return null;
		}
	}

	@Override
	public String getNomePorta(){
		String nomePorta = super.getNomePorta();
		if(nomePorta!=null && nomePorta.startsWith("null_") && (nomePorta.length()>"null_".length())){
			return nomePorta.substring("null_".length());
		}
		else{
			return nomePorta;
		}
	}
	
	public String getSoggettoFruitore() throws Exception { 
		if(StringUtils.isNotEmpty(this.getNomeSoggettoFruitore())) {
			return NamingUtils.getLabelSoggetto(this.getProtocollo(), this.getTipoSoggettoFruitore(), this.getNomeSoggettoFruitore());
		}
		
		return "";
	}
	
	public String getSoggettoErogatore() throws Exception { 
		if(StringUtils.isNotEmpty(this.getNomeSoggettoErogatore())) {
			return NamingUtils.getLabelSoggetto(this.getProtocollo(), this.getTipoSoggettoErogatore(), this.getNomeSoggettoErogatore());
		}
		
		return "";
	}
	
	public String getSoggettoPdd() throws Exception { 
		if(StringUtils.isNotEmpty(this.getPddNomeSoggetto())) {
			return NamingUtils.getLabelSoggetto(this.getProtocollo(), this.getPddTipoSoggetto(), this.getPddNomeSoggetto());
		}
		
		return "";
	}
	
	public String getProtocolloLabel() throws Exception{
		return NamingUtils.getLabelProtocollo(this.getProtocollo()); 
	}
	
	
	public String getServizio() throws Exception{
		if(StringUtils.isNotEmpty(this.getNomeServizio())) {
			return NamingUtils.getLabelAccordoServizioParteSpecificaSenzaErogatore(this.getProtocollo(), this.getTipoServizio(), this.getNomeServizio(), this.getVersioneServizio());
		}
		return "";
	}
	
	public String getPortaLabel() throws Exception{
		IProtocolFactory<?> protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByName(this.getProtocollo());
		PorteNamingUtils n = new PorteNamingUtils(protocolFactory);
		switch(this.getPddRuolo()) {
		case APPLICATIVA:
			return n.normalizePA(this.getNomePorta());
		case DELEGATA:
			return n.normalizePD(this.getNomePorta());
		case INTEGRATION_MANAGER:
		case ROUTER:
		default:
			return this.getNomePorta();
		}
	}
}
