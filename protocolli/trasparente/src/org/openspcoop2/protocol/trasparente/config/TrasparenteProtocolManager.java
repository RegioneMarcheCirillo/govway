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


package org.openspcoop2.protocol.trasparente.config;

import org.slf4j.Logger;
import org.openspcoop2.protocol.basic.config.BasicManager;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.sdk.ProtocolException;

/**
 * Classe che implementa, in base al protocollo Trasparente, l'interfaccia {@link org.openspcoop2.protocol.sdk.config.IProtocolManager} 
 *
 * @author Poli Andrea (apoli@link.it)
 * @author Nardi Lorenzo (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TrasparenteProtocolManager extends BasicManager {
	
	protected TrasparenteProperties trasparenteProperties = null;
	protected Logger logger = null;
	public TrasparenteProtocolManager(IProtocolFactory<?> protocolFactory) throws ProtocolException{
		super(protocolFactory);
		this.logger = this.getProtocolFactory().getLogger();
		this.trasparenteProperties = TrasparenteProperties.getInstance(this.logger);
	}
	
	
	@Override
	public Boolean isAggiungiDetailErroreApplicativo_FaultApplicativo() {
		return this.trasparenteProperties.isAggiungiDetailErroreApplicativo_SoapFaultApplicativo();
	}

	@Override
	public Boolean isAggiungiDetailErroreApplicativo_FaultPdD() {
		return this.trasparenteProperties.isAggiungiDetailErroreApplicativo_SoapFaultPdD();
	}
	
}
