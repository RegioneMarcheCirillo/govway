/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it).
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
package org.openspcoop2.web.monitor.core.dao;

import org.openspcoop2.utils.cache.AbstractCacheJmx;
import org.openspcoop2.utils.cache.AbstractCacheWrapper;
import org.openspcoop2.web.monitor.core.listener.AbstractConsoleStartupListener;


/***
 * 
 * Funzionalita' di supporto per la gestione delle maschere di ricerca.
 * 
 * @author Pintori Giuliano (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 *
 */
public class DynamicUtilsServiceCacheJmxRicercheConfigurazione extends AbstractCacheJmx {

	@Override
	public AbstractCacheWrapper getCacheWrapper() {
		return AbstractConsoleStartupListener.dynamicUtilsServiceCache_ricercheConfigurazione;
	}

	@Override
	public String getJmxDescription() {
		return "Risultati delle ricerche effettuate negli elementi presenti nelle maschere di ricerca";
	}

	
}