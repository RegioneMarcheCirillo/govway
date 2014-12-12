/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2014 Link.it srl (http://link.it). All rights reserved. 
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



package org.openspcoop2.utils.resources;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import org.apache.log4j.Logger;

/**
 * Classe dove sono forniti metodi per effettuare operazioni sull'albero JNDI 
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class GestoreJNDI {

	private java.util.Properties contextProperties;


	/**
	 * Costruttore
	 *
	 * @param contextP Proprieta' del contesto JNDI
	 */
	public GestoreJNDI(java.util.Properties contextP){
		this.contextProperties = contextP;
	}
	/**
	 * Costruttore
	 *
	 */
	public GestoreJNDI(){
		this.contextProperties = new java.util.Properties();
	}



	/**
	 * Ritorna l'oggetto registrato nell'albero JNDI con nome <var>fullPath</var>, se presente.
	 *
	 * @param fullPath Nome dell'oggetto da ricercare nell'albero JNDI
	 */
	public Object lookup(String fullPath) throws Exception {
		// Inizializzo Contesto
		InitialContext ctx = null;
		try{
			if(this.contextProperties.size() > 0){
				ctx = new InitialContext(this.contextProperties);
			}else{
				ctx = new InitialContext();
			}
			// Lookup Object
			Object obj = ctx.lookup(fullPath);
			// Return
			return obj;
		}catch(Exception e){
			throw e;
		}
		finally{
			try{
				if(ctx!=null)
					ctx.close();
			}catch(Exception eClose){}
		}
	}




	/**
	 * Elimina la registrazione di un oggetto con nome JNDI <var>fullPath</var> dall'albero JNDI 
	 *
	 * @param fullPath Nome dell'oggetto da eliminare nell'albero JNDI
	 * @return true se l'eliminazione ha successo, false altrimenti
	 */
	public boolean unbind(String fullPath) {
		return unbind(fullPath, null, null);
	}
	public boolean unbind(String fullPath,Logger log) {
		return unbind(fullPath, log, null);
	}
	public boolean unbind(String fullPath,Logger log,Logger logConsole) {
		Context currentContext = null;
		try{
			currentContext = new InitialContext() ;
			final String name = currentContext.composeName(fullPath,currentContext.getNameInNamespace() ) ;
			if(log!=null)
				log.debug( "Unbind  fullname " + name) ;
			try{
				currentContext.unbind( name ) ;
				//System.out.println( "eraser" ) ;
			}catch( final NameNotFoundException ignored ){
				if(log!=null)
					log.error( "Errore durante l'unbind (Ignored) ["+ignored+"]",ignored);
				if(logConsole!=null)
					logConsole.error( "Errore durante l'unbind (Ignored) ["+ignored+"]",ignored);
				return false;		

			}
			//			L'unbind fatto in questa maniera ricerca il componente sulla "root" dell albero
			//			non sul corretto sottoalbero come dovrebbe
			//			
			//			final String[] components = name.split( "/" ) ;
			//			for( int ix = components.length-1 ; ix >=0 ; ix-- ){
			//				final String nextPath = components[ix] ;
			//				log.debug( "Unbind  component \"" + nextPath + "\" in context " + currentContext ) ;
			//				try{
			//					currentContext.unbind( nextPath ) ;
			//					//System.out.println( "eraser" ) ;
			//				}catch( final NameNotFoundException ignored ){
			//					log.error( "Errore durante l'unbind (Ignored) ["+ignored+"]",ignored);
			//					logConsole.error( "Errore durante l'unbind (Ignored) ["+ignored+"]",ignored);
			//					return false;		
			//					
			//				}
			//			}
			return true;

		}catch(Exception e){
			if(log!=null)
				log.error( "Errore durante l'unbind ["+e+"]",e);
			if(logConsole!=null)
				logConsole.error( "Errore durante l'unbind ["+e+"]",e);
			return false;
		}finally{
			try{
				if(currentContext!=null)
					currentContext.close();
			}catch(Exception eClose){}
		}
	}


	/**
	 * Effettua la registrazione di un oggetto con nome JNDI <var>fullPath</var> nell'albero JNDI 
	 *
	 * @param fullPath Nome dell'oggetto da registrare nell'albero JNDI
	 * @return true se l'eliminazione ha successo, false altrimenti
	 */
	public boolean bind(String fullPath,Object toBind) {
		return bind(fullPath, toBind, null, null);
	}
	public boolean bind(String fullPath,Object toBind,Logger log) {
		return bind(fullPath, toBind, log, null);
	}
	public boolean bind(String fullPath,Object toBind,Logger log,Logger logConsole) {
		Context currentContext = null;
		try{

			//System.out.println("Attempting to bind object " + toBind + " to context path \"" + fullPath + "\"" ) ;

			currentContext = new InitialContext() ;
			final String name = currentContext.composeName(fullPath,currentContext.getNameInNamespace() ) ;
			final String[] components = name.split( "/" ) ;

			// the last item in the array refers to the object itself.
			// we don't want to create a (sub)Context for that; we
			// want to bind the object to it
			final int stop = components.length - 1 ; 		
			for( int ix = 0 ; ix < stop ; ++ix ){
				final String nextPath = components[ix] ;
				//System.out.println( "Looking up subcontext named \"" + nextPath + "\" in context " + currentContext ) ;
				try{
					currentContext = (Context) currentContext.lookup( nextPath ) ;
					//System.out.println( "found" ) ;
				}catch( final NameNotFoundException ignored ){
					//System.out.println( "not found; creating subcontext" ) ;
					currentContext = currentContext.createSubcontext( nextPath ) ;
					//System.out.println( "done" ) ;				
				}	
			}

			// by this point, we've built up the entire context path leading up
			// to the desired bind point... so we can bind the object itself
			currentContext.bind( components[stop] , toBind ) ;
			if(log!=null)
				log.info("binding ["+fullPath+"] to " + currentContext ) ;
			if(logConsole!=null)
				logConsole.info("binding ["+fullPath+"] to " + currentContext ) ;

			return true;

		}catch(Exception e){
			if(log!=null)
				log.error( "Errore durante il bind ["+e+"]",e);
			if(logConsole!=null)
				logConsole.error( "Errore durante il bind ["+e+"]",e);
			return false;
		}	finally{
			try{
				if(currentContext!=null)
					currentContext.close();
			}catch(Exception eClose){}
		}
	} 

}





