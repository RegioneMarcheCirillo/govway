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

package org.openspcoop2.pdd.core.behaviour.built_in.load_balance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openspcoop2.pdd.core.behaviour.BehaviourException;
import org.openspcoop2.pdd.core.behaviour.built_in.load_balance.health_check.HealthCheckConfigurazione;
import org.openspcoop2.utils.date.DateManager;
import org.openspcoop2.utils.date.DateUtils;

/**
 * LoadBalancerPool
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class LoadBalancerPool implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int DEFAULT_WEIGHT = 1; 
	
	public LoadBalancerPool(HealthCheckConfigurazione healthCheck) {
		this.healthCheck = healthCheck;
	}
	
	@Override
	public String toString() {
		synchronized (this.semaphore) {
			StringBuilder bf = new StringBuilder();
			bf.append("Connectors: ").append(this.connectorMap.size());
			bf.append("\nTotal Weight: ").append(this.totalWeight);
			bf.append("\nPosition: ").append(this.position);
			if(this.healthCheck!=null) {
				bf.append("\nPassiveHealtCheck: ").append(this.healthCheck.isPassiveCheckEnabled());
				if(this.healthCheck.isPassiveCheckEnabled()){
					bf.append("\n  Exclude for seconds: ").append(this.healthCheck.getPassiveHealthCheck_excludeForSeconds());
				}
			}
			for (String name : this.connectorMap.keySet()) {
				bf.append("\n");
				bf.append("- ").append(name).append(" : ").append(" ( weight:").append(this.connectorMap.get(name));
				if(this.connectorMap_activeConnections.containsKey(name)) {
					bf.append(" activeConnections:").append(this.connectorMap_activeConnections.get(name));
				}
				if(this.connectorMap_errorDate.containsKey(name)) {
					bf.append(" connectionError:").append(DateUtils.getSimpleDateFormatMs().format(this.connectorMap_errorDate.get(name)));
				}
				bf.append(" )");
			}
			return bf.toString();
		}
	}
	
	
	protected Boolean semaphore = true;
	protected Map<String, Integer> connectorMap = new HashMap<>();
	protected Map<String, Integer> connectorMap_activeConnections = new HashMap<>();
	protected Map<String, Date> connectorMap_errorDate = new HashMap<>();
	private int totalWeight = 0;
	
	private int position = -1;
	
	private HealthCheckConfigurazione healthCheck = null;
	
	

	public int getNextPosition(boolean checkByWeight) throws BehaviourException {
		
		if(!isPassiveHealthCheck()) {
			synchronized (this.semaphore) {
				return _getNextPosition(checkByWeight);
			}
		}
		else {
			synchronized (this.semaphore) {
				int pos = _getNextPosition(checkByWeight);
				
				Set<String> setOriginal = this.connectorMap.keySet();
				List<String> serverList = new ArrayList<>();
				serverList.addAll(setOriginal);
				
				Set<String> setAfterPassiveHealthCheck = passiveHealthCheck(setOriginal, false);
				
				// prima verifica
				String selectedConnector = serverList.get(pos);
				if(setAfterPassiveHealthCheck.contains(selectedConnector)) {
					return pos;
				}
				
				// controllo prossime posizioni fino a tornare a quella attuale
				int nextPos = _getNextPosition(checkByWeight);
				while(nextPos!=pos) {
					selectedConnector = serverList.get(nextPos);
					if(setAfterPassiveHealthCheck.contains(selectedConnector)) {
						return nextPos;
					}
					nextPos = _getNextPosition(checkByWeight);
				}
				
				throw new BehaviourException("Nessun connettore selezionabile (passive health check)");
			}
		}
		
	}
	private int _getNextPosition(boolean checkByWeight) {
		this.position++;
		if(checkByWeight) {
			if(this.position==this.totalWeight) {
				this.position = 0;
			}
		}
		else {
			if(this.position==this.connectorMap.size()) {
				this.position = 0;
			}
		}
		return this.position;
	}
	

	public String getNextConnectorLeastConnections() {
		synchronized (this.semaphore) {
			
			Set<String> setKeys = passiveHealthCheck(this.connectorMap.keySet(), false);
			
			List<String> listMin = new ArrayList<String>();
			int min = 0;
			if(!this.connectorMap_activeConnections.isEmpty()) {
				min = Integer.MAX_VALUE;
				for (String name : setKeys) {
					if(this.connectorMap_activeConnections.containsKey(name)==false) {
						if(min != 0) {
							min = 0;
							listMin.clear();
						}
						listMin.add(name);
					}
					else {
						int active = this.connectorMap_activeConnections.get(name);
						if(active<min) {
							min = active;
							listMin.clear();
							listMin.add(name);
						}
						else if(active==min) {
							listMin.add(name);
						}
					}
				}
			}

			if(listMin.isEmpty()) {
				listMin.addAll(this.connectorMap.keySet());
				//System.out.println("LISTA is EMPTY");
				
			}
			
			//System.out.println("LISTA min["+min+"]: "+listMin);
			
			return listMin.get(0);
			
		}
	}
	
	public boolean isEmpty() {
		return this.connectorMap.isEmpty();
	}
	
	public Set<String> getConnectorNames(boolean passiveHealthCheck) {
		if(passiveHealthCheck) {
			return passiveHealthCheck(this.connectorMap.keySet(), true);
		}
		else {
			return this.connectorMap.keySet();
		}
	}
	
	public int getWeight(String name) {
		return this.connectorMap.get(name);
	}
	
	public void addConnector(String name) throws BehaviourException {
		this.addConnector(name, DEFAULT_WEIGHT);
	}
	public void addConnector(String name, int weight) throws BehaviourException {
		synchronized (this.semaphore) {
			if(this.connectorMap.containsKey(name)) {
				throw new BehaviourException("Already exists connector '"+name+"'");
			}
			this.connectorMap.put(name, weight);
			this.totalWeight = this.totalWeight+weight;
		}
		
	}
	
	
	public void registerConnectionError(String name) throws BehaviourException {
		synchronized (this.semaphore) {
			if(this.connectorMap_errorDate.containsKey(name)==false) {
				// non aggiorniamo eventualmente la data, teniamo la prima
				this.connectorMap_errorDate.put(name, DateManager.getDate());
			}
		}
	}

	public void addActiveConnection(String name) throws BehaviourException {
		synchronized (this.semaphore) {
			int activeConnections = 0;
			if(this.connectorMap_activeConnections.containsKey(name)) {
				activeConnections = this.connectorMap_activeConnections.remove(name);
			}
			activeConnections++;
			this.connectorMap_activeConnections.put(name, activeConnections);
			//System.out.println("ADD ["+name+"] ["+activeConnections+"]");
		}
	}
	public void removeActiveConnection(String name) throws BehaviourException {
		synchronized (this.semaphore) {
			int activeConnections = 0;
			if(this.connectorMap_activeConnections.containsKey(name)) {
				activeConnections = this.connectorMap_activeConnections.remove(name);
			}
			activeConnections--;
			if(activeConnections>0) {
				this.connectorMap_activeConnections.put(name, activeConnections);
			}
			//System.out.println("REMOVE ["+name+"] ["+activeConnections+"]");
		}
	}
	
	
	protected boolean isPassiveHealthCheck() {
		return this.healthCheck!=null && this.healthCheck.isPassiveCheckEnabled() &&
				this.healthCheck.getPassiveHealthCheck_excludeForSeconds()!=null &&
				this.healthCheck.getPassiveHealthCheck_excludeForSeconds().intValue()>0;
	}
	
	private Set<String> passiveHealthCheck(Set<String> set, boolean syncErase){
		if(!isPassiveHealthCheck() || this.connectorMap_errorDate.isEmpty()) {
			return set;
		}
		
		Date now = DateManager.getDate();
		
		Set<String> newSet = new HashSet<String>();
		List<String> listRimuoviDate = new ArrayList<String>();
		
		for (String name : set) {
			if(this.connectorMap_errorDate.containsKey(name)) {
				Date registrationDate = this.connectorMap_errorDate.get(name);
				long registrationDateLong = registrationDate.getTime();
				long registrationDateExpired = registrationDateLong + (this.healthCheck.getPassiveHealthCheck_excludeForSeconds().intValue() * 1000);
				if(registrationDateExpired<now.getTime()) {
					//System.out.println("RILEVATO ERRORE SCADUTO PER C ["+name+"]");
					listRimuoviDate.add(name);
				}
				else {
					//System.out.println("RILEVATO ERRORE SCADUTO NON ANCORA SCADUTO PER C ["+name+"]");
					continue; // non e' ancora scaduto
				}
			}
						
			newSet.add(name);
		}
		
		if(listRimuoviDate!=null && !listRimuoviDate.isEmpty()) {
			if(syncErase) {
				synchronized (this.semaphore) { // un altro thread potrebbe già averlo modificato
					cleanErrorDate(listRimuoviDate, now);
				}
			}
			else {
				cleanErrorDate(listRimuoviDate, now);
			}
		}
		
		return newSet;
	}
	private void cleanErrorDate(List<String> listRimuoviDate, Date now) {
		for (String name : listRimuoviDate) {
			if(this.connectorMap_errorDate.containsKey(name)) {
				Date registrationDate = this.connectorMap_errorDate.get(name);
				long registrationDateLong = registrationDate.getTime();
				long registrationDateExpired = registrationDateLong + (this.healthCheck.getPassiveHealthCheck_excludeForSeconds().intValue() * 1000);
				if(registrationDateExpired<now.getTime()) {
					this.connectorMap_errorDate.remove(name);
				}
			}
		}
	}
}
