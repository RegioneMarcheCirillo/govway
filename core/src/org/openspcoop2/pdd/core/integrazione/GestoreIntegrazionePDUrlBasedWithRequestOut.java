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

package org.openspcoop2.pdd.core.integrazione;

import org.openspcoop2.protocol.sdk.constants.TipoIntegrazione;




/**
 * Classe utilizzata per la ricezione di informazioni di integrazione 
 * dai servizi applicativi verso la porta di dominio.
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class GestoreIntegrazionePDUrlBasedWithRequestOut extends GestoreIntegrazionePDUrlBased{
	
	@Override
	public void setOutRequestHeader(HeaderIntegrazione integrazione,
			OutRequestPDMessage outRequestPDMessage) throws HeaderIntegrazioneException{
	
		try{
			this.utilities.setRequestUrlProperties(integrazione, outRequestPDMessage.getProprietaUrlBased(),
					this.getProtocolFactory().createProtocolManager().buildIntegrationProperties(outRequestPDMessage.getBustaRichiesta(), true, TipoIntegrazione.URL));
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDUrlBased, "+e.getMessage(),e);
		}
		
	}
	
}