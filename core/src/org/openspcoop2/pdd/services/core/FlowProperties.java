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
package org.openspcoop2.pdd.services.core;

import org.openspcoop2.pdd.config.MTOMProcessorConfig;
import org.openspcoop2.pdd.config.MessageSecurityConfig;
import org.openspcoop2.protocol.sdk.constants.RuoloMessaggio;

/**
 * FlowProperties
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class FlowProperties{
	
	public MessageSecurityConfig messageSecurity = null;
	public MTOMProcessorConfig mtom = null;
	public RuoloMessaggio tipoMessaggio = null;
	
}