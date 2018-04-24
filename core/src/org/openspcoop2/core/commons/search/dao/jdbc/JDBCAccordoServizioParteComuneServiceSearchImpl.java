/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
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
package org.openspcoop2.core.commons.search.dao.jdbc;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.sql.Connection;

import org.slf4j.Logger;

import org.openspcoop2.utils.sql.ISQLQueryObject;

import org.openspcoop2.generic_project.expression.impl.sql.ISQLFieldConverter;
import org.openspcoop2.generic_project.dao.jdbc.utils.IJDBCFetch;
import org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject;
import org.openspcoop2.generic_project.dao.jdbc.IJDBCServiceSearchWithId;
import org.openspcoop2.core.commons.search.IdAccordoServizioParteComune;
import org.openspcoop2.generic_project.utils.UtilsTemplate;
import org.openspcoop2.generic_project.beans.CustomField;
import org.openspcoop2.generic_project.beans.InUse;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.beans.UnionExpression;
import org.openspcoop2.generic_project.beans.Union;
import org.openspcoop2.generic_project.beans.FunctionField;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCExpression;
import org.openspcoop2.generic_project.dao.jdbc.JDBCPaginatedExpression;

import org.openspcoop2.generic_project.dao.jdbc.JDBCServiceManagerProperties;
import org.openspcoop2.core.commons.search.dao.jdbc.converter.AccordoServizioParteComuneFieldConverter;
import org.openspcoop2.core.commons.search.dao.jdbc.fetch.AccordoServizioParteComuneFetch;
import org.openspcoop2.core.commons.search.dao.jdbc.JDBCServiceManager;

import org.openspcoop2.core.commons.search.AccordoServizioParteComune;
import org.openspcoop2.core.commons.search.AccordoServizioParteComuneAzione;
import org.openspcoop2.core.commons.search.PortType;
import org.openspcoop2.core.commons.search.Operation;

/**     
 * JDBCAccordoServizioParteComuneServiceSearchImpl
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JDBCAccordoServizioParteComuneServiceSearchImpl implements IJDBCServiceSearchWithId<AccordoServizioParteComune, IdAccordoServizioParteComune, JDBCServiceManager> {

	private AccordoServizioParteComuneFieldConverter _accordoServizioParteComuneFieldConverter = null;
	public AccordoServizioParteComuneFieldConverter getAccordoServizioParteComuneFieldConverter() {
		if(this._accordoServizioParteComuneFieldConverter==null){
			this._accordoServizioParteComuneFieldConverter = new AccordoServizioParteComuneFieldConverter(this.jdbcServiceManager.getJdbcProperties().getDatabaseType());
		}		
		return this._accordoServizioParteComuneFieldConverter;
	}
	@Override
	public ISQLFieldConverter getFieldConverter() {
		return this.getAccordoServizioParteComuneFieldConverter();
	}
	
	private AccordoServizioParteComuneFetch accordoServizioParteComuneFetch = new AccordoServizioParteComuneFetch();
	public AccordoServizioParteComuneFetch getAccordoServizioParteComuneFetch() {
		return this.accordoServizioParteComuneFetch;
	}
	@Override
	public IJDBCFetch getFetch() {
		return getAccordoServizioParteComuneFetch();
	}
	
	
	private JDBCServiceManager jdbcServiceManager = null;

	@Override
	public void setServiceManager(JDBCServiceManager serviceManager) throws ServiceException{
		this.jdbcServiceManager = serviceManager;
	}
	
	@Override
	public JDBCServiceManager getServiceManager() throws ServiceException{
		return this.jdbcServiceManager;
	}
	

	@Override
	public IdAccordoServizioParteComune convertToId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AccordoServizioParteComune accordoServizioParteComune) throws NotImplementedException, ServiceException, Exception{
	
		IdAccordoServizioParteComune idAccordoServizioParteComune = new IdAccordoServizioParteComune();
		// idAccordoServizioParteComune.setXXX(accordoServizioParteComune.getYYY());
		// ...
		// idAccordoServizioParteComune.setXXX(accordoServizioParteComune.getYYY());
		// TODO: popola IdAccordoServizioParteComune
	
		/* 
	     * TODO: implement code that returns the object id
	    */
	
	    // Delete this line when you have implemented the method
	    int throwNotImplemented = 1;
	    if(throwNotImplemented==1){
	            throw new NotImplementedException("NotImplemented");
	    }
	    // Delete this line when you have implemented the method 
	
		return idAccordoServizioParteComune;
	}
	
	@Override
	public AccordoServizioParteComune get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdAccordoServizioParteComune id, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {
		Long id_accordoServizioParteComune = ( (id!=null && id.getId()!=null && id.getId()>0) ? id.getId() : this.findIdAccordoServizioParteComune(jdbcProperties, log, connection, sqlQueryObject, id, true));
		return this._get(jdbcProperties, log, connection, sqlQueryObject, id_accordoServizioParteComune,idMappingResolutionBehaviour);
		
		
	}
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdAccordoServizioParteComune id) throws MultipleResultException, NotImplementedException, ServiceException,Exception {

		Long id_accordoServizioParteComune = this.findIdAccordoServizioParteComune(jdbcProperties, log, connection, sqlQueryObject, id, false);
		return id_accordoServizioParteComune != null && id_accordoServizioParteComune > 0;
		
	}
	
	@Override
	public List<IdAccordoServizioParteComune> findAllIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

		List<IdAccordoServizioParteComune> list = new ArrayList<IdAccordoServizioParteComune>();

		// TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari a create l'ID logico
		// 2. Usare metodo getAccordoServizioParteComuneFetch() sul risultato della select per ottenere un oggetto AccordoServizioParteComune
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 
		// 3. Usare metodo convertToId per ottenere l'id

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	AccordoServizioParteComune accordoServizioParteComune = this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
			IdAccordoServizioParteComune idAccordoServizioParteComune = this.convertToId(jdbcProperties,log,connection,sqlQueryObject,accordoServizioParteComune);
        	list.add(idAccordoServizioParteComune);
        }

        return list;
		
	}
	
	@Override
	public List<AccordoServizioParteComune> findAll(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotImplementedException, ServiceException,Exception {

        List<AccordoServizioParteComune> list = new ArrayList<AccordoServizioParteComune>();
        
        // TODO: implementazione non efficente. 
		// Per ottenere una implementazione efficente:
		// 1. Usare metodo select di questa classe indirizzando esattamente i field necessari
		// 2. Usare metodo getAccordoServizioParteComuneFetch() sul risultato della select per ottenere un oggetto AccordoServizioParteComune
		//	  La fetch con la map inserirà nell'oggetto solo i valori estratti 

        List<Long> ids = this.findAllTableIds(jdbcProperties, log, connection, sqlQueryObject, expression);
        
        for(Long id: ids) {
        	list.add(this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour));
        }

        return list;      
		
	}
	
	@Override
	public AccordoServizioParteComune find(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) 
		throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException,Exception {

        long id = this.findTableId(jdbcProperties, log, connection, sqlQueryObject, expression);
        if(id>0){
        	return this.get(jdbcProperties, log, connection, sqlQueryObject, id, idMappingResolutionBehaviour);
        }else{
        	throw new NotFoundException("Entry with id["+id+"] not found");
        }
		
	}
	
	@Override
	public NonNegativeNumber count(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws NotImplementedException, ServiceException,Exception {
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareCount(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model());
		
		sqlQueryObject.addSelectCountField(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model())+".id","tot",true);
		
		_join(expression,sqlQueryObject);
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.count(jdbcProperties, log, connection, sqlQueryObject, expression,
																			this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(),listaQuery);
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdAccordoServizioParteComune id) throws NotFoundException, NotImplementedException, ServiceException,Exception {
		
		Long id_accordoServizioParteComune = this.findIdAccordoServizioParteComune(jdbcProperties, log, connection, sqlQueryObject, id, true);
        return this._inUse(jdbcProperties, log, connection, sqlQueryObject, id_accordoServizioParteComune);
		
	}

	@Override
	public List<Object> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, IField field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return this.select(jdbcProperties, log, connection, sqlQueryObject,
								paginatedExpression, false, field);
	}
	
	@Override
	public List<Object> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, boolean distinct, IField field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		List<Map<String,Object>> map = 
			this.select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression, distinct, new IField[]{field});
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.selectSingleObject(map);
	}
	
	@Override
	public List<Map<String,Object>> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, IField ... field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return this.select(jdbcProperties, log, connection, sqlQueryObject,
								paginatedExpression, false, field);
	}
	
	@Override
	public List<Map<String,Object>> select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, boolean distinct, IField ... field) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,paginatedExpression,field);
		try{
		
			ISQLQueryObject sqlQueryObjectDistinct = 
						org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(distinct,sqlQueryObject, paginatedExpression, log,
												this.getAccordoServizioParteComuneFieldConverter(), field);

			return _select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression, sqlQueryObjectDistinct);
			
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,paginatedExpression,field);
		}
	}

	@Override
	public Object aggregate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		Map<String,Object> map = 
			this.aggregate(jdbcProperties, log, connection, sqlQueryObject, expression, new FunctionField[]{functionField});
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.selectAggregateObject(map,functionField);
	}
	
	@Override
	public Map<String,Object> aggregate(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {													
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,expression,functionField);
		try{
			List<Map<String,Object>> list = _select(jdbcProperties, log, connection, sqlQueryObject, expression);
			return list.get(0);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,expression,functionField);
		}
	}

	@Override
	public List<Map<String,Object>> groupBy(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCExpression expression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		if(expression.getGroupByFields().size()<=0){
			throw new ServiceException("GroupBy conditions not found in expression");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,expression,functionField);
		try{
			return _select(jdbcProperties, log, connection, sqlQueryObject, expression);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,expression,functionField);
		}
	}
	

	@Override
	public List<Map<String,Object>> groupBy(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
													JDBCPaginatedExpression paginatedExpression, FunctionField ... functionField) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		if(paginatedExpression.getGroupByFields().size()<=0){
			throw new ServiceException("GroupBy conditions not found in expression");
		}
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.setFields(sqlQueryObject,paginatedExpression,functionField);
		try{
			return _select(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression);
		}finally{
			org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.removeFields(sqlQueryObject,paginatedExpression,functionField);
		}
	}
	
	protected List<Map<String,Object>> _select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												IExpression expression) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		return _select(jdbcProperties, log, connection, sqlQueryObject, expression, null);
	}
	protected List<Map<String,Object>> _select(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												IExpression expression, ISQLQueryObject sqlQueryObjectDistinct) throws ServiceException,NotFoundException,NotImplementedException,Exception {
		
		List<Object> listaQuery = new ArrayList<Object>();
		List<JDBCObject> listaParams = new ArrayList<JDBCObject>();
		List<Object> returnField = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSelect(jdbcProperties, log, connection, sqlQueryObject, 
        						expression, this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(), 
        						listaQuery,listaParams);
		
		_join(expression,sqlQueryObject);
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.select(jdbcProperties, log, connection,
        								org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareSqlQueryObjectForSelectDistinct(sqlQueryObject,sqlQueryObjectDistinct), 
        								expression, this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(),
        								listaQuery,listaParams,returnField);
		if(list!=null && list.size()>0){
			return list;
		}
		else{
			throw new NotFoundException("Not Found");
		}
	}
	
	@Override
	public List<Map<String,Object>> union(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												Union union, UnionExpression ... unionExpression) throws ServiceException,NotFoundException,NotImplementedException,Exception {		
		
		List<ISQLQueryObject> sqlQueryObjectInnerList = new ArrayList<ISQLQueryObject>();
		List<JDBCObject> jdbcObjects = new ArrayList<JDBCObject>();
		List<Class<?>> returnClassTypes = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareUnion(jdbcProperties, log, connection, sqlQueryObject, 
        						this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        List<Map<String,Object>> list = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.union(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(), 
        								sqlQueryObjectInnerList, jdbcObjects, returnClassTypes, union, unionExpression);
        if(list!=null && list.size()>0){
			return list;
		}
		else{
			throw new NotFoundException("Not Found");
		}								
	}
	
	@Override
	public NonNegativeNumber unionCount(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
												Union union, UnionExpression ... unionExpression) throws ServiceException,NotFoundException,NotImplementedException,Exception {		
		
		List<ISQLQueryObject> sqlQueryObjectInnerList = new ArrayList<ISQLQueryObject>();
		List<JDBCObject> jdbcObjects = new ArrayList<JDBCObject>();
		List<Class<?>> returnClassTypes = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareUnionCount(jdbcProperties, log, connection, sqlQueryObject, 
        						this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(), 
        						sqlQueryObjectInnerList, jdbcObjects, union, unionExpression);
		
		if(unionExpression!=null){
			for (int i = 0; i < unionExpression.length; i++) {
				UnionExpression ue = unionExpression[i];
				IExpression expression = ue.getExpression();
				_join(expression,sqlQueryObjectInnerList.get(i));
			}
		}
        
        NonNegativeNumber number = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.unionCount(jdbcProperties, log, connection, sqlQueryObject, 
        								this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(), 
        								sqlQueryObjectInnerList, jdbcObjects, returnClassTypes, union, unionExpression);
        if(number!=null && number.longValue()>=0){
			return number;
		}
		else{
			throw new NotFoundException("Not Found");
		}
	}



	// -- ConstructorExpression	

	@Override
	public JDBCExpression newExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCExpression(this.getAccordoServizioParteComuneFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}


	@Override
	public JDBCPaginatedExpression newPaginatedExpression(Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(this.getAccordoServizioParteComuneFieldConverter());
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public JDBCExpression toExpression(JDBCPaginatedExpression paginatedExpression, Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCExpression(paginatedExpression);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public JDBCPaginatedExpression toPaginatedExpression(JDBCExpression expression, Logger log) throws NotImplementedException, ServiceException {
		try{
			return new JDBCPaginatedExpression(expression);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
	
	// -- DB

	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdAccordoServizioParteComune id, AccordoServizioParteComune obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,id,null));
	}
	
	@Override
	public void mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, AccordoServizioParteComune obj) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		_mappingTableIds(jdbcProperties,log,connection,sqlQueryObject,obj,
				this.get(jdbcProperties,log,connection,sqlQueryObject,tableId,null));
	}
	private void _mappingTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, AccordoServizioParteComune obj, AccordoServizioParteComune imgSaved) throws NotFoundException,NotImplementedException,ServiceException,Exception{
		if(imgSaved==null){
			return;
		}
		obj.setId(imgSaved.getId());
		if(obj.getIdReferente()!=null && 
				imgSaved.getIdReferente()!=null){
			obj.getIdReferente().setId(imgSaved.getIdReferente().getId());
		}
		if(obj.getAccordoServizioParteComuneAzioneList()!=null){
			List<org.openspcoop2.core.commons.search.AccordoServizioParteComuneAzione> listObj_ = obj.getAccordoServizioParteComuneAzioneList();
			for(org.openspcoop2.core.commons.search.AccordoServizioParteComuneAzione itemObj_ : listObj_){
				org.openspcoop2.core.commons.search.AccordoServizioParteComuneAzione itemAlreadySaved_ = null;
				if(imgSaved.getAccordoServizioParteComuneAzioneList()!=null){
					List<org.openspcoop2.core.commons.search.AccordoServizioParteComuneAzione> listImgSaved_ = imgSaved.getAccordoServizioParteComuneAzioneList();
					for(org.openspcoop2.core.commons.search.AccordoServizioParteComuneAzione itemImgSaved_ : listImgSaved_){
						boolean objEqualsToImgSaved_ = false;
						// TODO verify equals
						// objEqualsToImgSaved_ = org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_.getXXX(),itemImgSaved_.getXXX()) &&
						// 						 			...
						//						 			org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_.getYYY(),itemImgSaved_.getYYY());
						if(objEqualsToImgSaved_){
							itemAlreadySaved_=itemImgSaved_;
							break;
						}
					}
				}
				if(itemAlreadySaved_!=null){
					itemObj_.setId(itemAlreadySaved_.getId());
					if(itemObj_.getIdAccordoServizioParteComune()!=null && 
							itemAlreadySaved_.getIdAccordoServizioParteComune()!=null){
						itemObj_.getIdAccordoServizioParteComune().setId(itemAlreadySaved_.getIdAccordoServizioParteComune().getId());
						if(itemObj_.getIdAccordoServizioParteComune().getIdSoggetto()!=null && 
								itemAlreadySaved_.getIdAccordoServizioParteComune().getIdSoggetto()!=null){
							itemObj_.getIdAccordoServizioParteComune().getIdSoggetto().setId(itemAlreadySaved_.getIdAccordoServizioParteComune().getIdSoggetto().getId());
						}
					}
				}
			}
		}
		if(obj.getPortTypeList()!=null){
			List<org.openspcoop2.core.commons.search.PortType> listObj_ = obj.getPortTypeList();
			for(org.openspcoop2.core.commons.search.PortType itemObj_ : listObj_){
				org.openspcoop2.core.commons.search.PortType itemAlreadySaved_ = null;
				if(imgSaved.getPortTypeList()!=null){
					List<org.openspcoop2.core.commons.search.PortType> listImgSaved_ = imgSaved.getPortTypeList();
					for(org.openspcoop2.core.commons.search.PortType itemImgSaved_ : listImgSaved_){
						boolean objEqualsToImgSaved_ = false;
						// TODO verify equals
						// objEqualsToImgSaved_ = org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_.getXXX(),itemImgSaved_.getXXX()) &&
						// 						 			...
						//						 			org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_.getYYY(),itemImgSaved_.getYYY());
						if(objEqualsToImgSaved_){
							itemAlreadySaved_=itemImgSaved_;
							break;
						}
					}
				}
				if(itemAlreadySaved_!=null){
					itemObj_.setId(itemAlreadySaved_.getId());
					if(itemObj_.getOperationList()!=null){
						List<org.openspcoop2.core.commons.search.Operation> listObj_portType = itemObj_.getOperationList();
						for(org.openspcoop2.core.commons.search.Operation itemObj_portType : listObj_portType){
							org.openspcoop2.core.commons.search.Operation itemAlreadySaved_portType = null;
							if(itemAlreadySaved_.getOperationList()!=null){
								List<org.openspcoop2.core.commons.search.Operation> listImgSaved_portType = itemAlreadySaved_.getOperationList();
								for(org.openspcoop2.core.commons.search.Operation itemImgSaved_portType : listImgSaved_portType){
									boolean objEqualsToImgSaved_portType = false;
									// TODO verify equals
									// objEqualsToImgSaved_portType = org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_portType.getXXX(),itemImgSaved_portType.getXXX()) &&
									// 						 			...
									//						 			org.openspcoop2.generic_project.utils.Utilities.equals(itemObj_portType.getYYY(),itemImgSaved_portType.getYYY());
									if(objEqualsToImgSaved_portType){
										itemAlreadySaved_portType=itemImgSaved_portType;
										break;
									}
								}
							}
							if(itemAlreadySaved_portType!=null){
								itemObj_portType.setId(itemAlreadySaved_portType.getId());
								if(itemObj_portType.getIdPortType()!=null && 
										itemAlreadySaved_portType.getIdPortType()!=null){
									itemObj_portType.getIdPortType().setId(itemAlreadySaved_portType.getIdPortType().getId());
									if(itemObj_portType.getIdPortType().getIdAccordoServizioParteComune()!=null && 
											itemAlreadySaved_portType.getIdPortType().getIdAccordoServizioParteComune()!=null){
										itemObj_portType.getIdPortType().getIdAccordoServizioParteComune().setId(itemAlreadySaved_portType.getIdPortType().getIdAccordoServizioParteComune().getId());
										if(itemObj_portType.getIdPortType().getIdAccordoServizioParteComune().getIdSoggetto()!=null && 
												itemAlreadySaved_portType.getIdPortType().getIdAccordoServizioParteComune().getIdSoggetto()!=null){
											itemObj_portType.getIdPortType().getIdAccordoServizioParteComune().getIdSoggetto().setId(itemAlreadySaved_portType.getIdPortType().getIdAccordoServizioParteComune().getIdSoggetto().getId());
										}
									}
								}
							}
						}
					}
					if(itemObj_.getIdAccordoServizioParteComune()!=null && 
							itemAlreadySaved_.getIdAccordoServizioParteComune()!=null){
						itemObj_.getIdAccordoServizioParteComune().setId(itemAlreadySaved_.getIdAccordoServizioParteComune().getId());
						if(itemObj_.getIdAccordoServizioParteComune().getIdSoggetto()!=null && 
								itemAlreadySaved_.getIdAccordoServizioParteComune().getIdSoggetto()!=null){
							itemObj_.getIdAccordoServizioParteComune().getIdSoggetto().setId(itemAlreadySaved_.getIdAccordoServizioParteComune().getIdSoggetto().getId());
						}
					}
				}
			}
		}

		/* 
         * TODO: implement code for id mapping
        */

        // Delete this line when you have implemented the method
        int throwNotImplemented = 1;
        if(throwNotImplemented==1){
                throw new NotImplementedException("NotImplemented");
        }
        // Delete this line when you have implemented the method                
	}
	
	@Override
	public AccordoServizioParteComune get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._get(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId), idMappingResolutionBehaviour);
	}
	
	private AccordoServizioParteComune _get(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId, org.openspcoop2.generic_project.beans.IDMappingBehaviour idMappingResolutionBehaviour) throws NotFoundException, MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
		
		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();
				
		AccordoServizioParteComune accordoServizioParteComune = new AccordoServizioParteComune();
		

		// Object accordoServizioParteComune
		ISQLQueryObject sqlQueryObjectGet_accordoServizioParteComune = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_accordoServizioParteComune.setANDLogicOperator(true);
		sqlQueryObjectGet_accordoServizioParteComune.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model()));
		sqlQueryObjectGet_accordoServizioParteComune.addSelectField("id");
		sqlQueryObjectGet_accordoServizioParteComune.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().NOME,true));
		sqlQueryObjectGet_accordoServizioParteComune.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().VERSIONE,true));
		sqlQueryObjectGet_accordoServizioParteComune.addWhereCondition("id=?");

		// Get accordoServizioParteComune
		accordoServizioParteComune = (AccordoServizioParteComune) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_accordoServizioParteComune.createSQLQuery(), jdbcProperties.isShowSql(), AccordoServizioParteComune.model(), this.getAccordoServizioParteComuneFetch(),
			new JDBCObject(tableId,Long.class));


		if(idMappingResolutionBehaviour==null ||
			(org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour) || org.openspcoop2.generic_project.beans.IDMappingBehaviour.USE_TABLE_ID.equals(idMappingResolutionBehaviour))
		){
			// Object _accordoServizioParteComune_soggetto (recupero id)
			ISQLQueryObject sqlQueryObjectGet_accordoServizioParteComune_soggetto_readFkId = sqlQueryObjectGet.newSQLQueryObject();
			sqlQueryObjectGet_accordoServizioParteComune_soggetto_readFkId.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(org.openspcoop2.core.commons.search.AccordoServizioParteComune.model().PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE));
			sqlQueryObjectGet_accordoServizioParteComune_soggetto_readFkId.addSelectField("id_referente");
			sqlQueryObjectGet_accordoServizioParteComune_soggetto_readFkId.addWhereCondition("id=?");
			sqlQueryObjectGet_accordoServizioParteComune_soggetto_readFkId.setANDLogicOperator(true);
			Long idFK_accordoServizioParteComune_soggetto = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet_accordoServizioParteComune_soggetto_readFkId.createSQLQuery(), jdbcProperties.isShowSql(),Long.class,
					new JDBCObject(accordoServizioParteComune.getId(),Long.class));
			
			org.openspcoop2.core.commons.search.IdSoggetto id_accordoServizioParteComune_soggetto = null;
			if(idMappingResolutionBehaviour==null || org.openspcoop2.generic_project.beans.IDMappingBehaviour.ENABLED.equals(idMappingResolutionBehaviour)){
				id_accordoServizioParteComune_soggetto = ((JDBCSoggettoServiceSearch)(this.getServiceManager().getSoggettoServiceSearch())).findId(idFK_accordoServizioParteComune_soggetto, false);
			}else{
				id_accordoServizioParteComune_soggetto = new org.openspcoop2.core.commons.search.IdSoggetto();
			}
			id_accordoServizioParteComune_soggetto.setId(idFK_accordoServizioParteComune_soggetto);
			//TODO Impostare il corretto metodo che contiene l'identificativo logico
			//accordoServizioParteComune.setSoggetto(id_accordoServizioParteComune_soggetto);
		}


		// Object accordoServizioParteComune_accordoServizioParteComuneAzione
		ISQLQueryObject sqlQueryObjectGet_accordoServizioParteComune_accordoServizioParteComuneAzione = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_accordoServizioParteComune_accordoServizioParteComuneAzione.setANDLogicOperator(true);
		sqlQueryObjectGet_accordoServizioParteComune_accordoServizioParteComuneAzione.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE));
		sqlQueryObjectGet_accordoServizioParteComune_accordoServizioParteComuneAzione.addSelectField("id");
		sqlQueryObjectGet_accordoServizioParteComune_accordoServizioParteComuneAzione.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE.NOME,true));
		sqlQueryObjectGet_accordoServizioParteComune_accordoServizioParteComuneAzione.addWhereCondition("id_accordo=?");

		// Get accordoServizioParteComune_accordoServizioParteComuneAzione
		java.util.List<Object> accordoServizioParteComune_accordoServizioParteComuneAzione_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_accordoServizioParteComune_accordoServizioParteComuneAzione.createSQLQuery(), jdbcProperties.isShowSql(), AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE, this.getAccordoServizioParteComuneFetch(),
			new JDBCObject(accordoServizioParteComune.getId(),Long.class));

		if(accordoServizioParteComune_accordoServizioParteComuneAzione_list != null) {
			for (Object accordoServizioParteComune_accordoServizioParteComuneAzione_object: accordoServizioParteComune_accordoServizioParteComuneAzione_list) {
				AccordoServizioParteComuneAzione accordoServizioParteComune_accordoServizioParteComuneAzione = (AccordoServizioParteComuneAzione) accordoServizioParteComune_accordoServizioParteComuneAzione_object;


				accordoServizioParteComune.addAccordoServizioParteComuneAzione(accordoServizioParteComune_accordoServizioParteComuneAzione);
			}
		}

		// Object accordoServizioParteComune_portType
		ISQLQueryObject sqlQueryObjectGet_accordoServizioParteComune_portType = sqlQueryObjectGet.newSQLQueryObject();
		sqlQueryObjectGet_accordoServizioParteComune_portType.setANDLogicOperator(true);
		sqlQueryObjectGet_accordoServizioParteComune_portType.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model().PORT_TYPE));
		sqlQueryObjectGet_accordoServizioParteComune_portType.addSelectField("id");
		sqlQueryObjectGet_accordoServizioParteComune_portType.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().PORT_TYPE.NOME,true));
		sqlQueryObjectGet_accordoServizioParteComune_portType.addWhereCondition("id_accordo=?");

		// Get accordoServizioParteComune_portType
		java.util.List<Object> accordoServizioParteComune_portType_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_accordoServizioParteComune_portType.createSQLQuery(), jdbcProperties.isShowSql(), AccordoServizioParteComune.model().PORT_TYPE, this.getAccordoServizioParteComuneFetch(),
			new JDBCObject(accordoServizioParteComune.getId(),Long.class));

		if(accordoServizioParteComune_portType_list != null) {
			for (Object accordoServizioParteComune_portType_object: accordoServizioParteComune_portType_list) {
				PortType accordoServizioParteComune_portType = (PortType) accordoServizioParteComune_portType_object;



				// Object accordoServizioParteComune_portType_operation
				ISQLQueryObject sqlQueryObjectGet_accordoServizioParteComune_portType_operation = sqlQueryObjectGet.newSQLQueryObject();
				sqlQueryObjectGet_accordoServizioParteComune_portType_operation.setANDLogicOperator(true);
				sqlQueryObjectGet_accordoServizioParteComune_portType_operation.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION));
				sqlQueryObjectGet_accordoServizioParteComune_portType_operation.addSelectField("id");
				sqlQueryObjectGet_accordoServizioParteComune_portType_operation.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().PORT_TYPE.OPERATION.NOME,true));
				sqlQueryObjectGet_accordoServizioParteComune_portType_operation.addWhereCondition("id_port_type=?");

				// Get accordoServizioParteComune_portType_operation
				java.util.List<Object> accordoServizioParteComune_portType_operation_list = (java.util.List<Object>) jdbcUtilities.executeQuery(sqlQueryObjectGet_accordoServizioParteComune_portType_operation.createSQLQuery(), jdbcProperties.isShowSql(), AccordoServizioParteComune.model().PORT_TYPE.OPERATION, this.getAccordoServizioParteComuneFetch(),
					new JDBCObject(accordoServizioParteComune_portType.getId(),Long.class));

				if(accordoServizioParteComune_portType_operation_list != null) {
					for (Object accordoServizioParteComune_portType_operation_object: accordoServizioParteComune_portType_operation_list) {
						Operation accordoServizioParteComune_portType_operation = (Operation) accordoServizioParteComune_portType_operation_object;


						accordoServizioParteComune_portType.addOperation(accordoServizioParteComune_portType_operation);
					}
				}
				accordoServizioParteComune.addPortType(accordoServizioParteComune_portType);
			}
		}

		/* 
		 * TODO: implement code that returns the object identified by the id
		*/
		
		// Delete this line when you have implemented the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
		// Delete this line when you have implemented the method                
		
        return accordoServizioParteComune;  
	
	} 
	
	@Override
	public boolean exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
		return this._exists(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}
	
	private boolean _exists(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws MultipleResultException, NotImplementedException, ServiceException, Exception {
	
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
					new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);
				
		boolean existsAccordoServizioParteComune = false;

		sqlQueryObject = sqlQueryObject.newSQLQueryObject();
		sqlQueryObject.setANDLogicOperator(true);

		sqlQueryObject.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model()));
		sqlQueryObject.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().NOME,true));
		sqlQueryObject.addWhereCondition("id=?");


		// Exists accordoServizioParteComune
		existsAccordoServizioParteComune = jdbcUtilities.exists(sqlQueryObject.createSQLQuery(), jdbcProperties.isShowSql(),
			new JDBCObject(tableId,Long.class));

		
        return existsAccordoServizioParteComune;
	
	}
	
	private void _join(IExpression expression, ISQLQueryObject sqlQueryObject) throws NotImplementedException, ServiceException, Exception{
	
		/* 
		 * TODO: implement code that implement the join condition
		*/
		/*
		if(expression.inUseModel(AccordoServizioParteComune.model().XXXX,false)){
			String tableName1 = this.getAccordoServizioParteComuneFieldConverter().toAliasTable(AccordoServizioParteComune.model());
			String tableName2 = this.getAccordoServizioParteComuneFieldConverter().toAliasTable(AccordoServizioParteComune.model().XXX);
			sqlQueryObject.addWhereCondition(tableName1+".id="+tableName2+".id_table1");
		}
		*/
		
		/* 
         * TODO: implementa il codice che aggiunge la condizione FROM Table per le condizioni di join di oggetti annidati dal secondo livello in poi 
         *       La addFromTable deve essere aggiunta solo se l'oggetto del livello precedente non viene utilizzato nella espressione 
         *		 altrimenti il metodo sopra 'toSqlForPreparedStatementWithFromCondition' si occupa gia' di aggiungerla
        */
        /*
        if(expression.inUseModel(AccordoServizioParteComune.model().LEVEL1.LEVEL2,false)){
			if(expression.inUseModel(AccordoServizioParteComune.model().LEVEL1,false)==false){
				sqlQueryObject.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model().LEVEL1));
			}
		}
		...
		if(expression.inUseModel(AccordoServizioParteComune.model()....LEVELN.LEVELN+1,false)){
			if(expression.inUseModel(AccordoServizioParteComune.model().LEVELN,false)==false){
				sqlQueryObject.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model().LEVELN));
			}
		}
		*/
		
		// Delete this line when you have implemented the join condition
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
		// Delete this line when you have implemented the join condition
        
	}
	
	protected java.util.List<Object> _getRootTablePrimaryKeyValues(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdAccordoServizioParteComune id) throws NotFoundException, ServiceException, NotImplementedException, Exception{
	    // Identificativi
        java.util.List<Object> rootTableIdValues = new java.util.ArrayList<Object>();
        // TODO: Define the column values used to identify the primary key
		Long longId = this.findIdAccordoServizioParteComune(jdbcProperties, log, connection, sqlQueryObject.newSQLQueryObject(), id, true);
		rootTableIdValues.add(longId);
        
        // Delete this line when you have verified the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
		// Delete this line when you have verified the method
        
        return rootTableIdValues;
	}
	
	protected Map<String, List<IField>> _getMapTableToPKColumn() throws NotImplementedException, Exception{
	
		AccordoServizioParteComuneFieldConverter converter = this.getAccordoServizioParteComuneFieldConverter();
		Map<String, List<IField>> mapTableToPKColumn = new java.util.Hashtable<String, List<IField>>();
		UtilsTemplate<IField> utilities = new UtilsTemplate<IField>();

		// TODO: Define the columns used to identify the primary key
		//		  If a table doesn't have a primary key, don't add it to this map

		// AccordoServizioParteComune.model()
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model()),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model()))
			));

		// AccordoServizioParteComune.model().ID_REFERENTE
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().ID_REFERENTE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().ID_REFERENTE))
			));

		// AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE))
			));

		// AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE))
			));

		// AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().ACCORDO_SERVIZIO_PARTE_COMUNE_AZIONE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO))
			));

		// AccordoServizioParteComune.model().PORT_TYPE
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().PORT_TYPE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().PORT_TYPE))
			));

		// AccordoServizioParteComune.model().PORT_TYPE.OPERATION
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION))
			));

		// AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE))
			));

		// AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE))
			));

		// AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.OPERATION.ID_PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO))
			));

		// AccordoServizioParteComune.model().PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE))
			));

		// AccordoServizioParteComune.model().PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO
		mapTableToPKColumn.put(converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO),
			utilities.newList(
				new CustomField("id", Long.class, "id", converter.toTable(AccordoServizioParteComune.model().PORT_TYPE.ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO))
			));


        // Delete this line when you have verified the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
		// Delete this line when you have verified the method
        
        return mapTableToPKColumn;		
	}
	
	@Override
	public List<Long> findAllTableIds(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCPaginatedExpression paginatedExpression) throws ServiceException, NotImplementedException, Exception {
		
		List<Long> list = new ArrayList<Long>();

		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFindAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
												this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model());
		
		_join(paginatedExpression,sqlQueryObject);
		
		List<Object> listObjects = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.findAll(jdbcProperties, log, connection, sqlQueryObject, paginatedExpression,
																			this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(), objectIdClass, listaQuery);
		for(Object object: listObjects) {
			list.add((Long)object);
		}

        return list;
		
	}
	
	@Override
	public long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, JDBCExpression expression) throws ServiceException, NotFoundException, MultipleResultException, NotImplementedException, Exception {
	
		sqlQueryObject.setSelectDistinct(true);
		sqlQueryObject.setANDLogicOperator(true);
		sqlQueryObject.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model())+".id");
		Class<?> objectIdClass = Long.class;
		
		List<Object> listaQuery = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.prepareFind(jdbcProperties, log, connection, sqlQueryObject, expression,
												this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model());
		
		_join(expression,sqlQueryObject);

		Object res = org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.find(jdbcProperties, log, connection, sqlQueryObject, expression,
														this.getAccordoServizioParteComuneFieldConverter(), AccordoServizioParteComune.model(), objectIdClass, listaQuery);
		if(res!=null && (((Long) res).longValue()>0) ){
			return ((Long) res).longValue();
		}
		else{
			throw new NotFoundException("Not Found");
		}
		
	}

	@Override
	public InUse inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId) throws ServiceException, NotFoundException, NotImplementedException, Exception {
		return this._inUse(jdbcProperties, log, connection, sqlQueryObject, Long.valueOf(tableId));
	}

	private InUse _inUse(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, Long tableId) throws ServiceException, NotFoundException, NotImplementedException, Exception {

		InUse inUse = new InUse();
		inUse.setInUse(false);
		
		/* 
		 * TODO: implement code that checks whether the object identified by the id parameter is used by other objects
		*/
		
		// Delete this line when you have implemented the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
		// Delete this line when you have implemented the method

        return inUse;

	}
	
	@Override
	public IdAccordoServizioParteComune findId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, long tableId, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
		
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		/* 
		 * TODO: implement code that returns the object identified by the id
		*/

		// Delete this line when you have implemented the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
 		// Delete this line when you have implemented the method                

		// Object _accordoServizioParteComune
		//TODO Implementare la ricerca dell'id
		sqlQueryObjectGet.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model()));
		// TODO select field for identify ObjectId
		//sqlQueryObjectGet.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().NOME_COLONNA_1,true));
		//...
		//sqlQueryObjectGet.addSelectField(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().NOME_COLONNA_N,true));
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.addWhereCondition("id=?");

		// Recupero _accordoServizioParteComune
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_accordoServizioParteComune = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(tableId,Long.class)
		};
		List<Class<?>> listaFieldIdReturnType_accordoServizioParteComune = new ArrayList<Class<?>>();
		//listaFieldIdReturnType_accordoServizioParteComune.add(Id1.class);
		//...
		//listaFieldIdReturnType_accordoServizioParteComune.add(IdN.class);
		org.openspcoop2.core.commons.search.IdAccordoServizioParteComune id_accordoServizioParteComune = null;
		List<Object> listaFieldId_accordoServizioParteComune = jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
				listaFieldIdReturnType_accordoServizioParteComune, searchParams_accordoServizioParteComune);
		if(listaFieldId_accordoServizioParteComune==null || listaFieldId_accordoServizioParteComune.size()<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		else{
			// set _accordoServizioParteComune
			id_accordoServizioParteComune = new org.openspcoop2.core.commons.search.IdAccordoServizioParteComune();
			// id_accordoServizioParteComune.setId1(listaFieldId_accordoServizioParteComune.get(0));
			// ...
			// id_accordoServizioParteComune.setIdN(listaFieldId_accordoServizioParteComune.get(N-1));
		}
		
		return id_accordoServizioParteComune;
		
	}

	@Override
	public Long findTableId(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdAccordoServizioParteComune id, boolean throwNotFound)
			throws NotFoundException, ServiceException, NotImplementedException, Exception {
	
		return this.findIdAccordoServizioParteComune(jdbcProperties,log,connection,sqlQueryObject,id,throwNotFound);
			
	}
	
	@Override
	public List<List<Object>> nativeQuery(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, 
											String sql,List<Class<?>> returnClassTypes,Object ... param) throws ServiceException,NotFoundException,NotImplementedException,Exception{
		
		return org.openspcoop2.generic_project.dao.jdbc.utils.JDBCUtilities.nativeQuery(jdbcProperties, log, connection, sqlQueryObject,
																							sql,returnClassTypes,param);
														
	}
	
	protected Long findIdAccordoServizioParteComune(JDBCServiceManagerProperties jdbcProperties, Logger log, Connection connection, ISQLQueryObject sqlQueryObject, IdAccordoServizioParteComune id, boolean throwNotFound) throws NotFoundException, ServiceException, NotImplementedException, Exception {

		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities jdbcUtilities = 
			new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCPreparedStatementUtilities(sqlQueryObject.getTipoDatabaseOpenSPCoop2(), log, connection);

		ISQLQueryObject sqlQueryObjectGet = sqlQueryObject.newSQLQueryObject();

		/* 
		 * TODO: implement code that returns the object identified by the id
		*/

		// Delete this line when you have implemented the method
		int throwNotImplemented = 1;
		if(throwNotImplemented==1){
		        throw new NotImplementedException("NotImplemented");
		}
 		// Delete this line when you have implemented the method                

		// Object _accordoServizioParteComune
		//TODO Implementare la ricerca dell'id
		sqlQueryObjectGet.addFromTable(this.getAccordoServizioParteComuneFieldConverter().toTable(AccordoServizioParteComune.model()));
		sqlQueryObjectGet.addSelectField("id");
		// Devono essere mappati nella where condition i metodi dell'oggetto id.getXXX
		sqlQueryObjectGet.setANDLogicOperator(true);
		sqlQueryObjectGet.setSelectDistinct(true);
		//sqlQueryObjectGet.addWhereCondition(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().NOME_COLONNA_1,true)+"=?");
		// ...
		//sqlQueryObjectGet.addWhereCondition(this.getAccordoServizioParteComuneFieldConverter().toColumn(AccordoServizioParteComune.model().NOME_COLONNA_N,true)+"=?");

		// Recupero _accordoServizioParteComune
		// TODO Aggiungere i valori dei parametri di ricerca sopra definiti recuperandoli con i metodi dell'oggetto id.getXXX
		org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] searchParams_accordoServizioParteComune = new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject [] { 
			//new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(object,object.class),
			//...
			//new org.openspcoop2.generic_project.dao.jdbc.utils.JDBCObject(object,object.class)
		};
		Long id_accordoServizioParteComune = null;
		try{
			id_accordoServizioParteComune = (Long) jdbcUtilities.executeQuerySingleResult(sqlQueryObjectGet.createSQLQuery(), jdbcProperties.isShowSql(),
						Long.class, searchParams_accordoServizioParteComune);
		}catch(NotFoundException notFound){
			if(throwNotFound){
				throw new NotFoundException(notFound);
			}
		}
		if(id_accordoServizioParteComune==null || id_accordoServizioParteComune<=0){
			if(throwNotFound){
				throw new NotFoundException("Not Found");
			}
		}
		
		return id_accordoServizioParteComune;
	}
}
