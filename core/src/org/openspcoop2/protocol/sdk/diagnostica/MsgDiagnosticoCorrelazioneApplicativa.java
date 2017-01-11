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



package org.openspcoop2.protocol.sdk.diagnostica;

import java.io.Serializable;


/**
 * MsgDiagnosticoCorrelazioneApplicativa
 *
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class MsgDiagnosticoCorrelazioneApplicativa implements Serializable{

	private static final long serialVersionUID = 7616051015989365464L;

	private boolean delegata;
    private String idBusta;
    private String correlazione;
    private String protocollo;
	
    public MsgDiagnosticoCorrelazioneApplicativa() {
	}

	public boolean isDelegata() {
		return this.delegata;
	}

	public void setDelegata(boolean delegata) {
		this.delegata = delegata;
	}

	public String getIdBusta() {
		return this.idBusta;
	}

	public void setIdBusta(String idBusta) {
		this.idBusta = idBusta;
	}

	public String getProtocollo() {
		return this.protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getCorrelazione() {
		return this.correlazione;
	}

	public void setCorrelazione(String correlazione) {
		this.correlazione = correlazione;
	}
	
	
}


