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

package org.openspcoop2.protocol.sdk.state;

/**
 * Oggetto che rappresenta lo stato di una busta
 *
 * @author Poli Andrea (apoli@link.it)
 * @author Fabio Tronci (tronci@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class StateException extends Exception {

	 /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public StateException() {
	}
	public StateException(String msg) {
		super(msg);
	}
	
	 public StateException(String message, Throwable cause)
		{
			super(message, cause);
			// TODO Auto-generated constructor stub
		}
		public StateException(Throwable cause)
		{
			super(cause);
			// TODO Auto-generated constructor stub
		}
	
}
