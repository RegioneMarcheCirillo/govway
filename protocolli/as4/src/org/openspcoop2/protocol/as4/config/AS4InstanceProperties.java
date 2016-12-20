/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it). 
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


package org.openspcoop2.protocol.as4.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.openspcoop2.protocol.as4.constants.AS4Costanti;
import org.openspcoop2.utils.properties.InstanceProperties;


/**
* AS4InstanceProperties
*
* @author Andrea Poli <apoli@link.it>
 * @author $Author: apoli $
 * @version $Rev: 12359 $, $Date: 2016-11-18 17:24:57 +0100 (Fri, 18 Nov 2016) $
*/
public class AS4InstanceProperties extends InstanceProperties {

	AS4InstanceProperties(Properties reader,Logger log) throws Exception{
		super(AS4Costanti.OPENSPCOOP2_LOCAL_HOME,reader, log);
		
		// Leggo directory di configurazione
		String confDir = super.getValue("org.openspcoop2.protocol.as4.confDirectory");
		
		super.setLocalFileImplementation(AS4Costanti.AS4_PROPERTIES,AS4Costanti.AS4_PROPERTIES_LOCAL_PATH, confDir);
		
	}
}
