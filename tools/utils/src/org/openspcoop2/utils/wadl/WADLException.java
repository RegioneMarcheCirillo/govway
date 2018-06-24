/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it). 
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



package org.openspcoop2.utils.wadl;

import org.openspcoop2.utils.rest.ProcessingException;

/**
 * Eccezione che si puo' verificare durante la creazione dei wsdl 
 * 
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 *
 */

public class WADLException extends ProcessingException {

	private static final long serialVersionUID = 1L;

	public WADLException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public WADLException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WADLException() {
		super();
	}
	public WADLException(String msg) {
		super(msg);
	}
}

