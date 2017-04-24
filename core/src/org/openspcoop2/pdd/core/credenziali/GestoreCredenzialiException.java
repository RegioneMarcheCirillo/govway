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



package org.openspcoop2.pdd.core.credenziali;

/**	
 * Contiene la definizione di una eccezione lanciata dalle classi del package org.openspcoop.pdd.core.handlers
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 12564 $, $Date: 2017-01-11 14:31:31 +0100 (Wed, 11 Jan 2017) $
 */


public class GestoreCredenzialiException extends Exception {

	private String identitaHandler = null;
	public String getIdentitaHandler() {
		return this.identitaHandler;
	}
	// Impostato dal gestore degli handler
	protected void setIdentitaHandler(String identitaHandler) {
		this.identitaHandler = identitaHandler;
	}
	
	 public GestoreCredenzialiException(String message, Throwable cause)
		{
			super(message, cause);
			// TODO Auto-generated constructor stub
		}
		public GestoreCredenzialiException(Throwable cause)
		{
			super(cause);
			// TODO Auto-generated constructor stub
		}
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		public GestoreCredenzialiException() {
			super();
	    }
		public GestoreCredenzialiException(String msg) {
	        super(msg);
	    }

}