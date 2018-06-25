/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it). 
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

package org.openspcoop2.web.ctrlstat.plugins;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * IExtendedCoreServlet
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public interface IExtendedCoreServlet {

	public void performCreate(Connection connection, Object originalObject, IExtendedBean extendedBean) throws ExtendedException;
	public void performUpdate(Connection connection, Object originalObject, IExtendedBean extendedBean) throws ExtendedException;
	public void performDelete(Connection connection, Object originalObject, IExtendedBean extendedBean) throws ExtendedException;
	
	public IExtendedBean getExtendedBean(Connection connection, String id) throws ExtendedException;
	public boolean inUse(Connection connection, String id, StringBuffer descriptionInUse) throws ExtendedException;
	
	IExtendedBean readHttpParameters(Object originalObject, TipoOperazione tipoOperazione, IExtendedBean extendedBean, HttpServletRequest request) throws ExtendedException;
	public String getId(HttpServletRequest request) throws ExtendedException; 
}
