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

package org.openspcoop2.utils.rest.api;

import org.openspcoop2.utils.beans.BaseBean;

/**
 * ApiSchema
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 13343 $, $Date: 2017-10-09 14:13:31 +0200 (Mon, 09 Oct 2017) $
 */
public class ApiSchema extends BaseBean {
	
	private String name;
	private byte[] content;
	private ApiSchemaType type;
	
	public ApiSchema(String name,byte[] content,ApiSchemaType type) {
		this.name = name;
		this.content = content;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	public byte[] getContent() {
		return this.content;
	}
	public ApiSchemaType getType() {
		return this.type;
	}
	
}