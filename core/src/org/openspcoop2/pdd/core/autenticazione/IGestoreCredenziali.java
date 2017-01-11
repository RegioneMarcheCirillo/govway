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

package org.openspcoop2.pdd.core.autenticazione;

import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.pdd.core.ICore;
import org.openspcoop2.pdd.core.connettori.InfoConnettoreIngresso;

/**
 * Interfaccia che definisce il gestore delle credenziali
 *
 * @author Andrea Poli <apoli@link.it>
 * @author $Author$
 * @version $Rev$, $Date$
 */

public interface IGestoreCredenziali extends ICore {

	public Credenziali elaborazioneCredenziali(InfoConnettoreIngresso infoConnettoreIngresso, 
			OpenSPCoop2Message messaggio) throws GestoreCredenzialiException,GestoreCredenzialiConfigurationException;
	
	public String getIdentitaGestoreCredenziali();
	
}
