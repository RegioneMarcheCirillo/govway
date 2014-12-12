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
package org.openspcoop2.generic_project.dao.xml;

import org.apache.log4j.Logger;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UpdateField;
import org.openspcoop2.generic_project.beans.UpdateModel;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;

/**
 * IXMLServiceCRUDWithoutId
 * 
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public interface IXMLServiceCRUDWithoutId<XML,T,SM>  extends IXMLServiceManager<SM> {

	public void create(Logger log,XML xmlRoot,T obj) throws NotImplementedException,ServiceException,Exception;
	
	public void update(Logger log,XML xmlRoot,T obj) throws NotFoundException,NotImplementedException,ServiceException,Exception;
	
	public void updateFields(Logger log,XML xmlRoot,T obj, UpdateField ... updateFields) throws ServiceException,NotFoundException,NotImplementedException, Exception;
	
	public void updateFields(Logger log,XML xmlRoot,T obj, IExpression condition, UpdateField ... updateFields) throws ServiceException,NotFoundException,NotImplementedException,Exception;
	
	public void updateFields(Logger log,XML xmlRoot,T obj, UpdateModel ... updateModels) throws ServiceException,NotFoundException,NotImplementedException,Exception;
	
	public void updateOrCreate(Logger log,XML xmlRoot,T obj) throws NotImplementedException,ServiceException,Exception;
	
	public void delete(Logger log,XML xmlRoot,T obj) throws NotImplementedException,ServiceException,Exception;
	
	public NonNegativeNumber deleteAll(Logger log,XML xmlRoot) throws NotImplementedException,ServiceException,Exception;
	
	public NonNegativeNumber deleteAll(Logger log,XML xmlRoot,XMLExpression expression) throws NotImplementedException,ServiceException,Exception;
	
}
