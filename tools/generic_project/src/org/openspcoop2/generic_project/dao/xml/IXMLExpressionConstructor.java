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
package org.openspcoop2.generic_project.dao.xml;

import org.slf4j.Logger;
import org.openspcoop2.generic_project.dao.xml.XMLExpression;
import org.openspcoop2.generic_project.dao.xml.XMLPaginatedExpression;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;

/**
 * IXMLExpressionConstructor
 * 
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public interface IXMLExpressionConstructor<SM>  extends IXMLServiceManager<SM> {

	public XMLExpression newExpression(Logger log) throws NotImplementedException,ServiceException;
	
	public XMLPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException,ServiceException;

	
	public XMLExpression toExpression(XMLPaginatedExpression expression, Logger log) throws NotImplementedException,ServiceException;
	
	public XMLPaginatedExpression toPaginatedExpression(XMLExpression expression, Logger log) throws NotImplementedException,ServiceException;

}
