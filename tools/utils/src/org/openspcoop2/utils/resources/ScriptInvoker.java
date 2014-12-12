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

import org.openspcoop2.utils.UtilsException;

/**
* ScriptInvoker
*
* @author Andrea Poli <apoli@link.it>
* @author $Author$
* @version $Rev$, $Date$
*/
public class ScriptInvoker {

	private String script = null;
	private int exitValue = -1;
	private String outputStream = null;
	private String errorStream = null;

	public ScriptInvoker(String script){
		this.script = script;
	}
	public ScriptInvoker(){
	}
	
	public void run() throws UtilsException{
		this.run("");
	}
	public void run(String ... parameters) throws UtilsException{
		
		try{
		
			if(this.script==null){
				throw new UtilsException("Script non fornito");
			}
			
			java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
	
			// Invoco lo script
			StringBuffer scriptTmp = new StringBuffer(this.script);
			if(parameters!=null){
				for (int i = 0; i < parameters.length; i++) {
					scriptTmp.append(" ");
					scriptTmp.append(parameters[i]);
				}
			}
			
			java.lang.Process processStatus = runtime.exec(scriptTmp.toString());
			java.io.BufferedInputStream berror = new java.io.BufferedInputStream(processStatus.getErrorStream());
			java.io.BufferedInputStream bin = new java.io.BufferedInputStream(processStatus.getInputStream());

			boolean terminated = false;
			while(terminated == false){
				try{
					try{
						Thread.sleep(500);
					}catch(Exception e){}
					this.exitValue = processStatus.exitValue();
					terminated = true;
				}catch(java.lang.IllegalThreadStateException exit){}
			}

			StringBuffer stampa = new StringBuffer();
			int read = 0;
			while((read = bin.read())!=-1){
				stampa.append((char)read);
			}
			if(stampa.length()>0){
				this.outputStream = stampa.toString();
			}
				
			StringBuffer stampaError = new StringBuffer();
			read = 0;
			while((read = berror.read())!=-1){
				stampaError.append((char)read);
			}
			if(stampaError.length()>0)
				this.errorStream = stampaError.toString();
						
		}catch(Exception e){
			throw new UtilsException(e.getMessage(), e);
		}
	}

	public int getExitValue() {
		return this.exitValue;
	}

	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}

	public String getOutputStream() {
		return this.outputStream;
	}

	public void setOutputStream(String outputStream) {
		this.outputStream = outputStream;
	}

	public String getErrorStream() {
		return this.errorStream;
	}

	public void setErrorStream(String errorStream) {
		this.errorStream = errorStream;
	}

	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
}
