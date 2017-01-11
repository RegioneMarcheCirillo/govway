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

package org.openspcoop2.testsuite.db;

import java.sql.Connection;

import org.openspcoop2.core.constants.CostantiDB;

/**
 * Verifica le tracce di richiesta
 * 
 * @author Andi Rexha (rexha@openspcoop.org)
 * @author Carlo Ciurli (ciurli@openspcoop.org)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class VerificatoreTracciaRichiesta extends AbstractVerificatoreTraccia {

	
	public VerificatoreTracciaRichiesta(Connection con,String protocollo){
		super(con, protocollo);
	}
	
	
	
	
	@Override
	protected String getTipoTraccia() {
		return "TracciaRichiesta";
	}

	@Override
	protected String getColumnId() {
		return CostantiDB.TRACCE_COLUMN_ID_MESSAGGIO;
	}


	
	
}
