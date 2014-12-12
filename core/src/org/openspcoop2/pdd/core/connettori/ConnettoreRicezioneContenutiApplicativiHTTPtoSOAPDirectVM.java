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




package org.openspcoop2.pdd.core.connettori;

import org.openspcoop2.pdd.services.RicezioneContenutiApplicativiHTTPtoSOAP;
import org.openspcoop2.pdd.services.connector.ConnectorException;
import org.openspcoop2.pdd.services.connector.DirectVMConnectorInMessage;
import org.openspcoop2.pdd.services.connector.DirectVMConnectorOutMessage;
import org.openspcoop2.pdd.services.connector.RicezioneContenutiApplicativiHTTPtoSOAPConnector;
import org.openspcoop2.protocol.engine.URLProtocolContext;

/**
 * Classe utilizzata per effettuare consegne di messaggi Soap, attraverso
 * l'invocazione di un server http. 
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class ConnettoreRicezioneContenutiApplicativiHTTPtoSOAPDirectVM extends AbstractConnettoreDirectVM {

	public final static String TIPO = "vmPDtoSOAP";
	
	private String pd;
	
	@Override
	public String getIdModulo(){
		return RicezioneContenutiApplicativiHTTPtoSOAPConnector.ID_MODULO+"_VM";
	}
	@Override
	public String getFunction(){
		return URLProtocolContext.PDtoSOAP_FUNCTION;
	}
	@Override
	public void process(DirectVMConnectorInMessage inMessage,DirectVMConnectorOutMessage outMessage) throws ConnectorException{
		RicezioneContenutiApplicativiHTTPtoSOAP soapConnector = new RicezioneContenutiApplicativiHTTPtoSOAP();
		soapConnector.process(inMessage, outMessage);
	}
	
	@Override
	public boolean validate(ConnettoreMsg request) {
		
		if(request.getConnectorProperties().get("pd")==null){
			this.errore = "Proprieta' 'pd' non fornita e richiesta da questo tipo di connettore ["+request.getTipoConnettore()+"]";
			return false;
		}
		else{
			this.pd = request.getConnectorProperties().get("pd").trim();
		}
		
		return true;
	}
	@Override
	public String getFunctionParameters() {
		return super.normalizeFunctionParamters(this.pd);
	}
}





