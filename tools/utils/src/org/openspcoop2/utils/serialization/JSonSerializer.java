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


package org.openspcoop2.utils.serialization;

import java.io.OutputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**	
 * Contiene utility per effettuare la serializzazione di un oggetto
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class JSonSerializer implements ISerializer {

	private JsonConfig jsonConfig;
	
	public JSonSerializer(Filter filter){
		
		this(filter,null,null);
	}
	public JSonSerializer(Filter filter,IDBuilder idBuilder){
		this(filter,idBuilder,null);
	}
	public JSonSerializer(Filter filter,String [] excludes){
		this(filter,null,excludes);
	}
	public JSonSerializer(Filter filter,IDBuilder idBuilder, String [] excludes){
		
		JSonSerializer filtroInternoOggettiFiltratiDiversiByteArray = null;
		if(filter.sizeFiltersByValue()>0 || filter.sizeFiltersByName()>0)
			filtroInternoOggettiFiltratiDiversiByteArray = new JSonSerializer(new Filter(),idBuilder,excludes);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(filter,idBuilder,filtroInternoOggettiFiltratiDiversiByteArray));
		if(excludes!=null){
			jsonConfig.setExcludes(excludes);
		}
		this.jsonConfig = jsonConfig;
	}
	
	
	@Override
	public String getObject(Object o) throws IOException{
		try{
			Utilities.normalizeDateObjects(o);
			if( (o instanceof Enum) || (o != null && o.getClass().isArray()) || (o instanceof List) || (o instanceof Set) ){
				JSONArray jsonObject = JSONArray.fromObject( o , this.jsonConfig );
				return jsonObject.toString();
			}
			else if((o instanceof Annotation) || o != null && o.getClass().isAnnotation())
				throw new IOException("'object' is an Annotation.");
			else {
				JSONObject jsonObject = JSONObject.fromObject( o , this.jsonConfig );
				return jsonObject.toString();
			}
			
		}catch(Exception e){
			throw new IOException(e.getMessage(),e);
		}
	}
	
	@Override
	public void writeObject(Object o,OutputStream out) throws IOException{
		try{
			Utilities.normalizeDateObjects(o);
			String s = this.getObject(o);
			out.write(s.getBytes());
		}catch(Exception e){
			throw new IOException(e.getMessage(),e);
		}
	}
	
	@Override
	public void writeObject(Object o,Writer out) throws IOException{
		try{
			Utilities.normalizeDateObjects(o);
			String s = this.getObject(o);
			out.write(s);
		}catch(Exception e){
			throw new IOException(e.getMessage(),e);
		}
	}

}
