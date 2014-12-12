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


package org.openspcoop2.utils.id;

import java.util.UUID;

import org.openspcoop2.utils.id.IUniqueIdentifier;
import org.openspcoop2.utils.id.IUniqueIdentifierGenerator;
import org.openspcoop2.utils.id.UniqueIdentifierException;

/**
 * Implementazione tramite java.util.UUID
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class UniversallyUniqueIdentifierGenerator implements IUniqueIdentifierGenerator {

	@Override
	public IUniqueIdentifier newID() throws UniqueIdentifierException {
		UniversallyUniqueIdentifier uuidOpenSPCoop = new UniversallyUniqueIdentifier();
		uuidOpenSPCoop.setUuid(UUID.randomUUID());
		return uuidOpenSPCoop;
	}

	@Override
	public IUniqueIdentifier convertFromString(String value)
			throws UniqueIdentifierException {
		UniversallyUniqueIdentifier uuidOpenSPCoop = new UniversallyUniqueIdentifier();
		uuidOpenSPCoop.setUuid(UUID.fromString(value));
		return uuidOpenSPCoop;
	}

	@Override
	public void init(Object... o) throws UniqueIdentifierException {
		
	}

}
