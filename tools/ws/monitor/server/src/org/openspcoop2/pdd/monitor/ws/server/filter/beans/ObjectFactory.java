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
package org.openspcoop2.pdd.monitor.ws.server.filter.beans;

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
     * Create an instance of {@link BustaServizio }
     */
    public BustaServizio createBustaServizio() {
        return new BustaServizio();
    }
    
    /**
     * Create an instance of {@link Busta }
     */
    public Busta createBusta() {
        return new Busta();
    }
    
    /**
     * Create an instance of {@link Filtro }
     */
    public Filtro createFiltro() {
        return new Filtro();
    }
    
    /**
     * Create an instance of {@link BustaSoggetto }
     */
    public BustaSoggetto createBustaSoggetto() {
        return new BustaSoggetto();
    }
    

}