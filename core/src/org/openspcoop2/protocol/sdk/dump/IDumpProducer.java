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


package org.openspcoop2.protocol.sdk.dump;

import org.openspcoop2.core.config.OpenspcoopAppender;
import org.openspcoop2.protocol.sdk.tracciamento.TracciamentoException;

/**
 * Contiene la definizione una interfaccia per la registrazione personalizzata dei dump applicativi
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public interface IDumpProducer {

	/**
	 * Inizializza l'engine di un appender per la registrazione
	 * di un dump applicativo emesso da una porta di dominio.
	 * 
	 * @param appenderProperties Proprieta' dell'appender
	 * @throws TracciamentoException
	 */
	public void initializeAppender(OpenspcoopAppender appenderProperties) throws TracciamentoException;

		
	/**
	 * Dump di un messaggio
	 * 
	 * @param messaggio
	 * @throws TracciamentoException
	 */
	public void dump(Messaggio messaggio) throws TracciamentoException;
	
	
	
}
