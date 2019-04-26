/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it).
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
package org.openspcoop2.core.monitor.rs.server.api.impl.utils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.openspcoop2.utils.transport.http.WrappedHttpServletRequest;

/**
 * HttpRequestWrapper
 * 
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class HttpRequestWrapper extends WrappedHttpServletRequest  {

	private HashMap<String,String> overrides = new HashMap<>();
	private HashMap<String,String[]> overridesValues = new HashMap<>();
	
	@Override
	public String getParameter(String key) {
		if (this.overrides.get(key) != null) {
			return this.overrides.get(key);
		}
		
		return super.getParameter(key);
	}
	
	public void overrideParameter(String key, Object value) {
		if (value == null)
			this.overrides.put(key, null);
		else
			this.overrides.put(key, value.toString());
	}
	
	public void overrideParameterValues(String key, String[] values) {
		this.overridesValues.put(key, values);
	}
	
	@Override
	public String[] getParameterValues(String arg0) {
		if ( this.overridesValues.get(arg0) != null)
			return this.overridesValues.get(arg0);
		
		return super.getParameterValues(arg0);
	}

	public HttpRequestWrapper(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);
	}

}