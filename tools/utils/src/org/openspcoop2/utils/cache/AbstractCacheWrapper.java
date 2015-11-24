/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2015 Link.it srl (http://link.it). 
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

package org.openspcoop2.utils.cache;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openspcoop2.utils.UtilsException;

/**
 * AbstractCacheManager
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public abstract class AbstractCacheWrapper {

	private Cache cache = null;
	private Logger log = null;
	private String cacheName = null;
	
	public AbstractCacheWrapper(String cacheName, Logger log) throws UtilsException{
		
		this(cacheName, true, log, null, null, null, null);
		
	}
	
	public AbstractCacheWrapper(String cacheName, boolean initializeCache, Logger log) throws UtilsException{
		
		this(cacheName, initializeCache, log, null, null, null, null);
		
	}
	
	public AbstractCacheWrapper(String cacheName, Logger log,
			Integer cacheSize,CacheAlgorithm cacheAlgorithm,
			Integer itemIdleTimeSeconds, Integer itemLifeTimeSeconds) throws UtilsException{
		
		this(cacheName, true, log, cacheSize, cacheAlgorithm, itemIdleTimeSeconds, itemLifeTimeSeconds);
		
	}
	
	private AbstractCacheWrapper(String cacheName, boolean initializeCache, Logger log,
			Integer cacheSize,CacheAlgorithm cacheAlgorithm,
			Integer itemIdleTimeSeconds, Integer itemLifeTimeSeconds) throws UtilsException{
		
		if(cacheName==null){
			throw new UtilsException("Cache name undefined");
		}
		this.cacheName = cacheName;
		
		if(log==null){
			throw new UtilsException("Logger undefined");
		}
		this.log = log;
		
		if(initializeCache){
			this.initCacheConfigurazione(cacheSize, cacheAlgorithm, itemIdleTimeSeconds, itemLifeTimeSeconds);
		}
	}
	
	public String getCacheName() {
		return this.cacheName;
	}
		
	private void initCacheConfigurazione(Integer cacheSize,CacheAlgorithm cacheAlgorithm,
			Integer itemIdleTimeSeconds, Integer itemLifeTimeSeconds) throws UtilsException{
		
		this.cache = new Cache(this.cacheName);
		
		String msg = null;
		if( (cacheSize!=null) ||
				(cacheAlgorithm != null) ){
	     
			if( cacheSize!=null ){
				int dimensione = -1;
				try{
					dimensione = cacheSize.intValue();
					msg = "Cache size ("+this.cacheName+"): "+dimensione;
					this.log.info(msg);
					this.cache.setCacheSize(dimensione);
				}catch(Exception error){
					throw new UtilsException("Cache size parameter wrong ("+this.cacheName+"): "+error.getMessage());
				}
			}
			if( cacheAlgorithm != null ){
				msg = "Cache algorithm ("+this.cacheName+"): "+cacheAlgorithm.name();
				this.log.info(msg);
				this.cache.setCacheAlgoritm(cacheAlgorithm);
			}
			
		}
		
		if( (itemIdleTimeSeconds != null) ||
				(itemLifeTimeSeconds != null) ){

			if( itemIdleTimeSeconds != null  ){
				int itemIdleTime = -1;
				try{
					itemIdleTime = itemIdleTimeSeconds.intValue();
					msg = "Cache 'IdleTime' attribute ("+this.cacheName+"): "+itemIdleTimeSeconds;
					this.log.info(msg);
					this.cache.setItemIdleTime(itemIdleTime);
				}catch(Exception error){
					throw new UtilsException("Cache 'IdleTime' attribute wrong ("+this.cacheName+"): "+error.getMessage());
				}
			}
			if( itemLifeTimeSeconds != null  ){
				int itemLifeSecond = -1;
				try{
					itemLifeSecond = itemLifeTimeSeconds.intValue();
					msg = "Cache 'LifeTime' attribute ("+this.cacheName+"): "+itemLifeSecond;
					this.log.info(msg);
					this.cache.setItemLifeTime(itemLifeSecond);
				}catch(Exception error){
					throw new UtilsException("Cache 'LifeTime' attribute wrong ("+this.cacheName+"): "+error.getMessage());
				}
			}
			
		}
	}
	
	
	
	/* --------------- Cache --------------------*/
	
	public Logger getLog() {
		return this.log;
	}
	
	public boolean isCacheEnabled(){
		return this.cache!=null;
	}
	
	public void resetCache() throws UtilsException{
		if(this.cache!=null){
			try{
				this.cache.clear();
			}catch(Exception e){
				throw new UtilsException(e.getMessage(),e);
			}
		}
	}
	public String printStatsCache(String separator) throws UtilsException{
		if(this.cache!=null){
			try{
				return this.cache.printStats(separator);
			}catch(Exception e){
				throw new UtilsException(e.getMessage(),e);
			}
		}else{
			throw new UtilsException("Cache disabled");
		}
	}
	public void enableCache() throws UtilsException{
		if(this.cache!=null)
			throw new UtilsException("Cache already enabled");
		else{
			try{
				this.cache = new Cache(this.cacheName);
			}catch(Exception e){
				throw new UtilsException(e.getMessage(),e);
			}
		}
	}
	public void enableCache(Integer cacheSize,Boolean cacheAlgorithmLRU,Integer itemIdleTimeSeconds,Integer itemLifeTimeSeconds) throws UtilsException{
		
		CacheAlgorithm cacheAlgorithm = null;
		if(cacheAlgorithmLRU!=null){
			if(cacheAlgorithmLRU){
				cacheAlgorithm = CacheAlgorithm.LRU;
			}
			else{
				cacheAlgorithm = CacheAlgorithm.MRU;
			}
		}
		
		this.enableCache(cacheSize, cacheAlgorithm, itemIdleTimeSeconds, itemLifeTimeSeconds);
		
	}
	public void enableCache(Integer cacheSize,CacheAlgorithm cacheAlgorithm,Integer itemIdleTimeSeconds,Integer itemLifeTimeSeconds) throws UtilsException{
		if(this.cache!=null)
			throw new UtilsException("Cache already enabled");
		else{
			try{
				initCacheConfigurazione(cacheSize, cacheAlgorithm, itemIdleTimeSeconds, itemLifeTimeSeconds);
			}catch(Exception e){
				throw new UtilsException(e.getMessage(),e);
			}
		}
	}
	public void disableCache() throws UtilsException{
		if(this.cache==null)
			throw new UtilsException("Cache already disabled");
		else{
			try{
				this.cache.clear();
				this.cache = null;
			}catch(Exception e){
				throw new UtilsException(e.getMessage(),e);
			}
		}
	}
	public String listKeysCache(String separator) throws UtilsException{
		if(this.cache!=null){
			try{
				return this.cache.printKeys(separator);
			}catch(Exception e){
				throw new UtilsException(e.getMessage(),e);
			}
		}else{
			throw new UtilsException("Cache disabled");
		}
	}
	public String getObjectCache(String key) throws UtilsException{
		if(this.cache!=null){
			try{
				Object o = this.cache.get(key);
				if(o!=null){
					return o.toString();
				}else{
					return "Object with key ["+key+"] not exists";
				}
			}catch(Exception e){
				throw new UtilsException(e.getMessage(),e);
			}
		}else{
			throw new UtilsException("Cache disabled");
		}
	}
	
	
	
	
	
	/* --------------- CacheWrapper --------------------*/
	
	public abstract Object getDriver(Object param) throws UtilsException;
	public abstract boolean isCachableException(Throwable e);
	
	public synchronized Object getObjectCache(Object driverParam,boolean debug,String keyCacheParam,String methodName,Object... arguments) throws Throwable{
				
		
		Throwable cachableException = null;
		Object obj = null;
		boolean throwException = false;
		
		if(keyCacheParam == null)
			throw new UtilsException("["+methodName+"]: KeyCache undefined");	
		if(methodName == null)
			throw new UtilsException("["+keyCacheParam+"]: MethodName undefined");	
		String keyCache = methodName + "_" + keyCacheParam;
		
		try{
			
			if(debug){
				if(this.cache!=null){
					this.log.info("@"+keyCache+"@ INFO CACHE: "+this.cache.toString());
					this.log.info("@"+keyCache+"@ KEYS: \n\t"+this.cache.printKeys("\n\t"));
				}
				else{
					this.log.info("@"+keyCache+"@ CACHE DISABLED");	
				}
			}

			// se e' attiva una cache provo ad utilizzarla
			if(this.cache!=null){
				org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(keyCache);
				if(response != null){
					if(response.getObject()!=null){
						if(debug){
							this.log.info("@"+keyCache+"@ Object (type:"+response.getObject().getClass().getName()+") (method:"+methodName+") found in cache.");
						}
						return response.getObject();
					}else if(response.getException()!=null){
						this.log.info("@"+keyCache+"@ Exception (type:"+response.getException().getClass().getName()+") (method:"+methodName+") found in cache.");
						throwException = true;
						throw (Throwable) response.getException();
					}else{
						this.log.error("@"+keyCache+"@ Found entry in cache with key ["+keyCache+"] (method:"+methodName+") without object and exception???");
					}
				}
			}

			// Effettuo le query
			if(debug){
				this.log.info("@"+keyCache+"@ search object (method:"+methodName+") with driver...");
			}
			try{
				obj = getObject(driverParam, debug, methodName, arguments);
			}catch(Throwable e){
				if(this.isCachableException(e)){
					cachableException = e;
				}
				else{
					throw e;
				}
			}
			if(cachableException!=null){
				if(debug){
					this.log.info("@"+keyCache+"@ Driver throw CachableException: "+cachableException.getClass().getName());
				}
			}else if(obj!=null){
				if(debug){
					this.log.info("@"+keyCache+"@ Driver return Object: "+obj.getClass().getName());
				}
			}else{
				throw new Exception("Method ("+methodName+") return null value");
			}
			
					
			// Aggiungo la risposta in cache (se esiste una cache)	
			// Se ho una eccezione aggiungo in cache solo una not found
			if( this.cache!=null ){ 	
				if(cachableException!=null){
					if(debug){
						this.log.info("@"+keyCache+"@ Add Exception in cache");
					}
				}else if(obj!=null){
					if(debug){
						this.log.info("@"+keyCache+"@ Add Object in cache");
					}
				}else{
					throw new Exception("Method ("+methodName+") return null value");
				}
				try{	
					org.openspcoop2.utils.cache.CacheResponse responseCache = new org.openspcoop2.utils.cache.CacheResponse();
					if(cachableException!=null){
						responseCache.setException(cachableException);
					}else{
						responseCache.setObject((java.io.Serializable)obj);
					}
					this.cache.put(keyCache,responseCache);
				}catch(UtilsException e){
					this.log.error("@"+keyCache+"@ error occurs during insert in cache: "+e.getMessage(),e);
				}
			}

		}catch(Throwable e){
			if(throwException == false){
				this.log.error("@"+keyCache+"@ Error occurs: "+e.getMessage(),e);
				throw new UtilsException(e.getMessage(),e);
			}
		}
		
		if(cachableException!=null){
			throw cachableException;
		}else
			return obj;
	}
	
	private Object getObject(Object driverParam,boolean debug,String methodName,Object... arguments) throws Throwable{

		
		// Effettuo le query nella mia gerarchia di registri.
		Object obj = null;
		try{
			Object driver = this.getDriver(driverParam);
			if(driver==null){
				throw new UtilsException("Driver undefined");
			}
			if(arguments.length==0){
				Method method =  driver.getClass().getMethod(methodName);
				obj = method.invoke(driver);
			}else if(arguments.length==1){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass());
				obj = method.invoke(driver,arguments[0]);
			}else if(arguments.length==2){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1]);
			}else if(arguments.length==3){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass(),arguments[2].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1],arguments[2]);
			}else if(arguments.length==4){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass(),arguments[2].getClass(),
						arguments[3].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1],arguments[2],
						arguments[3]);
			}else if(arguments.length==5){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass(),arguments[2].getClass(),
						arguments[3].getClass(),arguments[4].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1],arguments[2],
						arguments[3],arguments[4]);
			}else if(arguments.length==6){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass(),arguments[2].getClass(),
						arguments[3].getClass(),arguments[4].getClass(),arguments[5].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1],arguments[2],
						arguments[3],arguments[4],arguments[5]);
			}else if(arguments.length==7){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass(),arguments[2].getClass(),
						arguments[3].getClass(),arguments[4].getClass(),arguments[5].getClass(),
						arguments[6].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1],arguments[2],
						arguments[3],arguments[4],arguments[5],
						arguments[6]);
			}else if(arguments.length==8){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass(),arguments[2].getClass(),
						arguments[3].getClass(),arguments[4].getClass(),arguments[5].getClass(),
						arguments[6].getClass(),arguments[7].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1],arguments[2],
						arguments[3],arguments[4],arguments[5],
						arguments[6],arguments[7]);
			}else if(arguments.length==9){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass(),arguments[2].getClass(),
						arguments[3].getClass(),arguments[4].getClass(),arguments[5].getClass(),
						arguments[6].getClass(),arguments[7].getClass(),arguments[8].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1],arguments[2],
						arguments[3],arguments[4],arguments[5],
						arguments[6],arguments[7],arguments[8]);
			}else if(arguments.length==10){
				Method method =  driver.getClass().getMethod(methodName,arguments[0].getClass(),arguments[1].getClass(),arguments[2].getClass(),
						arguments[3].getClass(),arguments[4].getClass(),arguments[5].getClass(),
						arguments[6].getClass(),arguments[7].getClass(),arguments[8].getClass(),
						arguments[9].getClass());
				obj = method.invoke(driver,arguments[0],arguments[1],arguments[2],
						arguments[3],arguments[4],arguments[5],
						arguments[6],arguments[7],arguments[8],
						arguments[9]);
			}else
				throw new Exception("More than 10 arguments unsupported");
		}catch(java.lang.reflect.InvocationTargetException e){
			if(e.getTargetException()!=null){
				throw e.getTargetException();
			}else{
				throw e;
			}
		}
		catch(Exception e){
			throw e;
		}
		
		return obj;
		
	}
}
