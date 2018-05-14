package org.openspcoop2.web.monitor.core.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.core.commons.CoreException;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.dao.IExpressionConstructor;
import org.openspcoop2.generic_project.expression.IExpression;

import it.link.pdd.core.utenti.UtenteSoggetto;
import org.openspcoop2.monitor.engine.constants.Costanti;
import org.openspcoop2.web.monitor.core.bean.UserDetailsBean;

public class PermessiUtenteOperatore {

	public static boolean CHECK_UNIQUE_SOGGETTO_SERVIZIO_UTENTE = false;
	
	private List<IDServizio> listIDServizi = new ArrayList<IDServizio>();
	private List<IDSoggetto> listIDSoggetti = new ArrayList<IDSoggetto>();
	
	public void addSoggetto(IDSoggetto idSoggetto) throws CoreException{
		for (IDSoggetto idSoggettoCheck : this.listIDSoggetti) {
			if(idSoggettoCheck.equals(idSoggetto)){
				throw new CoreException("Soggetto ["+idSoggetto+"] già aggiunto");
			}
		}
		if(CHECK_UNIQUE_SOGGETTO_SERVIZIO_UTENTE){
			for (IDServizio idServizio : this.listIDServizi) {
				if(idServizio.getSoggettoErogatore().equals(idSoggetto)){
					throw new CoreException("Soggetto ["+idSoggetto+"] non può essere aggiunto come filtro singolo poichè già esistono dei filtri sui servizi da lui erogati");
				}
			}
		}
		this.listIDSoggetti.add(idSoggetto);
	}
	
	public void addServizio(IDServizio idServizio) throws CoreException{
		for (IDServizio idServizioCheck : this.listIDServizi) {
			if(idServizioCheck.equals(idServizio)){
				throw new CoreException("Servizio ["+idServizio+"] già aggiunto");
			}
		}
		if(CHECK_UNIQUE_SOGGETTO_SERVIZIO_UTENTE){
			for (IDSoggetto idSoggetto : this.listIDSoggetti) {
				if(idSoggetto.equals(idServizio.getSoggettoErogatore())){
					throw new CoreException("Servizio ["+idServizio+"] non può essere aggiunto poichè esiste già un filtro generale sul soggetto erogatore valido per tutti i servizi");
				}
			}
		}
		this.listIDServizi.add(idServizio);
	}
	
	public void addPermessi(PermessiUtenteOperatore p) throws CoreException{
		for (IDSoggetto idSoggetto : p.listIDSoggetti) {
			this.addSoggetto(idSoggetto);
		}
		for (IDServizio idServizio : p.listIDServizi) {
			this.addServizio(idServizio);
		}
	}
	public void addPermessiSoggetti(PermessiUtenteOperatore p) throws CoreException{
		for (IDSoggetto idSoggetto : p.listIDSoggetti) {
			this.addSoggetto(idSoggetto);
		}
	}
	public void addPermessiServizi(PermessiUtenteOperatore p) throws CoreException{
		for (IDServizio idServizio : p.listIDServizi) {
			this.addServizio(idServizio);
		}
	}
	
	public List<IDServizio> getListIDServizi() {
		return this.listIDServizi;
	}

	public List<IDSoggetto> getListIDSoggetti() {
		return this.listIDSoggetti;
	}
	
	
	@Override
	public String toString(){
		StringBuffer bf = new StringBuffer();
		if(this.listIDSoggetti.size()>0){
			for (IDSoggetto idSoggetto : this.listIDSoggetti) {
				if(bf.length()>0){
					bf.append(",");
				}
				bf.append(idSoggetto.toString());
			}
		}
		if(this.listIDServizi.size()>0){
			for (IDServizio idServizio : this.listIDServizi) {
				if(bf.length()>0){
					bf.append(",");
				}
				bf.append(idServizio.toString());
			}
		}
		return bf.toString();
	}
	
	public IExpression toExpression(IExpressionConstructor exprConstructor,IField fieldIdentificativoPorta,
			IField fieldTipoSoggettoErogatore,IField fieldNomeSoggettoErogatore,IField fieldTipoServizio,IField fieldNomeServizio) throws CoreException{
		boolean includeTransazioniSenzaIdentificativoPorta = false;
		// Oramai non ha più senso, tutte le transazioni vengono sempre valorizzate con un codice porta, al massimo contengono quello di default della PdD
		// E' rimasta la condizione nel codice della query sottostante, ma non dovrebbe avere piu effetto.
		return toExpression(exprConstructor, fieldIdentificativoPorta, 
				fieldTipoSoggettoErogatore, fieldNomeSoggettoErogatore, fieldTipoServizio, fieldNomeServizio, 
				includeTransazioniSenzaIdentificativoPorta);
	}
	public IExpression toExpression(IExpressionConstructor exprConstructor,IField fieldIdentificativoPorta,
			IField fieldTipoSoggettoErogatore,IField fieldNomeSoggettoErogatore,IField fieldTipoServizio,IField fieldNomeServizio,
			boolean includeTransazioniSenzaIdentificativoPorta) throws CoreException{
		try{
			IExpression expr = exprConstructor.newExpression();
			expr.or();
			
			// per gli errori limite (es. pd non trovata)
			if(includeTransazioniSenzaIdentificativoPorta){
				expr.isNull(fieldIdentificativoPorta); // transazioni
				expr.equals(fieldIdentificativoPorta,Costanti.INFORMAZIONE_NON_DISPONIBILE); // statistiche
			}
			
			if(this.listIDSoggetti.size()>0){
				// I Soggetti rappresentano coloro che gestiscono la transazione, sia che siano fruizioni che erogazioni
				List<String> identificativiPorta = new ArrayList<String>();
				for (IDSoggetto idSoggetto : this.listIDSoggetti) {
					identificativiPorta.add(idSoggetto.getCodicePorta());
				}
				expr.in(fieldIdentificativoPorta, identificativiPorta);
			}	
					
			if(this.listIDServizi.size()>0){
				// I Servizi invece rappresentano proprio un servizio specifico (identificato dalla quadrupla tipo/nome servizio e tipo/nome soggetto)
				// indipendentemente dal fatto se il soggetto erogatore è un soggetto operativo (che quindi ha emesso la transazione e ha l'identificativo porta) o meno
				List<IExpression> servizi = new ArrayList<IExpression>();
				for (IDServizio idServizio : this.listIDServizi) {
					IExpression exprServ = exprConstructor.newExpression();
					exprServ.and();
					exprServ.isNotNull(fieldTipoSoggettoErogatore);
					exprServ.equals(fieldTipoSoggettoErogatore, idServizio.getSoggettoErogatore().getTipo());
					exprServ.isNotNull(fieldNomeSoggettoErogatore);
					exprServ.equals(fieldNomeSoggettoErogatore, idServizio.getSoggettoErogatore().getNome());
					exprServ.isNotNull(fieldTipoServizio);
					exprServ.equals(fieldTipoServizio, idServizio.getTipoServizio());
					exprServ.isNotNull(fieldNomeServizio);
					exprServ.equals(fieldNomeServizio, idServizio.getServizio());
					servizi.add(exprServ);
				}
				expr.or(servizi.toArray(new IExpression[1]));
			}
			
			return expr;
		}catch(Exception e){
			throw new CoreException(e.getMessage(),e);
		}
	}
	
	public IExpression toExpressionAllarmi(IExpressionConstructor exprConstructor,
			IField fieldIdentificativoPortaMittente,IField fieldTipoMittente,IField fieldMittente,
			IField fieldIdentificativoPortaDestinatario,IField fieldTipoDestinatario,IField fieldDestinatario,
			IField fieldTipoServizio,IField fieldNomeServizio ) throws CoreException{
		try{
			
			IExpression expr = exprConstructor.newExpression();
			expr.or();
			
			if(this.listIDSoggetti.size()>0){
				
				// utility
				IExpression mittenteStartExpr = exprConstructor.newExpression();
				mittenteStartExpr.and();
				mittenteStartExpr.equals(fieldIdentificativoPortaMittente,"*");
				mittenteStartExpr.equals(fieldTipoMittente,"*");
				mittenteStartExpr.equals(fieldMittente,"*");
				
				IExpression destinatarioStartExpr = exprConstructor.newExpression();
				destinatarioStartExpr.and();
				destinatarioStartExpr.equals(fieldIdentificativoPortaDestinatario,"*");
				destinatarioStartExpr.equals(fieldTipoDestinatario,"*");
				destinatarioStartExpr.equals(fieldDestinatario,"*");
				
				List<String> identificativiPorta = new ArrayList<String>();
				for (IDSoggetto idSoggetto : this.listIDSoggetti) {
					identificativiPorta.add(idSoggetto.getCodicePorta());
				}
				
				
				// gli allarmi che possono vedere sono:
				// mittente '*' e destinatario uno dei soggetti associati all'utenza
				// mittente uno dei soggetti associati all'utenza e destinatario '*'
				// mittente e destinatario associati all'utenza
				
				IExpression soggettiGestioneExpr = exprConstructor.newExpression();				
				soggettiGestioneExpr.or();
				
				
				
				// mittente '*' e destinatario uno dei soggetti associati all'utenza
				
				IExpression idportaDestExpr = exprConstructor.newExpression();
				idportaDestExpr.in(fieldIdentificativoPortaDestinatario,identificativiPorta);
				
				IExpression destinatarioExpr = exprConstructor.newExpression();
				destinatarioExpr.or();
				for (IDSoggetto idSoggetto : this.listIDSoggetti) {
					Map<IField, Object> destSoggetto = new HashMap<IField, Object>();
					destSoggetto.put(fieldTipoDestinatario,idSoggetto.getTipo());
					destSoggetto.put(fieldDestinatario,idSoggetto.getNome());
					destinatarioExpr.allEquals(destSoggetto);
				}
				
				soggettiGestioneExpr.and(mittenteStartExpr,idportaDestExpr,destinatarioExpr);
				
				
				
				// mittente uno dei soggetti associati all'utenza e destinatario '*'
				
				IExpression idportaMittExpr = exprConstructor.newExpression();
				idportaMittExpr.in(fieldIdentificativoPortaMittente,identificativiPorta);
				
				IExpression mittenteExpr = exprConstructor.newExpression();
				mittenteExpr.or();
				for (IDSoggetto idSoggetto : this.listIDSoggetti) {
					Map<IField, Object> mittSoggetto = new HashMap<IField, Object>();
					mittSoggetto.put(fieldTipoMittente,idSoggetto.getTipo());
					mittSoggetto.put(fieldMittente,idSoggetto.getNome());
					mittenteExpr.allEquals(mittSoggetto);
				}
				
				soggettiGestioneExpr.and(destinatarioStartExpr,idportaMittExpr,mittenteExpr);
				
				
				
				// mittente e destinatario associati all'utenza
				
				soggettiGestioneExpr.and(idportaMittExpr,mittenteExpr,idportaDestExpr,destinatarioExpr);
				
				
				expr.or(soggettiGestioneExpr);
			}
				
			if(this.listIDServizi.size()>0){
				
				// I Servizi invece rappresentano proprio un servizio specifico (identificato dalla quadrupla tipo/nome servizio e tipo/nome soggetto)
				
				List<IExpression> servizi = new ArrayList<IExpression>();
				for (IDServizio idServizio : this.listIDServizi) {
					IExpression exprServ = exprConstructor.newExpression();
					exprServ.and();
					exprServ.isNotNull(fieldTipoDestinatario);
					exprServ.equals(fieldTipoDestinatario, idServizio.getSoggettoErogatore().getTipo());
					exprServ.isNotNull(fieldDestinatario);
					exprServ.equals(fieldDestinatario, idServizio.getSoggettoErogatore().getNome());
					exprServ.isNotNull(fieldTipoServizio);
					exprServ.equals(fieldTipoServizio, idServizio.getTipoServizio());
					exprServ.isNotNull(fieldNomeServizio);
					exprServ.equals(fieldNomeServizio, idServizio.getServizio());
					servizi.add(exprServ);
				}
				expr.or(servizi.toArray(new IExpression[1]));
			}

			return expr;
		}catch(Exception e){
			throw new CoreException(e.getMessage(),e);
		}
	}
	
	public IExpression toExpressionConfigurazioneServizi(IExpressionConstructor exprConstructor,IField fieldTipoSoggetto,IField fieldNomeSoggetto,
			IField fieldTipoSoggettoErogatore,IField fieldNomeSoggettoErogatore,IField fieldTipoServizio,IField fieldNomeServizio,
			boolean setNotNull) throws CoreException{
		try{
			IExpression expr = exprConstructor.newExpression();
			expr.or();
			
			if(this.listIDSoggetti.size()>0){
				// I Soggetti rappresentano coloro che gestiscono la transazione, sia che siano fruizioni che erogazioni
				List<IExpression> soggetti = new ArrayList<IExpression>();
				for (IDSoggetto idSoggetto : this.listIDSoggetti) {
					IExpression exprSogg = exprConstructor.newExpression();
					exprSogg.and();
					if(setNotNull){
						exprSogg.isNotNull(fieldTipoSoggetto);
					}
					exprSogg.equals(fieldTipoSoggetto, idSoggetto.getTipo());
					if(setNotNull){
						exprSogg.isNotNull(fieldNomeSoggetto);
					}
					exprSogg.equals(fieldNomeSoggetto, idSoggetto.getNome());
					soggetti.add(exprSogg);
				}
				if(soggetti.size()>0)
					expr.or(soggetti.toArray(new IExpression[1]));
			}	
					
			if(this.listIDServizi.size()>0){
				// I Servizi invece rappresentano proprio un servizio specifico (identificato dalla quadrupla tipo/nome servizio e tipo/nome soggetto)
				// indipendentemente dal fatto se il soggetto erogatore è un soggetto operativo (che quindi ha emesso la transazione e ha l'identificativo porta) o meno
				List<IExpression> servizi = new ArrayList<IExpression>();
				for (IDServizio idServizio : this.listIDServizi) {
					IExpression exprServ = exprConstructor.newExpression();
					exprServ.and();
					if(setNotNull){
						exprServ.isNotNull(fieldTipoSoggettoErogatore);
					}
					exprServ.equals(fieldTipoSoggettoErogatore, idServizio.getSoggettoErogatore().getTipo());
					if(setNotNull){
						exprServ.isNotNull(fieldNomeSoggettoErogatore);
					}
					exprServ.equals(fieldNomeSoggettoErogatore, idServizio.getSoggettoErogatore().getNome());
					if(setNotNull){
						exprServ.isNotNull(fieldTipoServizio);
					}
					exprServ.equals(fieldTipoServizio, idServizio.getTipoServizio());
					if(setNotNull){
						exprServ.isNotNull(fieldNomeServizio);
					}
					exprServ.equals(fieldNomeServizio, idServizio.getServizio());
					servizi.add(exprServ);
				}
				if(servizi.size()>0)
					expr.or(servizi.toArray(new IExpression[1]));
			}
			
			return expr;
		}catch(Exception e){
			throw new CoreException(e.getMessage(),e);
		}
	}
	
	public static PermessiUtenteOperatore getPermessiUtenteOperatore(UserDetailsBean u,
			String tipoSoggettoLocale, String nomeSoggettoLocale)
			throws CoreException {
		
		if (u.isAdmin()) {
			// vuota: l'utente può vedere tutto. E' un amministratore
			return null;
		}
		
		if (StringUtils.isNotEmpty(nomeSoggettoLocale) ){
			
			// L'invocazione serve solamente a verificare i permessi
			// Poi gli oggetti ritornati DEVONO corrispondere esattamente ai permessi impostati sul database
			@SuppressWarnings("unused")
			PermessiUtenteOperatore soggLocale = PermessiUtenteOperatore.getPermessiUtenteOperatore(u, tipoSoggettoLocale, nomeSoggettoLocale, null, null);
			@SuppressWarnings("unused")
			PermessiUtenteOperatore servizio = PermessiUtenteOperatore.getPermessiUtenteOperatore(u, null, null, null, null);
//			soggLocale.addPermessiServizi(servizio);
//			return soggLocale;
//			
		}
//		else{
		return PermessiUtenteOperatore.getPermessiUtenteOperatore(u, null, null, null, null);
//		}
		
	}
	
	public static PermessiUtenteOperatore getPermessiUtenteOperatore(UserDetailsBean u,
			String tipoSoggettoLocale, String nomeSoggettoLocale, 
			String tipoServizio, String nomeServizio)
			throws CoreException {

		// Controllo sul soggetto indicato
		if (StringUtils.isNotEmpty(nomeSoggettoLocale) ) {		
			// Se ho selezionato un soggetto locale controllo che il valore inserito sia effettivamente un soggetto gestito dall'utente, 
			// altrimenti segnalo l'errore
			// il controllo vale solo se l'utente non e' admin
			if(!u.isAdmin()){
				boolean found = false;
				for (UtenteSoggetto utenteSoggetto : u.getUtenteSoggettoList()) {
					if(utenteSoggetto.getSoggetto().getTipo().equals(tipoSoggettoLocale) &&
							utenteSoggetto.getSoggetto().getNome().equals(nomeSoggettoLocale)){						
						found = true;
						break;
					}
				}
				if(!found){
					throw new CoreException("L'utente '"+u.getUsername()+"' non ha i diritti necessari per esaminare le informazioni diagnostiche del soggetto "+tipoSoggettoLocale+"/"+nomeSoggettoLocale);
				}
			}		
		}
		
		// Controllo sul servizio indicato
		if (StringUtils.isNotEmpty(nomeServizio) ) {		
			// Se ho selezionato un servizio controllo che il valore inserito sia effettivamente uno dei servizi permessi all'utente, 
			// altrimenti segnalo l'errore
			// il controllo vale solo se l'utente non e' admin
			if(!u.isAdmin()){
				boolean found = false;
				if (StringUtils.isNotEmpty(nomeSoggettoLocale) ) {
					for (UtenteSoggetto utenteSoggetto : u.getUtenteSoggettoList()) {
						if(utenteSoggetto.getSoggetto().getTipo().equals(tipoSoggettoLocale) &&
								utenteSoggetto.getSoggetto().getNome().equals(nomeSoggettoLocale)){						
							if(utenteSoggetto.getServizio()!=null){
								if(utenteSoggetto.getServizio().getTipo().equals(tipoServizio) &&
										utenteSoggetto.getServizio().getNome().equals(nomeServizio)){		
									found = true;
									break;
								}
							}
						}
					}
					if(!found){
						throw new CoreException("L'utente '"+u.getUsername()+"' non ha i diritti necessari per esaminare le informazioni diagnostiche del servizio "+tipoServizio+"/"+nomeServizio+
								" erogato dal soggetto "+tipoSoggettoLocale+"/"+nomeSoggettoLocale);
					}
				}
				else{
					throw new CoreException("Utilizzo del metodo non valido. Se viene indicato il servizio deve anche essere indicato il soggeto erogatore");
				}
			}		
		}
		
		
		// Ricerca puntuale dei permessi riguardanti il soggettoLocale o il servizio indicato
		if (StringUtils.isNotEmpty(nomeSoggettoLocale) || StringUtils.isNotEmpty(nomeServizio) ) {		
			
			// Recupero identificativoPorta se è stato selezionato un soggetto locale (o un servizio)
			String idPorta = Utility.getIdentificativoPorta(tipoSoggettoLocale, nomeSoggettoLocale);
			
			// Creazione dei permessi
			PermessiUtenteOperatore permessi = new PermessiUtenteOperatore();
			IDSoggetto idSoggetto = new IDSoggetto(tipoSoggettoLocale,nomeSoggettoLocale,idPorta);
			
			if(StringUtils.isNotEmpty(nomeServizio)){
				IDServizio idServizio = new IDServizio(idSoggetto, tipoServizio, nomeServizio);
				permessi.addServizio(idServizio);
			}
			else{
				permessi.addSoggetto(idSoggetto);
			}			
			return permessi;
		}
	

		// Ricerca in base a ciò che l'utente può vedere rispetto ai suoi soggetto o servizi

		if (u.isAdmin()) {
			// vuota: l'utente può vedere tutto. E' un amministratore
			return null;
		} else {
			
			PermessiUtenteOperatore permessi = new PermessiUtenteOperatore();
			
			for (UtenteSoggetto utenteSoggetto : u.getUtenteSoggettoList()) {
				
				// Recupero identificativoPorta se è stato selezionato un soggetto locale (o un servizio)
				String idPorta = Utility.getIdentificativoPorta(utenteSoggetto.getSoggetto().getTipo(), utenteSoggetto.getSoggetto().getNome());
				
				// Creazione permesso
				IDSoggetto idSoggetto = new IDSoggetto(utenteSoggetto.getSoggetto().getTipo(), utenteSoggetto.getSoggetto().getNome(),idPorta);
				
				if(utenteSoggetto.getServizio()!=null){
					IDServizio idServizio = new IDServizio(idSoggetto, utenteSoggetto.getServizio().getTipo(), utenteSoggetto.getServizio().getNome());
					permessi.addServizio(idServizio);
				}
				else{
					permessi.addSoggetto(idSoggetto);
				}			
				
			}
			
			return permessi;
		}
	}
	

	
}