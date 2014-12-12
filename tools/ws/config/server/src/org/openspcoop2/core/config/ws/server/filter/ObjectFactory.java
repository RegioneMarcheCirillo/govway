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
package org.openspcoop2.core.config.ws.server.filter;

import javax.xml.bind.annotation.XmlRegistry;




/**     
 * ObjectFactory
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
 @XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openspcoop2.core.tracciamento
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchFilterPortaApplicativa }
     */
    public SearchFilterPortaApplicativa createSearchFilterPortaApplicativa() {
        return new SearchFilterPortaApplicativa();
    }
    
    /**
     * Create an instance of {@link SearchFilterSoggetto }
     */
    public SearchFilterSoggetto createSearchFilterSoggetto() {
        return new SearchFilterSoggetto();
    }
    
    /**
     * Create an instance of {@link SearchFilterServizioApplicativo }
     */
    public SearchFilterServizioApplicativo createSearchFilterServizioApplicativo() {
        return new SearchFilterServizioApplicativo();
    }
    
    /**
     * Create an instance of {@link SearchFilterPortaDelegata }
     */
    public SearchFilterPortaDelegata createSearchFilterPortaDelegata() {
        return new SearchFilterPortaDelegata();
    }
    

}