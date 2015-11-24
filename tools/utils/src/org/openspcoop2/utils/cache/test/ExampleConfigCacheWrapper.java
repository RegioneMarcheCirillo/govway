/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2015 Link.it srl (http://link.it). 
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

package org.openspcoop2.utils.cache.test;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.cache.AbstractCacheWrapper;

/**
 * Cache
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ExampleConfigCacheWrapper extends AbstractCacheWrapper {

	public ExampleConfigCacheWrapper(boolean initializeCache, Logger log) throws UtilsException {
		super("config", initializeCache, log);
	}

	@Override
	public Object getDriver(Object param) throws UtilsException {
		return new ExampleConfigDummyDriver((Connection)param);
	}

	@Override
	public boolean isCachableException(Throwable e) {
		return e instanceof ExampleExceptionNotFound;
	}

}
